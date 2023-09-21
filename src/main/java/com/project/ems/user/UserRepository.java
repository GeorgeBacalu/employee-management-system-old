package com.project.ems.user;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import static com.project.ems.constants.PaginationConstants.USER_FILTER_QUERY;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Query(USER_FILTER_QUERY)
    Page<User> findAllByKey(Pageable pageable, @Param("key") String key);

    Optional<User> findByName(String name);
}
