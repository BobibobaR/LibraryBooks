package telran.library.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;


@Table(name= "authors")
@Entity
@NoArgsConstructor
@Getter
public class Author {
	@Id
	String name;
	String country;
	@ManyToMany(mappedBy="authors")
	List<Book> books;
	
	public Author(String name, String country) {
		super();
		this.name = name;
		this.country = country;
	}
	
	
}
