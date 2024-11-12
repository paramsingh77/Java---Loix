package net.engineeringdigest.journalApp.Controllers;

import net.engineeringdigest.journalApp.Entity.JournalEntity;
import net.engineeringdigest.journalApp.Entity.UserEntity;
import net.engineeringdigest.journalApp.Services.JournalEntryServices;
import net.engineeringdigest.journalApp.Services.UserEntryServices;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/journal")
public class JournalController {

    @Autowired
    private JournalEntryServices journalEntryServices;

    @Autowired
    private UserEntryServices userEntryServices;
//   GETMAPPING
@GetMapping("{username}")
public ResponseEntity<List<JournalEntity>> getAllJournalsOfUsers(@PathVariable String username) {
    UserEntity user = userEntryServices.getUserByUsername(username);
    List<JournalEntity> all =  user.getJournalEntries();
    if(!all.isEmpty()) {
        return new ResponseEntity<>(all,HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(all,HttpStatus.OK);
}

@GetMapping("/getId/{id}")
public ResponseEntity<JournalEntity> getJournal(ObjectId id) {
        JournalEntity journal =  journalEntryServices.getById(id);
        if(journal!=null) {
            return new ResponseEntity<>(journal,HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(journal,HttpStatus.OK);
    }

//   POSTMAPPING
    @PostMapping("/{username}")
    public ResponseEntity<JournalEntity> createJournal(@RequestBody JournalEntity journal, @PathVariable String username) {
        JournalEntity old = journalEntryServices.createEntry(journal, username);
        if(old != null) {
            return new ResponseEntity<>(old,HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }
//   PUTMAPPING
    @PutMapping("id/{id}")
    public ResponseEntity<JournalEntity> updateJournal(@RequestBody JournalEntity journal, @PathVariable ObjectId id) {
          JournalEntity old = journalEntryServices.updateEntry(journal,id);
          if(old!=null) {
                return new ResponseEntity<>(old,HttpStatus.OK);
          }
          return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }
   @DeleteMapping("/{username}/{id}")
    public void getJournalById(@PathVariable ObjectId id,@PathVariable String username ) {
     System.out.println("id :" + id + " username :" + username);
       journalEntryServices.deleteEntry(id,username);
    }
//   POST (id)
//   GET Mapping (id)

}
