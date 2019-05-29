package telran.library.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import telran.library.entities.Book;
@Repository
public interface BooksRepository extends JpaRepository<Book, Long> {

	List<Book> findByAuthorsName(String authorName);
	

	List<Book> findByIsbnIn(List<Long> isbns);

		
}
