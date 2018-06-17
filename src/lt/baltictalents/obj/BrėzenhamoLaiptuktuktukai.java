package lt.baltictalents.obj;

import java.io.*;

@SuppressWarnings("Since15") // naudosim Java galimybes ≥ 1.5, nuraminam IDEA, kad nesinervintų
public class BrėzenhamoLaiptuktuktukai {
  /**
   * Čia piešime paveikslą.
   * Paprastumo dėlei, vietoj spalvos imsime kokį nors simbolį
   */
  private final char[][] drobė;
  private final int aukštis;
  private final int plotis;

  /**
   * Sukuria naują {@code aukštis × plotis} drobę, nuspalvintą duotąja {@code „spalva“}.
   * <br>Drobės matmenys paprastumo dėlei bus apibrėžti kaip {@code [0,0]–[plotis-1,aukštis-1] };
   * spalva šio uždavinio kontekste tegu būna suprantama kaip fiksuoto pločio simbolis.
   * <br>Bandant piešti už ribų, drobė lai meta {@link ArrayIndexOutOfBoundsException}.
   * <br>Šis konstruktorius meta klaidą {@link IllegalArgumentException}, jei kuri nors koordinatė yra neigiama.
   * Nulis leidžiamas.
   *
   * <ul><h1>Klausimai:</h1>
   *   <li>Kodėl išimtinę situaciją aprašiau kaip JavaDoc komentarą, bet nedeklaravau {@code throws} direktyva?</li>
   *   <li>Kodėl drobė aprašoma kaip [y][x], o ne atvirkščiai?</li>
   *   <li>Kas būtų, jei netikrinčiau pradinių duomenų? Kodėl reikia, net ir šiuo atveju?</li>
   * </ul>
   * @param aukštis drobės erdvės koordinatė {@code y ≥ 0}
   * @param plotis drobės erdvės koordinatė {@code x ≥ 0}
   * @param spalva pradinė spalva, kuria nudažyta ši drobė
   *
   * @throws IllegalArgumentException jei nurodytas neigiamas drobės {@code aukštis} arba {@code plotis}. Nuliniai leidžiami.
   */
  public BrėzenhamoLaiptuktuktukai(int aukštis, int plotis, char spalva) /*throws IllegalArgumentException */{
    if(aukštis<0){
      throw new IllegalArgumentException("Paveikslo aukštis negali būti neigiamas ("+aukštis+").");
    }

    if(plotis<0){
      throw new IllegalArgumentException("Paveikslo plotis negali būti neigiamas ("+plotis+").");
    }

    this.aukštis = aukštis;
    this.plotis = plotis;

    this.drobė = new char[aukštis][plotis];
    for(int y=0; y<aukštis; y++) {
      // Arrays.fill(drobė[i], spalva);
      for(int x=0; x<plotis; x++){
        this.drobė[y][x] = spalva;
      }
    }

  }

  /**
   * Tekstinė paveikslo išraiška: <pre>
   *   lt.baltictalents.obj.BrėzenhamoLaiptuktuktukai
   *   {@code plotis × aukštis} simbolių – „spalvos“
   *   </pre>
   * pvz: <pre>
   *   lt.baltictalents.obj.BrėzenhamoLaiptuktuktukai
   *   ____x
   *   ___x_
   *   __x__
   *   _x___
   *   x____
   * </pre>
   * @see Object#toString()
   *
   */
  @Override
  public String toString(){
    // Pasiruošiam reikiamo dydžio char[] masyvą ir iš jo pasidarom teksto spausdinimo įrenginį:
    // tai, ką į tą įrenginį rašysime, atsidurs masyve.
    final CharArrayWriter cout = new CharArrayWriter(aukštis*(plotis+2)+4+this.getClass().getName().length());

    // O šitas sugeba spausdinti visokius skaičius ir kitus gerus dalykus į paprastą spausdinimo įrenginį.
    // Gal reikės, o gal ir ne – bet gal pravers kaip patogesnis.
    final PrintWriter out = new PrintWriter(cout);

    out.println(this.getClass().getName());
    // out.print(System.lineSeparator()); // čia yra vienas 1.7 Java naujas dalykas, dėl to anotacija prie klasės

    try {
      this.spausdinti(out);
    }catch(IOException ioe) {
      // Taip negali būti, nebent atsitiko kažkas siaubingo – pvz., pritrūko atminties arba adminas ištraukė laidą iš rozetės.
      // Negalim nieko su tuo padaryti, nebent išmesti klaidą toliau.
      throw new UncheckedIOException(ioe);
    }
    return String.valueOf(cout.toCharArray());
  }


  /**
   * Išveda drobės tekstinį atvaizdą į duotąjį teksto spausdinimo įrenginį.
   * @param out spausdinimo įrenginys
   * @throws IOException spausdinimo klaida: čia jos nesitikiu, nebent atsitiko kažkas tikrai baisaus
   */
  public void spausdinti( Writer out )throws IOException {
    for(int y=0; y<this.aukštis; y++) {
      out.append(String.valueOf(this.drobė[y]));
      out.append(System.lineSeparator()); // žr. aukščiau
    }
  }


