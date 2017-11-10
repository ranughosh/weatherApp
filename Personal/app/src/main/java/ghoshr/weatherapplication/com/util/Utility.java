package ghoshr.weatherapplication.com.util;

import java.util.regex.Matcher;

/**
 * Created by ghoshr on 10/24/2017.
 */

public class Utility {
    static String zipPattern="^\\d{5}(?:[-\\s]\\d{4})?$";
    public static boolean isZip(String location){

        if(location.matches(zipPattern))
            return true;
        return false;
    }
}
