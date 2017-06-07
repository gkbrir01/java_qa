package pl.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import pl.stqa.pft.addressbook.model.ContactData;

public class ContactModificationTests extends TestBase{

  @Test
  public void testContactModification(){
    app.getNavigationHelper().gotoHomePage();
    if (! app.getContactHelper().isThereAContact()){
      app.getContactHelper().createContact(new ContactData("Grzegorz10", "Kozlowski4", "Warsaw, ul. Magiera 3/22", null, "+48601000000", "gkbrir05@gmail.com","test1"));
    }
    app.getContactHelper().initContactModification();
    app.getContactHelper().fillContactForm(new ContactData("Grzegorz4", "Kozlowski3", "Warsaw, ul. Magiera 3/22", "+484083699", null, "gkbrir05@gmail.com",null), false);
    app.getContactHelper().submitContactModyfication();
    app.getContactHelper().returnToHomePage();

  }
}
