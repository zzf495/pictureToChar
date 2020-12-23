package com.hyznrj.utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

@SuppressWarnings("restriction")
public class Imagebase64 {

	static BASE64Encoder encoder = new sun.misc.BASE64Encoder();
	static BASE64Decoder decoder = new sun.misc.BASE64Decoder();
	public static void main(String[] args) {
		
	}
	static String getImageBinary(String filePath) throws IOException {
		File f = new File(filePath);
		BufferedImage bi = ImageIO.read(f);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(bi, "jpg", baos);
		byte[] bytes = baos.toByteArray();
		return encoder.encodeBuffer(bytes).trim();
	}

	static void base64StringToImage(String base64String,String savePath) throws IOException {
		byte[] bytes1 = decoder.decodeBuffer(base64String);
		ByteArrayInputStream bais = new ByteArrayInputStream(bytes1);
		BufferedImage bi1 = ImageIO.read(bais);
		File f1 = new File(savePath);
		ImageIO.write(bi1, "jpg", f1);
	}
}
