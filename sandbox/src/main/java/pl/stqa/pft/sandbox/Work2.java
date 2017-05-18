package pl.stqa.pft.sandbox;

/**
 * Created by gkozlowski on 2017-05-18.
 */
public class Work2 {

  public static void main(String[] args){
    Point p1 = new Point(10,15);
    Point p2 = new Point(5,10);

    //Implementation through function
    double distanceFunction = distance(p1,p2);
    System.out.println("Function: the distance between the points p1 i p2 is: "+distanceFunction);

    //Implementation through Point class method
    double distanceMethod = p1.distance(p1,p2);
    System.out.println("Method: the distance between the points p1 i p2 is: "+distanceMethod);
  }

  public static double distance(Point p1, Point p2){
     double distanceX = p1.x - p2.x;
     double distanceY = p1.y - p2.y;
     double distance=Math.sqrt(distanceX*distanceX+distanceY*distanceY);
     return distance;
  }
}
