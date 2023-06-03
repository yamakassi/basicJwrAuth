package ru.yamakassi.serverjwt.model;
import lombok.*;
import ru.yamakassi.serverjwt.dto.RoleDTO;

import javax.persistence.*;
import java.util.Set;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    private RoleDTO roleDTO;
}
