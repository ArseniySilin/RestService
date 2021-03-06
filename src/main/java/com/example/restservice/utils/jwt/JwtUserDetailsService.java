package com.example.restservice.utils.jwt;

import com.example.restservice.entities.users.exceptions.UsersException;
//import com.example.restservice.entities.users.repository.UsersRepository;
import com.example.restservice.entities.users.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {
//  @Autowired
//  private UsersRepository usersRepository;
//
  @Autowired
  private UsersService usersService;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user;

    try {
      user = usersService.getUserByUserName(username);
      return user;
    } catch (UsersException e) {
      throw new UsernameNotFoundException("User not found: " + username);
    }
  }
}
