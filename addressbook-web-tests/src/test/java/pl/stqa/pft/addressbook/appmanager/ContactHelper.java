package pl.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;
import pl.stqa.pft.addressbook.model.ContactData;

/**
 * Created by gkozlowski on 2017-05-27.
 */
public class ContactHelper {


  private FirefoxDriver wd;

  public ContactHelper(FirefoxDriver wd) {

    this.wd = wd;
  }

  public void submitContactCreation() {
  wd.findElement(By.xpath("//div[@id='content']/form/input[21]")).click();
}

  public void fillContactForm(ContactData contactData) {
  wd.findElement(By.name("firstname")).click();
  wd.findElement(By.name("firstname")).clear();
  wd.findElement(By.name("firstname")).sendKeys(contactData.getFirstName());
  wd.findElement(By.name("lastname")).click();
  wd.findElement(By.name("lastname")).clear();
  wd.findElement(By.name("lastname")).sendKeys(contactData.getLastName());
  wd.findElement(By.name("address")).click();
  wd.findElement(By.name("address")).clear();
  wd.findElement(By.name("address")).sendKeys(contactData.getAddress());
  wd.findElement(By.name("home")).click();
  wd.findElement(By.name("home")).clear();
  wd.findElement(By.name("home")).sendKeys(contactData.getHomePhone());
  wd.findElement(By.name("mobile")).click();
  wd.findElement(By.name("mobile")).clear();
  wd.findElement(By.name("mobile")).sendKeys(contactData.getMobilePhone());
  wd.findElement(By.name("email")).click();
  wd.findElement(By.name("email")).clear();
  wd.findElement(By.name("email")).sendKeys(contactData.getEmail());
  if (!wd.findElement(By.xpath("//div[@id='content']/form/select[1]//option[16]")).isSelected()) {
    wd.findElement(By.xpath("//div[@id='content']/form/select[1]//option[16]")).click();
  }
  if (!wd.findElement(By.xpath("//div[@id='content']/form/select[2]//option[3]")).isSelected()) {
    wd.findElement(By.xpath("//div[@id='content']/form/select[2]//option[3]")).click();
  }
  wd.findElement(By.name("byear")).click();
  wd.findElement(By.name("byear")).clear();
  wd.findElement(By.name("byear")).sendKeys(contactData.getYearB());
}

  public void initContactCreation() {
  wd.findElement(By.linkText("add new")).click();
}
}
