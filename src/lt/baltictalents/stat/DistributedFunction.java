package lt.baltictalents.stat;

import java.util.List;
import java.util.function.DoubleFunction;

/**
 * Funkcija, kuri pagal sukauptą skirstinį parenka reikšmę
 * @param <R>
 */
public interface DistributedFunction<R> {
    default void assureRange0to1(double v) {
        // TODO: klausimas, kodėl netinka sąlyga if(v<0.0 | v>1.0) ?
        if(!(v>=0.0) | !(v<=1.0)){
            throw new IllegalArgumentException(
                    String.format(
                        "This distribution (%s) is only defined for 0≤v≤1, but got v=%f",
                        this.getClass().getName(), v
                    ));
        }
    }

    /**
     * Šitą reikia realizuoti 
     * // TODO: 18.6.27  
     * @param v
     * @return
     */
    R select(double v);

}

