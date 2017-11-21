package sample.utils;

import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import sample.enums.GrayLevelMethod;
import sample.enums.Mode;
import sample.utils.modifygraylevelimpl.ModifyGrayLevelAverage;
import sample.utils.modifygraylevelimpl.ModifyGrayLevelYUV;
import sample.utils.modifyimpl.ModifyAdd;
import sample.utils.modifyimpl.ModifyDivide;
import sample.utils.modifyimpl.ModifyMultiply;
import sample.utils.modifyimpl.ModifySubstract;

public class ModifyImage extends WritableImage {

    private Modify modify;


    public ModifyImage(int width, int height) {
        super(width, height);
    }

    public ModifyImage(PixelReader reader, int width, int height) {
        super(reader, width, height);
    }

    public ModifyImage(PixelReader reader, int x, int y, int width, int height) {
        super(reader, x, y, width, height);
    }

    public ModifyImage modifySelf(Double[] c, Mode mode) {
        PixelWriter pw = this.getPixelWriter();
        PixelReader pr = this.getPixelReader();

        setModify(mode);

        for (int i = 0; i < this.getWidth(); i++) {
            for (int j = 0; j < this.getHeight(); j++) {
                Color oldPixel = pr.getColor(i, j);
                Color newPixel = Color.color(
                        modify.calculateSingleColor(oldPixel.getRed(), c[0]),
                        modify.calculateSingleColor(oldPixel.getGreen(), c[1]),
                        modify.calculateSingleColor(oldPixel.getBlue(), c[2])
                );
                pw.setColor(i, j, newPixel);
            }
        }
        return this;
    }

    private void setModify(Mode mode) {
        switch (mode) {
            case ADD:
                modify = new ModifyAdd();
                break;
            case SUBSTRACT:
                modify = new ModifySubstract();
                break;
            case MULTIPLY:
                modify = new ModifyMultiply();
                break;
            case DIVIDE:
                modify = new ModifyDivide();
                break;
        }
    }

    public ModifyImage modifySelfGrayLevel(GrayLevelMethod method) {
        PixelWriter pw = this.getPixelWriter();
        PixelReader pr = this.getPixelReader();

        ModifyGrayLevel m = getModifyImage(method);

        for (int i = 0; i < this.getWidth(); i++) {
            for (int j = 0; j < this.getHeight(); j++) {
                pw.setColor(i, j, m.calculateNewGrayPixel(pr.getColor(i, j)));
            }
        }

        return this;
    }

    private ModifyGrayLevel getModifyImage(GrayLevelMethod method) {
        switch (method) {
            case AVERAGE:
                return new ModifyGrayLevelAverage();
            case YUV:
                return new ModifyGrayLevelYUV();
        }
        return null;
    }
}
