package lt.baltictalents;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;

class ComparatorByDate implements Comparator<Studentas> {

    @Override
    public int compare(Studentas s1, Studentas s2) {
        return s1.getGimData() - s2.getGimData();
    }
}

public class Studentas implements Comparable<Studentas>{
    private final String vardas;
    private final String pavarde;
    private final int gimData;


    public String getVardas() {
        return vardas;
    }

    public String getPavarde() {
        return pavarde;
    }

    public int getGimData() {
        return gimData;
    }

    @Override
    public String toString() {
        return "Studentas("+hashCode()+"){" +
                "vardas='" + vardas + '\'' +
                ", pavarde='" + pavarde + '\'' +
                ", gimData=" + gimData +
                '}';
    }

    public Studentas(String vardas, String pavarde, int gimData) {
        this.vardas = vardas;
        this.pavarde = pavarde;
        this.gimData = gimData;
    }

    //@Override
    public int compareTo(Studentas that) {
        Objects.requireNonNull(that);
        if(this.getClass() != that.getClass()) {
            throw new IllegalArgumentException("Negaliu lygintis su " + that.getClass().getSimpleName());
        }

        if(!this.getPavarde().equals( that.getPavarde() )) {
            return this.getPavarde().compareTo(that.getPavarde());
        }
        if(!this.getVardas().equals( that.getVardas() )) {
            return this.getVardas().compareTo(that.getVardas());
        }
        if(this.getGimData() != that.getGimData() ) {
            return this.getGimData() - that.getGimData();
        }
        return 0;
    }

    @Override
    public boolean equals(Object that) {
        if(that == null) return false;
        if(that.getClass() != this.getClass() ) return false;

        // that yra Studentas
        return equals( (Studentas)that );
    }

    private int hashCode = 0;
    @Override
    public int hashCode() {
        if(hashCode != 0) return hashCode;

        return hashCode =
                ((pavarde.hashCode() * 37 )
                        + vardas.hashCode())*37
                + gimData*37*37*37;
    }

    public boolean equals(Studentas that) {
        return
           this != null &&
           this.getClass().equals( that.getClass() ) &&
           this.hashCode() == that.hashCode() &&
           this.pavarde.equals( that.pavarde ) &&
           this.vardas.equals( that.vardas ) &&
           (this.gimData == that.gimData) &&
                true;


    }


    final static Studentas trumpas = new Studentas("Donaldas", "Trumpas", 1946);
    final static int trumpoHashas = trumpas.hashCode();


    public static final Comparator<Studentas> SORT_BY_NAME = (s1,s2)->s1.getVardas().compareTo(s2.getVardas());
    public static final Comparator<Studentas> SORT_BY_FAMILY_NAME = (s1,s2)->s1.getPavarde().compareTo(s2.getPavarde());
    public static final Comparator<Studentas> SORT_BY_BIRTH_DATE = (s1,s2)->s1.getGimData() - s2.getGimData();

//-------------------------------------------------------------------
//-------------------------------------------------------------------
//-------------------------------------------------------------------
//-------------------------------------------------------------------
//-------------------------------------------------------------------
//-------------------------------------------------------------------
    public static void main(String[] args) {
        Studentas[] stud = new Studentas[]{
                trumpas,
                new Studentas("Bronius", "Verüga", 1990),
                new Studentas("Česlovas", "Veryga", 1985),
                new Studentas("Galina", "Blanka", 1995),
                new Studentas("Galina", "Blanka", 1985)
        };

//        System.out.println(Arrays.toString(stud));
        Arrays.sort(stud, new ComparatorByDate());
        System.out.println(Arrays.toString(stud));
        Arrays.sort(stud,
                new Comparator<Studentas>(){
                    public int compare(Studentas s1, Studentas s2) {
                        return -s1.getPavarde().compareTo(s2.getPavarde());
                    }
                }
                );

        Arrays.sort(
          stud,
                (s1,s2) -> s1.getVardas().compareTo(s2.getVardas())
        );

        Comparator<Studentas> pagalVarda = (s1,s2)->s1.getVardas().compareTo(s2.getVardas());
        Arrays.sort( stud, pagalVarda );

        Arrays.sort( stud, SORT_BY_BIRTH_DATE );
        Arrays.sort( stud, SORT_BY_NAME );
        Arrays.sort( stud, SORT_BY_FAMILY_NAME);
        SORT_BY_NAME.thenComparing(SORT_BY_FAMILY_NAME).thenComparing(SORT_BY_BIRTH_DATE);

        Comparator<Studentas> combined =
                ((Comparator<Studentas>)(s1,s2)->s1.getPavarde().compareTo(s2.getPavarde()))
                .thenComparing( (s1,s2)->s1.getVardas().compareTo(s2.getVardas()))
                .thenComparing( (s1,s2)->s1.getGimData() - s2.getGimData());

        System.out.println(Arrays.toString(stud));



        String elm = "d" + "i" + "v";
        switch(elm) {
            case "html" : /*....*/ break;
            case "body" : /*....*/ break;
            case "div"  :
                System.out.println("DIVas!!!!!");
                break;
            default:
        }

//        System.out.println(stud[0].hashCode());
        switch(stud[0].hashCode() % 10) {
            case 0 /*trumpoHashas*/:
                //System.out.println("TRUMPAS !!!!!");
                if(stud[0]==(trumpas)) {
                    System.out.println("TRUMPAS !!!!!");
                } // else .......
//                ....
//                ....
                break;

            default:
        }

    }
}
