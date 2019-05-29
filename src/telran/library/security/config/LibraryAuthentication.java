package telran.library.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import telran.library.security.accounting.IAccounting;


@Configuration
public class LibraryAuthentication implements UserDetailsService {
	
	@Autowired
	IAccounting account;
	 
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {	
		String password = account.getPassword(username);
		return new User(username,
				password, AuthorityUtils.createAuthorityList(account.getRoles(username)))  ;
	}

}
