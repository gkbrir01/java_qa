package pl.stqa.pft.mantis.model;

public class User {
  private String username;
  private String email;

  public String getUsername() {
    return username;
  }

  public String getEmail() {
    return email;
  }

  public User(String username, String email ){
    this.username = username;
    this.email = email;

  }
}
