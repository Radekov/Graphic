package sample.utils;

import javafx.scene.image.*;
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

    public static ModifyImage getImageGrayScale(Image image) {
        ModifyImage modifyImage = new ModifyImage((int) image.getWidth(), (int) image.getHeight());
        PixelWriter pw = modifyImage.getPixelWriter();
        PixelReader pr = image.getPixelReader();
        ModifyGrayLevel m = modifyImage.getModifyImage(GrayLevelMethod.YUV);

        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                pw.setColor(i, j, m.calculateNewGrayPixel(pr.getColor(i, j)));
            }
        }
        return modifyImage;
    }

    public ModifyImage modifyLightSelf(byte[] l) {
        byte[][] lut = {l, l, l};
        int width = (int) this.getWidth();
        int height = (int) this.getHeight();
        byte[] buffer = new byte[width * height * 4];

        PixelReader pixelReader = this.getPixelReader();
        pixelReader.getPixels(0, 0, width, height, WritablePixelFormat.getByteBgraPreInstance(), buffer, 0, width * 4);

        int r = 2, g = 1, b = 0;
        for (int i = 0; i < buffer.length; i += 4) {
            buffer[i + b] = lut[b][buffer[i + b] & 0xFF];
            buffer[i + g] = lut[g][buffer[i + g] & 0xFF];
            buffer[i + r] = lut[r][buffer[i + r] & 0xFF];
        }

        PixelWriter pixelWriter = this.getPixelWriter();
        pixelWriter.setPixels(0, 0, width, height, PixelFormat.getByteBgraPreInstance(), buffer, 0, width * 4);
        return this;


    }
}
