package com.netgame.netgame.commons;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by arkanay on 22/11/17.
 */

public class UtilsIO {

    // format : "yyyy-MM-dd'T'HH:mm:ss"
    public Date stringToDate (String format, String stringDate) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        Date date = new Date();
        try {
            date = dateFormat.parse(stringDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    //format : yyyy-MM-dd HH:mm:ss
    public String dateToString(Date date, String format){
        DateFormat dateFormat = new SimpleDateFormat(format);
        String stringDate = dateFormat.format(date);
        return stringDate;
    }

    public static String getStringDateNow(String sFormat){
        //input yyyy-MM-dd HH:mm:ss
        SimpleDateFormat sdfDate = new SimpleDateFormat(sFormat);
        Date now = new Date();
        String strDate = sdfDate.format(now);
        return strDate;
    }

    public static Date getDateNow(String sFormat){
        SimpleDateFormat formatDate = new SimpleDateFormat(sFormat);
        String fecha = getStringDateNow(sFormat);
        Date date = null;
        try {
            date = formatDate.parse(fecha);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String formatStringToString(String inputString, String firstFormat, String toFormat){
        SimpleDateFormat fromUser = new SimpleDateFormat(firstFormat);
        SimpleDateFormat myFormat = new SimpleDateFormat(toFormat);
        String reformattedStr = "";
        try {
            reformattedStr = myFormat.format(fromUser.parse(inputString));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return reformattedStr;
    }

}
