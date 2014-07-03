package org.ss.poi;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

public class IMGRead {

    private static final int IMG_WIDTH = 255;

    private static final int IMG_HEIGHT = 255;

    public Map<String, Color[]> read(String fileName) {
        System.err.println("Reading: " + fileName);
        File file = new File(fileName);

        BufferedImage source, image;//source and resized images
        Map<String, Color[]> data = new TreeMap<String, Color[]>();
        try {
            image = ImageIO.read(file);//read picture from file
            //int type = source.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : source.getType();//get type
            //image = resizeImage(source, type);//resize
            if ("on".equals(Parameters.getDecreaseColor())) {
                image = convert8(image);
            }
            //image = source; // :)

            // Getting pixel color for every pixel
            for (Integer y = 0; y < image.getHeight(); y++) {
                System.err.println("Reading picture. Line " + y + " of " + image.getHeight());
                Color[] line = new Color[image.getWidth()];
                for (int x = 0; x < image.getWidth(); x++) {
                    int clr = image.getRGB(x, y);
                    line[x] = new Color(clr);
                }
                data.put(String.format("%03d", y), line);

            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return data;
    }

    private static BufferedImage resizeImage(BufferedImage originalImage, int type) {
        BufferedImage resizedImage = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, type);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, IMG_WIDTH, IMG_HEIGHT, null);
        g.dispose();

        return resizedImage;
    }

    /**
     * Converts the source image to 8-bit colour using the default 256-colour
     * palette. No transparency.
     *
     * @param src the source image to convert
     * @return a copy of the source image with an 8-bit colour depth
     * see http://www.java2s.com/Code/Java/2D-Graphics-GUI/Providesusefulmethodsforconvertingimagesfromonecolourdepthtoanother.htm
     */
    public static BufferedImage convert8(BufferedImage src) {
        BufferedImage dest = new BufferedImage(src.getWidth(), src.getHeight(),
                BufferedImage.TYPE_BYTE_INDEXED);
        ColorConvertOp cco = new ColorConvertOp(src.getColorModel()
                .getColorSpace(), dest.getColorModel().getColorSpace(), null);
        cco.filter(src, dest);
        return dest;
    }
}
