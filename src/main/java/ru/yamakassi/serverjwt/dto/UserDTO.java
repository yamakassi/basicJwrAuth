package ru.yamakassi.serverjwt.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.yamakassi.serverjwt.model.Role;
import ru.yamakassi.serverjwt.model.User;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class UserDTO {


    private String login;
    private String password;
    private String firstName;
    private String lastName;
    private Set<RoleDTO> roleDTOS;



}

