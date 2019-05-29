package telran.library.security.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import telran.library.security.domain.AccountMongo;

@Repository
public interface SecurityRepository extends MongoRepository<AccountMongo, String> {

}
