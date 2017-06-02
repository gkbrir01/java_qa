package pl.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class NavigationHelper extends HelperBase{

  public NavigationHelper(WebDriver wd) {
    super(wd);
  }

  public void gotoGroupPage() {
    if (isElementPresent(By.tagName("h1"))
            && wd.findElement(By.tagName("h1")).getText().equals("Groups")
            && isElementPresent(By.name("new"))) {

    }
    click(By.linkText("groups"));
  }

  public void gotoHomePage(){
    click(By.linkText("home"));
  }

  public void returnToHomePage() {
    click(By.linkText("home"));
  }
}
