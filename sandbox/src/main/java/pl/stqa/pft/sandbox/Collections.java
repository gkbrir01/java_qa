package pl.stqa.pft.sandbox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by gkozlowski on 2017-06-08.
 */
public class Collections {

  public static void main(String[] args){
    String[] langs = {"Java", "C#", "Python", "PHP"};

    //List<String> languages = new ArrayList<String>();
    List<String> languages = Arrays.asList("Java", "C#", "Python", "PHP");
    //languages.add("Java");
    //languages.add("C#");
    //languages.add("Python");

    for (String l : languages){
      System.out.println("Chce nauczyć się "+ l);
    }
    //for (int i = 0; i < languages.size(); i++){
      //System.out.println("Chce nauczyć się "+ languages.get(i));
    }
  }

}
