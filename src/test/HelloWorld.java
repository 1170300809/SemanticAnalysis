package test;

import java.util.ArrayList;
import java.util.List;

public class HelloWorld {
  public static int value = 2;

  public static void main(String[] args) {
    List<String> strList = new ArrayList<>();

    strList.add("aaa");
    strList.add("bbb");
    strList.add("ccc");

    System.out.println(strList.toString());
    strList.remove(0);
    System.out.println(strList.toString());
    strList.remove(0);
    System.out.println(strList.toString());
    strList.add("ddd");
    System.out.println(strList.toString());
  }
}
