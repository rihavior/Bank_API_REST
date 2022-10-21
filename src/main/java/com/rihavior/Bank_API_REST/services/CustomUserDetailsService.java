package com.rihavior.Bank_API_REST.services;

import com.rihavior.Bank_API_REST.entities.users.User;
import com.rihavior.Bank_API_REST.repositories.UserRepository;
import com.rihavior.Bank_API_REST.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> user = userRepository.findByUsername(username);

        if (!user.isPresent()){
            throw new UsernameNotFoundException("User doesn't exist.");
        }

        CustomUserDetails customUserDetails = new CustomUserDetails(user.get());


        return customUserDetails;
    }
}
