package knn;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;

import static javax.imageio.ImageIO.write;

public class Imagette {

    int[][] gris;

    int etiquette;

    public Imagette(int l, int c) {
        this.gris = new int[l][c];
    }

    public int getGris(int l, int c) {
        return this.gris[l][c];
    }

    public Imagette setGris(int l, int c, int value) {
        this.gris[l][c] = value;
        return this;
    }

    public int getWidth() {
        return this.gris[0].length;
    }

    public int getHeight() {
        return this.gris.length;
    }

    public void save(String path) throws IOException {
        BufferedImage bi = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
        for (int l = 0; l < this.getHeight(); l++) {
            for (int c = 0; c < this.getWidth(); c++) {
                int value = this.getGris(l, c);
                bi.setRGB(c, l, new Color(value, value, value).getRGB());
            }
        }
        File outputFile = new File(path);
        write(bi, "png", outputFile);
    }

    public static Donnees loadImagettes(int size, String imagesPath, String labelsPath){
        File imagesFile = new File(imagesPath);
        File labelsFile = new File(labelsPath);
        Donnees donnees = new Donnees();
        try {
            DataInputStream imageLecteur = new DataInputStream(new FileInputStream(imagesFile));
            DataInputStream labelLecteur = new DataInputStream(new FileInputStream(labelsFile));

            imageLecteur.readInt();
            labelLecteur.readInt();

            imageLecteur.readInt();
            labelLecteur.readInt();

            int linesCount = imageLecteur.readInt();
            int columnCount = imageLecteur.readInt();

            ArrayList<Imagette> imagettes = new ArrayList<>();

            for (int i = 0; i < size; i++) {
                Imagette imagette = new Imagette(linesCount, columnCount);
                for (int l = 0; l < linesCount; l++) {
                    for (int c = 0; c < columnCount; c++) {
                        int octet = imageLecteur.readUnsignedByte();
                        imagette.setGris(l, c, octet);
                    }
                }
                imagette.etiquette = labelLecteur.readUnsignedByte();
//                imagette.save("train/imagette" + i + "_"+ labelImagette + ".png");
                donnees.getImagettes().add(imagette);
            }

            imageLecteur.close();
            labelLecteur.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return donnees;
    }
}
