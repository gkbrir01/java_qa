package pl.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pl.stqa.pft.addressbook.model.ContactData;
import pl.stqa.pft.addressbook.model.Contacts;

import java.io.File;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.junit.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

public class ContactModificationTests extends TestBase{

  @BeforeMethod
  public void ensurePreconditions(){
    if(app.db().contacts().size() == 0){
      app.goTo().homePage();
      app.contact().create(new ContactData().withFirstName("Grzegorz22").withLastName("Kozlowski22")
              .withAddress("Warsaw, ul. Magiera 3/22").withMobilePhone("+48601000000").withEmail("gkbrir05@gmail.com"));
    }
  }

  @Test
  public void testContactModification(){
    Contacts before = app.db().contacts();
    ContactData modifiedContact = before.iterator().next();
    ContactData contact = new ContactData()
            .withId(modifiedContact.getId()).withFirstName("Grzegorz88").withLastName("Kozlowski88")
            .withAddress("Warsaw, ul. Magiera 3/22").withHomePhone("+484083699").withMobilePhone("+48601412300")
            .withEmail("gkbrir05@gmail.com");
    app.goTo().homePage();
    app.contact().modify(contact);
    assertEquals(app.contact().count(), before.size());
    Contacts after = app.db().contacts();
    assertThat(after, equalTo(before.without(modifiedContact).withAdded(contact)));
  }

}
