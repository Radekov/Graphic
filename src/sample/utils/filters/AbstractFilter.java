package sample.utils.filters;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public abstract class AbstractFilter implements Filter {

    protected final int[] matrix;
    protected final int singleLength;
    protected final int toCenterMatrixLength;

    protected WritableImage writableImage;
    protected PixelWriter pixelWriter;
    protected PixelReader pixelReader;

    protected int countPixel = 0;

    public AbstractFilter(int[] matrix) {
        this.matrix = matrix;
        this.singleLength = ((int) Math.sqrt(matrix.length));
        this.toCenterMatrixLength = (singleLength - 1) / 2;
    }

    @Override
    public WritableImage filter(Image picture) {
        initialize(picture);
        calculateCorners();
        calculateBorders();
        calculateMiddle();
        return writableImage;
    }

    private void initialize(Image picture) {
        pixelReader = picture.getPixelReader();
        writableImage = new WritableImage((int) picture.getWidth(), (int) picture.getHeight());
        pixelWriter = writableImage.getPixelWriter();
    }

    private void calculateCorners() {
        ((Runnable) () -> calculateUpLeftCorner()).run();
        ((Runnable) () -> calculateUpRightCorner()).run();
        ((Runnable) () -> calculateDownLeftCorner()).run();
        ((Runnable) () -> calculateDownRightCorner()).run();
    }

    private void calculateUpLeftCorner() {
        int[] sumColor = {0, 0, 0};
        for (int w = 0; w < toCenterMatrixLength; w++) {
            for (int h = 0; h < toCenterMatrixLength; h++) {
                countPixel = 0;
                for (int wm = w - toCenterMatrixLength, im = 0; im < singleLength; wm++, im++) {
                    if (wm < 0) continue;
                    for (int hm = h - toCenterMatrixLength, jm = 0; jm < singleLength; hm++, jm++) {
                        if (hm < 0) continue;
                        countPixel++;
                        sumColor = setSumCalculateCorner(sumColor, wm, im, hm, jm);
                    }
                }
                pixelWriter.setColor(w, h, calculateColor(sumColor));
            }
        }
    }

    private void calculateUpRightCorner() {
        int[] sumColor = {0, 0, 0};
        for (int w = ((int) writableImage.getWidth()) - toCenterMatrixLength; w < (int) writableImage.getWidth(); w++) {
            for (int h = 0; h < toCenterMatrixLength; h++) {
                countPixel = 0;
                for (int wm = w - toCenterMatrixLength, im = 0; im < singleLength && wm < (int) writableImage.getWidth(); wm++, im++) {
                    for (int hm = h - toCenterMatrixLength, jm = 0; jm < singleLength; hm++, jm++) {
                        if (hm < 0) continue;
                        countPixel++;
                        sumColor = setSumCalculateCorner(sumColor, wm, im, hm, jm);
                    }
                }
                pixelWriter.setColor(w, h, calculateColor(sumColor));
            }
        }
    }

    private void calculateDownLeftCorner() {
        int[] sumColor = {0, 0, 0};
        for (int w = 0; w < toCenterMatrixLength; w++) {
            for (int h = ((int) writableImage.getHeight()) - toCenterMatrixLength; h < (int) writableImage.getHeight(); h++) {
                countPixel = 0;
                for (int wm = w - toCenterMatrixLength, im = 0; im < singleLength; wm++, im++) {
                    if (wm < 0) continue;
                    for (int hm = h - toCenterMatrixLength, jm = 0; jm < singleLength && hm < (int) writableImage.getHeight(); hm++, jm++) {
                        countPixel++;
                        sumColor = setSumCalculateCorner(sumColor, wm, im, hm, jm);
                    }
                }
                pixelWriter.setColor(w, h, calculateColor(sumColor));
            }
        }
    }

    private void calculateDownRightCorner() {
        int[] sumColor = {0, 0, 0};
        for (int w = ((int) writableImage.getWidth()) - toCenterMatrixLength; w < (int) writableImage.getWidth(); w++) {
            for (int h = ((int) writableImage.getHeight()) - toCenterMatrixLength; h < (int) writableImage.getHeight(); h++) {
                countPixel = 0;
                for (int wm = w - toCenterMatrixLength, im = 0; im < singleLength && wm < (int) writableImage.getWidth(); wm++, im++) {
                    if (wm < 0) continue;
                    for (int hm = h - toCenterMatrixLength, jm = 0; jm < singleLength && hm < (int) writableImage.getHeight(); hm++, jm++) {
                        countPixel++;
                        sumColor = setSumCalculateCorner(sumColor, wm, im, hm, jm);
                    }
                }
                pixelWriter.setColor(w, h, calculateColor(sumColor));
            }
        }
    }

    private void calculateBorders() {
        ((Runnable) () -> calculateBorderUp()).run();
        ((Runnable) () -> calculateBorderRight()).run();
        ((Runnable) () -> calculateBorderDown()).run();
        calculateBorderLeft();
    }

    private void calculateBorderUp() {
        int[] sumColor = {0, 0, 0};
        for (int w = toCenterMatrixLength; w < ((int) writableImage.getWidth()) - toCenterMatrixLength; w++) {
            for (int h = 0; h < toCenterMatrixLength; h++) {
                countPixel = 0;
                for (int wm = w - toCenterMatrixLength, im = 0; im < singleLength && wm < (int) writableImage.getWidth(); wm++, im++) {
                    for (int hm = h - toCenterMatrixLength, jm = 0; jm < singleLength; hm++, jm++) {
                        if (hm < 0) continue;
                        countPixel++;
                        sumColor = setSumCalculateCorner(sumColor, wm, im, hm, jm);
                    }
                }
                pixelWriter.setColor(w, h, calculateColor(sumColor));
            }
        }
    }

    private void calculateBorderRight() {
        int[] sumColor = {0, 0, 0};
        for (int w = ((int) writableImage.getWidth()) - toCenterMatrixLength; w < ((int) writableImage.getWidth()); w++) {
            for (int h = toCenterMatrixLength; h < ((int) writableImage.getHeight()) - toCenterMatrixLength; h++) {
                countPixel = 0;
                for (int wm = w - toCenterMatrixLength, im = 0; im < singleLength && wm < (int) writableImage.getWidth(); wm++, im++) {
                    for (int hm = h - toCenterMatrixLength, jm = 0; jm < singleLength && hm < (int) writableImage.getHeight(); hm++, jm++) {
                        countPixel++;
                        sumColor = setSumCalculateCorner(sumColor, wm, im, hm, jm);
                    }
                }
                pixelWriter.setColor(w, h, calculateColor(sumColor));
            }
        }
    }

    private void calculateBorderLeft() {
        int[] sumColor = {0, 0, 0};
        for (int w = 0; w < toCenterMatrixLength; w++) {
            for (int h = toCenterMatrixLength; h < ((int) writableImage.getHeight()) - toCenterMatrixLength; h++) {
                countPixel = 0;
                for (int wm = w - toCenterMatrixLength, im = 0; im < singleLength; wm++, im++) {
                    if (wm < 0) continue;
                    for (int hm = h - toCenterMatrixLength, jm = 0; jm < singleLength && hm < (int) writableImage.getHeight(); hm++, jm++) {
                        countPixel++;
                        sumColor = setSumCalculateCorner(sumColor, wm, im, hm, jm);
                    }
                }
                pixelWriter.setColor(w, h, calculateColor(sumColor));
            }
        }
    }

    private void calculateBorderDown() {
        int[] sumColor = {0, 0, 0};
        for (int w = toCenterMatrixLength; w < ((int) writableImage.getWidth()) - toCenterMatrixLength; w++) {
            for (int h = ((int) writableImage.getHeight()) - toCenterMatrixLength; h < ((int) writableImage.getHeight()); h++) {
                countPixel = 0;
                for (int wm = w - toCenterMatrixLength, im = 0; im < singleLength && wm < (int) writableImage.getWidth(); wm++, im++) {
                    for (int hm = h - toCenterMatrixLength, jm = 0; jm < singleLength && hm < (int) writableImage.getHeight(); hm++, jm++) {
                        countPixel++;
                        sumColor = setSumCalculateCorner(sumColor, wm, im, hm, jm);
                    }
                }
                pixelWriter.setColor(w, h, calculateColor(sumColor));
            }
        }
    }


    private void calculateMiddle() {
        for (int w = toCenterMatrixLength; w < ((int) writableImage.getWidth()) - toCenterMatrixLength; w++) {
            for (int h = toCenterMatrixLength; h < ((int) writableImage.getHeight() - toCenterMatrixLength); h++) {
                countPixel = 0;
                pixelWriter.setColor(w, h, calculateColor(w, h));
            }
        }
    }

    protected Color calculateColor(int w, int h) {
        int[] sumColor = {0, 0, 0};
        for (int wm = w - toCenterMatrixLength, im = 0; im < singleLength; wm++, im++) {
            for (int hm = h - toCenterMatrixLength, jm = 0; jm < singleLength; hm++, jm++) {
                countPixel++;
                sumColor = setSumCalculateCorner(sumColor, wm, im, hm, jm);
            }
        }
        return calculateColor(sumColor);
    }

    private int[] setSumCalculateCorner(int[] sumColor, int wm, int im, int hm, int jm) {
        int index = jm * singleLength + im;
        Color c = pixelReader.getColor(wm, hm);
        sumColor[0] = sumColor[0] + ((int) (c.getRed() * 255)) * matrix[index];
        sumColor[1] = sumColor[1] + ((int) (c.getGreen() * 255)) * matrix[index];
        sumColor[2] = sumColor[2] + ((int) (c.getBlue() * 255)) * matrix[index];
        return sumColor;
    }

    protected abstract Color calculateColor(int[] color);

}
