package lt.baltictalents.struct;

public interface ModifiableCollection<T extends Object> extends Collection<T> {
  void add(T item);
  void remove(T item);
}
