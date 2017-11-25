package sample.utils.filters;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MedianFilter implements Filter {

    protected WritableImage writableImage;
    protected PixelWriter pixelWriter;
    protected PixelReader pixelReader;

    private int length = 3;
    private int toCenterMatrixLength = length / 2;

    protected List<Integer> rMedian = new ArrayList<>();
    protected List<Integer> gMedian = new ArrayList<>();
    protected List<Integer> bMedian = new ArrayList<>();

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
        for (int w = 0; w < toCenterMatrixLength; w++) {
            for (int h = 0; h < toCenterMatrixLength; h++) {
                clearMedians();
                for (int wm = w - toCenterMatrixLength, im = 0; im < length; wm++, im++) {
                    if (wm < 0) continue;
                    for (int hm = h - toCenterMatrixLength, jm = 0; jm < length; hm++, jm++) {
                        if (hm < 0) continue;
                        addColor(wm, hm);
                    }
                }
                pixelWriter.setColor(w, h, calculateColor());
            }
        }
    }

    private void calculateUpRightCorner() {

        for (int w = ((int) writableImage.getWidth()) - toCenterMatrixLength; w < (int) writableImage.getWidth(); w++) {
            for (int h = 0; h < toCenterMatrixLength; h++) {
                clearMedians();
                for (int wm = w - toCenterMatrixLength, im = 0; im < length && wm < (int) writableImage.getWidth(); wm++, im++) {
                    for (int hm = h - toCenterMatrixLength, jm = 0; jm < length; hm++, jm++) {
                        if (hm < 0) continue;
                        addColor(wm, hm);
                    }
                }
                pixelWriter.setColor(w, h, calculateColor());
            }
        }
    }

    private void calculateDownLeftCorner() {
        int[] sumColor = {0, 0, 0};
        for (int w = 0; w < toCenterMatrixLength; w++) {
            for (int h = ((int) writableImage.getHeight()) - toCenterMatrixLength; h < (int) writableImage.getHeight(); h++) {
                clearMedians();
                for (int wm = w - toCenterMatrixLength, im = 0; im < length; wm++, im++) {
                    if (wm < 0) continue;
                    for (int hm = h - toCenterMatrixLength, jm = 0; jm < length && hm < (int) writableImage.getHeight(); hm++, jm++) {
                        addColor(wm, hm);
                    }
                }
                pixelWriter.setColor(w, h, calculateColor());
            }
        }
    }

    private void calculateDownRightCorner() {
        int[] sumColor = {0, 0, 0};
        for (int w = ((int) writableImage.getWidth()) - toCenterMatrixLength; w < (int) writableImage.getWidth(); w++) {
            for (int h = ((int) writableImage.getHeight()) - toCenterMatrixLength; h < (int) writableImage.getHeight(); h++) {
                clearMedians();
                for (int wm = w - toCenterMatrixLength, im = 0; im < length && wm < (int) writableImage.getWidth(); wm++, im++) {
                    if (wm < 0) continue;
                    for (int hm = h - toCenterMatrixLength, jm = 0; jm < length && hm < (int) writableImage.getHeight(); hm++, jm++) {
                        addColor(wm, hm);
                    }
                }
                pixelWriter.setColor(w, h, calculateColor());
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
        for (int w = toCenterMatrixLength; w < ((int) writableImage.getWidth()) - toCenterMatrixLength; w++) {
            for (int h = 0; h < toCenterMatrixLength; h++) {
                clearMedians();
                for (int wm = w - toCenterMatrixLength, im = 0; im < length && wm < (int) writableImage.getWidth(); wm++, im++) {
                    for (int hm = h - toCenterMatrixLength, jm = 0; jm < length; hm++, jm++) {
                        if (hm < 0) continue;
                        addColor(wm, hm);
                    }
                }
                pixelWriter.setColor(w, h, calculateColor());
            }
        }
    }

    private void calculateBorderRight() {
        for (int w = ((int) writableImage.getWidth()) - toCenterMatrixLength; w < ((int) writableImage.getWidth()); w++) {
            for (int h = toCenterMatrixLength; h < ((int) writableImage.getHeight()) - toCenterMatrixLength; h++) {
                clearMedians();
                for (int wm = w - toCenterMatrixLength, im = 0; im < length && wm < (int) writableImage.getWidth(); wm++, im++) {
                    for (int hm = h - toCenterMatrixLength, jm = 0; jm < length && hm < (int) writableImage.getHeight(); hm++, jm++) {
                        addColor(wm, hm);
                    }
                }
                pixelWriter.setColor(w, h, calculateColor());
            }
        }
    }

    private void calculateBorderLeft() {
        for (int w = 0; w < toCenterMatrixLength; w++) {
            for (int h = toCenterMatrixLength; h < ((int) writableImage.getHeight()) - toCenterMatrixLength; h++) {
                clearMedians();
                for (int wm = w - toCenterMatrixLength, im = 0; im < length; wm++, im++) {
                    if (wm < 0) continue;
                    for (int hm = h - toCenterMatrixLength, jm = 0; jm < length && hm < (int) writableImage.getHeight(); hm++, jm++) {
                        addColor(wm, hm);
                    }
                }
                pixelWriter.setColor(w, h, calculateColor());
            }
        }
    }

    private void calculateBorderDown() {
        for (int w = toCenterMatrixLength; w < ((int) writableImage.getWidth()) - toCenterMatrixLength; w++) {
            for (int h = ((int) writableImage.getHeight()) - toCenterMatrixLength; h < ((int) writableImage.getHeight()); h++) {
                clearMedians();
                for (int wm = w - toCenterMatrixLength, im = 0; im < length && wm < (int) writableImage.getWidth(); wm++, im++) {
                    for (int hm = h - toCenterMatrixLength, jm = 0; jm < length && hm < (int) writableImage.getHeight(); hm++, jm++) {
                        addColor(wm, hm);
                    }
                }
                pixelWriter.setColor(w, h, calculateColor());
            }
        }
    }

    private void calculateMiddle() {
        for (int w = toCenterMatrixLength; w < ((int) writableImage.getWidth()) - toCenterMatrixLength; w++) {
            for (int h = toCenterMatrixLength; h < ((int) writableImage.getHeight() - toCenterMatrixLength); h++) {
                clearMedians();
                pixelWriter.setColor(w, h, calculateColor(w, h));
            }
        }
    }

    protected Color calculateColor(int w, int h) {
        int[] sumColor = {0, 0, 0};
        for (int wm = w - toCenterMatrixLength, im = 0; im < length; wm++, im++) {
            for (int hm = h - toCenterMatrixLength, jm = 0; jm < length; hm++, jm++) {
                addColor(wm, hm);
            }
        }
        return calculateColor();
    }


    private void clearMedians() {
        rMedian.clear();
        bMedian.clear();
        gMedian.clear();
    }

    private void addColor(int wm, int hm) {
        Color c = pixelReader.getColor(wm, hm);
        rMedian.add((int) (c.getRed() * 255.));
        bMedian.add((int) (c.getGreen() * 255.));
        gMedian.add((int) (c.getBlue() * 255.));
    }

    private Color calculateColor() {
        Collections.sort(rMedian);
        Collections.sort(bMedian);
        Collections.sort(gMedian);
        int middle = rMedian.size() / 2;
        return Color.rgb(rMedian.get(middle), bMedian.get(middle), gMedian.get(middle));
    }
}
