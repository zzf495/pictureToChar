package com.hyznrj.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

import com.hyznrj.entity.PictureCommandEntity;
import com.hyznrj.entity.RgbPixel;

/**
 * 指令解析器
 * @date 2019年4月9日
 */
public class CommandExplainer {
	public static void zipPicture(PictureCommandEntity explain,BufferedImage image){
		/* 压缩 */
		if(explain.getWidth()>0||explain.getHeight()>0){
			image=PictureReader.zipImageFileWithRate(image, explain.getWidth(), explain.getHeight());
			if(explain.isSaveZip()){
				boolean save = false;
				try {
					save = PictureReader.saveBufferedImage(image, explain.getAbsolutePathAndSuffixName() + "_zip.jpg");
				} catch (IOException e) {
					e.printStackTrace();
				}
				if(!save){
					StringOutputter.print("压缩图片保存失败");
				}
			}
		}else{
			StringOutputter.print("压缩图片:跳过");
		}
	}
	public static void run(String cmd) {
		PictureCommandEntity explain = CommandExplainer.explain(cmd);
		explain.print();
		/* 获取image对象 */
		BufferedImage image = null;
		try {
			image = PictureReader.getBufferedImage(explain.getSrc());
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		/*是否先压缩*/
		if(explain.isZipFirst()){
			zipPicture(explain,image);
		}
		/* 判断是否灰度化 */
		if (explain.isGray()) {
			try {
				image = PictureReader.getGrayBufferedImage(image);
				/* 判断是否需要保存 */
				if (explain.isSavePic()) {
					boolean save=PictureReader.saveBufferedImage(image, explain.getAbsolutePathAndSuffixName() + "_gray.jpg");
					if(!save){
						StringOutputter.print("灰度化图片保存失败");
					}else{
						StringOutputter.print("灰度化处理:成功");
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				StringOutputter.print("灰度化处理失败");
				return;
			}
		}else{
			StringOutputter.print("灰度化处理:跳过");
		}
		/* 判断是否二值化 */
		if (explain.isBinary()) {
			try {
				image = PictureReader.getBinaryBufferedImage(image);
				/* 判断是否需要保存 */
				if (explain.isSavePic()) {
					boolean save=PictureReader.saveBufferedImage(image, explain.getAbsolutePathAndSuffixName() + "_binary.jpg");
					if(!save){
						StringOutputter.print("二值化图片保存失败");
					}else{
						StringOutputter.print("二值化处理:成功");
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				StringOutputter.print("二值化处理失败");
				return;
			}
		}else{
			StringOutputter.print("二值化处理:跳过");
		}
		
		if(!explain.isZipFirst()){
			zipPicture(explain,image);
		}
		try {
			/* 创建文本文件 */
			String txtFilePath=explain.getAbsolutePathAndSuffixName() + "_text.txt";
			File file = FileUtil.createFile(txtFilePath);
			/* 字符图片文件路径 */
			String output=explain.getAbsolutePathAndSuffixName()+"_CharImage.jpg";
			FileOutputStream fop = new FileOutputStream(file);
			RgbNumberWritter.write(image, fop);
			fop.close();
			StringOutputter.print("字符文本生成成功");
			boolean success=PictureReader.writePictureFromFile(file, output, image.getWidth(), image.getHeight(),
					explain.getFontSize(), explain.isWhiteBackGround());
			if(!success){
				StringOutputter.print("写文本图片失败");
			}
			if(!explain.isSaveTxt()){
				boolean delete = new File(txtFilePath).delete();
				StringOutputter.print("删除文本文件:? ==> ?",txtFilePath,(delete? "成功":"失败"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			StringOutputter.print("文本化处理失败");
			return;
		}
	}
	/**
	 * 指令输入规则： url--指令 <br>
	 * 指令用空格隔开包括（不区分大小写）: <br>
	 * wb:白底黑字(默认) <br>
	 * bw:黑底白字 <br>
	 * {数字}w:设置为{数字}的宽度 <br>
	 * {数字}h:设置为{数字}的高度 <br>
	 * {数字}size:设置字体为{数字}pt <br>
	 * gray:灰度化图片 <br>
	 * binary:二值化图片 <br>
	 * saveTxt:保存txt文本，命名为{ 文件名_text.txt} <br>
	 * savePic:保存临时的文件，命名为  {文件名_gray.jpg} 或 {文件名_binary.jpg} <br>
	 * saveZip:保存压缩后的图片,命名为  {文件名_zip.jpg}
	 */
	public static PictureCommandEntity explain(String input){
		PictureCommandEntity entity=new PictureCommandEntity();
		int of = input.lastIndexOf("--");//获取分割符
		if(of>0){
			String src=input.substring(0,of).trim();//获取输入的图片
			entity.setSrc(src);
			entity.setAbsolutePathAndSuffixName(FileUtil.absolutePathAndSuffixName(src));//获取文件前缀
			/*
			 * 解析指令
			 */
			if(of+2<input.length()){
				String inputCommand=input.substring(of+2).toUpperCase();
				String[] commandList = inputCommand.split(" ");
				for(String key:commandList){
					/*如果是需要保存临时图片*/
					key=key.trim();
					if(key.equals("SAVEPIC")){
						entity.setSavePic(true);
						continue;
					}/*如果是需要保存txt*/
					else if(key.equals("SAVETXT")){
						entity.setSaveTxt(true);
						continue;
					}
					/*如果是需要保存压缩图片*/
					else if(key.equals("SAVEZIP")){
						entity.setSaveZip(true);
						continue;
					}/*如果先压缩再处理*/
					else if(key.equals("ZIPFIRST")){
						entity.setZipFirst(true);
						continue;
					}
					/*如果是设置背景*/
					else if(key.equals("BW")){
						entity.setWhiteBackGround(false);
						continue;
					}/*灰度化*/
					else if(key.equals("GRAY")){
						entity.setGray(true);
						continue;
					}/*二值化*/
					else if(key.equals("BINARY")){
						entity.setBinary(true);
						continue;
					}/*如果是设置宽度*/
					else if(key.endsWith("W")){
						String str = key.substring(0, key.length()-1);
						if(str.matches("(\\d)+")){
							entity.setWidth(Integer.valueOf(str));
						}
						continue;
					}/*如果是设置高度*/
					else if(key.endsWith("H")){
						String str = key.substring(0, key.length()-1);
						if(str.matches("(\\d)+")){
							entity.setHeight(Integer.valueOf(str));
						}
						continue;
					}/*如果是设置大小*/
					else if(key.endsWith("SIZE")){
						String str = key.substring(0, key.length()-4);
						if(str.matches("(\\d)+")){
							entity.setFontSize(Integer.valueOf(str));
						}
						continue;
					}
				}
				//获取输入的指令,转化为小写
			}
		}else{
			entity.setSrc(input);
			entity.setAbsolutePathAndSuffixName(FileUtil.absolutePathAndSuffixName(input));//获取文件前缀
		}
		
		return entity;
	}
}
