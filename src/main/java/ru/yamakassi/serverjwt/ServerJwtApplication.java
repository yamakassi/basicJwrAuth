package ru.yamakassi.serverjwt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Import;
import ru.yamakassi.serverjwt.config.RedisConfig;

@SpringBootApplication
@Import(RedisConfig.class) // Import the RedisConfig class
public class ServerJwtApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerJwtApplication.class, args);
    }

}
