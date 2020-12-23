package com.hyznrj.entity;

import com.hyznrj.utils.StringOutputter;

public class PictureCommandEntity {
	private boolean savePic = false;// 默认不保存临时图片
	private boolean saveTxt = false;// 默认不保存txt文本
	private boolean saveZip = false;// 默认不保存压缩后的图片
	private boolean zipFirst= false;//先压缩再处理，默认压缩放在最后
	private boolean gray = false;//默认不进行灰度化
	private boolean binary = false;//默认不进行二值化
	private boolean whiteBackGround = true;// 默认白底黑字
	private int width = -1;//默认宽度
	private int height = -1;//默认高度
	
	private int fontSize = 13;// 默认13号字体
	private String src=null;//image文件的路径
	private String absolutePathAndSuffixName=null;//文件前缀
	public void print(){
		StringOutputter.print(
				"------PARAMS--------\n"
				+"src:? \n"
				+"absolutePathAndSuffixName:? \n"
				+"zipFirst:? \n"
				+"fontSize:? \n"
				+"width:? \n"
				+"height:? \n"
				+"whiteBackGround:? \n"
				+"gray:? \n"
				+"binary:? \n"
				+"savePic:? \n"
				+"saveTxt:? \n"
				+"saveZip:? \n"
				+"-------------------\n"
				, src,absolutePathAndSuffixName,zipFirst,fontSize,width,height,whiteBackGround,gray,binary,savePic,saveTxt,saveZip);
	}
	public boolean isSaveZip() {
		return saveZip;
	}
	public void setSaveZip(boolean saveZip) {
		this.saveZip = saveZip;
	}
	public String getSrc() {
		return src;
	}
	public void setSrc(String src) {
		this.src = src;
	}
	public String getAbsolutePathAndSuffixName() {
		return absolutePathAndSuffixName;
	}
	public void setAbsolutePathAndSuffixName(String absolutePathAndSuffixName) {
		this.absolutePathAndSuffixName = absolutePathAndSuffixName;
	}
	public boolean isSavePic() {
		return savePic;
	}
	public void setSavePic(boolean savePic) {
		this.savePic = savePic;
	}
	public boolean isSaveTxt() {
		return saveTxt;
	}
	public void setSaveTxt(boolean saveTxt) {
		this.saveTxt = saveTxt;
	}
	public boolean isGray() {
		return gray;
	}
	public void setGray(boolean gray) {
		this.gray = gray;
	}
	public boolean isBinary() {
		return binary;
	}
	public void setBinary(boolean binary) {
		this.binary = binary;
	}
	public boolean isWhiteBackGround() {
		return whiteBackGround;
	}
	public void setWhiteBackGround(boolean whiteBackGround) {
		this.whiteBackGround = whiteBackGround;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public int getFontSize() {
		return fontSize;
	}
	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}
	public boolean isZipFirst() {
		return zipFirst;
	}
	public void setZipFirst(boolean zipFirst) {
		this.zipFirst = zipFirst;
	}
}
