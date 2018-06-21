package lt.baltictalents.struct;

import lt.baltictalents.struct.misc.AbstractCollection;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;

public class SimpleArrayList<T> extends AbstractCollection<T> implements List<T> {


  protected int size = 0;
  protected Object[] items;
  protected long version = 0;

  /**
   * Sukuria tuščią sąrašą su maksimalia talpa initialSize;
   *
   * @param initialSize
   */
  public SimpleArrayList(int initialSize) {
    items = new Object[initialSize];
  }

  /**
   * Nuosekliąja paieška randa, ar yra bent vienas elementas items[i].equals(item)
   * @param item ko ieškom
   * @return {@true}, kai tik randa pirmą arba {@code false} jei neranda peržiūrėjus visus elementus
   */
  @Override
  public boolean contains(T item) {
    for(int i=0, n=size(); i<n; i++) {
      if(items[i].equals(item)) return true;
    }
    return false;
  }

  /**
   * @return tikras dydis, o ne paveldėtas iš tuščios kolekcijos
   */
  @Override
  public int size() {
    return this.size;
  }

  /**
   * Sukuria pilnai užpildytą sąrašą iš masyvo
   *
   * @param items elementai, kuriais užpildyti
   */
  public SimpleArrayList(T... items) {
    this.items = items.clone();
  }

  /**
   * Sukuria naują šio sąrašo kopiją su nauju masyvu.
   *
   * @return
   */
  @Override
  public SimpleArrayList<T> clone() {
    SimpleArrayList<T> newList = new SimpleArrayList<>(this.size);
    newList.items = this.items.clone();
    return newList;
  }

  @Override
  public String toString() {
    Object[] elementųMasyvas = this.items;
    @SuppressWarnings("UnnecessaryLocalVariable") String masyvoTekstas = Arrays.toString(elementųMasyvas);

    return masyvoTekstas;
  }


  /**
   * Patikrina, ar pakeitimo
   * @param oldVersion
   */
  protected void checkVersion(long oldVersion) {
    if (version > oldVersion) {
      throw new ConcurrentModificationException("Kažkas modifikavo vidinį masyvą, jo kopijavimo metu.");
    }
  }


  /**
   * Patikrina, kad yra vietos sutalpinti tiek elementų.
   * Šioje paprasčiausioje realizacijoje tik nulūžta, jei nėra.
   *
   * @param size
   * @throws IndexOutOfBoundsException jei nėra vietos.
   */
  protected void ensureSize(int size) {
    if (size > items.length) {
      throw new ArrayIndexOutOfBoundsException("Sąrašas perpildytas!!!");
    }
  }

  protected static void ensureInRange(int what, int min, int max) {
    if (what < min || what > max) {
      throw new ArrayIndexOutOfBoundsException(what + " is not in range [" + min + "," + max + "]");
    }
  }

  /**
   * Prideda elementą į sąrašo pabaigą.
   * Jei sąrašas perpildytas, generuojama {@link ArrayIndexOutOfBoundsException}
   *
   * @param item
   * @throws {@link IndexOutOfBoundsException} jei sąrašas jau maksimaliai užpildytas
   */
  public void add(T item) {
    ensureSize(this.size + 1);

    items[size] = item;
    size++;

    version++;
  }

  /**
   * Prideda elementą į nurodytą sąrašo pabaigą.
   * Jei sąrašas perpildytas, generuojama {@link ArrayIndexOutOfBoundsException}
   *
   * @param item
   * @throws {@link IndexOutOfBoundsException} jei sąrašas jau maksimaliai užpildytas
   */
  public void add(int index, T item) {
    ensureInRange(index, 0, this.size);
    ensureSize(this.size + 1);

    long version = ++this.version;
    size++;

    //noinspection ManualArrayCopy  -- darom taip tyčia, IDEA siūlo perrašyt su System::arraycopy()
    for (int i = size - 1; i > index; i--) {
      items[i] = items[i - 1];
    }

    items[index] = item;

    checkVersion(version);
  }

  /**
   * Prideda daug elementų į sąrašo pabaigą.
   *
   * @param items
   * @throws ArrayIndexOutOfBoundsException jei sąrašas perpildytas.
   */
  @SafeVarargs
  public final void add(T... items) {
    ensureSize(this.size + items.length);
    final long version = ++this.version;

    for (int i=0, n=items.length; i<n; i++) {
      T item = items[i];
      this.items[size] = item;
      size++;
    }

    checkVersion(version);
  }

  /**
   * Šito reikia tam, kad galėčiau kolekciją naudoti for( : ) išraiškoje.
   *
   * @return
   */
  @Override
  public Iterator iterator() {
    throw new UnsupportedOperationException("TODO kada nors ateityje");
  }

  /**
   * grąžina surikiuotą savo paties kopiją
   *
   * @return
   */
  @SuppressWarnings("unchecked") // needs to clone generic T[]
  @Override
  public SimpleArrayList<T> sort() {
    Object[] items = this.items.clone();
    Arrays.sort(items);
    return new SimpleArrayList(items);
  }


  public static void main(String... args) {

    SimpleArrayList<String> l = new SimpleArrayList<>(2);  // paprasčiausiam masyvu paremtam sąrašui mes privalom nurodyti dydį ir jo neviršyti
    l.add("xxx", "yyy");                                    // todėl turime įsitikinti, kad išsiskyrėme pakankamai daug vietos iš anksto ir įvesti saugiklius perpildymui
    // l.add("zzz")         // <---- čia jau būtų perpildymo klaida

    SimpleArrayList<String> l2 = new DynamicArrayList<>(); // augantis sąrašas pasirūpina, kad visuomet būtų paruoštas pakankamai didelis masyvas
    l2.add("xxx", "yyy");  // bet mes negalime patikimai prognozuoti, kada bus išprovokuotas pilnas masyvo perkopijavimas į didesnį.
    l2.add("zzz");        // todėl: a) programa gali užstrigti ilgam ir netinkamu laiku, b) kopijavimo metu prarasime KAI KURIUOS pakeitimus, kuriuos per tą laiką
    // atliks kitos gijos, pvz. naujus elementus

    l2.add(2, "z2");
    l2.add(4, "z4");
    l2.add(3, "z3");
    l2.add(6, "z7");

    System.out.println(l2.toString());
  }

}


