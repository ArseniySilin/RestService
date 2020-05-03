package com.example.restservice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class LoginController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody User user) throws Exception {
      int resultCode = DB.loginUser(user.getUsername(), user.getPassword());
      String message = Messages.getMessageByCode(resultCode);
      Response r;

      if (resultCode != Messages.SUCCESS.code) {
          r = new Response(resultCode, message);
      } else {
        Token token = Tokenize.generateToken(user);
        Gson gson = new Gson();
        String data = gson.toJson(token);
        r =  new Response(resultCode, message, data);
      }

      return ResponseEntity.ok(r);
    }

//    @PostMapping(
//            path = "/login",
//            consumes = MediaType.APPLICATION_JSON_VALUE,
//            produces = MediaType.APPLICATION_JSON_VALUE
//    )
//    public Response response(@RequestBody User user) throws Exception {
//        int resultCode = DB.loginUser(user.getUsername(), user.getPassword());
//        String message = Messages.getMessageByCode(resultCode);
//
//        if (resultCode != Messages.SUCCESS.code) {
//            return new Response(resultCode, message);
//        }
//
//        Token token = Tokenize.generateToken(user);
//        Gson gson = new Gson();
//        String data = gson.toJson(token);
//
//        return new Response(resultCode, message, data);
//    }
}