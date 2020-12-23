package com.hyznrj.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.hyznrj.entity.RgbPixel;

public class PictureReader {
	

	private static final String FONT_NAME = "幼圆";
	/**
	 * 判断x,y轴是否越界
	 * 
	 * @param image
	 * @param x
	 * @param y
	 * @return
	 */
	public static boolean isLegal(BufferedImage image, int x, int y) {
		if (x < image.getMinX() || x >= image.getWidth() || y < image.getMinY() || y >= image.getHeight()) {
			return false;
		}
		return true;
	}
	/**
	 * 获取最原生的BufferedImage对象
	 * @param src
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static BufferedImage getBufferedImage(final String src) throws FileNotFoundException, IOException {
		return ImageIO.read(new FileInputStream(new File(src.trim())));
	}
	//
	/**
	 * 获取二值化图片
	 * 
	 * @return
	 */
	public static BufferedImage getBinaryBufferedImage(final BufferedImage image) {
		if (image == null) {
			return null;
		}
		int width = image.getWidth();
		int height = image.getHeight();
		BufferedImage binaryImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY);
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				int rgb = image.getRGB(i, j);
				binaryImage.setRGB(i, j, rgb);
			}
		}
		return binaryImage;
	}

	/**
	 * 
	 * <p>
	 * 根据路径获取灰度化后的图片
	 * </p>
	 * <p>
	 * 当zip有效时，如果width大于0，则用width进行像素压缩，否则用height压缩
	 * </p>
	 * 
	 * @param src
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static BufferedImage getGrayBufferedImage(final BufferedImage image)
			throws FileNotFoundException, IOException {
		int width = image.getWidth();
		int height = image.getHeight();
		BufferedImage grayImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				int rgb = image.getRGB(i, j);
				grayImage.setRGB(i, j, rgb);
			}
		}
		return grayImage;
	}

	public static boolean saveBufferedImage(BufferedImage image, String src) throws IOException {
		if (image != null) {
			File file = FileUtil.createFile(src);
			ImageIO.write(image, "jpg", file);
			return true;
		}
		return false;
	}

	/**
	 * 获取图片在x,y轴的rgb值
	 */
	public static RgbPixel getScreenPixel(BufferedImage image, int x, int y) {
		if (isLegal(image, x, y)) {
			RgbPixel rgb = new RgbPixel();
			int pix = image.getRGB(x, y);
			rgb.setRed((pix & 0xff0000) >> 16);// 最高8位为R
			rgb.setGreen((pix & 0x00ff00) >> 8);// 中间8位为G
			rgb.setBlue((pix & 0x0000ff));// 最低8位为B
			return rgb;
		}
		return null;
	}
	/**
	 * 按设置的宽度高度比例压缩图片文件,如果width和height都大于0，则以此为基准进行压缩（可能造成图片变型）
	 * 
	 * @param oldFile
	 *            要进行压缩的文件全路径
	 * @param newFile
	 *            新文件
	 * @param width
	 *            宽度
	 * @param height
	 *            高度
	 * @return 返回压缩后的文件的全路径
	 */
	public static BufferedImage zipImageFileWithRate(BufferedImage image, int width, int height) {
		if (image == null) {
			return null;
		}
		if(width<=0&&height<=0){
			return image;
		}
		/* 对服务器上的临时文件进行处理 */
		/* 按比例压缩 */
		int w = image.getWidth(null);
		int h = image.getHeight(null);
		double bili;
		if (width > 0&&height<=0) {
			bili = width / (double) w;
			height = (int) (h * bili);
		} else if (height > 0&&width<=0) {
				bili = height / (double) h;
				width = (int) (w * bili);
		}
		
		BufferedImage buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics = buffImg.createGraphics();
		graphics.getFontRenderContext();
		graphics.setBackground(new Color(255, 255, 255));
		graphics.setColor(new Color(255, 255, 255));
		graphics.fillRect(0, 0, width, height);
		graphics.drawImage(image.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);
		StringOutputter.print("压缩图片 (宽:?,高:?)==>成功", width,height);
		return buffImg;
	}
	/**
	 * 将txt文本转化为图片(jpg)
	 * 提供了两种样式：白底黑字和黑底白字
	 * @param file
	 * @param output
	 * @param IMAGE_WIDTH
	 * @param IMAGE_HEIGHT
	 * @param fontSize
	 * @return
	 */
	@SuppressWarnings("resource")
	public static boolean writePictureFromFile(File file,String output,
			 int IMAGE_WIDTH , int IMAGE_HEIGHT,final int fontSize,boolean whiteBackGround){
		boolean flag=false;
		if(file!=null&&file.exists()&&!file.isDirectory()){
			BufferedReader reader = null;
	        try {
	            reader = new BufferedReader(new FileReader(file));
	            //像素点和字体大小转化
//	        	IMAGE_WIDTH*=ptToPx(fontSize);
//	        	IMAGE_HEIGHT*=(ptToPx(fontSize)+2);
		        IMAGE_WIDTH*=((fontSize&1)==0? fontSize:fontSize+1);
		        IMAGE_HEIGHT*=(fontSize+2);
//	        	StringOutputter .print("produce a image with Width:? ,Height:?",IMAGE_WIDTH,IMAGE_HEIGHT);
		        BufferedImage bimage = new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT,BufferedImage.TYPE_INT_RGB);
		        //获取图像上下文
		        Graphics g = createGraphics(bimage,IMAGE_WIDTH,IMAGE_HEIGHT,fontSize,whiteBackGround);
		        String line;
		        //图片中文本行高
		        FontMetrics metrics = g.getFontMetrics();
//		        final int Y_LINEHEIGHT =  (ptToPx(fontSize)+2);//一般是像素大小+2
		        final int Y_LINEHEIGHT = metrics.getHeight(); //相当于fontsize+2;
//		        StringOutputter.print("@ :height:? ,Y_LINEHEIGHT: ?", height,Y_LINEHEIGHT);
		        int lineNum = 1;
		        try {
		            while((line = reader.readLine()) != null){
//		            	StringOutputter.print("Get Line[?]:?",lineNum,line);
		                g.drawString(line, 0, lineNum * Y_LINEHEIGHT);
//		                StringOutputter.print("lineNum * Y_LINEHEIGHT: ? ,width:?", lineNum * Y_LINEHEIGHT,metrics.stringWidth(line));
		                lineNum++;
		            }
		            g.dispose();
		            //保存为jpg图片
		            FileOutputStream out = new FileOutputStream(output);
	                ImageIO.write(bimage, "JPEG", out);
	                out.close();
	                StringOutputter.print("写文本图片(像素:(?,?))==>成功 (输出路径:?)",IMAGE_WIDTH,IMAGE_HEIGHT, output);
		        } catch (IOException e) {
		            e.printStackTrace();
		            return false;
		        }
		        return true;
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	            return false;
	        }finally{
	        	if(reader!=null){
	        		try {
						reader.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
	        	}
	        }
	       
		}
		return flag;
	}
	
    /**
     * 将pt(即字体大小)转换为px(像素大小)
     * @param pt
     * @return
     */
    public static int ptToPx(double pt){
    	/* 
    	 * 参考 http://www.runoob.com/w3cnote/px-pt-em-convert-table.html 
    	 * pt = 1/72(英寸), px = 1/dpi(英寸)
    	 * 以 Windows 下的 96dpi 来计算，1 pt = px * 96/72 = px * 4/3
    	 * */
    	return (int)(pt/0.75);
    }
    /**
     * 获取图片的上下文，设置样式
     * @param image 处理的图片对象
     * @param IMAGE_WIDTH 宽
     * @param IMAGE_HEIGHT 高
     * @param fontSize 字体大小
     * @param whiteBackGround 是否为白底黑字
     * @return
     */
    private static Graphics createGraphics(final BufferedImage image,
    		final int IMAGE_WIDTH,final  int IMAGE_HEIGHT,final int fontSize,final boolean whiteBackGround){
        if(IMAGE_WIDTH>0&&IMAGE_HEIGHT>0){
        	//创建图片
        	Graphics g = image.createGraphics();
        	//设置背景色
        	if(whiteBackGround){
        		g.setColor(null); 
        	}else{
        		g.setColor(Color.BLACK);
        	}
            g.fillRect(0, 0, IMAGE_WIDTH, IMAGE_HEIGHT);//绘制背景
            //设置前景色
            if(whiteBackGround){
            	 g.setColor(Color.BLACK); 
            }else{
            	 g.setColor(Color.WHITE); 
            }
            g.setFont(new Font(FONT_NAME, Font.PLAIN, fontSize)); //设置字体
            return g;
        }
    	return null;
    }
}
