package lt.baltictalents.struct;

import lt.baltictalents.struct.misc.Countable;
import lt.baltictalents.struct.misc.Sortable;

public interface List<T> extends Collection<T>, Countable, Sortable<T> {}

