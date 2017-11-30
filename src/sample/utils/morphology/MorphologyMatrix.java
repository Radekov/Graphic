package sample.utils.morphology;

import java.util.List;

public class MorphologyMatrix {

    private boolean[][] matrix;

    public MorphologyMatrix(List<List<Boolean>> matrix) {
        this.matrix = new boolean[matrix.size()][matrix.get(0).size()];
        for (int row = 0; row < matrix.size(); row++) {
            for (int col = 0; col < matrix.get(row).size(); col++) {
                this.matrix[row][col] = matrix.get(row).get(col);
            }
        }
    }

    public int getHeight() {
        return matrix[0].length;
    }

    public int getWidth() {
        return matrix.length;
    }

    public boolean[][] getMatrix() {
        return matrix;
    }

    public int getWidthOffset() {
        return -(getWidth() / 2);
    }

    public int getHeightOffset() {
        return -(getHeight() / 2);
    }
}
