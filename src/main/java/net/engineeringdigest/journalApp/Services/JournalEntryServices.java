package net.engineeringdigest.journalApp.Services;

import net.engineeringdigest.journalApp.Entity.JournalEntity;
import net.engineeringdigest.journalApp.Entity.UserEntity;
import net.engineeringdigest.journalApp.Respository.JournalEntityRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class JournalEntryServices {

    @Autowired
    private JournalEntityRepository journalEntityRepository;

    @Autowired
    private UserEntryServices userEntryServices;

    public List<JournalEntity> getAll(){
        return journalEntityRepository.findAll();
    }

    public JournalEntity getById(ObjectId id){
        return journalEntityRepository.findById(id).orElse(null);
    }

    public JournalEntity createEntry(JournalEntity journalEntry, String username){
// Create the entry
        UserEntity user  = userEntryServices.getUserByUsername(username);
        journalEntry.setDate (LocalDateTime.now());
        JournalEntity journal = journalEntityRepository.save(journalEntry);
        user.getJournalEntries().add(journal);

        userEntryServices.saveUser(user);
        return journal;
    }

    public JournalEntity updateEntry(JournalEntity journalEntity,ObjectId id){
        JournalEntity old = journalEntityRepository.findById(id).orElse(null);
        if(old != null){
            old.setTitle(old.getTitle() == null || journalEntity.getTitle().isEmpty() ? old.getTitle() : journalEntity.getTitle() );
            old.setContent(old.getContent() == null || journalEntity.getContent().isEmpty() ? old.getContent() : journalEntity.getContent() );
            journalEntityRepository.save(old);
            return old;
        }
        return null;
    }

    public void deleteEntry(ObjectId id, String username){
        System.out.println("Deleting journal entry with id " + id + " and username " + username);
        UserEntity user = userEntryServices.getUserByUsername(username);
        user.getJournalEntries().removeIf(journalEntry -> journalEntry.getId().equals(id));
        userEntryServices.saveUser(user);
        journalEntityRepository.deleteById(id);
    }
}
