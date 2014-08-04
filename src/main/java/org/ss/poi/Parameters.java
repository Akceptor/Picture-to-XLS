package org.ss.poi;

/**
 * Created by vostapiv on 7/3/2014.
 */
public class Parameters {
    private static String decreaseColor = "off";
    private static String cellSize = "PIXEL";
    private static String resize = "off";
    private static String findDMCColor = "off";
    private static int width = 255;
    private static int height = 255;

    public static String getDecreaseColor() {
        return decreaseColor;
    }

    public static void setDecreaseColor(String decreaseColor) {
        Parameters.decreaseColor = decreaseColor;
        System.out.println("DECREASE COLOR:" + decreaseColor);
    }

    public static String getCellSize() {
        return cellSize;
    }

    public static void setCellSize(String cellSize) {
        Parameters.cellSize = cellSize;
        System.out.println("CELL SIZE:" + cellSize);
    }

    public static String getResize() {
        return resize;
    }

    public static void setResize(String resize) {

        System.out.println("DO RESIZE:" + resize);
        Parameters.resize = resize;
    }

    public static int getWidth() {
        return width;
    }

    public static void setWidth(int width) {
        System.out.println("RESIZE WIDTH:" + width);
        Parameters.width = width;
    }

    public static int getHeight() {
        return height;
    }

    public static void setHeight(int height) {
        System.out.println("RESIZE HEIGHT:" + height);
        Parameters.height = height;
    }

    public static void setFindDMCColor(String findDMCColor) {
        Parameters.findDMCColor = findDMCColor;
        System.out.println("FIND DMC COLOR:" + findDMCColor);
    }

    public static String getFindDMCColor() {
        return findDMCColor;
    }
}
