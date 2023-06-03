package ru.yamakassi.serverjwt.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yamakassi.serverjwt.dto.UserDTO;
import ru.yamakassi.serverjwt.dto.RoleDTO;
import ru.yamakassi.serverjwt.repo.UserRepository;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.Optional;

@Service
@Slf4j

public class UserServiceImpl implements UserService {

    private  final UserRepository userRepository;


    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;

    }


    public Optional<UserDTO> getByLogin(@NonNull String login) {
        return DataInitializer.convertByDTO(userRepository.getUserByLogin(login));
    }

    @Override
    public Optional<UserDTO> createUser(@NonNull UserDTO userDTO) {
        return Optional.empty();
    }

}