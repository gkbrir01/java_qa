package pl.stqa.pft.addressbook.tests;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thoughtworks.xstream.XStream;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pl.stqa.pft.addressbook.model.ContactData;
import pl.stqa.pft.addressbook.model.Contacts;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.junit.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase{

  @DataProvider
  public Iterator<Object[]> validContactsFromJson() throws IOException {
    BufferedReader reader = new BufferedReader(new FileReader(new File("addressbook-web-tests/src/test/resources/contacts.json")));
    String json ="";
    String line = reader.readLine();
    while (line != null) {
      json += line;
      line = reader.readLine();
    }
    Gson gson = new Gson();
    List<ContactData> contacts = gson.fromJson(json,new TypeToken<List<ContactData>>(){}.getType()); //List<ContactData>.class
    return contacts.stream().map((c) -> new Object[] {c}).collect(Collectors.toList()).iterator();
  }

  @DataProvider
  public Iterator<Object[]> validContactsFromXML() throws IOException {
    BufferedReader reader = new BufferedReader(new FileReader(new File("addressbook-web-tests/src/test/resources/contacts.xml")));
    String xml ="";
    String line = reader.readLine();
    while (line != null) {
      xml += line;
      line = reader.readLine();
    }
    XStream xstream = new XStream();
    xstream.processAnnotations(ContactData.class);
    List<ContactData> contacts = (List<ContactData>) xstream.fromXML(xml);
    return contacts.stream().map((c) -> new Object[] {c}).collect(Collectors.toList()).iterator();
  }

  @DataProvider
  public Iterator<Object[]> validContactsFromCSV() throws IOException {
    List<Object[]> list = new ArrayList<Object[]>();
    BufferedReader reader = new BufferedReader(new FileReader(new File("addressbook-web-tests/src/test/resources/contacts.csv")));
    String line = reader.readLine();
    while (line != null) {
      String[] split = line.split(";");
      list.add(new Object[] {new ContactData().withFirstName(split[0]).withLastName(split[1]).withAddress(split[2])
              .withMobilePhone(split[3]).withEmail(split[4]).withGroup(split[5])});
      line = reader.readLine();
    }
    return list.iterator();
  }

  @DataProvider
  public Iterator<Object[]> validContacts(){
    List<Object[]> list = new ArrayList<Object[]>();
    list.add(new Object[] {new ContactData().withFirstName("Grzegorz1").withLastName("Kozlowski1").withAddress("Warsaw, ul. Magiera 1")
            .withMobilePhone("+48601200301").withEmail("test1@gmail.com").withGroup("test1")});
    list.add(new Object[] {new ContactData().withFirstName("Grzegorz2").withLastName("Kozlowski2").withAddress("Warsaw, ul. Magiera 2")
            .withMobilePhone("+48601200302").withEmail("test2@gmail.com").withGroup("test1")});
    list.add(new Object[] {new ContactData().withFirstName("Grzegorz3").withLastName("Kozlowski3").withAddress("Warsaw, ul. Magiera 3")
            .withMobilePhone("+48601200303").withEmail("test3@gmail.com").withGroup("test1")});
    return list.iterator();
  }

  @Test(dataProvider = "validContactsFromJson")
  public void testContactCreation(ContactData contact) {
    app.goTo().homePage();
    Contacts before = app.contact().all();
    app.contact().create(contact);
    assertThat(app.contact().count(), equalTo(before.size() + 1));
    Contacts after = app.contact().all();
    assertThat(after, equalTo(
            before.withAdded(contact.withId(after.stream().mapToInt((c) -> c.getId()).max().getAsInt()))));
  }

  @Test (enabled = false)
  public void testCurrentDir(){
    File currentDir = new File(".");
    System.out.println(currentDir.getAbsolutePath());
    File photo = new File("addressbook-web-tests/src/test/resources/Foto.png");
    System.out.println(photo.getAbsolutePath());
    System.out.println(photo.exists());
  }

}
