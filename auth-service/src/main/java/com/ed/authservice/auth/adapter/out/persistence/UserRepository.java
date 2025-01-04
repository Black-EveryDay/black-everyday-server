package com.ed.authservice.auth.adapter.out.persistence;

import com.ed.authservice.auth.application.port.out.UserPersistencePort;
import com.ed.authservice.auth.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepository implements UserPersistencePort {

  private final UserJpaRepository userJpaRepository;

  @Override
  public User saveUser(User user) {
    UserJpaEntity savedUser = userJpaRepository.save(
        UserJpaEntity.builder().username(user.getUsername()).password(user.getPassword())
            .userRole(user.getUserRole()).build());

    return User.builder()
        .id(savedUser.getUserId())
        .publicId(savedUser.getUserPublicId())
        .username(savedUser.getUsername())
        .password(savedUser.getPassword())
        .userRole(savedUser.getUserRole())
        .build();
  }

  @Override
  public boolean existsUser(String username) {
    return userJpaRepository.existsByUsername(username);
  }
}
