package telran.library.security.accounting;

public interface IAccounting {
	String getPassword(String username);
	String[] getRoles(String userName);
}
