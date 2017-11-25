package sample.utils.filters;

import javafx.scene.paint.Color;

public class AverageFilter extends AbstractFilter {

    public AverageFilter(int[] matrix) {
        super(matrix);
    }

    public static AverageFilter averraFilter3x3() {
        return new AverageFilter(MatrixConstans.averagingFilter3x3);
    }

    public static AverageFilter averraFilter5x5() {
        return new AverageFilter(MatrixConstans.averagingFilter5x5);
    }

    public static AverageFilter averraFilter7x7() {
        return new AverageFilter(MatrixConstans.averagingFilter7x7);
    }


    @Override
    protected Color calculateColor(int[] color) {
        color[0] = color[0] / (countPixel);
        color[1] = color[1] / (countPixel);
        color[2] = color[2] / (countPixel);

        color[0] = color[0] > 255 ? 255 : color[0];
        color[1] = color[1] > 255 ? 255 : color[1];
        color[2] = color[2] > 255 ? 255 : color[2];
        return Color.rgb(color[0], color[1], color[2]);
    }
}
