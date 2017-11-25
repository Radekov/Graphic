package sample.utils.binarization;

import javafx.scene.image.WritableImage;
import sample.utils.ModifyImage;
import sample.utils.histogram.Histogram;

public class MeanIterativeBinarization implements Binarization {

    @Override
    public WritableImage binarize(WritableImage wi) {
        ModifyImage mi = cloneWithGrayscale(wi);
        Histogram histogram = Histogram.calculateHistogram(mi);

        int threshold, newThreshold = 128;
        long totalWhite, totalBlack, countWhite, countBlack;
        double meanWhite, meanBlack;

        do {
            threshold = newThreshold;

            totalWhite = totalBlack = countWhite = countBlack = 0;
            for (int i = 0; i < histogram.getHistogram().length; i++) {
                if (i < threshold) {
                    totalBlack += histogram.getHistogram()[i] * i;
                    countBlack += histogram.getHistogram()[i];
                } else {
                    totalWhite += histogram.getHistogram()[i] * i;
                    countWhite += histogram.getHistogram()[i];
                }
            }

            meanWhite = ((double) totalWhite / countWhite);
            meanBlack = ((double) totalBlack / countBlack);

            newThreshold = (int) Math.abs((meanWhite + meanBlack) / 2);

            System.out.println("Calculated threshold: " + newThreshold);
        }
        while (Math.abs((int) meanWhite - (int) meanBlack) != 0 && threshold != newThreshold);

        Binarization b = new ManualBinarization(newThreshold);
        return b.binarize(mi);
    }
}
