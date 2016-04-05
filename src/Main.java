
package grafikappm;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Main extends JPanel {

    /**
     * @param args the command line arguments
     */
    private BufferedImage image, originalImage;
    private String imagePath;
    private JFrame frame;
    private Processing proccesing;

    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        new Main();

    }

    public Main() {
        super();
        frame = new JFrame("Program");
        setPreferredSize(new Dimension(400, 400));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        proccesing = new Processing();
        JMenuBar menuBar = new JMenuBar();
        JMenu filemenu = new JMenu("File");
        JMenu editormenu = new JMenu("Przetwarzanie");
        JMenuItem addsub = new JMenuItem("Dodawanie/Odjemowanie");
        addsub.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int value;
                try {
                    value = Integer.parseInt(JOptionPane.showInputDialog("Podaj wartość"));
                } catch (Exception z) {
                    value = 0;
                }
                if (value != 0) {
                    proccesing.addsub(value, image);
                    repaint();
                }
            }
        });
        JMenuItem multipliaction = new JMenuItem("Mnożenie/Dzielenie");
        multipliaction.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                double value;
                try {
                    value = Double.parseDouble((JOptionPane.showInputDialog("Podaj wartość")).replace(',', '.'));
                } catch (Exception z) {
                    value = 0;
                }
                if (value != 0 && value > 0) {
                    proccesing.multipliaction(value, image);
                    repaint();
                }
            }
        });
        JMenuItem grayScale1 = new JMenuItem("Skala szarości");
        grayScale1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                proccesing.grayScale1(image);
                repaint();
            }
        });
        JMenuItem grayScale2 = new JMenuItem("Skala szarości");
        grayScale2.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                proccesing.grayScale2(image);
                repaint();
            }
        });
        JMenuItem load = new JMenuItem("Wczytaj");
        load.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                loadFile();
                if(image != null){
                setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
                repaint();
                frame.pack();                    
                }
            }
        });
        JMenuItem save = new JMenuItem("Zapisz");
        save.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                saveFile();
            }
        });
        JMenuItem backChange = new JMenuItem("Cofnij zmiany");
        backChange.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                proccesing.backChange(image, originalImage);
                repaint();
            }
        });
        filemenu.add(load);
        filemenu.add(save);
        filemenu.add(backChange);
        editormenu.add(addsub);
        editormenu.add(multipliaction);
        editormenu.add(grayScale1);
        editormenu.add(grayScale2);
        menuBar.add(filemenu);
        menuBar.add(editormenu);
        frame.setJMenuBar(menuBar);
        frame.add(this);
        frame.setVisible(true);
        frame.pack();
    }

    private void loadFile() {
        JFileChooser filePath = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("PNG && PPM File", "ppm", "png");
        filePath.setFileFilter(filter);
        filePath.setDialogTitle("Wczytaj plik");
        if (filePath.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            imagePath = filePath.getSelectedFile().getPath();
            String path = filePath.getSelectedFile().toString();
            String ext = path.substring(path.lastIndexOf(".") + 1);
            if (ext.equals("ppm")) {
                PPM file = new PPM();
                try {
                    image = file.createImage(imagePath);
                    originalImage = file.createImage(imagePath);
                } catch (IOException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else if (ext.equals("png")) {
                try {
                    File file2 = new File(imagePath);
                    image = ImageIO.read(file2);
                    originalImage = ImageIO.read(file2);
                } catch (IOException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                JOptionPane.showMessageDialog(filePath, "Zły format pliku!");
                loadFile();
            }
        } else {
            return;
        }
    }

    private void saveFile() {
        JFileChooser saveFile = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG, GIF, PNG & TIFF Images", "jpg", "gif", "tif", "png");
        saveFile.addChoosableFileFilter(filter);
        saveFile.setFileFilter(filter);
        saveFile.setAcceptAllFileFilterUsed(false);
        saveFile.setDialogTitle("Zapisz plik");
        if (saveFile.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            String path = saveFile.getSelectedFile().toString();
            File outPutFile = new File(path);
            String ext = path.substring(path.lastIndexOf(".") + 1);
            if (ext.equals("jpg")) {
                try {
                    ImageIO.write(image, "jpg", outPutFile);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                JOptionPane.showMessageDialog(saveFile, "Zły format pliku!");
                saveFile();
            }
        } else {
            return;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); //To change body of generated methods, choose Tools | Templates.
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(image, 0, 0, null);
    }

}
