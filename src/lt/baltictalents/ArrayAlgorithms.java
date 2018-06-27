package lt.baltictalents;

import java.util.Objects;

/**
 * Praplėstas {@link java.util.Comparator<T>}.
 * Originalus {@Comparator} interfeisas apibrėžia tik {@code compare(a,b)} semantiką, kuri teigia:
 * <ul>
 *     <li>Jei {@code a == b}, tai {@code assert a.equals(b) }</li>
 *     <li>Jei {@code a.equals(b)}, tai {@code assert a.compare(b) == 0 }</li>
 *     <li>Jei {@code a} ir {@code b} neulyginami, tai {@code assert compare(a,b)==0}</li>
 *     <li>{@code assert signum(compare(a,b)) == -signum(compare(b,a)) }, kur signum = (a)->{a==0? 0 : a>0? 1 : -1} </li>
 * </li>
 * </ul>
 *
 * Čia mes tą semantiką kiek sugriežtinam, įvedam papildomos kontrolės ir apibrėžiam ribinius atvejus.
 * @param <T>
 */
interface WellOrderedComparator<T> extends java.util.Comparator<T> {

    /**
     *
     * @param a
     * @param b
     * @return
     */
    @Override
    int compare(T a, T b);

    /**
     * Ar du objektai apskritai sulyginami. Šabloninė realizacija.
     * @param a
     * @param b
     * @return false, jei nėra galimybių sulyginti, kitu atveju true
     */
    default boolean mayCompare(T a, T b){
        // sulyginami tik du null tarpusavyje
        if(a==null) return (b==null);
        if(b==null) return false;

        // Toliau žinome, kad abu ne null
        // sulyginti galime, jei abu vienos klasės;
        // taip pat – B.Liskov principas, kad paveldėta klasė turi veikti identiškai paveldėtajai.
        return a.getClass().isAssignableFrom( b.getClass() ) ||
                b.getClass().isAssignableFrom( a.getClass() );
    }

    default void requireComparable(T a, T b) {
        if(!mayCompare(a,b)) throw new IllegalArgumentException(String.format("Can't compare %s to %s", a, b ));
    }


    default boolean weaklyLess(T a, T b) {
        final int a_cmp_b = compare(a,b);
        final int b_cmp_a = compare(b,a);

        if(a_cmp_b < 0) {
            if(b_cmp_a >= 0) return true;
            throw new IllegalStateException(String.format("Can't be both a<b and b<a; a=%s, b=%s", a, b));
        }
        if(compare(b,a)>0) {
            return true;
        }
        if(compare(a,b)==0 ) return false;

        throw new IllegalStateException(String.format("Can't be both a<b and b<a; a=%s, b=%s", a, b));
    }


    default boolean strictlyLess(T a, T b) {
        return (compare(a,b)<0) & (compare(b,a)>0);
    }

    default boolean less(T a, T b){
        requireComparable(a,b);
        return weaklyLess(a,b) && strictlyLess(a,b);
    }


    default boolean weaklyGreater(T a, T b) {
        return weaklyLess(b,a);
    }

    default boolean stronglyGreater(T a, T b) {
        return strictlyLess(b,a);
    }

    default boolean greater(T a, T b) {
        return less(b,a);
    }

    /**
     * Neither is less or greater.
     * @param a
     * @param b
     * @return
     */
    default boolean weaklyEquals(T a, T b) {
        return !weaklyLess(a,b) && !weaklyGreater(a,b);
    }

    /**
     * Checks the transitiveness that a≤b & b≤c ⇒ a≤c.
     * @param a
     * @param b
     * @param c
     * @return
     */
    default boolean weaklyOrdered(T a, T b, T c) {
        return weaklyLess(a,b) && weaklyLess(b,c) && weaklyLess(a,c);
    }

    default boolean weaklyLessOrUndecided(T a, T b) {
        return !weaklyGreater(b,a);
    }

    default boolean weaklyGreaterOrUndecided(T a, T b) {
        return !weaklyLess(a,b);
    }

    default boolean notLessNotGreater(T a, T b) {
        return !weaklyLess(a,b) && !weaklyGreater(a,b);
    }
}

public class ArrayAlgorithms<T extends Comparable<T>> {
    public final WellOrderedComparator<T> wellOrderedComparator;

    public ArrayAlgorithms(WellOrderedComparator<T> comp){
        this.wellOrderedComparator = comp;
    }

    public static<T> void requireNonEmpty(T... array){
        Objects.requireNonNull(array);
        if(array.length < 1){
            throw new IllegalArgumentException("Array must not be empty.");
        }
    }

    public T min(T...items) {
        requireNonEmpty(items);

        T min=items[0];
        for(int i=1, n=items.length; i<n; i++){
            if(wellOrderedComparator.strictlyLess(items[i],min)) min = items[i];
        }

        return min;
    }

    public T max(T...items) {
        requireNonEmpty(items);

        T max=items[0];
        for(int i=1, n=items.length; i<n; i++){
            if(wellOrderedComparator.stronglyGreater(items[i],max)) max = items[i];
        }

        return max;
    }


    protected static void validateSubRange(Object[] array, int head, int tail ){
        if(head<0 || head>tail || tail>=array.length) {
            throw new ArrayIndexOutOfBoundsException(
                    String.format("Must be 0 ≤ head ≤ tail < %d, but got head=%d, tail=%d", array.length, head, tail)
            );
        }
    }


    /**
     * Calculates the middle index between left and right, inclusive, with a protection of overflow, by rearranging:
     * (a + b)/2 ⇒ a + (b-a)/2.
     * @param left
     * @param right
     * @return
     */
    public static final int middle(int left, int right){
        return left + ((right-left)>>>1);
    }


    public int findIndex(T[] array, int head, int tail, T item) {
        validateSubRange(array, head, tail);
        int guess = middle(head,tail);

        // note: we modify the arguments
        while(tail>head && !wellOrderedComparator.notLessNotGreater(array[guess],item)) {
            if(wellOrderedComparator.weaklyLess(array[guess],item)){
                head = guess+1;
                guess = middle(head,tail);
            } else {
                tail = guess;
                guess = middle(head,tail+1);
            }
        }

        return guess;
    }

    /**
     * Inserts item into a sorted subrange of array.
     *
     * The destination array must not be full, i.e. there must be at least one cell beyond the end of the given
     * range (`tail´). The range is given as `head´ and `tail´, inclusive.
     * @param array
     * @param head
     * @param tail
     * @param item
     */
    public void insert(T[] array, int head, int tail, T item) {

        // TODO: 18.6.27
    }

    public static void main(String[] args) {
        class IntegerComparator implements WellOrderedComparator<Integer> {

            @Override
            public int compare(Integer a, Integer b) {
                return a-b;
            }
        }

        ArrayAlgorithms<Integer> algo = new ArrayAlgorithms<>(new IntegerComparator());
        System.out.println(algo.findIndex( new Integer[]{1,5,5,5,7,7,10,11,11,11,15},0,1, 10 ));
    }

}

