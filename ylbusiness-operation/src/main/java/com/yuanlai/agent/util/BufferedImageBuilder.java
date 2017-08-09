package com.yuanlai.agent.util;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * JAVA处理不正确处理图片ICC信息蒙上红色的问题 放弃ImageIO.read();<br><br>
 * ImageIO.read()方法读取图片时可能存在不正确处理图片ICC信息的问题，ICC为JPEG图片格式中的一种头部信息，导致渲染图片前景色时蒙上一层红色。<br><br>
 *
 * 2016年9月5日
 *
 * @author wangwenxiang
 *
 */
public class BufferedImageBuilder {
	
	public static BufferedImage toBufferedImage(Image image) {  
        if (image instanceof BufferedImage) {  
            return (BufferedImage) image;  
        }  
        // This code ensures that all the pixels in the image are loaded  
        image = new ImageIcon(image).getImage();  
        BufferedImage bimage = null;  
        GraphicsEnvironment ge = GraphicsEnvironment  
                .getLocalGraphicsEnvironment();  
        try {  
            int transparency = Transparency.OPAQUE;  
            GraphicsDevice gs = ge.getDefaultScreenDevice();  
            GraphicsConfiguration gc = gs.getDefaultConfiguration();  
            bimage = gc.createCompatibleImage(image.getWidth(null),  
                    image.getHeight(null), transparency);  
        } catch (HeadlessException e) {  
            // The system does not have a screen  
        }  
        if (bimage == null) {  
            // Create a buffered image using the default color model  
            int type = BufferedImage.TYPE_INT_RGB;  
            bimage = new BufferedImage(image.getWidth(null),  
                    image.getHeight(null), type);  
        }  
        // Copy image to buffered image  
        Graphics g = bimage.createGraphics();  
        // Paint the image onto the buffered image  
        g.drawImage(image, 0, 0, null);  
        g.dispose();  
        return bimage;  
    }
}
