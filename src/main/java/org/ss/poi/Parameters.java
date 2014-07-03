package org.ss.poi;

/**
 * Created by vostapiv on 7/3/2014.
 */
public class Parameters {
    private static String decreaseColor = "off";
    private static String cellSize = "PIXEL";

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
}
