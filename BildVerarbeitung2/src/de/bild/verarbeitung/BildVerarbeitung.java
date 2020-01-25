package de.bild.verarbeitung;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import de.bild.info.BildInfo;
import de.bild.utils.Utils;

/**
 *
 * Hier wird das Programm ausgefuehret 
 *
 */
public class BildVerarbeitung {
	
	public static void main(String[] args) throws IOException {
		
		
		// das Programm meldet Falsches Format 
		for(int i = 0; i < args.length; i++) {
			if(!(args[i].contains(".bmp"))) {
				System.out.println("kein gueltiges Format");
				System.exit(0);
			}
		}
		
		
		// Verarbeitung für drei Bilder (z.b 0221.bmp, 0344.bmp, 3198.bmp)
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
			
			
			// pixelweise das Dunkelbild vom verarbeitende Bild abzihen
			for(int i = 0; i < 100; i++) {
		    	for(int j = 0; j < 150; j++) {
		    		abziehen[i][j] = tmpInfoa[i][j] - tmpInfob[i][j];
		    	}
		    }
			
			// pixelweise das verarbeitende Bild durch WhiteBild
			double[][] dividieren = Utils.PixelDoubleDivision(abziehen, tmpInfoc);
			
			// GraustufenMittelwert des Whitebildes berechnen
			double whiteMittelwert = Utils.GraustufenMittelwert("white.bmp", infod);
			
			// pixelweise das verarbeitende Bild mit dem GraustufenMittelwert multiplizieren
			for(int i = 0; i < 100; i++) {
		    	for(int j = 0; j < 150; j++) {
		    		dividieren[i][j] = dividieren[i][j] * whiteMittelwert;
		    		
		    	}
		    }
			
			// convert double[][] to int[][]
			int[][] finalImage = Utils.DoubletoInt(dividieren);
			
			// jeder Pixel (0 ... 255) to Byte (-127 ... 128)
			infoe.pixel = new byte[100][150];
			Utils.PixelToByte(finalImage, infoe);
			
			// setzt der VorspannInfo und die pixelweise Verarbeitendes Bild zusammen
			byte[] data = Utils.ConvertImage(infoa, infoe);
			
			
			// convert byte[][] to bmp-Image
		    InputStream in = new ByteArrayInputStream(data);
	        BufferedImage bImageFromConvert = ImageIO.read(in);
	        ImageIO.write(bImageFromConvert, "bmp", new File(
	                args[t].substring(0,4)+"korr.bmp"));
			
		}

		System.out.println("Verarbeitung beendet");
		
	}
}
