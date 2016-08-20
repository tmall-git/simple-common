package com.simple.common.util;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import javax.imageio.stream.ImageOutputStream;

import com.sun.imageio.plugins.jpeg.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Element;


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
	
	public static void main(String[] args) {
		//img_change("D:\\","3.png");
		File f = new File("D:\\3.png");
		img_change(f,50);
	}
	
	public static void img_change(File file,int with) {
		Tosmallerpic(file,"_"+with,with,with,(float)0.7);
	}
	
	public static String getScaleFilePath(String oriFilePath,int width) {
		if (oriFilePath.contains(".")) {
			return oriFilePath.substring(0,oriFilePath.lastIndexOf("."))+"_"+width+"."+oriFilePath.substring(oriFilePath.lastIndexOf(".")+1);
		}
		return oriFilePath;
	}
	
	private static void img_change(String url,String name) {
		Tosmallerpic(new File(url+name), "_middle", 188, 165, (float)0.7);
		Tosmallerpic(new File(url+name), "_small", 45, 45, (float)0.7);
		Tosmallerpic(new File(url+name), "_smaller", 116, 100, (float)0.7);
	}
	
	/** 
	    * 
	    * @param f 图片所在的文件夹路径 
	    * @param file 图片路径 
	    * @param ext 扩展名 
	    * @param n 图片名 
	    * @param w 目标宽 
	    * @param h 目标高 
	    * @param per 百分比 
	    */ 
	    private static void  Tosmallerpic(File file,String ext,int w,int h,float per){ 
	            Image src; 
	            try { 
	               src  =  javax.imageio.ImageIO.read(file); //构造Image对象 
	               String fileAbsolutePath = file.getAbsolutePath();
	               String img_midname  =  fileAbsolutePath.substring(0,fileAbsolutePath.lastIndexOf("."))+ext+"."+fileAbsolutePath.substring(fileAbsolutePath.lastIndexOf(".")+1); 
	               int old_w = src.getWidth(null); //得到源图宽 
	               int old_h = src.getHeight(null); 
	               int new_w = 0; 
	               int new_h = 0; //得到源图长 
	 
	               double w2 = (old_w*1.00)/(w*1.00); 
	               double h2 = (old_h*1.00)/(h*1.00); 
	 
	               //图片跟据长宽留白，成一个正方形图。 
	               BufferedImage oldpic; 
	               if(old_w>old_h) 
	               { 
	                   oldpic = new BufferedImage(old_w,old_w,BufferedImage.TYPE_INT_RGB); 
	               }else{if(old_w<old_h){ 
	                   oldpic = new BufferedImage(old_h,old_h,BufferedImage.TYPE_INT_RGB); 
	               }else{ 
	                    oldpic = new BufferedImage(old_w,old_h,BufferedImage.TYPE_INT_RGB); 
	               } 
	               } 
	                Graphics2D g  =  oldpic.createGraphics(); 
	                g.setColor(Color.white); 
	                if(old_w>old_h) 
	                { 
	                    g.fillRect(0, 0, old_w, old_w); 
	                    g.drawImage(src, 0, (old_w - old_h) / 2, old_w, old_h, Color.white, null); 
	                }else{ 
	                    if(old_w<old_h){ 
	                    g.fillRect(0,0,old_h,old_h); 
	                    g.drawImage(src, (old_h - old_w) / 2, 0, old_w, old_h, Color.white, null); 
	                    }else{ 
	                        //g.fillRect(0,0,old_h,old_h); 
	                        g.drawImage(src.getScaledInstance(old_w, old_h,  Image.SCALE_SMOOTH), 0,0,null); 
	                    } 
	                }              
	                g.dispose(); 
	                src  =  oldpic; 
	                //图片调整为方形结束 
	               /**图片大小判断暂时去掉
	               if(old_w>w) 
	               new_w = (int)Math.round(old_w/w2); 
	               else 
	                   new_w = old_w; 
	               if(old_h>h) 
	               new_h = (int)Math.round(old_h/h2);//计算新图长宽 
	               else 
	                   new_h = old_h; 
	                   */
	                new_w = w;
	                new_h = h;
	               BufferedImage image_to_save  =  new BufferedImage(new_w,new_h,BufferedImage.TYPE_INT_RGB);        
	               image_to_save.getGraphics().drawImage(src.getScaledInstance(new_w, new_h,  Image.SCALE_SMOOTH), 0,0,null); 
	               FileOutputStream fos = new FileOutputStream(img_midname); //输出到文件流 
	                
	               //旧的使用 jpeg classes进行处理的方法
//	               JPEGImageEncoder encoder  =  JPEGCodec.createJPEGEncoder(fos); 
//	               JPEGEncodeParam jep = JPEGCodec.getDefaultJPEGEncodeParam(image_to_save); 
	                /* 压缩质量 */ 
//	               jep.setQuality(per, true); 
//	               encoder.encode(image_to_save, jep); 
	                
	               //新的方法
	               saveAsJPEG(100, image_to_save, per, fos);
	                
	               fos.close(); 
	               } catch (IOException ex) { 
	            	   ex.printStackTrace();
	                //Logger.getLogger(Img_Middle.class.getName()).log(Level.SEVERE, null, ex); 
	            } 
	    } 
	     
	   
	    /**
	     * 以JPEG编码保存图片
	     * @param dpi  分辨率
	     * @param image_to_save  要处理的图像图片
	     * @param JPEGcompression  压缩比
	     * @param fos 文件输出流
	     * @throws IOException
	     */
	    public static void saveAsJPEG(Integer dpi ,BufferedImage image_to_save, float JPEGcompression, FileOutputStream fos) throws IOException {
	          
	        //useful documentation at http://docs.oracle.com/javase/7/docs/api/javax/imageio/metadata/doc-files/jpeg_metadata.html
	        //useful example program at http://johnbokma.com/java/obtaining-image-metadata.html to output JPEG data
	      
	        //old jpeg class
	        //com.sun.image.codec.jpeg.JPEGImageEncoder jpegEncoder  =  com.sun.image.codec.jpeg.JPEGCodec.createJPEGEncoder(fos);
	        //com.sun.image.codec.jpeg.JPEGEncodeParam jpegEncodeParam  =  jpegEncoder.getDefaultJPEGEncodeParam(image_to_save);
	      
	        // Image writer
	        JPEGImageWriter imageWriter  =  (JPEGImageWriter) ImageIO.getImageWritersBySuffix("jpg").next();
	        ImageOutputStream ios  =  ImageIO.createImageOutputStream(fos);
	        imageWriter.setOutput(ios);
	        //and metadata
	        IIOMetadata imageMetaData  =  imageWriter.getDefaultImageMetadata(new ImageTypeSpecifier(image_to_save), null);
	         
	         
	        if(dpi !=  null && !dpi.equals("")){
	             
	             //old metadata
	            //jpegEncodeParam.setDensityUnit(com.sun.image.codec.jpeg.JPEGEncodeParam.DENSITY_UNIT_DOTS_INCH);
	            //jpegEncodeParam.setXDensity(dpi);
	            //jpegEncodeParam.setYDensity(dpi);
	      
	            //new metadata
	            Element tree  =  (Element) imageMetaData.getAsTree("javax_imageio_jpeg_image_1.0");
	            Element jfif  =  (Element)tree.getElementsByTagName("app0JFIF").item(0);
	            jfif.setAttribute("Xdensity", Integer.toString(dpi) );
	            jfif.setAttribute("Ydensity", Integer.toString(dpi));
	             
	        }
	      
	      
	        if(JPEGcompression >= 0 && JPEGcompression <= 1f){
	      
	            //old compression
	            //jpegEncodeParam.setQuality(JPEGcompression,false);
	      
	            // new Compression
	            JPEGImageWriteParam jpegParams  =  (JPEGImageWriteParam) imageWriter.getDefaultWriteParam();
	            jpegParams.setCompressionMode(JPEGImageWriteParam.MODE_EXPLICIT);
	            jpegParams.setCompressionQuality(JPEGcompression);
	      
	        }
	      
	        //old write and clean
	        //jpegEncoder.encode(image_to_save, jpegEncodeParam);
	      
	        //new Write and clean up
	        imageWriter.write(imageMetaData, new IIOImage(image_to_save, null, null), null);
	        ios.close();
	        imageWriter.dispose();
	      
	    }
}
