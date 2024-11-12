package net.engineeringdigest.journalApp.Services;
import net.engineeringdigest.journalApp.Entity.UserEntity;
import net.engineeringdigest.journalApp.Respository.UserEntityRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class UserEntryServices {
    @Autowired
    private UserEntityRepository userEntityRepository;
    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    public List<UserEntity> getAllUsers() {
        return userEntityRepository.findAll();
    }

    public UserEntity getUserByUsername(String username) {
        return userEntityRepository.findByUsername(username);
    }

    public UserEntity createEntity(UserEntity user) {
        return userEntityRepository.save(user);
    }

    public UserEntity saveUser(UserEntity userEntity) {
        return userEntityRepository.save(userEntity);
    }

    public void saveNewUser(UserEntity userEntity) {
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        userEntity.setRoles(Arrays.asList("USER"));
        userEntityRepository.save(userEntity);

    }
    public UserEntity updateEntity(UserEntity userEntity, String username) {
        Optional<UserEntity> user1 = userEntityRepository.findById(username);
        if(user1.isPresent()) {
            UserEntity user= user1.get();
            user.setUsername(user.getUsername() == null || userEntity.getUsername().equals("") ? user.getUsername() : userEntity.getUsername());
            user.setPassword(user.getPassword() == null || userEntity.getPassword().equals("") ? user.getPassword() : userEntity.getPassword());
            userEntityRepository.save(user);
            return user;
        }
        return null;
    }

    public UserEntity deleteEntity(String username) {
       UserEntity user = userEntityRepository.findByUsername(username);
       if(user != null) {
           userEntityRepository.delete(user);
       }
       return user;
    }
}

