package lt.baltictalents.stat;

import java.util.Arrays;

public class UnitedStatesAgeDistribution implements DistributedFunction<Integer> {
    private static final int[] totalPopulationByAge = {
            3944153,
            3978070,
            4096929,
            4119040,
            4063170,
            4056858,
            4066381,
            4030579,
            4046486,
            4148353,
            4172541,
            4114415,
            4106243,
            4118013,
            4165982,
            4242820,
            4316139,
            4395295,
            4500855,
            4585234,
            4519129,
            4354294,
            4264642,
            4198571,
            4249363,
            4262350,
            4152305,
            4248869,
            4215249,
            4223076,
            4285668,
            3970218,
            3986847,
            3880150,
            3839216,
            3956434,
            3802087,
            3934445,
            4121880,
            4364796,
            4383274,
            4114985,
            4076104,
            4105105,
            4211496,
            4508868,
            4519761,
            4535265,
            4538796,
            4605901,
            4660295,
            4464631,
            4500846,
            4380354,
            4291999,
            4254709,
            4037513,
            3936386,
            3794928,
            3641269,
            3621131,
            3492596,
            3563182,
            3483884,
            2657131,
            2680761,
            2639141,
            2649365,
            2323672,
            2142324,
            2043121,
            1949323,
            1864275,
            1736960,
            1684487,
            1620077,
            1471070,
            1455330,
            1400123,
            1371195,
            1308511,
            1212865,
            1161421,
            1074809,
            985721 ,
            914723 ,
            814211 ,
            712908 ,
            640619 ,
            537998 ,
            435563 ,
            344987 ,
            281389 ,
            216978 ,
            169449 ,
            129717 ,
            95223  ,
            68138  ,
            45900  ,
            32266  ,
            53364  ,
            0
    };

    private static int totalCount = 0;

    public static final int[] youngerThanByAge;

    static {
        youngerThanByAge = new int[totalPopulationByAge.length];
        int count = 0;
        for(int i=0, n=totalPopulationByAge.length; i<n; i++){
            youngerThanByAge[i] = count+=totalPopulationByAge[i];
        }
        totalCount = count;
    }

    @Override
    public Integer select(double v) {
        return youngerThanByAge[Math.abs(Arrays.binarySearch(youngerThanByAge, (int)(v*totalCount) ))];
    }

    public static void main(String[] args) {
        DistributedFunction<Integer> age = new UnitedStatesAgeDistribution();
        System.out.println( age.select(0.0) );
        System.out.println( age.select(0.1) );
        System.out.println( age.select(0.5) );
        System.out.println( age.select(0.7) );
        System.out.println( age.select(0.9999) );
    }
}
