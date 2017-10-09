package sample.primitive;

import javafx.scene.canvas.GraphicsContext;

public class LineFigure extends AbstractFigure implements Figure {

    public LineFigure(GraphicsContext g) {
        super(g);
    }

    @Override
    public void draw(double x1, double y1, double x2, double y2) {
        g.strokeLine(x1, y1, x2, y2);
    }

    @Override
    public void drawWithValues(double x1, double y1, double x2, double y2) {
        draw(x1, y1, x2, y2);
    }
}
