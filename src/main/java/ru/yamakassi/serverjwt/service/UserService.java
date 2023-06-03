package ru.yamakassi.serverjwt.service;

import lombok.NonNull;
import ru.yamakassi.serverjwt.dto.UserDTO;
import ru.yamakassi.serverjwt.model.User;

import java.util.Optional;

public interface UserService {
     Optional<UserDTO> getByLogin(@NonNull String login);

     User createUser(@NonNull UserDTO userDTO);
}
