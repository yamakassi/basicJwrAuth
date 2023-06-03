package ru.yamakassi.serverjwt.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.yamakassi.serverjwt.dto.RoleDTO;
import ru.yamakassi.serverjwt.dto.UserDTO;
import ru.yamakassi.serverjwt.model.Role;
import ru.yamakassi.serverjwt.model.User;
import ru.yamakassi.serverjwt.repo.RoleRepository;
import ru.yamakassi.serverjwt.repo.UserRepository;

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
        User user1 = this.convertUser(userDTO1);
        userRepository.save(user1);

    }
    public static User convertUser(UserDTO userDTO) {
        User user = new User();
        user.setLogin(userDTO.getLogin());
        user.setPassword(userDTO.getPassword());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        Set<Role> roles = userDTO.getRoleDTOS().stream()
                .map(roleDTO -> {
                    Role role = new Role();
                    role.setRoleDTO(roleDTO);

                    return role;
                })
                .collect(Collectors.toSet());

        user.setRoles(roles);
        return user;
    }

    public static Optional<UserDTO> convertByDTO(Optional<User> userDB) {
        User user = userDB.orElse(null);
        if (user == null) {
            return Optional.empty();
        }
        UserDTO userDTO = new UserDTO();
        userDTO.setLogin(user.getLogin());
        userDTO.setPassword(user.getPassword());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());

        Set<RoleDTO> roleDTOS = user.getRoles().stream()
                .map(Role::getRoleDTO)
                .collect(Collectors.toSet());
        userDTO.setRoleDTOS(roleDTOS);

        return Optional.of(userDTO);

    }

}