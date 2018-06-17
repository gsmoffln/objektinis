package lt.baltictalents.struct;

public class EmptyCollection<T> extends AbstractCollection<T>{

  @Override
  public Collection<T> clone() throws CloneNotSupportedException{
    return new EmptyCollection<>();
  }

}
