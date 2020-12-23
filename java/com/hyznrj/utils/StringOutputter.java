package com.hyznrj.utils;

public class StringOutputter {
	public static void print(String str,Object ...value){
		System.out.println(concat(str, value));
	}
	public static String concat(String str,Object ...value){
		StringBuffer sbf=new StringBuffer();
		int pos=0;
		for(int i=0;i<str.length();i++){
			char charAt = str.charAt(i);
			if('?'==charAt){
				sbf.append(value[pos++]);
			}else{
				sbf.append(charAt);
			}
		}
		return sbf.toString();
	}
}
