package lt.baltictalents.struct;

import java.util.NoSuchElementException;

public abstract class AbstractCollection<T> implements Collection<T>{

  @Override
  public boolean contains(T elementas) {
    return false;
  }

  @Override
  public int size() {
    return 0;
  }

  @Override
  abstract public Collection<T> clone() throws CloneNotSupportedException;/*{
    return new EmptyCollection<>();
  }*/

  @Override
  public boolean equals(Collection that) {
    return that != null && this==that || that.hashCode()==this.hashCode() && that.size()==this.size();
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

    Collection that = (Collection)o;

    if(that.size() != this.size()) return this.size() - that.size();

    /*
        // TODO: [@gsm,2018.06.14] Čia reikės perbėgti per turinį ir surasti pirmą nesutampantį
        // Bet EmptyCollection neturi elementų, tai dar ir nebėgam
     */

    return 0;
  }
}
