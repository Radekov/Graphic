package sample.utils.histogram;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritablePixelFormat;
import sample.utils.ModifyImage;

public class Histogram {

    private int[] histogram = new int[256];
    private int[] cumulativeDistribution;

    public int[] getHistogram() {
        return histogram;
    }

    public static Histogram calculateHistogram(Image image) {
        ModifyImage modifyImage = ModifyImage.getImageGrayScale(image);

        int width = (int) modifyImage.getWidth();
        int height = (int) modifyImage.getHeight();
        byte[] buffer = new byte[width * height * 4];

        PixelReader pixelReader = modifyImage.getPixelReader();
        pixelReader.getPixels(0, 0, width, height, WritablePixelFormat.getByteBgraPreInstance(), buffer, 0, width * 4);

        Histogram histogram = new Histogram();
        int[] histArray = histogram.getHistogram();

        for (int i = 0; i < buffer.length; i += 4) {
            histArray[buffer[i] & 0xFF]++;
        }
        return histogram;
    }


    public void setHistogram(int[] histogram) {
        this.histogram = histogram;
    }

    public int[] getCumulativeDistribution() {
        if (cumulativeDistribution == null) {
            cumulativeDistribution = new int[256];
            cumulativeDistribution[0] = histogram[0];
            for (int i = 1; i < histogram.length; i++) {
                cumulativeDistribution[i] = cumulativeDistribution[i - 1] + histogram[i];
            }
        }
        return cumulativeDistribution;
    }

    public ModifyImage stretchImageHistogram(ModifyImage image) {
        int min = 0, max = 255;
        for (int i = 0; i < getHistogram().length; i++) {
            if (getHistogram()[i] > 0) {
                min = i;
                break;
            }
        }
        for (int i = getHistogram().length - 1; i >= 0; i--) {
            if (getHistogram()[i] > 0) {
                max = i;
                break;
            }
        }

        if (min == max) {
            return image;
        }

        byte[] lut = new byte[256];
        for (int i = 0; i < lut.length; i++) {
            if (i < min) {
                lut[i] = 0;
            } else if (i > max) {
                lut[i] = (byte) 255;
            } else {
                lut[i] = (byte) ((255.0 / (max - min)) * (i - min));
            }
        }

        for (int w = 0; w < (int) image.getWidth(); w++) {
            for (int h = 0; h < (int) image.getHeight(); h++) {

            }
        }

        return image.modifyLightSelf(lut);
    }

    public ModifyImage equalizeHistogram(ModifyImage image) {
        int[] cumulativeDistribution = getCumulativeDistribution();
        int minCDstr = 0;
        for (int i = 0; i < cumulativeDistribution.length; i++) {
            if (cumulativeDistribution[i] > 0) {
                minCDstr = cumulativeDistribution[i];
                break;
            }
        }

        int pixels = (int) (image.getWidth() * image.getHeight());
        byte[] lut = new byte[256];
        for (int i = 0; i < cumulativeDistribution.length; i++) {
            lut[i] = (byte) ((int) (((double) cumulativeDistribution[i] - minCDstr) / ((double) pixels - minCDstr) * 255.0));
        }

        return image.modifyLightSelf(lut);
    }
}
