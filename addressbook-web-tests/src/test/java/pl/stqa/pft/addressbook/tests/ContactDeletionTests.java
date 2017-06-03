package pl.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import pl.stqa.pft.addressbook.model.ContactData;

public class ContactDeletionTests extends TestBase{

  @Test
  public void testContactDeletion(){
    app.getNavigationHelper().gotoHomePage();
    if (! app.getContactHelper().isThereAContact()){
      app.getContactHelper().createContact(new ContactData("Grzegorz10", "Kozlowski4", "Warsaw, ul. Magiera 3/22", null, "+48601000000", "gkbrir05@gmail.com","test1"),true);
    }
    app.getContactHelper().selectContact();
    app.getContactHelper().deleteSelectedGroups();
    app.getContactHelper().acceptAlert();
    app.getContactHelper().returnToHomePage();
  }
}
