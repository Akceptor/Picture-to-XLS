package org.ss.poi;

import java.awt.*;

/**
 * Represents DMC Floss color
 */
public class DMCColor {
    private int dmcCode;
    private String dmcName;
    private Color rgb;
    private String rgbHEX;

    public DMCColor() {
        this.dmcCode = 0;
        this.dmcName = "Blank/White";
        this.rgb = Color.WHITE;
        this.rgbHEX = String.format("#%02x%02x%02x", rgb.getRed(), rgb.getGreen(), rgb.getBlue());
    }

    public DMCColor(int dmcCode, String dmcName, Color rgb) {
        this.dmcCode = dmcCode;
        this.dmcName = dmcName;
        this.rgb = rgb;
        this.rgbHEX = String.format("#%02x%02x%02x", rgb.getRed(), rgb.getGreen(), rgb.getBlue());
    }

    public DMCColor(int dmcCode, String dmcName, int iR, int iG, int iB) {
        this.dmcCode = dmcCode;
        this.dmcName = dmcName;
        this.rgb = new Color(iR, iG, iB);
        this.rgbHEX = String.format("#%02x%02x%02x", iR, iG, iB);
    }

    public int getDmcCode() {
        return dmcCode;
    }

    public void setDmcCode(int dmcCode) {
        this.dmcCode = dmcCode;
    }

    public String getDmcName() {
        return dmcName;
    }

    public void setDmcName(String dmcName) {
        this.dmcName = dmcName;
    }

    public Color getRgb() {
        return rgb;
    }

    public void setRgb(Color rgb) {
        this.rgb = rgb;
    }

    public String getRgbHEX() {
        return rgbHEX;
    }

    public void setRgbHEX(String rgbHEX) {
        this.rgbHEX = rgbHEX;
    }

    @Override
    public String toString() {
        return "DMCColor{" +
                "Code=" + dmcCode +
                ", DMC Name='" + dmcName + '\'' +
                ", RGB=" + rgb +
                ", HEX='" + rgbHEX + '\'' +
                '}';
    }
}
