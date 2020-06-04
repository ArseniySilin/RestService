package com.example.restservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class GenerateURLController {
  private String address = "http://localhost:8080";

  @GetMapping("/generateUrl")
  public Response url() {
    String randomUuid = UUID.randomUUID().toString();
    String generatedUrl = new GeneratedURL(address, randomUuid).getUrl();

    return new Response(Messages.SUCCESS.code, Messages.SUCCESS.message, generatedUrl);
  }
}
