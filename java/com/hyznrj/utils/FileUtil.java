package com.hyznrj.utils;

import java.io.File;
import java.io.IOException;

public class FileUtil {
	private static final String POINT = ".";
	/**
	 * 获取从Path和名字
	 * @param src
	 * @return
	 */
	public static String absolutePathAndSuffixName(String src){
		File file=new File(src);
		String str = file.getAbsolutePath();
		int indexOf = str.lastIndexOf(POINT);
		return str.substring(0, indexOf);
	}
	/**
	 * 返回File文件的文件名前缀(.前面的名字)
	 * @param file
	 * @return
	 */
	public static String getSuffixName(File file){
		String str = file.getName();
		int indexOf = str.lastIndexOf(POINT);
		return str.substring(0, indexOf);
	}
	/**
	 * 根据路径（包括不存在目录的情况下）创建文件
	 * @param src
	 * @return
	 * @throws IOException
	 */
	public static File createFile(String src) throws IOException{
		File file =new File(src);
		if(file.isDirectory()){
			return null;
		}
		File parentFile = file.getParentFile();
		if(!parentFile.exists()&&!parentFile.mkdirs()){
			return null;
		}
		if(!file.exists()&&!file.createNewFile()){
			return null;
		}
		return file;
	}
}
