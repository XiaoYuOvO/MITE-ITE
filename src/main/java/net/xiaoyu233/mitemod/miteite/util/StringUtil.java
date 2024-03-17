package net.xiaoyu233.mitemod.miteite.util;

public class StringUtil {
    public static String intToRoman(int value){
        String[][]r = {
                {"","I","II","III","IV","V","VI","VII","VIII","IX"},
                {"","X","XX","XXX","XL","L","LX","LXX","LXXX","XC"},
                {"","C","CC","CCC","CD","D","DC","DCC","DCCC","CM"},
                {"","M","MM","MMM"}
        };

        return r[3][value / 1000 % 10] +
                r[2][value / 100 % 10] +
                r[1][value / 10 % 10] +
                r[0][value % 10];
    }
}
