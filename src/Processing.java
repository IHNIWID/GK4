
package grafikappm;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class Processing {

    int[] lut;

    public Processing() {
        lut = new int[256];
    }

    public void addsub(int value, BufferedImage image) {
        for (int i = 0; i < 256; i++) {
            if (i + value < 0) {
                lut[i] = 0;
            } else if (i + value > 255) {
                lut[i] = 255;
            } else {
                lut[i] = i + value;
            }
        }
        changePxel(image);
    }
    
    public void multipliaction(double value, BufferedImage image){
        for (int i = 0; i < 256; i++) {
            if (Math.round(i * value) < 0) {
                lut[i] = 0;
            } else if (Math.round(i * value) > 255) {
                lut[i] = 255;
            } else {
                lut[i] =(int) Math.round(i * value);
            }
        }
        changePxel(image);
    }
    
    public void backChange(BufferedImage image, BufferedImage original){
        for(int i = 0 ; i < image.getWidth(); i++){
            for( int j = 0 ; j < image.getHeight(); j++){
                image.setRGB(i, j, original.getRGB(i, j));
            }
        }
    }

    public void changePxel(BufferedImage image) {
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                Color temp = new Color(image.getRGB(i, j));
                Color c = new Color(lut[temp.getRed()], lut[temp.getBlue()], lut[temp.getGreen()]);
                image.setRGB(i, j, c.getRGB());
            }
        }
    }

    public void grayScale1(BufferedImage image) {
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                Color temp = new Color(image.getRGB(i, j));
                int intColor = Math.round((temp.getRed() + temp.getGreen() + temp.getBlue()) / 3);
                image.setRGB(i, j, (new Color(intColor, intColor, intColor)).getRGB());
            }
        }
    }

    public void grayScale2(BufferedImage image) {
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                Color temp = new Color(image.getRGB(i, j));
                int intColor = (int) Math.round(0.299 * temp.getRed() + 0.587 * temp.getGreen() + 0.114 * temp.getBlue());
                image.setRGB(i, j, (new Color(intColor, intColor, intColor)).getRGB());
            }
        }
    }
}
