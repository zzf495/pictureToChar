package com.hyznrj.utils;

import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.hyznrj.entity.RgbPixel;

public class RgbNumberWritter {
	private final static Map<String,Character> CACHE=new HashMap<String,Character>();
	
	private final static Character DEFAULT='0';
	private final static String DICTORY=" .,·-'`:!1+*abcdefghijklmnopqrstuvwxyz<>()\\/{}[]?23456789AJKLICFDBEGHMNPQRSTUVWXYZ%&#$0O@";
	private final static int DICTORY_LEN=DICTORY.length();
	public static final int[] counter=new int[DICTORY_LEN];
	public static final int[] counter_poiter=new int[DICTORY_LEN];
	private final static double STEP=255.0/DICTORY_LEN;
	
	public static int getChar(RgbPixel rgb){
		double key =DICTORY_LEN- Math.round(rgb.getGray()/STEP);
		if(key>=1){
			key-=1;
		}
		return (int)key;
	}
	
//	public static void write(BufferedImage image,FileOutputStream fop) throws IOException{
//		/* 遍历整个图像，xy像素点填充字符 */
//		Map<Integer,Integer> map =new HashMap<Integer,Integer>();
//		Map<Integer,Integer> map2 =new HashMap<Integer,Integer>();
//		Set<Integer> set=new HashSet<Integer>();
//		for (int j = image.getMinY(); j < image.getHeight(); j++) {
//			for (int i = image.getMinX(); i < image.getWidth(); i++) {
//				RgbPixel pixel = PictureReader.getScreenPixel(image, i, j);
//				int index = RgbNumberWritter.getChar(pixel);
//				counter[index]++;
//				set.add(index);
//			}
//		}
//		for(Integer i:set){
//			map.put(i, counter[i]);
//			map2.put(counter[i], i);
//			StringOutputter.print("? put ?", i,counter[i]);
//		}
//		Arrays.sort(counter);
//		for(int i=0;i<counter.length;i++){
//			counter_poiter[map2.get(counter[i])]=i;
//			StringOutputter.print("counter_poiter[?]=?", map2.get(counter[i]),i);
//		}
//	}
	public static void write(BufferedImage image,FileOutputStream fop) throws IOException{
		/* 遍历整个图像，xy像素点填充字符 */
		for (int j = image.getMinY(); j < image.getHeight(); j++) {
			for (int i = image.getMinX(); i < image.getWidth(); i++) {
				RgbPixel pixel = PictureReader.getScreenPixel(image, i, j);
				int index = RgbNumberWritter.getChar(pixel);
				fop.write(DICTORY.charAt(index));// 插入字符
				fop.write(32);// 加个空格，防止图片被压扁
			}
			/* 填充换行符 */
			fop.write(10);
		}
	}
}
