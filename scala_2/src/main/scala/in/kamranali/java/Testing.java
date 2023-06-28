package in.kamranali.java;

import java.util.Arrays;
import java.util.TreeMap;

public class Testing {

  public static void main(String[] args) throws ClassNotFoundException{

    System.out.println(Testing.class);

    Class var = Class.forName("in.kamranali.java.Testing");
    // System.out.println(Arrays.toString(var.getMethods()));


    Testing variable = new Testing();
    Class anotherTestingClass = variable.getClass();
    System.out.println("anotherTestingClass: " + anotherTestingClass);



  }

}
