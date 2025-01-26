package com.ed.payment.infrastructure.out.batch.listener;

import java.time.Duration;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

@Slf4j
public class JobDurationListener implements JobExecutionListener {

    private LocalDateTime startTime;

    @Override
    public void beforeJob(JobExecution jobExecution) {
        startTime = LocalDateTime.now();
        log.info("Job '{}' started at {}", jobExecution.getJobInstance().getJobName(), startTime);
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        LocalDateTime endTime = LocalDateTime.now();
        log.info("Job '{}' finished at {}", jobExecution.getJobInstance().getJobName(), endTime);

        Duration duration = Duration.between(startTime, endTime);
        log.info("Job '{}' total duration: {} seconds", jobExecution.getJobInstance().getJobName(), duration.getSeconds());
    }
}