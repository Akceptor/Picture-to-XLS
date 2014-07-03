package org.ss.poi;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class POIWrite {

    private static Map<String, short[]> pixelSizes;

    static {
        short[] sizePixel = {25, 50};
        short[] sizeSmall = {50, 100};
        short[] sizeBig = {300, 600};
        pixelSizes = new HashMap<String, short[]>();
        pixelSizes.put("PIXEL", sizePixel);
        pixelSizes.put("SMALL", sizeSmall);
        pixelSizes.put("BIG", sizeBig);
    }

    public void write(Map<String, Color[]> data, XSSFWorkbook workbook, String sheetName, String filename) {
        System.err.println("Writing: " + sheetName);
        XSSFSheet sheet = workbook.createSheet("Picture" + sheetName);
        Map<Color, XSSFCellStyle> styleMap = new HashMap<Color, XSSFCellStyle>();
        XSSFCellStyle blackStyle = workbook.createCellStyle();
        blackStyle.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        blackStyle.setFillForegroundColor(new XSSFColor(Color.BLACK));
        styleMap.put(Color.BLACK, blackStyle);

        Set<String> keyset = data.keySet();
        int rownum = 0;
        for (String key : keyset) {
            Row row = sheet.createRow(rownum++);

            double freeMem = (Runtime.getRuntime().freeMemory() / (1024 * 1024));
            double totalMem = (Runtime.getRuntime().totalMemory() / (1024 * 1024));
            String memoryUsageString = "Memory: free: " + freeMem + "Mb, total: " + totalMem + "Mb";
            System.err.println("ROW: " + rownum + ", " + memoryUsageString);

            row.setHeight(pixelSizes.get(Parameters.getCellSize())[0]);
            Color[] objArr = data.get(key);
            int cellnum = 0;
            for (Color color : objArr) {
                sheet.setColumnWidth(cellnum, pixelSizes.get(Parameters.getCellSize())[1]);
                Cell cell = row.createCell(cellnum++);

                XSSFCellStyle style = styleMap.get(color);
                if (style == null) {
                    style = workbook.createCellStyle();
                    style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
                    style.setFillForegroundColor(new XSSFColor(color));
                    styleMap.put(color, style);
                    System.err.println("Style added, now: " + styleMap.size());
                }
                cell.setCellStyle(style);
            }
        }

        try {
            System.out.println("Writing file: " + filename);
            FileOutputStream out =
                    new FileOutputStream(new File(filename + ".xlsx"));

            workbook.write(out);
            out.close();
            System.out.println("Frame #" + sheetName + " written successfully...");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.err.println("Nr of styles: " + styleMap.size());
    }

    private XSSFColor setColor(byte r, byte g, byte b) {
        return new XSSFColor(new java.awt.Color(r, g, b));
    }

}
