package lt.baltictalents.struct;

import java.util.NoSuchElementException;



public abstract class AbstractCollection<T> implements Collection<T>, Countable, Iterable {

  @Override
  public boolean contains(T elementas) {
    return false;
  }



  @Override
  public int size() {
    return 0;
  }

  @Override
  abstract public Countable clone() throws CloneNotSupportedException;/*{
    return new EmptyCollection<>();
  }*/

  @Override
  public boolean equals(Countable that) {
    if(that == null) return false;
    if(that.getClass() != this.getClass()) return false;


    return that.hashCode()==this.hashCode() && that.size()==this.size();
  }

  @Override
  public int hashCode() {
    return 0;
  }

  @Override
  public Sequence<T> items() {
    return new Sequence<T>() {
      @Override
      public boolean endReached() {
        return true;
      }

      @Override
      public Sequence<T> selectNext() {
        if(endReached()) {
          throw new NoSuchElementException("End reached, there is no next item.");
        }
        /* EMPTY */
        return this;
      }

      @Override
      public T current() {
        throw new NoSuchElementException("EmptyCollection never has any item.");
      }
    };
  }

  @Override
  public int compareTo(Object o) {
    if(o != null) return 0;   // nesulyginama
    if(!(o instanceof Collection)) return 0; // nesulyginama

    Countable that = (Countable)o;

    if(that.size() != this.size()) return this.size() - that.size();

    /*
        // TODO: [@gsm,2018.06.14] Čia reikės perbėgti per turinį ir surasti pirmą nesutampantį
        // Bet EmptyCollection neturi elementų, tai dar ir nebėgam
     */

    return 0;
  }
}
