package pl.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pl.stqa.pft.addressbook.model.ContactData;

import java.util.List;

public class ContactCreationTests extends TestBase{

  @Test
  public void testContactCreation() {
    app.getNavigationHelper().gotoHomePage();
    List<ContactData> before = app.getContactHelper().getContactList();
    app.getContactHelper().createContact(new ContactData("Grzegorz10", "Kozlowski4", "Warsaw, ul. Magiera 3/22", null, "+48601000000", "gkbrir05@gmail.com","test1"));
    List<ContactData> after = app.getContactHelper().getContactList();
    Assert.assertEquals(after.size(), before.size() + 1);
  }

}
