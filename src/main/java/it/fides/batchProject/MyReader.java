package it.fides.batchProject;

import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Component
public class MyReader {
	
	@Value("${file.input}")
	private String fileInput;
	
    public FlatFileItemReaderBuilder<PersonEntity> reader() {
        FlatFileItemReaderBuilder<PersonEntity> reader = new FlatFileItemReaderBuilder<>();
        reader.name("PersonItemReader");
        reader.resource(new ClassPathResource(fileInput));
        reader.delimited().delimiter(",").names(new String[] {"email"});
        reader.fieldSetMapper(new BeanWrapperFieldSetMapper<PersonEntity>() {{
            setTargetType(PersonEntity.class);
        }});

        return reader;
    }
}
