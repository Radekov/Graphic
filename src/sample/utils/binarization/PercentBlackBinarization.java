package sample.utils.binarization;

import javafx.scene.image.WritableImage;
import sample.utils.ModifyImage;
import sample.utils.histogram.Histogram;

public class PercentBlackBinarization implements Binarization {

    private double percent;

    public PercentBlackBinarization(int percent) {
        this.percent = percent / 100.0;
    }

    @Override
    public WritableImage binarize(WritableImage wi) {
        ModifyImage mi = cloneWithGrayscale(wi);
        int[] cumulativeDistribution = Histogram.calculateHistogram(wi).getCumulativeDistribution();

        int desired = (int) (percent * (mi.getWidth() * mi.getHeight()));
        int value = 0;
        byte[] lut = new byte[256];
        for (int i = 0; i < cumulativeDistribution.length; i++) {
            if (cumulativeDistribution[i] >= desired) {
                value = i;
                break;
            }

        }
        Binarization b = new ManualBinarization(value);
        return b.binarize(mi);
    }
}
