package com.example.restservice;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class LoginController {
  public static final String path = "/login"; // TODO: use enums?

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private JwtTokenUtil jwtTokenUtil;

  @Autowired
  private JwtUserDetailsService userDetailsService;

  @RequestMapping(value = "/login", method = RequestMethod.POST)
  public ResponseEntity<?> createAuthenticationToken(@RequestBody User user) throws Exception {
    int resultCode = DB.isUserExist(user.getUsername(), user.getPassword());
    String message = Messages.getMessageByCode(resultCode);
    Response response;

    if (resultCode != Messages.SUCCESS.code) {
        response = new Response(resultCode, message);
        return ResponseEntity.ok(response);
    }

    String accessToken  = jwtTokenUtil.generateAccessToken(user.getUsername());
    String refreshToken  = jwtTokenUtil.generateRefreshToken();
    Token token = new Token(accessToken, refreshToken);
    Gson gson = new Gson();
    String data = gson.toJson(token);
    response =  new Response(resultCode, message, data);

    boolean didTokensUpdateSuccessfully = DB.updateUserTokens(user.getUsername(), accessToken, refreshToken);

    if (!didTokensUpdateSuccessfully) {
      // TODO: add custom error messages
      response = new Response(Messages.ERROR.code, Messages.ERROR.message);
    }

    return ResponseEntity.ok(response);
  }
}