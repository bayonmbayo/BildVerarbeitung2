package de.bild.utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import de.bild.info.BildInfo;

public class Utils {
	
	public static void BitmapToByte(String fileName, BildInfo info) throws IOException {
		
		byte[][] imgarray = new byte[100][150];
		
		BufferedImage bi = ImageIO.read(new File(fileName));
		// you should stop here
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
	    ImageIO.write(bi, "bmp", bos );
	    byte [] data = bos.toByteArray();
	    
	    byte [] tmpdata = new byte[15000];
	    byte [] vorspann = new byte[1078];
	    
	    for(int i = 0; i < vorspann.length; i++) {
	    		vorspann[i] = data[i];
	    		//System.out.println(vorspann[i]);
	    }  
	    
	    for(int i = 1078; i < data.length; i++) {
	    	//System.out.println(i);
	    		tmpdata[i - 1078] = data[i];
	    		
	    }
	    int i = 0;
	    int j = 0;
	    int k = 0;
	    //int a = 0;
	    while(i < tmpdata.length && k < 100) {
	    	byte tmp = tmpdata[i];
	        imgarray[k][j] = tmp;
	        //System.out.println(a++);
	    	j++;    	
	    	if(j == 150) {
	    		j = 0;
	    		k++;
	    	}
	    	i++;
	    }
	    
	    info.pixel = imgarray;
	    info.vorspann = vorspann;
		
	}
	
	public static int[][] ByteToPixel(BildInfo info) {
		int[][] pixelInt = new int[100][150];
		for(int i = 0; i < 100; i++) {
	    	for(int j = 0; j < 150; j++) {
	    		int p = info.pixel[i][j];
	    		if(p >= -128 && p <= -1) {
	    			int b = p + 256;
	    			pixelInt[i][j] = b;
	    		}else if(p >= 0 && p <= 127) {
	    			pixelInt[i][j] = p;
	    		}
	    	}
	    	
	    }
		return pixelInt;
	}
	
	public static void ByteToPixel(int[][] pixelInt, BildInfo info) {
		for(int i = 0; i < 100; i++) {
	    	for(int j = 0; j < 150; j++) {
	    		int p = pixelInt[i][j];
	    		if(p >= 128 && p <= 255) {
	    			byte b = (byte) (p - 256);
	    			info.pixel[i][j] = b;
	    		}else if(p >= 0 && p <= 127) {
	    			info.pixel[i][j] = (byte) p;
	    		}
	    	}
	    	
	    }
	}
	
	public static double[][] PixelDoubleDivision(int[][] pixela, int [][] pixelb) {
		double[][] pixelDouble = new double[100][150];
		for(int i = 0; i < 100; i++) {
	    	for(int j = 0; j < 150; j++) {
	    		pixelDouble[i][j] = Math.round((double) pixela[i][j] / (double) pixelb[i][j]);
	    	}
		}
		return pixelDouble;
		
	}
	
	public static int[][] DoubletoInt(double[][] pixel) {
		int[][] pixelInt = new int[100][150];
		for(int i = 0; i < 100; i++) {
	    	for(int j = 0; j < 150; j++) {
	    		pixelInt[i][j] = (int) Math.round((double) pixel[i][j]);
	    	}
		}
		return pixelInt;
		
	}
	
	public static double GraustufenMittelwert(String fileName, BildInfo info) throws IOException{
		BitmapToByte(fileName, info);
		BildInfo tmpInfo = info;
		int[][] tmpPixelInt = ByteToPixel(tmpInfo);
		double mittelwert = 0;
		double sum = 0;
		for(int i = 0; i < 100; i++) {
	    	for(int j = 0; j < 150; j++) {
	    		sum = sum + tmpPixelInt[i][j];
	    	}
		}
		
		mittelwert = sum/15000;
		
		return mittelwert;
	}
	
	public static byte[] ConvertImage(BildInfo infoa, BildInfo infob) {
		byte[] finaldata = new byte[16078];
		for(int i = 0; i < 1078; i++) {
			finaldata[i] = infoa.vorspann[i];
		}
		
		int i = 0;
	    int j = 0;
	    int k = 1078;
	    int a = 0;
	    while(i < 100) {
	    	byte tmp = infob.pixel[i][j];
	    	//System.out.println(tmp);
	        finaldata[k] = tmp;
	        
	    	j++;    	
	    	if(j == 150) {
	    		j = 0;
	    		i++;
	    	}
	    	k++;
	    }
	    
	    return finaldata;
		
	}
	
}
