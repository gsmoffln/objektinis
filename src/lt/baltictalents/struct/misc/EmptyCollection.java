package lt.baltictalents.struct.misc;

import java.util.Iterator;

public class EmptyCollection<T> extends AbstractCollection<T> implements Countable{

  @Override
  public Countable clone() {
    return new EmptyCollection<>();
  }

  @Override
  public Iterator<T> iterator() {
    return null;
  }
}
