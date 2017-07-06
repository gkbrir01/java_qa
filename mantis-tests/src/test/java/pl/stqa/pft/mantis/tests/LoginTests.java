package pl.stqa.pft.mantis.tests;

import org.testng.annotations.Test;
import pl.stqa.pft.mantis.appmanager.HttpSesion;

import java.io.IOException;

import static org.testng.AssertJUnit.assertTrue;

public class LoginTests extends TestBase {

  @Test
  public void testLogin() throws IOException {
    HttpSesion session = app.newSession();
    assertTrue(session.login("administrator", "Elixir01"));
    assertTrue(session.isLoggedInAs("administrator"));
  }
}
