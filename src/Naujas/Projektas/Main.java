package Naujas.Projektas;

import java.util.*;

public class Main {
  public static void main(String args[]) {
    int mas[] = {4,5,6,7,8};
    int suma = 0;
   
    for (int x : mas) {
      suma+= x;
      System.out.println(x);
    }
    System.out.println();
    System.out.println(suma);

  }

  public static void main(String[] args) {

    class TestSort2{
      public void main(String args[]){

        ArrayList<Integer> al=new ArrayList<Integer>();
        al.add(Integer.valueOf(201));
        al.add(Integer.valueOf(101));
        al.add(230);// atitinka Integer.valueOf(230)

        Collections.sort(al);

        Iterator itr=al.iterator();
        while(itr.hasNext()){
          System.out.println(itr.next());
        }
      }
    }

  }
}