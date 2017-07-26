package pl.stqa.pft.soap;

import net.webservicex.GeoIP;
import net.webservicex.GeoIPService;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class GeoIpServiceTests {

  @Test
  public void testMyIp(){
    GeoIP geoIP = new GeoIPService().getGeoIPServiceSoap12().getGeoIP("193.27.6.12");
    assertEquals(geoIP.getCountryCode(), "POL");
  }

  @Test
  public void testInvalidIp(){
    GeoIP geoIP = new GeoIPService().getGeoIPServiceSoap12().getGeoIP("193.27.6.xxx");
    assertEquals(geoIP.getCountryCode(), "POL");
  }
}
