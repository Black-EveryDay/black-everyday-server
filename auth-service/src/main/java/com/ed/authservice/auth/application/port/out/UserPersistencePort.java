package com.ed.authservice.auth.application.port.out;

import com.ed.authservice.auth.domain.User;

public interface UserPersistencePort {

  User saveUser(User user);

  boolean existsUser(String username);
}
