package ru.yamakassi.serverjwt.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yamakassi.serverjwt.model.Role;

public interface RoleRepository extends JpaRepository<Role,Long> {
}
