package lt.baltictalents.stat;

import java.io.*;
import java.util.StringTokenizer;

public class Pair implements DistributedFunction<String> {
  private String name;
  private double probability;
  static int personCount = 0;

  private Pair(){};

  public Pair(String name, double probability){
    this.name = name;
    this.probability = probability;
  }
  private static Pair[] zmoniuMasyvas = new Pair[1000];

  public void skaityk() throws IOException { /* skaitymo metodą paskui padaryt private, ir panaudot jį kitam klasės
                                                metode select() */

    BufferedReader br = new BufferedReader(new FileReader("src/lt/baltictalents/stat/mazaiVardu.txt"));
    String line;
    String name;
    double probability;
    int i = 0;

    //metodui split iškart nusakyt, kiek tarpų yra tarp žodžių??
    while((line = br.readLine()) != null){
      StringTokenizer st = new StringTokenizer(line);
      name = st.nextToken();
      probability = Double.parseDouble(st.nextToken());
      //System.out.println(line);
      Pair person = new Pair(name, probability);
      zmoniuMasyvas[i++] = person;
      personCount++;
    }
    //Failo uždarymas????

  }

  @Override
  public String select(double v) {
    return null;
  }

  public static void main (String[] args) throws IOException {

    Pair vardas = new Pair();
    vardas.skaityk();
    //////////////////////////////////
    System.out.println("zmoniu masyvo dydis: " + zmoniuMasyvas.length);
    /////////////////////////////////
    //Ar reiktų naudoti jau sukurtą struct masyvą??? (1000 nes pats tiek sukūriau)
    //Sukūriau personCount skaitiklį masyvo ciklui


    //test
    for (int i = 0; i < vardas.personCount; i++){
      System.out.println(zmoniuMasyvas[i].name + " " + zmoniuMasyvas[i].probability);
    }
  }
}
