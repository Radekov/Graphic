package sample.utils.modifygraylevelimpl;

import javafx.scene.paint.Color;
import sample.utils.ModifyGrayLevel;

public class ModifyGrayLevelYUV implements ModifyGrayLevel {

    @Override
    public Color calculateNewGrayPixel(Color oldPixel) {
        double canal = (oldPixel.getRed() * .299 + oldPixel.getGreen() * .587 + oldPixel.getBlue() * .114);
        return Color.color(canal, canal, canal);
    }
}
