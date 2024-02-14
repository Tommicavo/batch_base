package it.fides.batchProject;

import java.io.File;
import java.io.FileOutputStream;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

@Configuration
public class BatchConfiguration {
    
	@Autowired
	private PersonService personService;
	
    private String fileInput = "inputData.csv";
    private String dirOutput = "pdf/";
    
    @Bean
    public Job importUserJob(JobRepository jobRepository, Step step1) {
        return new JobBuilder("importUserJob", jobRepository)
          .incrementer(new RunIdIncrementer())
          .flow(step1)
          .end()
          .build();
    }
    
    @Bean
    public Step step1(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("step1", jobRepository)
          .<PersonEntity, PersonEntity> chunk(10, transactionManager)
          .reader(reader())
          //.processor(processor())
          .writer(writer())
          .build();
    }
    
	@Bean
    public FlatFileItemReader<PersonEntity> reader() {
        return new FlatFileItemReaderBuilder<PersonEntity>().name("PersonItemReader")
          .resource(new ClassPathResource(fileInput))
          .delimited()
          .delimiter(",")
          .names(new String[] {"email"})
          .fieldSetMapper(new BeanWrapperFieldSetMapper<PersonEntity>() {{
              setTargetType(PersonEntity.class);
          }})
          .build();
	}
	 
	@Bean
    public ItemWriter<PersonEntity> writer() {
		
		return new ItemWriter<PersonEntity>() {
            @Override
            public void write(Chunk<? extends PersonEntity> persons) throws Exception {
            	
                for (PersonEntity person : persons) {
                    PersonEntity personEntity = personService.getPersonByEmail(person.getEmail());
                    String content = personEntity.getId() + " - " + personEntity.getFirstName() + " " + personEntity.getLastName();
                    
                    File directory = new File(dirOutput);
                    if (!directory.exists()) {
                        directory.mkdirs();
                    }
                    
                    Document document = new Document();
                    PdfWriter.getInstance(document, new FileOutputStream(dirOutput + personEntity.getId() + " " + personEntity.getFirstName() + ".pdf"));

                    document.open();
                    document.add(new Paragraph(content));
                    document.close();
                }
            }
        };
    }
}
