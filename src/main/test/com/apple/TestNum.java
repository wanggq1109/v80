package com.apple;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TestNum {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMMM-yyyy",  
                Locale.ENGLISH);  
		String engstr= sdf.format(new Date());
		int index = engstr.indexOf("-", 0);
		String str1= engstr.substring(0, index);
		String temp = engstr.substring(index+1);
		int index2 = temp.indexOf("-", 0);
		String str2 = temp.substring(0, index2);
		
		System.out.println(str1);
		System.out.println(str2);
		
		
	}

}
