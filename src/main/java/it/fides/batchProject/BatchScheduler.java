package it.fides.batchProject;

import java.time.Duration;
import java.time.LocalDateTime;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import it.fides.batchProject.utils.AppLogger;
import jakarta.annotation.PostConstruct;

@Component
public class BatchScheduler {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job importUserJob;

    @Autowired
    private AppLogger logger;
    
    private static final LocalDateTime START_DATE = LocalDateTime.of(2024, 2, 14, 17, 32); // (year, month, day, hour, minute)
    private static final Duration INITIAL_DELAY = Duration.between(LocalDateTime.now(), START_DATE);
    private static final long LOOP_DELAY = 10_000; // 10 seconds

    @PostConstruct
    public void scheduleBatchJob() {
        long initialDelayMillis = INITIAL_DELAY.toMillis();
        Runnable task = () -> {
            try {
                JobParameters jobParameters = new JobParametersBuilder()
                        .addLong("timestamp", System.currentTimeMillis())
                        .toJobParameters();

                jobLauncher.run(importUserJob, jobParameters);
                logger.log.info("\n\n!!! BATCH APP STARTED NOW !!!\n\n");
                System.out.println();
            } catch (Exception e) {
                System.err.println("Error starting batch job: " + e.getMessage());
            }
        };

        new Thread(() -> {
            try {
                Thread.sleep(initialDelayMillis);
                while (true) {
                    task.run();
                    Thread.sleep(LOOP_DELAY);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }
}
