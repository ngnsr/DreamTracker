package com.rr.dreamtracker.dto;

import com.rr.dreamtracker.entity.UserRole;
import lombok.Data;

@Data
public class UserDto {
    private String name;
    private String email;

    private UserRole role;
}
