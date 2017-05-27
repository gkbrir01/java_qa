package pl.stqa.pft.addressbook.appmanager;

import com.sun.org.apache.xml.internal.security.utils.HelperNodeList;
import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * Created by gkozlowski on 2017-05-27.
 */
public class NavigationHelper extends HelperBase{

  public NavigationHelper(FirefoxDriver wd) {
    super(wd);
  }

  public void gotoGroupPage() {
    click(By.linkText("groups"));
  }
}
