package sample.utils.filters;

import javafx.scene.paint.Color;

public class SobelFilter extends AbstractFilter {

    public SobelFilter(int[] matrix) {
        super(matrix);
    }

    public static SobelFilter horizontalSobel() {
        return new SobelFilter(MatrixConstans.horizontalSobel);
    }

    public static SobelFilter verticalSobel() {
        return new SobelFilter(MatrixConstans.verticalSobel);
    }

    @Override
    protected Color calculateColor(int[] color) {
        color[0] = color[0] > 255 ? 255 : color[0];
        color[1] = color[1] > 255 ? 255 : color[1];
        color[2] = color[2] > 255 ? 255 : color[2];

        color[0] = color[0] < 0 ? 0 : color[0];
        color[1] = color[1] < 0 ? 0 : color[1];
        color[2] = color[2] < 0 ? 0 : color[2];
        return Color.rgb(color[0], color[1], color[2]);
    }
}
