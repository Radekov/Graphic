package sample.utils.filters;

import javafx.scene.paint.Color;

public class GaussFilter extends AbstractFilter {

    public GaussFilter(int[] matrix) {
        super(matrix);
    }

    public static GaussFilter gauss1() {
        return new GaussFilter(MatrixConstans.gauss1);
    }

    public static GaussFilter gauss2() {
        return new GaussFilter(MatrixConstans.gauss2);
    }

    public static GaussFilter gauss3() {
        return new GaussFilter(MatrixConstans.gauss3);
    }

    public static GaussFilter gauss4() {
        return new GaussFilter(MatrixConstans.gauss4);
    }

    public static GaussFilter gauss5() {
        return new GaussFilter(MatrixConstans.gauss5);
    }

    @Override
    protected Color calculateColor(int[] color) {
        color[0] = color[0] / (16);
        color[1] = color[1] / (16);
        color[2] = color[2] / (16);

        return Color.rgb(color[0], color[1], color[2]);
    }
}
