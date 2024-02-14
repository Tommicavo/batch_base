package it.fides.batchProject;

import java.io.File;
import java.io.FileOutputStream;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

@Component
public class MyWriter implements ItemWriter<PdfModel> {
	
	@Value("${directory.output}")
	private String dirOutput;
	
	public MyWriter() {}
	
    @Override
    public void write(Chunk<? extends PdfModel> pdfs) throws Exception {
        for (PdfModel pdf : pdfs) {
            File directory = new File(dirOutput);
            if (!directory.exists()) {
                directory.mkdirs();
            }
            
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(dirOutput + pdf.getId() + "_" + pdf.getFirstName() + ".pdf"));

            document.open();
            document.add(new Paragraph(pdf.toString()));
            document.close();
        }
    }
}
