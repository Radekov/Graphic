package sample.utils.binarization;

import javafx.scene.image.WritableImage;
import sample.utils.ModifyImage;

public class ManualBinarization implements Binarization {

    private int value;

    public ManualBinarization(int val) {
        this.value = val;
    }

    @Override
    public WritableImage binarize(WritableImage wi) {
        ModifyImage mi = cloneWithGrayscale(wi);

        byte[] lut = new byte[256];
        for (int i = value; i < 256; i++) {
            lut[i] = (byte) 255;
        }
        return mi.modifyLightSelf(lut);
    }
}
