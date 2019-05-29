package telran.library.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import telran.library.entities.Reader;
import telran.library.entities.Record;
@Repository
public interface ReadersRepository extends JpaRepository<Reader, Integer> {

	Record findByIdAndRecordsReturnDateNull(int readerId);

	List<Reader> findByRecordsDelayDaysGreaterThan(int dalayDays);

	List<Reader> findByIdIn(List<Long> readersId);
	

	
}
