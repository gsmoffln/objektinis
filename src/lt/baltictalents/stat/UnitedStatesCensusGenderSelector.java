package lt.baltictalents.stat;

public class UnitedStatesCensusGenderSelector implements DistributedFunction<Gender>{
    private final int totalPopulation = 308745538;
    private final int malePopulation = 151781326;

    @Override
    public Gender select(double v) {
        assureRange0to1(v);

        // pagal cenzą, parenkam MALE arba FEMALE
        if((int)(v*totalPopulation) < malePopulation) {
            return Gender.MALE;
        }
        return Gender.FEMALE;
    }



    public static void main(String[] args) {
        DistributedFunction<Gender> gender = new UnitedStatesCensusGenderSelector();
        System.out.println(gender.select(0.1));
        System.out.println(gender.select(0.48));
        System.out.println(gender.select(0.5));
        System.out.println(gender.select(0.9));
        System.out.println(gender.select(100.0));

    }

}
