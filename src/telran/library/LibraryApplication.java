package telran.library;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import telran.library.security.dao.SecurityRepository;


@SpringBootApplication
public class LibraryApplication {

	@Autowired
	SecurityRepository securityRepository;
	

	public static void main(String[] args) {
	
	    SpringApplication.run(LibraryApplication.class, args);
	    
	}

	



}
