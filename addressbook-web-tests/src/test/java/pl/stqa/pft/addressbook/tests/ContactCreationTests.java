package pl.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import pl.stqa.pft.addressbook.model.ContactData;

public class ContactCreationTests extends TestBase{

  @Test
  public void testContactCreation() {
    app.initContactCreation();
    app.fillContactForm(new ContactData("Grzegorz4", "Kozlowski3", "Warsaw, ul. Magiera 3/22", "+484083699", "+48601000000", "gkbrir05@gmail.com", "1995"));
    app.submitContactCreation();
  }

}
