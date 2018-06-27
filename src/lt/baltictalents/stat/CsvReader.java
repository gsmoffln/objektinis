package lt.baltictalents.stat;

import java.util.Scanner;
import java.util.function.DoubleFunction;
import java.util.function.Supplier;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CsvReader<D> {
    private int skipLines = 1;
    private Scanner scanner = null;

}

class Readers{
    private static final Logger log = Logger.getLogger(Readers.class.getName());


    public static void main(String[] args) {
        Supplier<Object> objGen = Object::new;
        log.info(objGen.get().toString());

        Pattern pattern = Pattern.compile("\\s*(\\S+)\\s+(\\S+)\\s+([0-9]+)\\s*");
        Matcher m = pattern.matcher(" jonas     petraitis 1990   ");
        m.matches();
        System.out.printf("[%s]",m.group(1));

    }
}

