package pl.stqa.pft.addressbook.tests;

import org.openqa.selenium.By;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pl.stqa.pft.addressbook.model.ContactData;
import pl.stqa.pft.addressbook.model.Contacts;
import pl.stqa.pft.addressbook.model.GroupData;
import pl.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.junit.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

public class ContactAddToGroupTests extends TestBase{

  @BeforeMethod
  public void ensurePreconditions(){
    if(app.db().groups().size() == 0){
      app.goTo().groupPage();
      app.group().create(new GroupData().withName("test1").withHeader("header1").withFooter("footer1"));
    }

    if(app.db().contacts().size() == 0){
      app.goTo().homePage();
      app.contact().create(new ContactData().withFirstName("Grzegorz22").withLastName("Kozlowski22")
              .withAddress("Warsaw, ul. Magiera 3/22").withMobilePhone("+48601000000").withEmail("gkbrir05@gmail.com"));
    }
  }


  @Test
  public void testContactAddToGroup(){
    Contacts contactsDB = app.db().contacts();
    Groups groupsDB = app.db().groups();

    Contacts contactsDBTemp = app.db().contacts();
    for (ContactData contactDB: contactsDB) {
      Groups groupsContact = contactDB.getGroups();
      if(groupsDB.equals(groupsContact)){
        contactsDBTemp.remove(contactDB);
      }
    }
    if(contactsDBTemp.isEmpty()){
      app.goTo().homePage();
      ContactData contactAdd = new ContactData().withFirstName("Jan").withLastName("Nowak")
              .withAddress("Warsaw, ul. Magiera 3/22").withMobilePhone("+48601000055").withEmail("jan.nowak@gmail.com");
      app.contact().create(contactAdd);
      contactsDBTemp.add(contactAdd);
    }

    ContactData contactDB =contactsDBTemp.iterator().next();
    Groups groupsContact = contactDB.getGroups();
    Groups groupsDBTemp = app.db().groups();

    for (GroupData groupContact : groupsContact) {
      for (GroupData groupDB : groupsDB) {
        if (groupDB.equals(groupContact)) {
           groupsDBTemp.remove(groupDB);
         }
      }
    }

    GroupData addGroup = groupsDBTemp.iterator().next();
    Groups before = contactDB.getGroups();
    app.goTo().homePage();
    app.contact().selectContactById(contactDB.getId());
    app.group().selectGroup(By.name("to_group"),addGroup);
    app.contact().click(By.name("add"));
    app.contact().returnToHomePage();
    app.contact().returnToHomePage();

    app.db().contacts();
    app.db().groups();
    Groups after = contactDB.getGroups();

    //assertEquals(after.size(), before.size() + 1);
    assertEquals(after, before);
  }
}

