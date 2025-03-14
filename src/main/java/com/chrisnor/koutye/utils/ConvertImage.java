package com.chrisnor.koutye.utils;


import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.nio.file.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.*;
import java.awt.image.DataBufferByte;

import org.apache.commons.io.FileUtils;

public class ConvertImage {
	/*
	public static String extractBytes (String filePath) throws IOException {
		 
		 byte[] fileContent = FileUtils.readFileToByteArray(new File(filePath));
		 String encodedString = Base64.getEncoder().encodeToString(fileContent);
		 return encodedString;
	}
	*/
	public static byte[] extractBytes (String filePath) throws IOException {
		 System.out.println(filePath);
		 File imgPath = new File(filePath);
		 BufferedImage bufferedImage = ImageIO.read(imgPath);
	     WritableRaster raster = bufferedImage.getRaster();
	     DataBufferByte data = (DataBufferByte) raster.getDataBuffer();
	     return data.getData();
		 //ByteArrayOutputStream bao = new ByteArrayOutputStream();
		 //ImageIO.write(bufferedImage,"jpg",bao);
		 //return bao.toByteArray();
	  }
	  
}
