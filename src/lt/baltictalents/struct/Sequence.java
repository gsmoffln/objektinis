package lt.baltictalents.struct;

public interface Sequence<T> {
  boolean endReached();

  /**
   * Pereina prie kito elemento kolekcijoje. Grąžina save, kad būtų galima
   * parašyti grandinėlę e.selectNext().selectNext(). [....] .getCurrent()
   * @return
   */
  Sequence<T> selectNext();
  T current();
}
