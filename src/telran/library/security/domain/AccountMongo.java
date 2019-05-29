package telran.library.security.domain;

import java.time.LocalDate;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Document(collection = "accounts")
public class AccountMongo  {
	@Id
    String username;
	@Setter
	String password;
	@Setter
	LocalDate date;
	@Setter
	Set<String> roles;
	public AccountMongo(String username, String password, Set<String> roles) {
		super();
		this.username = username;
		this.password = password;
		this.roles = roles;
	}
	
	
}
