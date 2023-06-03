package ru.yamakassi.serverjwt.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.yamakassi.serverjwt.dto.RoleDTO;
import ru.yamakassi.serverjwt.dto.UserDTO;
import ru.yamakassi.serverjwt.model.Role;
import ru.yamakassi.serverjwt.model.User;
import ru.yamakassi.serverjwt.repo.RoleRepository;
import ru.yamakassi.serverjwt.repo.UserRepository;
import ru.yamakassi.serverjwt.utils.UserConverter;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class DataInitializer {


    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private  final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @PostConstruct
    public void init() {
        UserDTO userDTO1 = new UserDTO("anton", passwordEncoder.encode("1234"), "Антон", "Иванов", Collections.singleton(RoleDTO.USER));
        User user1 = UserConverter.convertUser(userDTO1);
        userRepository.save(user1);

    }


}