package com.suminjin.appbase;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by jspark on 2016-03-11.
 */
public class StringUtils {

    public static boolean isValid(String response) {
        boolean result = true;
        if (response == null || response.isEmpty()) {
            result = false;
        }
        return result;
    }


    /**
     * add right space padding to string
     *
     * @param s
     * @param n
     * @return
     */
    public static String padRightSpace(String s, int n) {
        return String.format("%1$-" + n + "s", s);
    }

    /**
     * add left zero padding to digit string
     *
     * @param d
     * @param n
     * @return
     */
    public static String padLeftZero(int d, int n) {
        return String.format("%0" + n + "d", d);
    }

    /**
     * @param str
     * @return
     */
    public static String setComma(String str) {
        String returnValue = "";
        if (isValid(str)) {
            try {
                int value = Integer.parseInt(str);
                returnValue = new DecimalFormat("#,###").format(value);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return returnValue;
    }

    /**
     * @param yyyyMMddmmss
     * @return
     */
    public static String reformatDateTimeString(String yyyyMMddmmss) {
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddhhmmss");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String result = yyyyMMddmmss;
        try {
            Date d = sdf1.parse(yyyyMMddmmss);
            result = sdf2.format(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }
}
