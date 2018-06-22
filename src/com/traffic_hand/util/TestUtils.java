package com.traffic_hand.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

public class TestUtils {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(new Date());
		
        SimpleDateFormat sdf =   new SimpleDateFormat( " yyyy-MM-dd HH:mm:ss " ); 

        String str = sdf.format(new Date()); 
        System.out.println(str);
	}

}
