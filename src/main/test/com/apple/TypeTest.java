package com.apple;

import org.apache.commons.lang.StringUtils;

public class TypeTest {

	/**
	 * @param args
	 * 
	 * 
	 */
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String code = "010101";
		if(StringUtils.isEmpty(code)){
			code = "01";
			System.out.println(code);
		}else{
			int len = code.length();
			if(len>2){
				int count1 = Integer.parseInt(code.substring(len-1));
				count1++;
				String count2 = code.substring(0, len-1);
				System.out.println(count2+String.valueOf(count1));
				
			}
			if(len==2)
			{	int count1 = Integer.valueOf(code);
				count1++;
				code = code+"0"+String.valueOf(count1);
				System.out.println(code);
				
			}
			
			
			
		}
	  
		

	}

}
