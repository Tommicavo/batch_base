package it.fides.batchProject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonService {
	
	@Autowired
	private PersonRepository personRepo;
	
	public PersonEntity getPersonByEmail(String email) {
		return personRepo.findPersonByEmail(email);
	}	
}
