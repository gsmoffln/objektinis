package lt.baltictalents.struct.misc;

import lt.baltictalents.struct.Collection;

public interface ModifiableCollection<T extends Object> extends Collection<T> {
  void add(T item);
  void remove(T item);
}
