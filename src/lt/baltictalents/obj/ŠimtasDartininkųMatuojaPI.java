package lt.baltictalents.obj;
//import java.util.*;


public class ŠimtasDartininkųMatuojaPI {

  public static final int  KIEK_DARTININKŲ;
  public static final long KIEK_METIMŲ_KIEKVIENAM = 100_000L;
  public static final boolean AR_TESTAVIMAS = true;

  static {

    if(Runtime.getRuntime().availableProcessors() > 1) {
      KIEK_DARTININKŲ = 100;
    } else {
      KIEK_DARTININKŲ = 1;
    }

  }


  // static laukai yra bendri visiems šitos klasės objektam
  static int kiekIšViso = 0;
  static int kiekPataikiau = 0;
  static long pradzia;
  static long pabaiga;

  // Žodelis „volatile“ reiškia, kad būtinai būtinai
  // kaskart būtų žiūrima faktinė lauko reikšmė atmintyje,
  // o ne kažkaip išskaičiuota.
  public static volatile int kiekGijų = 0;

  static final java.util.Random r = new java.util.Random();

  public static void _main(String ... args) {

    (new Thread() {
      public void run() {
        //tikrasMain();
      }
    }).start();

  }


  public static void main(String ... args) {
    pradzia = System.nanoTime();

    // Testuodami mes norime atkartojamos situacijos
    if(AR_TESTAVIMAS) {
      r.setSeed(1337L);
    } else {
      // O realioje sistemoje mes norime tikro veikimo, kuris kaskart skirsis
      r.setSeed(System.nanoTime());
    }

    for (int j = 0; j < KIEK_DARTININKŲ; j++) {

      // Skaičiuosim kiek „gyvų“ vykdymo gijų,
      // kad žinotume, kada skaičiavimai baigėsi.
      // Šitas kodas gali nebūti sinchronizuotas, nes
      // jį vykdome „pagrindinėje“ gijoje.
      // Kaip sinchronizacijos „spyną“ naudosime bendrą objektą Random r,
      // bet iš principo tai gali būti bet kuris
      // kitas bendras objektas, ar net klasė.

      //synchronized(ŠimtasDartininkųMatuojaPI.class){
        kiekGijų++;
      //}

      // Patternas toks:
      // 1. Paveldime iš Thread
      // 2. Thread::run() yra gijos gyvenimas
      // 3. Todėl mes paveldėtą metodą run() pakeičiame savu
      // 4. Susikuriame tokios modifikuotos klasės egzempliorių
      // 5. Thread.start( naujaGija )
      // 6. Susumuojam rezultatus
      // 7. Be to skaičiuojam, kiek yra „gyvų“ gijų, kad žinotume, kada visos baigė darbą
      // 8. NB.! gijų starto ir pabaigos eiliškumas nebūtinai sutampa – žr. tekstą konsolėje!
      new Thread() {

        int yra = 0;
        int visi = 0;

        public void run() {
          System.out.println("Gija " + this + " startuoja.");

          for (long i = 0L; i < KIEK_METIMŲ_KIEKVIENAM; i++) {
            double x = r.nextDouble() * 2.0 - 1.0;
            double y = r.nextDouble() * 2.0 - 1.0;

            if (x * x + y * y <= 1.0) {
              yra++;
            }

            visi++;
          }


          System.out.println("Gija " + this + " viską padarė.");
          synchronized (r) {
            // sumuojam atskirų gijų rezultatus
            kiekIšViso += visi;
            kiekPataikiau += yra;

            // Ir pasižymim, kad baigėm šioje gijoje.
            // Šitas kodas priklauso modifikuotai Thread klasei,
            // todėl statinį lauką kiekGijų (priklausantį klasei ŠimtasDartininkų...)
            // galime pasiekti per tą klasę.
            /*ŠimtasDartininkųMatuojaPI.*/kiekGijų--;
          }
        }
      }.start();
    }

    // laukiam, kol visos gijos baigs darbą
    while (kiekGijų > 0) {
      // kol yra dar skaičiuojančių gijų, sukam ciklą,
      // kaskart biškutį užmigdydami pagrindinę
      try {
        Thread.sleep(1);
      } catch (InterruptedException e) {
        System.out.println("Kas mane per anksti pažadino? "+e);
      }
    }
    pabaiga = System.nanoTime();


    System.out.println("4.0 × "+ kiekPataikiau + "/"+kiekIšViso+" = "+4.0 * kiekPataikiau / kiekIšViso);
    System.out.println("Užtrukau " + (pabaiga - pradzia) / 1000_000_000.0 + " sek.");
  }

}
