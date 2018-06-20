package lt.baltictalents.struct;

import java.util.Random;

public abstract class RandomNumbers extends AbstractCollection<Integer> {

//  @Override
//  public Countable clone() throws CloneNotSupportedException {
//    //return new RandomNumbers();
//  }

  @Override
  public Sequence items() {

    return new Sequence<Integer>() {

      private final Random r = new Random();
      private int current = r.nextInt();


      @Override
      public boolean endReached() {
        return true;
      }

      @Override
      public Sequence<Integer> selectNext() {
        current = r.nextInt();
        return this;
      }

      @Override
      public Integer current() {
        return Integer.valueOf(current);
      }
    };
  }


  public static void main(String[] args) {
    //Collection<Integer> col = new RandomNumbers();
//    System.out.println(col.size());
//
//    Sequence<Integer> items = col.items();
//    System.out.println(items.current());
//    System.out.println(items.current());
//    System.out.println(items.selectNext().current());
//    System.out.println(items.current());



  }


}