  /**
   * Tarnybinis metodas: patikrina, ar nurodyta koordinatė egzistuoja drobėje. Jei X<0, Y<0 arba X≥plotis, Y≥aukštis,
   * byra išimtis {@link ArrayIndexOutOfBoundsException}.
   * @param x horizontali koordinatė, {@code 0≤x<plotis}
   * @param y vertikali koordinatė, {@code 0≤y<aukštis}
   * @throws ArrayIndexOutOfBoundsException jei koordinatė yra už drobės ribų
   */
  private void užtikrintiGerasKoordinates(int x, int y) {
    if(x < 0) { throw new ArrayIndexOutOfBoundsException(String.format("X koordinatė negali būti neigiama (%d)", x)); }
    if(x >= this.plotis) { throw new ArrayIndexOutOfBoundsException(String.format("X koordinatė negali būti didesnė ar lygi pločiui (%d≥%d)", x, this.plotis)); }

    if(y < 0) { throw new ArrayIndexOutOfBoundsException(String.format("Y koordinatė negali būti neigiama (%d)", y)); }
    if(y >= this.aukštis) { throw new ArrayIndexOutOfBoundsException(String.format("Y koordinatė negali būti didesnė ar lygi aukščiui (%d≥%d)", y, this.aukštis)); }
  }


  /**
   * Nupiešia stačiakampį nurodytose koordinatėse nurodyta spalva.
   *
   * @param kairė
   * @param viršus
   * @param plotis
   * @param aukštis
   * @param spalva
   *
   * @throws ArrayIndexOutOfBoundsException jei drobėje tokios koordinatės nėra
   */
  public void pieštiStačiakampį(int kairė, int viršus, int plotis, int aukštis, char spalva) {
    užtikrintiGerasKoordinates(kairė,viršus);
    užtikrintiGerasKoordinates(kairė+plotis, viršus+aukštis);

    final char[][] drobė = this.drobė; // įsimenam čia, nes this.*** irgi kažkiek kainuoja laiko
    for(int y=viršus, apačia=viršus+aukštis; y<apačia; y++) {
      final char[] eilutė = drobė[y];  // t.p. su konkrečia eilute, ją cikle brūžinsim
      for(int x=kairė, dešinė=kairė+plotis; x<dešinė; x++) {
        eilutė[x] = spalva;
      }
    }
  }


  // TODO: [@gsm,2018.06.12] BUGAS kai dx overflowina dy

  /**
   * Nupiešia atkarpą Brėzenhamo algoritmu
   * Piešia tik žemyn dešinėn arba kairėn aukštyn,
   * kai vertikalus pokytis < horizontalų.
   * @param x0 pradžios X
   * @param y0 pradžios Y
   * @param x1 pabaigos X
   * @param y1 pabaigos Y
   * @param spalva simbolis – „spalva“
   */
  public void pieštiAtkarpą(int x0, int y0, int x1, int y1, char spalva) {
    užtikrintiGerasKoordinates(x0,y0);
    užtikrintiGerasKoordinates(x1,y1);

    // apsukam, jei reikia: iš „dešinės aukštyn“ piešiam kaip iš „kairės žemyn“
    if(x1<=x0 & y1<=y0) {
      int t=x0;
      x1 = x0;
      x0 = t;

      t  = y0;
      y0 = y1;
      y1 = t;
    }

    int dx=x1-x0;
    int dy=y1-y0;

    // Čia realizuojam atvejį, kai linija piešiama dešinėn ir apačion, labiau dešinėn nei apačion.
    // Kiti 7 atvejai pasidaro analogiškai, o kai dx=0 arba dy=0, trivialu.
    // KLAUSIMAS: kodėl operatorius "&", o ne "&&"?

    if((dx>0) & (dy>0) & (dx>=dy)) {
      final char[][] drobė=this.drobė;

      // Jeigu šito nepadarysim (t.y. paliksim 0), tai linija bus per pusę pikselio žemiau nei reikia.
      int ax = dx >> 1; // dx/2;
      int ay = dy >> 1; // dy/2;

      for(int x=x0, y=y0; y<=y1 & x<=x1; x++){
        drobė[y][x] = spalva;

        ax += dy;
        ay += dx;

        if(ay >= ax) {
          y++;
          ay -= ax;
          ax = dx >> 1; // Iš principo galėjom pasiskaičiuot: dx/2;
        }
      }
    } else if(dx == 0) {
      if(y0 <= y1) {
        for(int y=y0; y<=y1; y++) {
          drobė[y][x0] = spalva;
        }
      } else {
        for(int y=y1; y<=y0; y--) {
          drobė[y][x0] = spalva;
        }
      }
    } else if(dy == 0) {
      if(x0 <= x1) {
        for(int x=x0; x<=x1; x++) {
          drobė[y0][x] = spalva;
        }
      } else {
        for(int x=x1; x<=x0; x++) {
          drobė[y0][x] = spalva;
        }
      }

     } else {
      // TODO: [@gsm,2018.06.12] atvejai aukštyn-dešinėn ir žemyn-kairėn nerealizuoti
    }


  }


  public static void main(String[] args) throws IOException {
    PrintWriter out = new PrintWriter(System.out);  // rezultatus loginsim ekrane



    // testas: ar susikuria 10x10 masyvas iš Ⓡ
    out.println("10×10 drobė:");
    BrėzenhamoLaiptuktuktukai b = new BrėzenhamoLaiptuktuktukai(10,10,'Ⓡ');
    b.spausdinti(out);
    out.flush();  // PrintWriter tą padaro automatiškai, bet ne visada to norim (KODĖL???)

    // testas: ar vidurys „nusispalvina“ ⓒ
    out.println("Užspalvinam vidurį:");
    b.pieštiStačiakampį(3, 1, 5, 7, 'ⓒ');
    b.spausdinti(out);
    out.flush();

    out.println("Atkarpa2:");
    b.pieštiAtkarpą(0,3,5,3,'H');
    b.spausdinti(out);
    out.flush();

    out.println("Atkarpa3:");
    b.pieštiAtkarpą(7,9,7,5,'V');
    b.spausdinti(out);
    out.flush();

    out.println("Atkarpa1:");
    b.pieštiAtkarpą(1,1,7,5,'Ž');
    b.spausdinti(out);
    out.flush();

  }

}
