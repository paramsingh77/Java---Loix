package net.engineeringdigest.journalApp.Respository;

import net.engineeringdigest.journalApp.Entity.JournalEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JournalEntityRepository extends MongoRepository<JournalEntity, ObjectId> {
}
