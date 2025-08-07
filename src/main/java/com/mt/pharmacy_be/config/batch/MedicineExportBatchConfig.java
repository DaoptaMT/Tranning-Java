package com.mt.pharmacy_be.config.batch;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mt.pharmacy_be.batch.listener.ExportJobListener;
import com.mt.pharmacy_be.dto.medicineDTO.MedicineSearchRequestDTO;
import com.mt.pharmacy_be.entity.MedicineEntity;
import com.mt.pharmacy_be.repository.MedicineRepository;
import com.mt.pharmacy_be.util.MedicineSpecificationFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;

/**
 * Configuration class for the Medicine Export Batch Job.
 * Author: Thanh Truc
 * Date: 05/08/2025
 * Description: This configuration defines the job, step, reader, writer, and task executor
 */
@Configuration
@Slf4j
public class MedicineExportBatchConfig {

    @Bean
    public Job medicineExportJob(JobRepository jobRepository, Step exportStep) {
        return new JobBuilder("medicineExportJob", jobRepository)
                .start(exportStep)
                .listener(new ExportJobListener())
                .build();
    }

    @Bean
    public Step exportStep(JobRepository jobRepository, PlatformTransactionManager transactionManager,
                           RepositoryItemReader<MedicineEntity> medicineExportReader,
                           FlatFileItemWriter<MedicineEntity> csvMedicineWriter) {
        return new StepBuilder("exportStep", jobRepository)
                .<MedicineEntity, MedicineEntity>chunk(500, transactionManager)
                .reader(medicineExportReader)
                .writer(csvMedicineWriter)
                .taskExecutor(batchExportTaskExecutor())
                .build();
    }

    @Bean
    @StepScope
    public RepositoryItemReader<MedicineEntity> dynamicMedicineExportReader(
            @Value("#{jobParameters['exportType']}") String exportType,
            @Value("#{jobParameters['searchCriteria']}") String searchCriteriaJson,
            @Value("#{jobParameters['page']}") Long page,
            @Value("#{jobParameters['pageSize']}") Long pageSize,
            ObjectMapper objectMapper,
            MedicineSpecificationFactory specificationFactory,
            MedicineRepository medicineRepository
    ) throws JsonProcessingException {
        Specification<MedicineEntity> specification = (root, query, cb)
                -> cb.conjunction();

        if ("filtered".equalsIgnoreCase(exportType) && StringUtils.hasText(searchCriteriaJson)) {
            MedicineSearchRequestDTO request = objectMapper.readValue(searchCriteriaJson, MedicineSearchRequestDTO.class);
            specification = specificationFactory.buildMedicineSpecification(request);
        }

        RepositoryItemReader<MedicineEntity> reader = new RepositoryItemReader<>();
        reader.setRepository(medicineRepository);
        reader.setMethodName("findAll");
        reader.setArguments(List.of(specification));
        reader.setSort(Collections.singletonMap("id", Sort.Direction.ASC));
        reader.setPageSize(pageSize != null ? pageSize.intValue() : 1000);

        if ("paginated".equalsIgnoreCase(exportType) && page != null && pageSize != null) {
            int offset = page.intValue() * pageSize.intValue();
            reader.setCurrentItemCount(offset);
            reader.setMaxItemCount(offset + pageSize.intValue());
        }

        return reader;
    }

    @Bean
    @StepScope
    public FlatFileItemWriter<MedicineEntity> csvMedicineWriter(@Value("#{jobParameters['filePath']}") String filePath) {

        FlatFileItemWriter<MedicineEntity> writer = new FlatFileItemWriter<>();
        writer.setResource(new FileSystemResource(filePath));
        writer.setAppendAllowed(false);
        writer.setHeaderCallback(writer1 -> writer1.write(
                "id,code,name,price,quantity,vat,note,maker,origin,retailProfit,kindOfMedicine,activeElement"
        ));
        writer.setLineAggregator(new DelimitedLineAggregator<>() {{
            setDelimiter(",");
            setFieldExtractor(medicine -> new Object[]{
                    medicine.getId(), medicine.getCode(), medicine.getName(), medicine.getPrice(),
                    medicine.getQuantity(), medicine.getVat(), medicine.getNote(), medicine.getMaker(),
                    medicine.getOrigin(), medicine.getRetailProfit(),
                    medicine.getKindOfMedicineEntity() != null ? medicine.getKindOfMedicineEntity().getName() : "",
                    medicine.getActiveElement()
            });
        }});
        return writer;
    }

    @Bean
    public TaskExecutor batchExportTaskExecutor() {
        SimpleAsyncTaskExecutor executor = new SimpleAsyncTaskExecutor("spring_batch");
        executor.setConcurrencyLimit(Runtime.getRuntime().availableProcessors());
        return executor;
    }
}
