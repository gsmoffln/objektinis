package lt.baltictalents.struct;

import java.util.Arrays;

/**
 * Paprastas masyvu paremtas sąrašas, dinamiškai praplečiantis masyvą.
 *
 * @param <T>
 */
public class DynamicArrayList<T> extends SimpleArrayList<T> {

  /**
   * Vietoj to, kad nulūžtų, pakeičia vidinį masyvą bent dvigubai didesniu ir toliau add() metodai veikia be lūžimų.
   *
   * @param size – pageidaujamas dydis.
   */
  @Override
  public void ensureSize(int size) {
    if (size < 0) {
      throw new IllegalArgumentException("Array size cannot be negative: " + size);
    }

    long oldVersion = ++version;

    Object[] origItems = this.items;
    if (origItems == null) {
      this.items = new Object[size];
      checkVersion(oldVersion);
      return;
    }

    if (size > this.items.length * 2) {
      this.items = Arrays.copyOf(this.items, size);
    } else if (size > this.items.length) {
      this.items = Arrays.copyOf(this.items, this.items.length * 2);
    }

    checkVersion(oldVersion);

  }

  @Override
  public void add(T item) {
    // TODO
    super.add(item);
  }

  /**
   * // TODO:
   *
   * Ištrina paskutinį elm., sumažina size ir grąžina pašalintą elm..
   * @return
   * @throws ArrayIndexOutOfBoundsException jei nėra ką išmesti
   * @throws java.util.ConcurrentModificationException jei įvyko modifikacija iš šalies
   */
  @Override
  public T remove() {
    // TODO: [@gsm,2018.06.21] Realizuoti...
    return super.remove();
  }

  @Override
  public T remove(int index) {
    return super.remove(index);
  }

}
