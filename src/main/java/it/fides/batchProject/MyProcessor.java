package it.fides.batchProject;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MyProcessor implements ItemProcessor<PersonEntity, PersonEntity> {

    @Autowired
    private PersonService personService;

    @Override
    public PersonEntity process(PersonEntity personEntity) throws Exception {
        PersonEntity person = personService.getPersonByEmail(personEntity.getEmail());
        PdfModel pdf = new PdfModel();
        pdf.setFirstName(person.getFirstName());
        pdf.setLastName(person.getLastName());
        pdf.setEmail(person.getEmail());
        
        return person;
    }
}
