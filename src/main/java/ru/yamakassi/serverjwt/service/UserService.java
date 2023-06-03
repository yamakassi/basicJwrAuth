package ru.yamakassi.serverjwt.service;

import lombok.NonNull;
import ru.yamakassi.serverjwt.dto.UserDTO;

import java.util.Optional;

public interface UserService {
     Optional<UserDTO> getByLogin(@NonNull String login);

     Optional<UserDTO> createUser(@NonNull UserDTO userDTO);
}
