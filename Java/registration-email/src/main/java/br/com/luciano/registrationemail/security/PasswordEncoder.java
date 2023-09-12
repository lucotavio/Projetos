package br.com.luciano.registrationemail.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class PasswordEncoder {

    @Bean
    public BCryptPasswordEncoder byBCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
