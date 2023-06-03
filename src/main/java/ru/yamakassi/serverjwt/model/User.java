package ru.yamakassi.serverjwt.model;

import lombok.Data;
import ru.yamakassi.serverjwt.dto.RoleDTO;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String login;
    private String password;
    private String firstName;
    private String lastName;
    @ManyToMany(cascade=CascadeType.ALL)
    private Set<Role> roles;

}
