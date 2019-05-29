package telran.library.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import telran.library.dto.Cover;

@Table(name = "books")
@Entity
@Getter
@NoArgsConstructor
public class Book {
    
	@Id
	Long isbn;
	String title;
	Cover cover;
	@Setter
	int amount;
	int pickPeriod;
    
	@ManyToMany
	List<Author> authors;
	public Book(long isbn, String title, Cover cover, int amount, int pickPeriod, List<Author> authors) {
		super();
		this.isbn = isbn;
		this.title = title;
		this.cover = cover;
		this.amount = amount;
		this.pickPeriod = pickPeriod;
		this.authors = authors;
	}

	
	
}
