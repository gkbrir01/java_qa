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

    ///Method of removing contact that belong to all groups
    Contacts contactsDBTemp = removeContactToAllGroup(contactsDB, groupsDB);

    //If each contact belongs to all groups, call the method that removes the contact from the group
    if(contactsDBTemp.isEmpty()){
      ContactRemoveWithGroupTests contactWithGroup = new ContactRemoveWithGroupTests();
      contactWithGroup.contactRemoveFromGroup(groupsDB.iterator().next(), contactsDB.iterator().next());
      contactsDB = app.db().contacts();
      contactsDBTemp = removeContactToAllGroup(contactsDB, groupsDB);
    }

    ContactData contact =contactsDBTemp.iterator().next();
    Groups before = contact.getGroups();
    //Method returns the groups that can be added to a contact
    Groups groupsDBTemp = removeGroupWithContact(groupsDB, before);
    GroupData addGroup = groupsDBTemp.iterator().next();

    contactAddToGroup(contact, addGroup);

    Contacts contactsDBAfter = app.db().contacts();
    Groups after = new Groups();
    for (ContactData afterContact : contactsDBAfter) {
      if (afterContact.equals(contact)){
        after = afterContact.getGroups();
      }
    }

    assertEquals(after.size(), before.size() + 1);
    assertThat(after, equalTo(before.withAdded(addGroup)));
  }

  //Method of removing contact that belong to all groups
  private Contacts removeContactToAllGroup(Contacts contactsDB, Groups groupsDB) {
    Contacts contactsDBTemp = app.db().contacts();
    for (ContactData contactDB: contactsDB) {
      Groups groupsForContact = contactDB.getGroups();
      if(groupsDB.equals(groupsForContact)){
        contactsDBTemp.remove(contactDB);
      }
    }
    return contactsDBTemp;
  }

  //Method returns the groups that can be added to a contact
  private Groups removeGroupWithContact(Groups groupsDB, Groups before) {
    Groups groupsDBTemp = app.db().groups();
    for (GroupData groupContact : before) {
      for (GroupData group : groupsDB) {
        if (group.equals(groupContact)) {
           groupsDBTemp.remove(group);
         }
      }
    }
    return groupsDBTemp;
  }

  public void contactAddToGroup(ContactData contact, GroupData addGroup) {
    app.goTo().homePage();
    app.group().selectGroupAll(By.name("group"));
    app.contact().selectContactById(contact.getId());
    app.group().selectGroup(By.name("to_group"),addGroup);
    app.contact().click(By.name("add"));
    app.contact().returnToHomePage();
    app.contact().returnToHomePage();
  }
}

