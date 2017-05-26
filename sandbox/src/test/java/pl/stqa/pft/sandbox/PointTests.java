package pl.stqa.pft.sandbox;

import org.testng.Assert;
import org.testng.annotations.Test;

public class PointTests {

  //Work3

  @Test
  public void testDistance(){
     Point p1 = new Point(10,15);
     Point p2 = new Point(5,10);
     Assert.assertEquals(p1.distance(p1,p2), 7.0710678118654755);
   }
}
