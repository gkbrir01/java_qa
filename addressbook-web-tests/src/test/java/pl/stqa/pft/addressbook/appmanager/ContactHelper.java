package pl.stqa.pft.addressbook.appmanager;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import pl.stqa.pft.addressbook.model.ContactData;
import pl.stqa.pft.addressbook.model.Contacts;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
      if (id == idx){
        cells.get(7).findElement(By.tagName("a")).click();
        return;
      }
    }
  }

  public void initContactDetailsById(int id) {
    WebElement table = wd.findElement(By.cssSelector("table#maintable"));
    List<WebElement> rows = table.findElements(By.cssSelector("tr[name=entry]"));
    for (WebElement row : rows)
    {
      List<WebElement> cells = row.findElements(By.tagName("td"));
      int idx = Integer.parseInt(cells.get(0).findElement(By.tagName("input")).getAttribute("value"));
      if (id == idx){
        cells.get(6).findElement(By.tagName("a")).click();
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

  public int count() {
    return wd.findElements(By.name("selected[]")).size();
  }

  public Contacts all() {
    Contacts contacts = new Contacts();
    WebElement table = wd.findElement(By.cssSelector("table#maintable"));
    List<WebElement> rows = table.findElements(By.cssSelector("tr[name=entry]"));
    for (WebElement row : rows)
    {
      List<WebElement> cells = row.findElements(By.tagName("td"));
      String firstName = cells.get(2).getText();
      String lastName = cells.get(1).getText();
      int id = Integer.parseInt(cells.get(0).findElement(By.tagName("input")).getAttribute("value"));
      String aDdress =cells.get(3).getText();
      String allEmails=cells.get(4).getText();
      String allPhones = cells.get(5).getText();
      contacts.add(new ContactData().withId(id).withFirstName(firstName).withLastName(lastName).withAddress(aDdress)
              .withAllEmail(allEmails).withAllPhones(allPhones));
    }
    return contacts;
  }

  public ContactData infoFromEditForm(ContactData contact) {
    initContactModificationById(contact.getId());
    String firstname = wd.findElement(By.name("firstname")).getAttribute("value");
    String lastname = wd.findElement(By.name("lastname")).getAttribute("value");
    String address = wd.findElement(By.name("address")).getAttribute("value");
    String home = wd.findElement(By.name("home")).getAttribute("value");
    String mobile = wd.findElement(By.name("mobile")).getAttribute("value");
    String work = wd.findElement(By.name("work")).getAttribute("value");
    String email = wd.findElement(By.name("email")).getAttribute("value");
    String email2 = wd.findElement(By.name("email2")).getAttribute("value");
    String email3 = wd.findElement(By.name("email3")).getAttribute("value");
    wd.navigate().back();
    return new ContactData().withId(contact.getId()).withFirstName(firstname).withLastName(lastname).withAddress(address)
            .withEmail(email).withEmail2(email2).withEmail3(email3)
            .withHomePhone(home).withMobilePhone(mobile).withWorkPhone(work);
  }

  public ContactData infoFromDetails(ContactData contact) {
    initContactDetailsById(contact.getId());
    String textBlock = wd.findElement(By.cssSelector("#content")).getText();
    String[] textRows = textBlock.split("\n");
    String[] fullName = textRows[0].split("\\s");
    String firstname = fullName[0];
    String lastname = fullName[1];

    int i = 1;
    String address = "";
      while(!textRows[i].isEmpty()){
        address  = Arrays.asList(address,textRows[i]).stream()
                .filter((s) -> ! s.equals("")).collect(Collectors.joining("\n"));
        i++;
      }

    i++;
    String allphones = "";
    while(!textRows[i].isEmpty()){
      allphones  = Arrays.asList(allphones,textRows[i]).stream()
              .filter((s) -> ! s.equals("")).collect(Collectors.joining("\n"));
      i++;
    }

    i++;
    String allemails = "";
    while(!textRows[i].isEmpty()){
      allemails  = Arrays.asList(allemails,textRows[i]).stream()
              .filter((s) -> ! s.equals("")).collect(Collectors.joining("\n"));
      i++;
    }

    //wd.navigate().back();
    return new ContactData().withId(contact.getId()).withFirstName(firstname).withLastName(lastname).withAddress(address)
           .withAllPhones(cleanedPhones(allphones)).withAllEmail(allemails);
  }

  public static String cleanedPhones(String phones){
    return phones.replace("H: ","").replace("M: ","").replace("W: ","");
  }
}

