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

    Groups groupsDBTemp = app.db().groups();
    for (GroupData GroupDB: groupsDB) {
      Contacts contactGroup = GroupDB.getContacts();
      if(contactGroup.size() == 0){
        groupsDBTemp.remove(GroupDB);
      }
    }

    if(groupsDBTemp.isEmpty()){
      app.goTo().homePage();
      GroupData group = groupsDB.iterator().next();
      ContactData contactAdd = new ContactData().withFirstName("Jan").withLastName("Nowak")
              .withAddress("Warsaw, ul. Magiera 3/22").withMobilePhone("+48601000055").withEmail("jan.nowak@gmail.com")
              .inGroup(group);
              app.contact().create(contactAdd);
      groupsDBTemp.add(group);
    }

    GroupData removeGroup = groupsDBTemp.iterator().next();
    System.out.println("removeGroup "+removeGroup);
    Contacts contacts = removeGroup.getContacts();
    System.out.println("contacts "+contacts);
    ContactData contact = contacts.iterator().next();
    System.out.println("contact "+contact);

    app.goTo().homePage();
    app.group().selectGroup(By.name("group"),removeGroup);
    app.contact().selectContactById(contact.getId());
    app.contact().click(By.name("remove"));
    app.contact().returnToHomePage();
    Groups after =contact.getGroups();
    assertEquals(after.size(), contacts.size() - 1);
    //Contacts after = app.db().contacts();
    //assertThat(after, equalTo(before.without(deletedContact)));
  }
}

