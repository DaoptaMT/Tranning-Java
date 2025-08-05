package com.mt.pharmacy_be.batch.listener;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;

/**
 * Listener for export job completion notifications.
 * This class logs the status of the export job after it completes.
 * Author: Thanh Truc
 * Date: 05/08/2025
 * Description: This listener is triggered after the export job execution to log the results of the job.
 */
public class ExportJobListener extends JobExecutionListenerSupport {
    @Override
    public void afterJob(JobExecution jobExecution) {
        System.out.println("✅ Export completed: " + jobExecution.getStatus());
    }
}

