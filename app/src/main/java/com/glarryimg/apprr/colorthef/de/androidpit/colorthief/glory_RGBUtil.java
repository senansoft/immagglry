
package com.glarryimg.apprr.colorthef.de.androidpit.colorthief;

public class glory_RGBUtil {


    public static int packRGB(int[] rgb) {
        if (rgb.length != 3) {
            throw new IllegalArgumentException("RGB array should contain exactly 3 values.");
        }
        return rgb[0] << 16 | rgb[1] << 8 | rgb[2];
    }


    public static int[] unpackRGB(int packedRgb) {
        int[] rgb = new int[3];
        rgb[0] = packedRgb >> 16 & 0xFF;
        rgb[1] = packedRgb >> 8 & 0xFF;
        rgb[2] = packedRgb & 0xFF;
        return rgb;
    }

    /**
     *
     * @param rgbArray
     *            the array of arrays containing unpacked RGB values
     * @return the array of packed RGB values
     * @throws IllegalArgumentException
     *             if at least one of rgb has not exactly 3 elements
     */
    public static int[] packRGBArray(int[][] rgbArray) {
        int[] packedArray = new int[rgbArray.length];
        for (int n = 0; n < rgbArray.length; ++n) {
            packedArray[n] = packRGB(rgbArray[n]);
        }
        return packedArray;
    }

    /**
     *
     * @param packedRgbArray
     *            the array of packed RGB values
     * @return the array of arrays containing unpacked RGB values
     */
    public static int[][] unpackRGBArray(int[] packedRgbArray) {
        int[][] rgbArray = new int[packedRgbArray.length][3];
        for (int n = 0; n < packedRgbArray.length; ++n) {
            rgbArray[n] = unpackRGB(packedRgbArray[n]);
        }
        return rgbArray;
    }

}