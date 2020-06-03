package com.example.restservice;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class LoginController {
  public static final String path = "/login"; // TODO: use enums?

  @Autowired
  private UsersRepository usersRepository;

  @RequestMapping(value = "/login", method = RequestMethod.POST)
  public ResponseEntity<?> loginUser(@RequestBody User user) throws Exception {
    // authorize user
    int authorizeUserResultCode = usersRepository.authorizeUser(user.getUsername(), user.getPassword());
    String message = Messages.getMessageByCode(authorizeUserResultCode);
    Response response;

    if (authorizeUserResultCode != Messages.SUCCESS.code) {
        response = new Response(authorizeUserResultCode, message);
        return ResponseEntity.ok(response);
    }

    // generate user tokens
    Gson gson = new Gson();
    UserTokens userTokens = usersRepository.generateUserTokens(user.getUsername());
    String data = gson.toJson(userTokens);

    response =  new Response(authorizeUserResultCode, message, data);

    // update user tokens
    int tokensUpdateResultCode = usersRepository.updateUserTokens(
      user.getUsername(),
      userTokens.accessToken,
      userTokens.refreshToken
    );

    if (tokensUpdateResultCode != Messages.SUCCESS.code) {
      response = new Response(Messages.ERROR.DATABASE_ERROR.code, Messages.ERROR.DATABASE_ERROR.message);
    }

    return ResponseEntity.ok(response);
  }
}