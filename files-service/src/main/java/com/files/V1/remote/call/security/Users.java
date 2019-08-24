package com.files.V1.remote.call.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Users {

    @Id
    private String id;
    private String email;
    private String password;
    private String role;
}
