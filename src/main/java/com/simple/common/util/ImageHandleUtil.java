package com.simple.common.util;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 *
 * @author kangjie
 *
 */
public class ImageHandleUtil {

	private static final Log logger = LogFactory.getLog(ImageHandleUtil.class);
	private static final int BUFFER_SIZE = 16 * 1024 ;
	private static final String ROOT_PATH = "/image";
	
	private static BufferedImage toImage(File f) {
		try {
			return ImageIO.read(new FileInputStream(f));
		} catch (IOException e) {
			logger.error(e);
		}
		return null;
	}
	
	/**
	 * 校验图片尺寸和大小
	 * @param f
	 * @param width
	 * @param height
	 * @param maxSize KB
	 * @return
	 */
	public static boolean isSizeValid(File f ,int width,int height,long maxSize) {
		BufferedImage image = toImage(f);
		int imageWidth = image.getWidth();
		int imageHeight = image.getHeight();
		long imageSize = (long) (f.length()/1024.0);
		if (width > 0 && width != imageWidth) {
			return false;
		}
		
		if (height > 0 && height != imageHeight) {
			return false;
		}
		if ((maxSize > 0) && (imageSize > maxSize)) {
			return false;
		}
		return true;
	}
	
	
	/**
	 * 穿过来base64位编码格式的图片内容
	 * @param base64
	 * @param path
	 * @param imgName
	 */
	public static String decodeBase64ToImage(String base64, String desFile) {
		File file = new File(ROOT_PATH+desFile);
		if (!file.exists()) {
			file.mkdirs();
		}
		sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
	    try {
	      FileOutputStream write = new FileOutputStream(file);
	      byte[] decoderBytes = decoder.decodeBuffer(base64);
	      write.write(decoderBytes);
	      write.close();
	      return ROOT_PATH+desFile;
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
	    return null ;
	}	
	
	public static String uploadFile(String data,String desFile) {
	        try {  
	        	File file = new File(desFile);
	        	if (!file.exists()) {
	        		file.mkdirs();
	        	}
	        	//注意点：实际的图片数据是从 data:image/jpeg;base64, 后开始的  
	            byte[] k =  Base64.decodeBytes(data.substring("data:image/jpeg;base64,".length()));  
	            InputStream is = new ByteArrayInputStream(k);  
	            String fileName = UUID.randomUUID().toString();  
	            //String imgFilePath = serverPath + "\\static\\usertemp\\" + fileName + ".jpg";  
	            //以下其实可以忽略，将图片压缩处理了一下，可以小一点  
	            double ratio = 1.0;  
	            BufferedImage image = ImageIO.read(is);  
	            int newWidth = (int) (image.getWidth() * ratio);  
	            int newHeight = (int) (image.getHeight() * ratio);  
	            Image newimage = image.getScaledInstance(newWidth, newHeight,  
	            Image.SCALE_SMOOTH);  
	            BufferedImage tag = new BufferedImage(newWidth, newHeight,  
	                    BufferedImage.TYPE_INT_RGB);  
	            Graphics g = tag.getGraphics();  
	            g.drawImage(newimage, 0, 0, null);  
	            g.dispose();  
	            String suffix = file.getName().substring(file.getName().lastIndexOf(".")+1);
	            ImageIO.write(tag, suffix, new File(desFile));  
	            return fileName;  
	        }catch(Exception e) {
	        	e.printStackTrace();
	        }
	        return null;
	}
	
	/**
	 * 
	 * @param imgInputStream
	 * @param imgOutputStream
	 * @param level
	 */
//	public static void scaleImage(InputStream imgInputStream, OutputStream imgOutputStream, int limit) {
//		try {
//			float per = 0.7f;
//			Image src = javax.imageio.ImageIO.read(imgInputStream);
//			int width = src.getWidth(null);
//			int height = src.getHeight(null);
//			double rate = 0;
//			if(height > width){//如果高大于宽
//				rate = height * 1.0 / limit;
//				height = limit;//设置高为限制值
//				width = (int) Math.floor(width / rate);
//			} else {
//				rate = width * 1.0 / limit;
//				width = limit;//设置宽为限制值
//				height = (int) Math.floor(height / rate);
//			}
//			
////            // 构造Image对象  
////            int old_w = src.getWidth(null); // 得到源图宽  
////            int old_h = src.getHeight(null);  
////            int new_w = 0;  
////            int new_h = 0; // 得到源图长  
////            double w2 = (old_w * 1.00) / (w * 1.00);  
////            double h2 = (old_h * 1.00) / (h * 1.00);  
//            // 图片跟据长宽留白，成一个正方形图。  
//            BufferedImage oldpic = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB); ;  
////            if (old_w > old_h) {  
////                oldpic = new BufferedImage(old_w, old_w,  
////                        BufferedImage.TYPE_INT_RGB);  
////            } else {  
////                if (old_w < old_h) {  
////                    oldpic = new BufferedImage(old_h, old_h,  
////                            BufferedImage.TYPE_INT_RGB);  
////                } else {  
////                    oldpic = new BufferedImage(old_w, old_h,  
////                            BufferedImage.TYPE_INT_RGB);  
////                }  
////            }  
//            Graphics2D g = oldpic.createGraphics();  
//            g.setColor(Color.white);  
////            if (old_w > old_h) {  
////                g.fillRect(0, 0, old_w, old_w);  
////                g.drawImage(src, 0, (old_w - old_h) / 2, old_w, old_h,  
////                        Color.white, null);  
////            } else {  
////                if (old_w < old_h) {  
////                    g.fillRect(0, 0, old_h, old_h);  
////                    g.drawImage(src, (old_h - old_w) / 2, 0, old_w, old_h,  
////                            Color.white, null);  
////                } else {  
////  
////                    // g.fillRect(0,0,old_h,old_h);  
////                    g.drawImage(src.getScaledInstance(old_w, old_h,  
////                            Image.SCALE_SMOOTH), 0, 0, null);  
////                }  
////            }  
//            g.drawImage(src, 0, 0, width, height, Color.white, null);  
//            g.dispose();  
//            src = oldpic;  
//            // 图片调整为方形结束  
////            if (old_w > w)  
////                new_w = (int) Math.round(old_w / w2);  
////            else  
////                new_w = old_w;  
////            if (old_h > h)  
////                new_h = (int) Math.round(old_h / h2);// 计算新图长宽  
////            else  
////                new_h = old_h;  
//            BufferedImage tag = new BufferedImage(width, height,  
//                    BufferedImage.TYPE_INT_RGB);  
//            // tag.getGraphics().drawImage(src,0,0,new_w,new_h,null);  
//            // 绘制缩小后的图  
//            tag.getGraphics().drawImage(  
//                    src.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0,  
//                    0, null);  
//            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(imgOutputStream);  
//            JPEGEncodeParam jep = JPEGCodec.getDefaultJPEGEncodeParam(tag);  
//            /* 压缩质量 */  
//            jep.setQuality(per, true);  
//            encoder.encode(tag, jep);  
//            // encoder.encode(tag);  
//            // 近JPEG编码  
//            imgOutputStream.close();  
//
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
}
