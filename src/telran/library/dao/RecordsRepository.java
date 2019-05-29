package telran.library.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import telran.library.entities.Book;
import telran.library.entities.Record;
@Repository
@Transactional
public interface RecordsRepository extends JpaRepository<Record, Long>{

	Record findByBookIsbn(long isbn);

	Record findByBookIsbnAndReturnDateNull(long isbn);
	Record findByReaderIdAndBookIsbnAndReturnDateNull(int readerId, long isbn);

	List<Record> findByBook(Book book);

	List<Record> findByBookAuthorsName(String authorName);
     
	
	Stream <Record> findByReturnDateNull();

	Stream <Record> findByBookIsbnAndReturnDateNullAndPickDateBefore(long isbn, LocalDate minusDays);


	@Query(value="select count(*) from records join readers on records.reader_id = readers.id where year between :yearFrom and :yearTo group by book_isbn order by count(*) desc limit 1",nativeQuery= true)
	Long findMaxCountIsbn(@Param("yearFrom") int yearFrom, @Param("yearTo") int yearTo);
	
	@Query(value="select book.isbn from Record where reader.year between :yearFrom and :yearTo group by  book.isbn having count(*) =:countMax")
	List<Long> findPopularIsbn(@Param("countMax") long countMax,@Param("yearFrom") int yearFrom, @Param("yearTo") int yearTo);
	
	
	
	@Query(value ="select count(*) from records group by reader_id order by count(*) desc limit 1",nativeQuery= true)
	long findMaxCountReaders();
	
	
	@Query(value ="select reader_id from records join readers on records.reader_id = readers.id group by reader_id having count(*)=:countMax",nativeQuery= true)
	List<Long> findActiveReaders(@Param("countMax") long countMax);

	
	 

}
