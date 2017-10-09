package sample.primitive;

import javafx.scene.canvas.GraphicsContext;

public class RectangleFigure extends AbstractFigure implements Figure {

    public RectangleFigure(GraphicsContext g) {
        super(g);
    }

    @Override
    public void draw(double x1, double y1, double x2, double y2) {
        if (x1 > x2) {
            double tmp = x1;
            x1 = x2;
            x2 = tmp;
        }
        if (y1 > y2) {
            double tmp = y1;
            y1 = y2;
            y2 = tmp;
        }
        g.strokeRect(x1, y1, x2 - x1, y2 - y1);
    }

    @Override
    public void drawWithValues(double x1, double y1, double x2, double y2) {
        draw(x1, y1, x1 + x2, y1 + y2);
    }
}
