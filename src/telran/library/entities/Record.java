package telran.library.entities;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "records")
@Entity
@Getter
@NoArgsConstructor
public class Record {
	
	@Id
	@GeneratedValue
    long id;
	LocalDate pickDate;
	@Setter
	LocalDate returnDate;
	@Setter
	int delayDays;
	
	@ManyToOne
	Book book;
	@ManyToOne
	Reader reader;
	public Record(LocalDate pickDate, Book book, Reader reader) {
		super();
		this.pickDate = pickDate;
		this.book = book;
		this.reader = reader;
	}
	
	
	
	
}
