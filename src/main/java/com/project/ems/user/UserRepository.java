package com.project.ems.user;

import com.project.ems.role.Role;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    List<User> findAllByRole(Role role);

    void deleteAllByRole(Role role);
}
