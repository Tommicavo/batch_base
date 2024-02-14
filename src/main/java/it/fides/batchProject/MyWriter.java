package it.fides.batchProject;

import java.io.File;
import java.io.FileOutputStream;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

@Component
public class MyWriter implements ItemWriter<PersonEntity> {
	
	@Autowired
	private PersonService personService;
	
	@Value("${directory.output}")
	private String dirOutput;
	
	public MyWriter() {}
	
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
}
