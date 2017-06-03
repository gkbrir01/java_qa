package pl.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;

public class ContactDeletionTests extends TestBase{

  @Test
  public void testContactDeletion(){
    app.getNavigationHelper().gotoHomePage();
    app.getContactHelper().selectContact();
    app.getContactHelper().deleteSelectedGroups();
    app.getContactHelper().acceptAlert();
    app.getNavigationHelper().returnToHomePage();
  }
}
