

package com.glarryimg.apprr.colorthef.de.androidpit.colorthief;

import android.graphics.Bitmap;

import java.util.Arrays;


public class glory_ColorThief {

    private static final int DEFAULT_QUALITY = 10;
    private static final boolean DEFAULT_IGNORE_WHITE = true;


    public static int[][] getPalette(Bitmap sourceImage, int colorCount) {
        glory_MMCQ.CMap cmap = getColorMap(sourceImage, colorCount);
        if (cmap == null) {
            return null;
        }
        return cmap.palette();
    }

    public static glory_MMCQ.CMap getColorMap(Bitmap sourceImage, int colorCount) {
        return getColorMap(sourceImage, colorCount, DEFAULT_QUALITY, DEFAULT_IGNORE_WHITE);
    }


    public static glory_MMCQ.CMap getColorMap(
            Bitmap sourceImage,
            int colorCount,
            int quality,
            boolean ignoreWhite) {
        if (colorCount < 2 || colorCount > 256) {
            throw new IllegalArgumentException("Specified colorCount must be between 2 and 256.");
        }
        if (quality < 1) {
            throw new IllegalArgumentException("Specified quality should be greater then 0.");
        }

        int[][] pixelArray;

        pixelArray = getPixelsSlow(sourceImage, quality, ignoreWhite);

        // Send array to quantize function which clusters values using median cut algorithm
        glory_MMCQ.CMap cmap = glory_MMCQ.quantize(pixelArray, colorCount);
        return cmap;
    }

    /**
     * Gets the image's pixels via BufferedImage.getRGB(..). Slow, but the fast method doesn't work
     * for all color models.
     * 
     * @param sourceImage
     *            the source image
     * @param quality
     *            1 is the highest quality settings. 10 is the default. There is a trade-off between
     *            quality and speed. The bigger the number, the faster the palette generation but
     *            the greater the likelihood that colors will be missed.
     * @param ignoreWhite
     *            if <code>true</code>, white pixels are ignored
     * 
     * @return an array of pixels (each an RGB int array)
     */
    private static int[][] getPixelsSlow(
            Bitmap sourceImage,
            int quality,
            boolean ignoreWhite) {
        int width = sourceImage.getWidth();
        int height = sourceImage.getHeight();

        int pixelCount = width * height;

        // numRegardedPixels must be rounded up to avoid an ArrayIndexOutOfBoundsException if all
        // pixels are good.
        int numRegardedPixels = (pixelCount + quality - 1) / quality;

        int numUsedPixels = 0;

        int[][] res = new int[numRegardedPixels][];
        int r, g, b;

        for (int i = 0; i < pixelCount; i += quality) {
            int row = i / width;
            int col = i % width;
            int rgb = sourceImage.getPixel(col, row);

            r = (rgb >> 16) & 0xFF;
            g = (rgb >> 8) & 0xFF;
            b = (rgb) & 0xFF;
            if (!(ignoreWhite && r > 250 && g > 250 && b > 250)) {
                res[numUsedPixels] = new int[] {r, g, b};
                numUsedPixels++;
            }
        }

        return Arrays.copyOfRange(res, 0, numUsedPixels);
    }

}
