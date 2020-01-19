package de.bild.verarbeitung;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import de.bild.info.BildInfo;
import de.bild.utils.Utils;

public class BildVerarbeitung {
	
	public static void main(String[] args) throws IOException {
		
		for(int i = 0; i < args.length; i++) {
			if(!(args[i].contains(".bmp"))) {
				System.exit(0);
			}
		}
		
		for(int t = 0; t < 3; t++) {
			
			BildInfo infoa = new BildInfo();
			BildInfo infob = new BildInfo();
			BildInfo infoc = new BildInfo();
			BildInfo infod = new BildInfo();
			BildInfo infoe = new BildInfo();
			
			Utils.BitmapToByte(args[t], infoa);
			Utils.BitmapToByte(args[3], infob);
			Utils.BitmapToByte(args[4], infoc);
			
			int[][] tmpInfoa = Utils.ByteToPixel(infoa);
			int[][] tmpInfob = Utils.ByteToPixel(infob);
			int[][] tmpInfoc = Utils.ByteToPixel(infoc);
			int[][] abziehen = new int[100][150];
			
			for(int i = 0; i < 100; i++) {
		    	for(int j = 0; j < 150; j++) {
		    		abziehen[i][j] = tmpInfoa[i][j] - tmpInfob[i][j];
		    	}
		    }
			
			
			double[][] dividieren = Utils.PixelDoubleDivision(abziehen, tmpInfoc);
			
			double whiteMittelwert = Utils.GraustufenMittelwert("white.bmp", infod);
			
			for(int i = 0; i < 100; i++) {
		    	for(int j = 0; j < 150; j++) {
		    		dividieren[i][j] = dividieren[i][j] * whiteMittelwert;
		    		
		    	}
		    }
			
			int[][] finalImage = Utils.DoubletoInt(dividieren);
			
			infoe.pixel = new byte[100][150];
			Utils.ByteToPixel(finalImage, infoe);
			
			byte[] data = Utils.ConvertImage(infoa, infoe);
			
			
		    InputStream in = new ByteArrayInputStream(data);
	        BufferedImage bImageFromConvert = ImageIO.read(in);

	        ImageIO.write(bImageFromConvert, "bmp", new File(
	                args[t].substring(0,4)+"korr.bmp"));
			
		}

		
		
	}
}
