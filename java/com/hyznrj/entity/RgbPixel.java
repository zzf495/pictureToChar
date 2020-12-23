package com.hyznrj.entity;

/**
 * 存储像素的RGB
 * (虽然在image里可以直接获取)
 * @date 2019年4月9日
 */
public class RgbPixel {
	private int red;
	private int green;
	private int blue;
	public int getRed() {
		return red;
	}
	public void setRed(int red) {
		this.red = red;
	}
	public int getGreen() {
		return green;
	}
	public void setGreen(int green) {
		this.green = green;
	}
	public int getBlue() {
		return blue;
	}
	public void setBlue(int blue) {
		this.blue = blue;
	}
	public int getSize(){
		return (red+green+blue)/3;
	}
	public double getGray(){
		/*
		 * 灰度值公式:Gray=R×0.299+G×0.587+B×0.114
		 */
		double gray=red*0.299+green*0.587+blue*0.114;
		return gray;
	}
}