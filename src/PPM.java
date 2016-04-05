
package grafikappm;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystemException;


public class PPM {

    FileInputStream stream;

    public BufferedImage createImage(String path) throws FileNotFoundException, IOException {
        byte[] bufor = new byte[1024];
        stream = new FileInputStream(path);
        BufferedInputStream bufstream = new BufferedInputStream(stream, 1024);
        String format;
        if ((char) bufstream.read() == 'P' && (char) bufstream.read() == '3') {
            format = "P3";
        } else {
            format = "P6";
        }
        char c = ' ';
        int width = 0, height = 0, maxValue = 0;
        for (int i = 0;;) {
            do {
                if (!Character.isWhitespace(c)) {
                    break;
                }
            } while (Character.isWhitespace(c = (char) bufstream.read()));
            if (c == '#') {
                while ((c = (char) bufstream.read()) != '\n');
            } else {
                if (i == 3) {
                    break;
                }
                if (!Character.isDigit(c)) {
                    throw new FileSystemException("Złe dane w pliku!");
                }
                String s = "";
                do {
                    s += c;
                } while (Character.isDigit(c = (char) bufstream.read()));
                if (i % 3 == 0) {
                    width = Integer.parseInt(s);
                }
                if (i % 3 == 1) {
                    height = Integer.parseInt(s);
                }
                if (i % 3 == 2) {
                    maxValue = Integer.parseInt(s);
                }
                i++;
            }
        }
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        int r = 0, g = 0, b = 0;
        if (format.equals("P6")) {
            int data = (int) c;
            for (int i = 0; data != -1; i++) {
                if (i % 3 == 0) {
                    r = data;
                }
                if (i % 3 == 1) {
                    g = data;
                }
                if (i % 3 == 2) {
                    b = data;
                    bi.setRGB((i / 3) % width, i / 3 / width, (r ) << 16 | (g) << 8 | (b ));
                }
                data = bufstream.read();
            }
        } else {
            for (int i = 0; i < width * height * 3; i++) {
                String data2 = "";
                while (Character.isWhitespace(c = (char) bufstream.read()));
                while (Character.isDigit(c)) {
                    data2 += c;
                    c = (char) bufstream.read();
                }
                int value = Integer.parseInt(data2);
                if (i % 3 == 0) {
                    r = value;
                }
                if (i % 3 == 1) {
                    g = value;
                }
                if (i % 3 == 2) {
                    b = value;
                    bi.setRGB((i / 3) % width, i / 3 / width, (r & 0xFF) << 16 | (g & 0xFF) << 8 | (b & 0xFF));

                }
            }
        }
        return bi;
    }
}
