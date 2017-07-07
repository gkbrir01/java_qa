package pl.stqa.pft.mantis.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pl.stqa.pft.mantis.model.User;

import java.util.List;

public class RegistrationHelper extends HelperBase {

  public RegistrationHelper(ApplicationManager app) {
    super(app);
  }

  public void start(String username, String email) {
    wd.get(app.getProperty("web.baseUrl") + "/signup_page.php");
    type(By.name("username"), username);
    type(By.name("email"), email);
    click(By.cssSelector("input[value='Signup']"));
  }

  public void finish(String confirmationLink, String password) {
    wd.get(confirmationLink);
    type(By.name("password"), password);
    type(By.name("password_confirm"), password);
    click(By.cssSelector("input[value='Update User']"));
  }

  public void manageUsers(String username, String password) {
    wd.get(app.getProperty("web.baseUrl") + "/login.php");
    type(By.name("username"), username);
    type(By.name("password"), password);
    click(By.cssSelector("input[value='Login']"));
    click(By.linkText("Manage"));
    click(By.linkText("Manage Users"));
  }

  public void selectionUser() {
    List<WebElement> rows = wd.findElements(By.cssSelector("tr[class^=row]"));
    for (int i=1; i < rows.size(); i++) {
      List<WebElement> cells = rows.get(i).findElements(By.tagName("td"));
      String userName = cells.get(0).getText();
        if (!userName.equals("administrator")) {
          cells.get(0).findElement(By.tagName("a")).click();
          return;
        }
    }
  }

  public User resetGetUser() {
    String emailUser = wd.findElement(By.name("email")).getAttribute("value");
    String userName = wd.findElement(By.name("username")).getAttribute("value");
    User user = new User(userName, emailUser);
    click(By.cssSelector("input[value='Reset Password']"));
    return user;
  }
}
