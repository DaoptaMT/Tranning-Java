package com.mt.pharmacy_be.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@EnableAsync
@EnableConfigurationProperties(AsyncProperties.class)
public class AsyncConfig {

    /**
     * Configures a ThreadPoolTaskExecutor for handling asynchronous tasks.
     * Author: Thanh Truc
     * Date: 28/07/2024
     * Description: This method sets up a ThreadPoolTaskExecutor with properties defined in AsyncProperties.
     */
    @Bean(name = "taskExecutor")
    public Executor taskExecutor(AsyncProperties props) {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(props.getCorePoolSize());
        executor.setMaxPoolSize(props.getMaxPoolSize());
        executor.setQueueCapacity(props.getQueueCapacity());
        executor.setThreadNamePrefix("async-task-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }

    /**
     * Configures a ThreadPoolTaskExecutor specifically for export tasks.
     * Author: Thanh Truc
     * Date: 05/08/2025
     * Description: This method sets up a ThreadPoolTaskExecutor for export operations with a dedicated configuration.
     */
    @Bean(name = "exportExecutor")
    public Executor exportExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(4);
        executor.setMaxPoolSize(8);
        executor.setQueueCapacity(20);
        executor.setThreadNamePrefix("export-");
        executor.initialize();
        return executor;
    }
}