package net.engineeringdigest.journalApp.config;

import net.engineeringdigest.journalApp.Services.UserDetailServiceIml;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SpringSecurity extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailServiceIml userDetailsServices;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests().antMatchers("/journal/**").authenticated().anyRequest().permitAll().and().httpBasic();
            http.csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsServices).passwordEncoder(new BCryptPasswordEncoder());

    }
}
