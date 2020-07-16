package com.example.restservice;

import com.example.restservice.users.exceptions.UsersException;
import com.example.restservice.users.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {
  @Autowired
  private UsersRepository usersRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user;

    try {
      user = usersRepository.getUserByUserName(username);
      return user;
    } catch (UsersException e) {
      throw new UsernameNotFoundException("User not found: " + username);
    }
  }
}
