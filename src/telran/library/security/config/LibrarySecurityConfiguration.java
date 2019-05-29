package telran.library.security.config;

import static telran.library.dto.LibraryApiConstants.*;


import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
@Configuration
public class LibrarySecurityConfiguration extends WebSecurityConfigurerAdapter{
	
	
	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.httpBasic();//each request should contain username and password
		httpSecurity.csrf().disable();//csrt tokens are disable for POST request
		
		httpSecurity.authorizeRequests().antMatchers
		(ADD_AUTHOR,REMOVE_AUTHOR +"/*").hasRole(MANAGER);
		httpSecurity.authorizeRequests().antMatchers
		(PICK_BOOK,RETURN_BOOK).hasRole(LIBRARIAN);
		httpSecurity.authorizeRequests().antMatchers
		(GET_MOST_POPULAR_BOOK +"/*").hasRole(STATIST);
		httpSecurity.authorizeRequests().antMatchers
		(SHUTDOWN).hasRole(ADMIN);
	
		httpSecurity.authorizeRequests().antMatchers
		(ADD_BOOK,ADD_READER).hasAnyRole(MANAGER,LIBRARIAN);
		httpSecurity.authorizeRequests().antMatchers
		(GET_READERS_DELAYING).hasAnyRole(LIBRARIAN,READER);
		httpSecurity.authorizeRequests().antMatchers
		(GET_MOST_ACTIVE_READER).hasAnyRole(READER,STATIST);
		httpSecurity.authorizeRequests().antMatchers
		(GET_BOOK_AUTHORS+"/*",GET_AUTHOR_BOOKS+"/*").permitAll();
		httpSecurity.authorizeRequests().antMatchers
		(LOGIN).authenticated();
	}
}
