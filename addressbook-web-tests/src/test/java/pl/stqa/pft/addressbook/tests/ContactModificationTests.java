package pl.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import pl.stqa.pft.addressbook.model.ContactData;

public class ContactModificationTests extends TestBase{

  @Test
  public void testContactModification(){
    app.getContactHelper().initContactModification();
    app.getContactHelper().fillContactForm(new ContactData("Grzegorz4", "Kozlowski3", "Warsaw, ul. Magiera 3/22", "+484083699", "+48601000000", "gkbrir05@gmail.com"));
    app.getContactHelper().submitContactModyfication();
  }
}
