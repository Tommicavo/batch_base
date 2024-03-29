package it.fides.batchProject;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class BatchConfiguration {
    
	@Autowired
	private MyReader myReader;
	
	@Autowired
	private MyProcessor myProcessor;
	
	@Autowired
	private MyWriter myWriter;

	@Bean
    public FlatFileItemReader<PersonEntity> reader() {
		return myReader.reader().build();
	}
	
	@Bean
	public ItemProcessor<PersonEntity, PdfModel> processor(){
		return myProcessor;
	}
	
	@Bean
    public ItemWriter<PdfModel> writer() {
		return myWriter;
    }
	
    @Bean
    public Job importUserJob(JobRepository jobRepository, Step step) {
        return new JobBuilder("importUserJob", jobRepository)
          .incrementer(new RunIdIncrementer())
          .flow(step)
          .end()
          .build();
    }
    
    @Bean
    public Step step(JobRepository jobRepository, PlatformTransactionManager transactionManager,
    		FlatFileItemReader<PersonEntity> reader) {
        return new StepBuilder("step", jobRepository)
          .<PersonEntity, PdfModel> chunk(10, transactionManager)
          .reader(reader)
          .processor(myProcessor)
          .writer(myWriter)
          .build();
    }
}
