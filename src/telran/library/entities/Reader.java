package telran.library.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "readers")
@Entity
@NoArgsConstructor
@Getter
public class Reader {
	
	@Id
	int id;
	String name;
	int year;
	@Setter
	long phoneNumber;
	
	@OneToMany(mappedBy ="reader")
	List<Record> records;

	public Reader(int id, String name, int year, long phoneNumber) {
		super();
		this.id = id;
		this.name = name;
		this.year = year;
		this.phoneNumber = phoneNumber;
	}

	

	
	
}
