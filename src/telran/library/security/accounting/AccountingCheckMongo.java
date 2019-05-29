package telran.library.security.accounting;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Service;

import telran.library.security.dao.SecurityRepository;
import telran.library.security.domain.AccountMongo;

@Service
@ManagedResource
public class AccountingCheckMongo implements IAccounting {
	
	@Value("${period:30}")
	int experationPeriod;

	@ManagedAttribute
	public int getExperationPeriod() {
		return experationPeriod;
	}
	@ManagedAttribute
	public void setExperationPeriod(int experationPeriod) {
		this.experationPeriod = experationPeriod;
	}

	@Autowired
	SecurityRepository accounts;
	
	
	@Override
	public String getPassword(String username) {
	    AccountMongo account = accounts.findById(username).orElse(null);
		if(account == null) return " ";
		LocalDate expDate = account.getDate().plusDays(experationPeriod);
		if(LocalDate.now().isAfter(expDate) || LocalDate.now().equals(expDate)) return "";
		return account.getPassword();
	}

	@Override
	public String[] getRoles(String username) {
		AccountMongo account = accounts.findById(username).orElse(null);
	
		if(account == null) return new String[0];
	
		return account.getRoles().toArray(new String[0]);
	}

}
