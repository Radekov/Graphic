package sample.utils.modifygraylevelimpl;

import javafx.scene.paint.Color;
import sample.utils.ModifyGrayLevel;

public class ModifyGrayLevelAverage implements ModifyGrayLevel {

    @Override
    public Color calculateNewGrayPixel(Color oldPixel) {
        double canal = (oldPixel.getRed() + oldPixel.getGreen() + oldPixel.getBlue()) / 3.0;
        return Color.color(canal, canal, canal);
    }
}
