package pl.stqa.pft.addressbook;

import org.testng.annotations.Test;

public class ContactCreationTests extends TestBase{

  @Test
  public void testContactCreation() {
    initContactCreation();
    fillContactForm(new ContactData("Grzegorz4", "Kozlowski3", "Warsaw, ul. Magiera 3/22", "+484083699", "+48601000000", "gkbrir05@gmail.com", "1995"));
    submitContactCreation();
  }

}
