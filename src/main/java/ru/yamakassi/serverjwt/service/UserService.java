package ru.yamakassi.serverjwt.service;

import lombok.NonNull;
import ru.yamakassi.serverjwt.dto.User;

import java.util.Optional;

public interface UserService {
     Optional<User> getByLogin(@NonNull String login);
}
