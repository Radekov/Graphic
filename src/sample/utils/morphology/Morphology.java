package sample.utils.morphology;

import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

public class Morphology {

    private boolean[][] matrix;

    private static Morphology instance;

    private Morphology() {

    }

    public static Morphology getInstance() {
        if (instance == null)
            instance = new Morphology();
        return instance;
    }

    private BinaryImage imageToBinaryImage(WritableImage image) {
        BinaryImage binaryImage = new BinaryImage((int) image.getWidth(), (int) image.getHeight());
        PixelReader pixelReader = image.getPixelReader();
        for (int h = 0; h < image.getHeight(); h++) {
            for (int w = 0; w < image.getWidth(); w++) {
                if ((pixelReader.getArgb(w, h) & 0xFFFFFF) > 0)
                    binaryImage.getValues()[w][h] = true;
                else
                    binaryImage.getValues()[w][h] = false;
            }
        }
        return binaryImage;
    }

    private WritableImage binaryImageToImage(BinaryImage binaryImage) {
        WritableImage image = new WritableImage(binaryImage.getWidth(), binaryImage.getHeight());
        PixelWriter pixelWriter = image.getPixelWriter();
        boolean[][] booleanValues = binaryImage.getValues();
        for (int y = 0; y < binaryImage.getHeight(); y++) {
            for (int x = 0; x < binaryImage.getWidth(); x++) {
                if (booleanValues[x][y]) {
                    pixelWriter.setArgb(x, y, 0xFFFFFFFF);
                } else {
                    pixelWriter.setArgb(x, y, 0xFF000000);
                }
            }
        }
        return image;
    }

    public WritableImage dilation(WritableImage image, MorphologyMatrix matrix) {
        return binaryImageToImage(dilation(imageToBinaryImage(image), matrix));
    }

    private BinaryImage dilation(BinaryImage binaryImage, MorphologyMatrix matrix) {
        BinaryImage result = new BinaryImage(binaryImage.getWidth(), binaryImage.getHeight());
        boolean[][] origValues = binaryImage.getValues();
        boolean[][] resultValues = result.getValues();
        boolean[][] matrixValues = matrix.getMatrix();
        int offsetX = matrix.getWidthOffset();
        int offsetY = matrix.getHeightOffset();
        for (int y = 0; y < binaryImage.getHeight(); y++) {
            pixelLoop:
            for (int x = 0; x < binaryImage.getWidth(); x++) {
                for (int my = 0; my < matrix.getHeight(); my++) {
                    for (int mx = 0; mx < matrix.getWidth(); mx++) {
                        if (!matrixValues[mx][my]) {
                            continue;
                        }

                        int ix = x + mx + offsetX;
                        if (ix < 0 || ix >= binaryImage.getWidth()) {
                            continue;
                        }
                        int iy = y + my + offsetY;
                        if (iy < 0 || iy >= binaryImage.getHeight()) {
                            continue;
                        }

                        if (origValues[ix][iy]) {
                            resultValues[x][y] = true;
                            continue pixelLoop;
                        }
                    }
                }
            }
        }
        return result;
    }

    public WritableImage erosion(WritableImage image, MorphologyMatrix matrix) {
        return binaryImageToImage(erosion(imageToBinaryImage(image), matrix));
    }

    private BinaryImage erosion(BinaryImage binaryImage, MorphologyMatrix matrix) {
        BinaryImage result = new BinaryImage(binaryImage.getWidth(), binaryImage.getHeight());
        boolean[][] origValues = binaryImage.getValues();
        boolean[][] resultValues = result.getValues();
        boolean[][] matrixValues = matrix.getMatrix();
        int offsetX = matrix.getWidthOffset();
        int offsetY = matrix.getHeightOffset();
        boolean foundValidValues;
        for (int y = 0; y < binaryImage.getHeight(); y++) {
            pixelLoop:
            for (int x = 0; x < binaryImage.getWidth(); x++) {
                foundValidValues = false;
                resultValues[x][y] = true;
                for (int my = 0; my < matrix.getHeight(); my++) {
                    for (int mx = 0; mx < matrix.getWidth(); mx++) {
                        if (!matrixValues[mx][my]) {
                            continue;
                        }

                        int ix = x + mx + offsetX;
                        if (ix < 0 || ix >= binaryImage.getWidth()) {
                            continue;
                        }
                        int iy = y + my + offsetY;
                        if (iy < 0 || iy >= binaryImage.getHeight()) {
                            continue;
                        }

                        foundValidValues = true;
                        if (!origValues[ix][iy]) {
                            resultValues[x][y] = false;
                            continue pixelLoop;
                        }
                    }
                }
                if (!foundValidValues) {
                    resultValues[x][y] = false;
                }
            }
        }
        return result;
    }

    public WritableImage opening(WritableImage image, MorphologyMatrix matrix) {
        return binaryImageToImage(dilation(erosion(imageToBinaryImage(image), matrix), matrix));
    }

    public WritableImage closing(WritableImage image, MorphologyMatrix matrix) {
        return binaryImageToImage(erosion(dilation(imageToBinaryImage(image), matrix), matrix));
    }

    private class BinaryImage {
        private boolean[][] val;
        private int width, height;

        public BinaryImage(int width, int height) {
            this.width = width;
            this.height = height;

            val = new boolean[width][height];
        }

        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return height;
        }

        public boolean[][] getValues() {
            return val;
        }
    }
}
