package com.traffic_hand.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LocalStringUtils {

    
    public static String getDate() {
        SimpleDateFormat sdf =   new SimpleDateFormat( " yyyy-MM-dd HH:mm:ss " ); 
        String str = sdf.format(new Date());
        return str;
    }
    
    public static String getDate(String exp) {
        SimpleDateFormat sdf =   new SimpleDateFormat( exp ); 
        String str = sdf.format(new Date());
        return str;
    }
}
