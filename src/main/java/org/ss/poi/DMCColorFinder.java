package org.ss.poi;

import java.awt.*;

/**
 * Created by vostapiv on 8/4/2014.
 */
public class DMCColorFinder {

    public static DMCColor findNearestDMCColor(Color currentColor) {
        DMCColor nearestColor = DMCColors.COLORS.get(0);
        Integer nearestDistance = new Integer(Integer.MAX_VALUE);
        for (DMCColor color : DMCColors.COLORS) {
            //TODO needs logic changes
            if (nearestDistance > Math.sqrt(
                    Math.pow(currentColor.getRed() - color.getRgb().getRed(), 2)
                            - Math.pow(currentColor.getGreen() - color.getRgb().getGreen(), 2)
                            - Math.pow(currentColor.getBlue() - color.getRgb().getBlue(), 2))) {
                nearestColor = color;
            }
        }
        return nearestColor;
    }
}
