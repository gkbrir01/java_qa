package pl.stqa.pft.addressbook.appmanager;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import pl.stqa.pft.addressbook.model.ContactData;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ContactHelper extends HelperBase{

  public ContactHelper(WebDriver wd) {
    super(wd);
  }

  public void submitContactCreation() {
    click(By.xpath("//div[@id='content']/form/input[21]"));
  }

  public void fillContactForm(ContactData contactData, boolean creation) {
    type(By.name("firstname"),contactData.getFirstName());
    type(By.name("lastname"),contactData.getLastName());
    type(By.name("address"),contactData.getAddress());
    type(By.name("home"),contactData.getHomePhone());
    type(By.name("mobile"),contactData.getMobilePhone());
    type(By.name("email"),contactData.getEmail());

    if (creation){
      new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroup());
    } else{
      Assert.assertFalse(isElementPresent(By.name("new_group")));
    }
  }

  public void initContactCreation() {
    click(By.linkText("add new"));
  }

  public void acceptAlert(){
    wd.switchTo().alert().accept();
  }

  public void initContactModification(int index) {
    WebElement table = wd.findElement(By.cssSelector("table#maintable"));
    List<WebElement> rows = table.findElements(By.cssSelector("tr[name=entry]"));
    List<WebElement> cells = rows.get(index).findElements(By.tagName("td"));
    cells.get(7).click();
  }

  public void initContactModificationById(int id) {
    WebElement table = wd.findElement(By.cssSelector("table#maintable"));
    List<WebElement> rows = table.findElements(By.cssSelector("tr[name=entry]"));
    for (WebElement row : rows)
    {
      List<WebElement> cells = row.findElements(By.tagName("td"));
      int idx = Integer.parseInt(cells.get(0).findElement(By.tagName("input")).getAttribute("value"));
      //System.out.println("value: " + idx +" Input parameter :"+ id );
      if (id == idx){
        cells.get(7).click();
        return;
      }
    }
  }


  public void submitContactModyfication() {
    click(By.xpath("//div[@id='content']/form[1]/input[22]"));
  }

  public void selectContactById(int id) {
    wd.findElement(By.cssSelector("input[value='" + id + "']")).click();
  }

  public void deleteSelectedGroups() {
    click(By.xpath("//div[@id='content']/form[2]/div[2]/input"));
  }

  public void returnToHomePage() {
    if(isElementPresent(By.id("maintable"))){
      return;
    }
    click(By.linkText("home"));
  }

  public void create(ContactData contact) {
    initContactCreation();
    fillContactForm(contact,true);
    submitContactCreation();
    returnToHomePage();
  }

  public void modify(ContactData contact) {
    initContactModificationById(contact.getId());
    fillContactForm(contact,false);
    submitContactModyfication();
    returnToHomePage();
  }

  public void delete(ContactData contact) {
    selectContactById(contact.getId());
    deleteSelectedGroups();
    acceptAlert();
    returnToHomePage();
  }

  public boolean isThereAContact() {
    return isElementPresent(By.name("selected[]"));
  }

  public int getContactCount() {
    return wd.findElements(By.name("selected[]")).size();
  }

  public Set<ContactData> all() {
    Set<ContactData> contacts = new HashSet<ContactData>();
    WebElement table = wd.findElement(By.cssSelector("table#maintable"));
    List<WebElement> rows = table.findElements(By.cssSelector("tr[name=entry]"));
    for (WebElement row : rows)
    {
      List<WebElement> cells = row.findElements(By.tagName("td"));
      String firstName = cells.get(2).getText();
      //System.out.println("FirstName: " + firstName);
      String lastName = cells.get(1).getText();
      //System.out.println("LastName: " + lastName);
      int id = Integer.parseInt(cells.get(0).findElement(By.tagName("input")).getAttribute("value"));
      //System.out.println("id: " + id);
      contacts.add(new ContactData().withId(id).withFirstName(firstName).withLastName(lastName));
    }
    return contacts;
  }

}
