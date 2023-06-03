package ru.yamakassi.serverjwt.service;

import io.jsonwebtoken.Claims;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.yamakassi.serverjwt.dto.JwtRequest;
import ru.yamakassi.serverjwt.dto.JwtResponse;
import ru.yamakassi.serverjwt.dto.UserDTO;
import ru.yamakassi.serverjwt.exception.AuthException;
import ru.yamakassi.serverjwt.model.JwtAuthentication;
import ru.yamakassi.serverjwt.model.User;
import ru.yamakassi.serverjwt.utils.UserConverter;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    //private final Map<String, String> refreshStorage = new HashMap<>();
    private final RedisTemplate<String, Object> redisRefreshStorage;
    private final JwtProvider jwtProvider;
    @Autowired
    private PasswordEncoder passwordEncoder;


    public JwtResponse login(@NonNull JwtRequest authRequest) {
        final UserDTO userDTO = userService.getByLogin(authRequest.getLogin())
                .orElseThrow(() -> new AuthException("Пользователь не найден"));
        if (passwordEncoder.matches(authRequest.getPassword(), userDTO.getPassword())) {
            final String accessToken = jwtProvider.generateAccessToken(userDTO);
            final String refreshToken = jwtProvider.generateRefreshToken(userDTO);
            redisRefreshStorage.opsForHash().put("AUTH", userDTO.getLogin(), refreshToken);
            // refreshStorage.put(userDTO.getLogin(), refreshToken);
            return new JwtResponse(accessToken, refreshToken);
        } else {
            throw new AuthException("Неправильный пароль");
        }
    }

    public JwtResponse getAccessToken(@NonNull String refreshToken) {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String login = claims.getSubject();
            final String saveRefreshToken = (String) redisRefreshStorage.opsForHash().get("AUTH", login);
            //refreshStorage.get(login);
            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                final UserDTO userDTO = userService.getByLogin(login)
                        .orElseThrow(() -> new AuthException("Пользователь не найден"));
                final String accessToken = jwtProvider.generateAccessToken(userDTO);
                return new JwtResponse(accessToken, null);
            }
        }
        return new JwtResponse(null, null);
    }

    public JwtResponse refresh(@NonNull String refreshToken) {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String login = claims.getSubject();
            final String saveRefreshToken = (String) redisRefreshStorage.opsForHash().get("AUTH", login);
            //refreshStorage.get(login);
            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                final UserDTO userDTO = userService.getByLogin(login)
                        .orElseThrow(() -> new AuthException("Пользователь не найден"));
                final String accessToken = jwtProvider.generateAccessToken(userDTO);
                final String newRefreshToken = jwtProvider.generateRefreshToken(userDTO);
                redisRefreshStorage.opsForHash().put("AUTH", userDTO.getLogin(), newRefreshToken);

                //refreshStorage.put(userDTO.getLogin(), newRefreshToken);
                return new JwtResponse(accessToken, newRefreshToken);
            }
        }
        throw new AuthException("Невалидный JWT токен");
    }

    public JwtAuthentication getAuthInfo() {
        return (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
    }

    public JwtResponse registration(UserDTO userDTO) {
        userDTO.setPassword( passwordEncoder.encode(userDTO.getPassword()));
        User userRegister = userService.createUser(userDTO);

        if (userRegister!=null) {
            final String accessToken = jwtProvider.generateAccessToken(userDTO);
            final String refreshToken = jwtProvider.generateRefreshToken(userDTO);
            redisRefreshStorage.opsForHash().put("AUTH", userDTO.getLogin(), refreshToken);
            // refreshStorage.put(userDTO.getLogin(), refreshToken);
            return new JwtResponse(accessToken, refreshToken);
        } else {
            throw new AuthException("пользователь не зарегистрирован");
        }

    }

}
