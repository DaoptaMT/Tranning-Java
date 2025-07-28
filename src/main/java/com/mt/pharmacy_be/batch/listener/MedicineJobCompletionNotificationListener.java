package com.mt.pharmacy_be.batch.listener;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

import java.time.Duration;

/**
 * Listener for job completion notifications for the medicine import job.
 * This class logs the status of the job after it completes and calculates the duration of the job.
 * Author: Thanh Truc
 * Date: 28/07/2024
 * Description: This listener is triggered after the job execution to log the results and duration of the job.
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class MedicineJobCompletionNotificationListener implements JobExecutionListener {

    @Override
    public void beforeJob(@NonNull JobExecution jobExecution) {
        log.info("Medicine import job is starting...");
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("Medicine import job completed successfully!");
        } else if (jobExecution.getStatus() == BatchStatus.FAILED) {
            log.error("Medicine import job failed with status: {}", jobExecution.getStatus());
            log.error("Exception: {}", jobExecution.getAllFailureExceptions());
        }

        // Calculate duration between start and end time
        String duration = "N/A";
        if (jobExecution.getStartTime() != null && jobExecution.getEndTime() != null) {
            Duration jobDuration = Duration.between(jobExecution.getStartTime(), jobExecution.getEndTime());
            duration = jobDuration.toMillis() + " ms";
        }

        log.info("Job ID: {}, Job Name: {}, Job Status: {}, Start Time: {}, End Time: {}, Duration: {}",
                jobExecution.getJobId(),
                jobExecution.getJobInstance().getJobName(),
                jobExecution.getStatus(),
                jobExecution.getStartTime(),
                jobExecution.getEndTime(),
                duration);
    }
}
