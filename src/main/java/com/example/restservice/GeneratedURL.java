package com.example.restservice;

public class GeneratedURL {
  private String url;

  GeneratedURL(String url, String randomUuid) {
    this.url = url + "/" + randomUuid;
  }

  public String getUrl() {
    return url;
  }
}
