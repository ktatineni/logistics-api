package com.logistics.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {

	public static void main(String[] args) {
		
		/*int count = 0;
		String words = "20.01.01.01";
		// TODO Auto-generated method stub
		for(int i=0; i<words.length(); i++){
			//System.out.println("Count :: "+words);
			System.out.println("Before :: "+words);
			if( words.contains(".") ){
				System.out.println("After :: "+words);
	            count++;  
			System.out.println("Count :: "+count);
			}
	    }*/
		
		
		 String str = "20.01.01.01";
		    Pattern p = Pattern.compile("[.!?]");
		    Matcher matcher = p.matcher(str);
		    int count = 0;
		    while(matcher.find()) {
		        count++;
		    }
		    System.out.println("Count : " + count);
		
	}

}
