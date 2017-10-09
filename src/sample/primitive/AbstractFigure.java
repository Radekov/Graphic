package sample.primitive;

import javafx.scene.canvas.GraphicsContext;

public abstract class AbstractFigure implements Figure {

    protected GraphicsContext g;

    public AbstractFigure(GraphicsContext g) {
        this.g = g;
    }
}
