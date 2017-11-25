package sample.utils.binarization;

import javafx.scene.image.WritableImage;
import sample.utils.ModifyImage;

public interface Binarization {

    WritableImage binarize(WritableImage wi);

    default ModifyImage cloneWithGrayscale(WritableImage wi) {
        return ModifyImage.getImageGrayScale(wi);
    }
}
