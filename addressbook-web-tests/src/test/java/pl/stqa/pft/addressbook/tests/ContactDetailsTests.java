package pl.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pl.stqa.pft.addressbook.model.ContactData;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.junit.MatcherAssert.assertThat;

public class ContactDetailsTests extends TestBase{

  @BeforeMethod
  public void ensurePreconditions(){
    app.goTo().homePage();
    if (app.contact().all().size() == 0){
      app.contact().create(new ContactData().withFirstName("Grzegorz10").withLastName("Kozlowski4").withAddress("Warsaw, ul. Magiera 3/22").withHomePhone("+48 20 50 50").withMobilePhone("+48601000000").withEmail("gkbrir05@gmail.com").withGroup("test1"));
    }
  }
  @Test
  public void testContactDetails(){
    app.goTo().homePage();
    ContactData contact = app.contact().all().iterator().next();
    ContactData contactInfoFromDetails = app.contact().infoFromDetails(contact);
    app.goTo().homePage();
    ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact);



    assertThat(contactInfoFromDetails.getFirstName(), equalTo(contactInfoFromEditForm.getFirstName()));
    assertThat(contactInfoFromDetails.getLastName(), equalTo(contactInfoFromEditForm.getLastName()));
    assertThat(contactInfoFromDetails.getAllPhones(), equalTo(mergePhones(contactInfoFromEditForm)));
    assertThat(contactInfoFromDetails.getAllEmail(), equalTo(mergeEmails(contactInfoFromEditForm)));

  }

  private String mergePhones(ContactData contact) {
    return Arrays.asList(contact.getHomePhone(), contact.getMobilePhone(),contact.getWorkPhone())
            .stream().filter((s) -> ! s.equals(""))
            .collect(Collectors.joining("\n"));
  }
  private String mergeEmails(ContactData contact) {
    return Arrays.asList(contact.getEmail(), contact.getEmail2(),contact.getEmail3())
            .stream().filter((s) -> ! s.equals(""))
            .collect(Collectors.joining("\n"));
  }



}
