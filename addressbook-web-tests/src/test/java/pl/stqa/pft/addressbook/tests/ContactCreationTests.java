package pl.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import pl.stqa.pft.addressbook.model.ContactData;

public class ContactCreationTests extends TestBase{

  @Test
  public void testContactCreation() {
    app.getNavigationHelper().gotoHomePage();
    app.getContactHelper().initContactCreation();
    app.getContactHelper().fillContactForm(new ContactData("Grzegorz10", "Kozlowski4", "Warsaw, ul. Magiera 3/22", null, "+48601000000", "gkbrir05@gmail.com","test1"),true);
    app.getContactHelper().submitContactCreation();
    app.getNavigationHelper().returnToHomePage();

  }

}