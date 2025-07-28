package com.kifiya.dto;

import lombok.Data;
import java.util.List;

@Data
public class UserDto {
    private Long id;
    private String email;
    private String name;
    private String username;
    private String password;
    private List<String> roles;
}
