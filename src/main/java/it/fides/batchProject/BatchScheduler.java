package it.fides.batchProject;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import it.fides.batchProject.utils.AppLogger;

@Component
public class BatchScheduler {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job importUserJob;

    @Autowired
    private AppLogger logger;

    /*
    private static final long START_DELAY = calculateDelay(LocalDateTime.of(2024, 2, 14, 17, 15));
    public static final long calculateDelay(LocalDateTime targetDateTime) {
    	return LocalDateTime.now().until(targetDateTime, ChronoUnit.MILLIS);
    }
    */
    @Scheduled(fixedRate = 10000, initialDelay = 20000)
    public void startBatchJob() {
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
    }
}
