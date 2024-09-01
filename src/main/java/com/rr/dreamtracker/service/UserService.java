package com.rr.dreamtracker.service;

import com.rr.dreamtracker.entity.User;
import com.rr.dreamtracker.entity.UserRole;
import com.rr.dreamtracker.exception.ConflictException;
import com.rr.dreamtracker.exception.NotFoundException;
import com.rr.dreamtracker.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder pwdEncoder;

    private final Set<GrantedAuthority> set = new HashSet<>();
    public UserService(UserRepository userRepository, PasswordEncoder pwdEncoder) {
        this.userRepository = userRepository;
        this.pwdEncoder = pwdEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws NotFoundException {
        Optional<User> user = userRepository.findUserByEmail(email);
        if (user.isEmpty()) {
            throw new NotFoundException("User not found");
        }
        GrantedAuthority authorities = new SimpleGrantedAuthority(user.get().getRoles().toString());
        set.add(authorities);

        return new org.springframework.security.core.userdetails.User(email, user.get().getPassword(), set);
    }

    public boolean registerUser(User user) {
        Optional<User> userContainer = userRepository.findUserByEmail(user.getEmail());
        if(userContainer.isPresent()){
            throw new ConflictException("User already exists!");
        }

        user.setRoles(UserRole.ROLE_USER.name());
        user.setPassword(pwdEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }

    public boolean registerAdmin(User admin) {
        Optional<User> userContainer = userRepository.findUserByEmail(admin.getEmail());
        if(userContainer.isPresent()){
            throw new ConflictException("User already exists!");
        }

        admin.setRoles(UserRole.ROLE_ADMIN.name());
        admin.setPassword(pwdEncoder.encode(admin.getPassword()));
        userRepository.save(admin);
        return true;
    }


}
