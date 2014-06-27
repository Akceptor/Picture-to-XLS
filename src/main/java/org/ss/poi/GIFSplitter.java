package org.ss.poi;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class GIFSplitter {

    private IMGRead ir;

    private POIWrite pw;

    public GIFSplitter() {
        ir = new IMGRead();
        pw = new POIWrite();
    }
    
    private static void ensureTargetDirectoryExists(File aTargetDir) {
        if (!aTargetDir.exists()) {
            aTargetDir.mkdirs();
            System.out.println("Created: " + aTargetDir.getAbsolutePath());
        }
    }

    public void split(String fileName) {
        File file = new File(fileName);
        String time = Long.toString(System.currentTimeMillis());
        ensureTargetDirectoryExists(new File(System.getProperty("java.io.tmpdir")+"/temp/"+time+"/"));
        File f = new File(System.getProperty("java.io.tmpdir")+"/temp/"+time+"/");
        List<String> names = new ArrayList<String>(Arrays.asList(f.list()));
        System.err.println(">>"+names);
        if (fileName.endsWith("gif")) {
            ImageReader reader = ImageIO.getImageReadersBySuffix("GIF").next();
            ImageInputStream in;
            try {
                in = ImageIO.createImageInputStream(file);
                reader.setInput(in);
                HSSFWorkbook workbook = new HSSFWorkbook();
                for (int i = 0, count = reader.getNumImages(true); i < count; i++)
                {
                    BufferedImage image = reader.read(i);
                    ImageIO.write(image, "JPEG", new File(System.getProperty("java.io.tmpdir")+"/temp/" + time + "/" + i + ".jpg"));
                    Map<String, Object[]> data = ir.read(System.getProperty("java.io.tmpdir")+"/temp/" + time + "/" + i + ".jpg");
                    pw.write(data, workbook, Integer.toString(i), fileName);
                }

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }

}
