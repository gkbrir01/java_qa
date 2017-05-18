package pl.stqa.pft.sandbox;

/**
 * Created by gkozlowski on 2017-05-18.
 */
public class Point {
  public double x;
  public double y;

  Point(double x, double y) {
    this.x = x;
    this.y = y;
  }

    public double distance(Point p1, Point p2){
      double distanceX = p1.x - p2.x;
      double distanceY = p1.y - p2.y;
      double distance=Math.sqrt(distanceX*distanceX+distanceY*distanceY);
      return distance;
    }

}
