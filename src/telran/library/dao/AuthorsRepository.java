package telran.library.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import telran.library.entities.Author;
@Repository
public interface AuthorsRepository extends JpaRepository<Author, String> {

	List<Author> findByBooksIsbn(long isbn);



}
