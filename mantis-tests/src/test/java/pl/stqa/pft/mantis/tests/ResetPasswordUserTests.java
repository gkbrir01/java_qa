package pl.stqa.pft.mantis.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pl.stqa.pft.mantis.appmanager.HttpSesion;
import pl.stqa.pft.mantis.model.MailMessage;
import pl.stqa.pft.mantis.model.User;
import ru.lanwen.verbalregex.VerbalExpression;

import java.io.IOException;
import java.util.List;

import static org.testng.Assert.assertTrue;

public class ResetPasswordUserTests extends TestBase {
  @BeforeMethod
  public void startMailServer() {
    app.mail().start();
  }

  @Test
  public void testResetPasswordUser() throws InterruptedException, IOException {
    String password = "password";
    app.registration().manageUsers("administrator", "root");
    app.registration().selectionUser();
    User user = app.registration().resetGetUser();
    String userName = user.getUsername();
    String email = user.getEmail();

    List<MailMessage> mailMessages = app.mail().waitForMail(1, 10000);
    String confirmationLink = findConfirmationLink(mailMessages, email);

    app.registration().finish(confirmationLink, password);

    HttpSesion session = app.newSession();
    assertTrue(session.login(userName,password));
    assertTrue(session.isLoggedInAs(userName));
  }

  private String findConfirmationLink(List<MailMessage> mailMessages, String email) {
    MailMessage mailMessage = mailMessages.stream().filter((m) -> m.to.equals(email)).findFirst().get();
    VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();
    return regex.getText(mailMessage.text);
  }

  @AfterMethod(alwaysRun = true)
  public void stopMailServer() {
    app.mail().stop();
  }

}
