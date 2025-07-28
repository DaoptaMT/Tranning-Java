package com.mt.pharmacy_be.config;

import com.mt.pharmacy_be.batch.listener.MedicineJobCompletionNotificationListener;
import com.mt.pharmacy_be.batch.processor.MedicineCsvItemProcessor;
import com.mt.pharmacy_be.dto.medicineDTO.MedicineCsvDTO;
import com.mt.pharmacy_be.entity.Medicine;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import java.net.BindException;

/**
 * Configuration class for batch processing of medicine data from CSV files.
 * This class sets up the job, step, reader, processor, and writer for importing medicine data.
 * Author: Thanh Truc
 * Date: 28/07/2024
 * Description: This configuration handles the reading of CSV files, processing of medicine data,
 * writing to the database, and managing transactions.
 */
@Configuration
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
public class MedicineBatchConfig {

    JobRepository jobRepository;
    PlatformTransactionManager transactionManager;
    MedicineCsvItemProcessor medicineCsvItemProcessor;
    MedicineJobCompletionNotificationListener listener;
    EntityManagerFactory entityManagerFactory;

    @Bean
    @StepScope
    public FlatFileItemReader<MedicineCsvDTO> medicineReader(
            @Value("#{jobParameters['filePath']}") String filePath
    ) {
        log.info("Setting up reader for file: {}", filePath);
        FlatFileItemReader<MedicineCsvDTO> itemReader = new FlatFileItemReader<>();
        itemReader.setResource(new FileSystemResource(filePath));
        itemReader.setLinesToSkip(1);
        itemReader.setLineMapper(lineMapper());
        return itemReader;
    }

    private LineMapper<MedicineCsvDTO> lineMapper() {
        DefaultLineMapper<MedicineCsvDTO> lineMapper = new DefaultLineMapper<>();

        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames("name", "price", "quantity", "vat", "note", "maker", "origin", "retail_profit",
                "kind_of_medicine_id", "active_element");

        BeanWrapperFieldSetMapper<MedicineCsvDTO> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(MedicineCsvDTO.class);

        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);

        return lineMapper;
    }

    @Bean
    public JpaItemWriter<Medicine> medicineJpaItemWriter(EntityManagerFactory entityManagerFactory) {
        JpaItemWriter<Medicine> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        return writer;
    }

    @Bean
    public TaskExecutor batchTaskExecutor() {
        SimpleAsyncTaskExecutor taskExecutor = new SimpleAsyncTaskExecutor("MedicineBatchTaskExecutor");
        taskExecutor.setConcurrencyLimit(10);
        return taskExecutor;
    }

    @Bean
    public Step importMedicineStep(FlatFileItemReader<MedicineCsvDTO> medicineReader) {
        return new StepBuilder("importMedicineStep", jobRepository)
                .<MedicineCsvDTO, Medicine>chunk(50, transactionManager)
                .reader(medicineReader)
                .processor(medicineCsvItemProcessor)
                .writer(medicineJpaItemWriter(entityManagerFactory))
                .faultTolerant()
                .skip(FlatFileParseException.class) // Ignore errors when parsing lines
                .skip(BindException.class) // Ignore binding errors to DTO
                .skipLimit(100) // Allow up to 100 skips
                .taskExecutor(batchTaskExecutor())
                .build();
    }

    @Bean
    public Job importMedicineJob(Step importMedicineStep) {
        return new JobBuilder("importMedicineJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(importMedicineStep)
                .end()
                .build();
    }
}
