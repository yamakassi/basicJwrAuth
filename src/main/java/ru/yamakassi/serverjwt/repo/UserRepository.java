package ru.yamakassi.serverjwt.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.yamakassi.serverjwt.dto.UserDTO;
import ru.yamakassi.serverjwt.model.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {


    Optional<User> getUserByLogin(String login);
}
