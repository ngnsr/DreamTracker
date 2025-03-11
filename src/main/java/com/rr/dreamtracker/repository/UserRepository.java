package com.rr.dreamtracker.repository;

import com.rr.dreamtracker.dto.UserDto;
import com.rr.dreamtracker.entity.User;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByEmail(String email);

//    UserDto save(@NonNull User user);
}
