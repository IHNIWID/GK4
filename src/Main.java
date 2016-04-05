/**
 * Created by Jakub Tomczuk on 05.04.2016.
 */
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.sun.prism.Image;

public class Main extends JPanel {

    private static final long serialVersionUID = 1L;
    private BufferedImage img;

    public Main() {

        ppm k = new ppm();
        try {						// tutaj wpisujemy plik ppm ktory chcemy wczytac
            //k.load("lena_384.ppm");// pm3
            k.load("etud.ppm");		//pm6
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        JFrame window = new JFrame("Frame");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
        window.add(this);
        img = k.ppmi;
        window.setPreferredSize(new Dimension(k.width, k.height));
        window.pack();
        // ppm.save("szmata", k.width, k.height, k.pixels);

        try {
            ImageIO.write(img, "jpg", new File("JPEGexport.jpg"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        System.out.println("Done");

    }

    public static void main(String[] args) throws IOException {
        new Main();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(img, 0, 0, null);
    }
}
