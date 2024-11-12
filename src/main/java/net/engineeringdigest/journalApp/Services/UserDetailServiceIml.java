package net.engineeringdigest.journalApp.Services;

import net.engineeringdigest.journalApp.Entity.UserEntity;
import net.engineeringdigest.journalApp.Respository.UserEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceIml implements UserDetailsService {

    @Autowired
    private UserEntityRepository userEntityRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userEntityRepository.findByUsername(username);
        if (userEntity != null) {
            org.springframework.security.core.userdetails.User.builder()
                    .username(userEntity.getUsername()).password(userEntity.getPassword()).roles(userEntity.getRoles().toArray(new String[0])).build();
        }
        throw new UsernameNotFoundException(username);
    }

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
//        auth.userDetailsService(this).passwordEncoder(new BCryptPasswordEncoder());
//    }

}
