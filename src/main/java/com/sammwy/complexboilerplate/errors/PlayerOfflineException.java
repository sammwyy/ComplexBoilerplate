package com.sammwy.complexboilerplate.errors;

public class PlayerOfflineException extends Exception {
  private String username;

  public PlayerOfflineException(String username) {
    super("Player " + username + " isn't online.");
    this.username = username;
  }

  public String getUsername() {
    return this.username;
  }
}