package net.engineeringdigest.journalApp.Controllers;

import net.engineeringdigest.journalApp.Entity.JournalEntity;
import net.engineeringdigest.journalApp.Entity.UserEntity;
import net.engineeringdigest.journalApp.Services.UserEntryServices;
import org.apache.catalina.User;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserEntryServices userEntryServices;

    @GetMapping
    public ResponseEntity<List<UserEntity>> getUsers() {
        List<UserEntity> userEntities = userEntryServices.getAllUsers();
        if(userEntities == null) {
            return new ResponseEntity<>(userEntities, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(userEntities, HttpStatus.OK);
    }

//    @GetMapping by i

    @PostMapping
    public ResponseEntity<UserEntity> createUser(@RequestBody UserEntity user) {
        System.out.println(  " data is : " + user.getUsername());
        UserEntity users = userEntryServices.createEntity(user);
        if(users==null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(users, HttpStatus.CREATED);
    }
    @PostMapping("update/{username}")
    public ResponseEntity<UserEntity> updateUser(@RequestBody UserEntity user, @PathVariable String username) {
        UserEntity userInDb = userEntryServices.getUserByUsername(username);
        if(userInDb==null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else {
            userInDb.setUsername(user.getUsername());
            userInDb.setPassword(user.getPassword());
            userEntryServices.saveUser(userInDb);
            return new ResponseEntity<>(userInDb, HttpStatus.OK);
        }
    }
//    DeleteById
    @DeleteMapping("/{username}")
    public ResponseEntity<?> deleteUser(@PathVariable String username ) {
        UserEntity user = userEntryServices.deleteEntity(username);
        if(user==null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

}
