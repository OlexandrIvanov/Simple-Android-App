package com.example.simpleandroidapp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Tools {
    public static String parceDateToRightFormat(String date){
        String rightDate = "";
        Calendar cal;
        Date date1 = null;
        try {
            date1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(date);
        } catch (ParseException e) {
          return rightDate;
        }finally {
            cal = Calendar.getInstance();
            cal.setTime(date1);
            rightDate = cal.get(Calendar.MONTH)+ "-" +cal.get(Calendar.DAY_OF_MONTH) +"-" +cal.get(Calendar.YEAR);
        }
        return rightDate;
    }

}
