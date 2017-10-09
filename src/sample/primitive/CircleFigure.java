package sample.primitive;

import javafx.scene.canvas.GraphicsContext;

public class CircleFigure extends AbstractFigure implements Figure {

    public CircleFigure(GraphicsContext g) {
        super(g);
    }

    @Override
    public void draw(double x1, double y1, double x2, double y2) {
        if (x2 < x1) {
            double tmp = x1;
            x1 = x2;
            x2 = tmp;
        }
        x2 = x2 - x1;
        if (y2 < y1) {
            double tmp = y1;
            y1 = y2;
            y2 = tmp;
        }
        y2 = y2 - y1;
        g.strokeOval(x1, y1, x2, y2);
    }

    @Override
    public void drawWithValues(double x1, double y1, double x2, double y2) {
        g.strokeOval(x1, y1, x1 + x2, y1 + y2);
    }
}
