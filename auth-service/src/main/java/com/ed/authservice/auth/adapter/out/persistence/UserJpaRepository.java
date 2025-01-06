package com.ed.authservice.auth.adapter.out.persistence;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<UserJpaEntity, Long> {

  boolean existsByUsername(String username);

  Optional<UserJpaEntity> findByUsername(String username);
}
