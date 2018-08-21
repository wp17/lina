package com.github.wp17.lina.util;

public class StringUtil {

	public static boolean isEmpty(String arg){
		if (null == arg) {
			return true;
		}
		
		if (arg.trim().equals("")) {
			return true;
		}
		
		return false;
	}
	
	public static boolean isNotEmpty(String arg){
		return !isEmpty(arg);
	}
}
