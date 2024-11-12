package net.engineeringdigest.journalApp.Respository;

import net.engineeringdigest.journalApp.Entity.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserEntityRepository extends MongoRepository<UserEntity, String> {

     UserEntity findByUsername(String username);
}