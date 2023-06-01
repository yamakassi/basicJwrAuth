package ru.yamakassi.serverjwt.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yamakassi.serverjwt.dto.User;
import ru.yamakassi.serverjwt.model.Role;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final List<User> users;

    public UserServiceImpl() {
        this.users = List.of(
                new User("anton", "1234", "Антон", "Иванов", Collections.singleton(Role.USER)),
                new User("ivan", "12345", "Сергей", "Петров", Collections.singleton(Role.ADMIN))
        );
    }

    public Optional<User> getByLogin(@NonNull String login) {
        return users.stream()
                .filter(user -> login.equals(user.getLogin()))
                .findFirst();
    }

}