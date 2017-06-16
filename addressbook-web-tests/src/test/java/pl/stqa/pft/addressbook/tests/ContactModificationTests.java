package pl.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pl.stqa.pft.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

public class ContactModificationTests extends TestBase{

  @BeforeMethod
  public void ensurePreconditions(){
    app.goTo().homePage();
    if (app.contact().all().size() == 0){
      app.contact().create(new ContactData().withFirstName("Grzegorz10").withLastName("Kozlowski4").withAddress("Warsaw, ul. Magiera 3/22").withMobilePhone("+48601000000").withEmail("gkbrir05@gmail.com").withGroup("test1"));
    }
  }

  @Test
  public void testContactModification(){
    Set<ContactData> before = app.contact().all();
    ContactData modifiedContact = before.iterator().next();
    ContactData contact = new ContactData()
            .withId(modifiedContact.getId()).withFirstName("Grzegorz4").withLastName("Kozlowski3").withAddress("Warsaw, ul. Magiera 3/22").withHomePhone("+484083699").withEmail("gkbrir05@gmail.com");
    app.contact().modify(contact);
    Set<ContactData> after = app.contact().all();
    Assert.assertEquals(after.size(), before.size());

    before.remove(modifiedContact);
    before.add(contact);
    Assert.assertEquals(before,after);
  }

}
