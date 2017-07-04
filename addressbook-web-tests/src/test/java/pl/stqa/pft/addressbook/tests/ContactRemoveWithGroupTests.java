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

public class ContactRemoveWithGroupTests extends TestBase {
  @BeforeMethod
  public void ensurePreconditions(){
    if(app.db().groups().size() == 0){
      app.goTo().groupPage();
      app.group().create(new GroupData().withName("test1").withHeader("header1").withFooter("footer1"));
    }

    if(app.db().contacts().size() == 0){
      Groups groupsDB = app.db().groups();
      app.goTo().homePage();
      app.contact().create(new ContactData().withFirstName("Grzegorz22").withLastName("Kozlowski22")
              .withAddress("Warsaw, ul. Magiera 3/22").withMobilePhone("+48601000000").withEmail("gkbrir05@gmail.com")
              .inGroup(groupsDB.iterator().next()));
    }
  }

  @Test
  public void testContactRemoveWithGroup(){
    Groups groupsDB = app.db().groups();
    Contacts contactsDB = app.db().contacts();

    //Remove groups that do not have contacts
    Groups groupsDBTemp = removeGroupWithZeroContacts(groupsDB);

    //When no group has a contact, call the method that adds the contact to the group
    if (groupsDBTemp.isEmpty()){
      ContactAddToGroupTests groupsWithOutContact = new ContactAddToGroupTests();
      groupsWithOutContact.contactAddToGroup(contactsDB.iterator().next(),groupsDB.iterator().next());
      groupsDB = app.db().groups();
      groupsDBTemp = removeGroupWithZeroContacts(groupsDB);
    }

    GroupData removeFromGroup = groupsDBTemp.iterator().next();
    Contacts before = removeFromGroup.getContacts();
    ContactData contact = before.iterator().next();


    contactRemoveFromGroup(removeFromGroup, contact);

    Groups groupsDBAfter = app.db().groups();
    Contacts after = new Contacts();
    for (GroupData afterGroup : groupsDBAfter) {
      if (afterGroup.equals(removeFromGroup)){
        after = afterGroup.getContacts();
      }
    }
    assertEquals(after.size(), before.size() - 1);
    assertThat(after, equalTo(before.without(contact)));
  }

  //Method remove groups that do not have contacts
  private Groups removeGroupWithZeroContacts(Groups groupsDB) {
    Groups groupsDBTemp = app.db().groups();
    for (GroupData group: groupsDB) {
      Contacts contactsForGroup = group.getContacts();
      if(contactsForGroup.size() == 0){
        groupsDBTemp.remove(group);
      }
    }
    return groupsDBTemp;
  }

  public void contactRemoveFromGroup(GroupData removeFromGroup, ContactData contact) {
    app.goTo().homePage();
    app.group().selectGroup(By.name("group"),removeFromGroup);
    app.contact().selectContactById(contact.getId());
    app.contact().click(By.name("remove"));
    app.contact().returnToHomePage();
  }
}

