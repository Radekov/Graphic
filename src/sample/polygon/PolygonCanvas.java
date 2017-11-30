package sample.polygon;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

public class PolygonCanvas extends Pane {

    public enum Mode {
        SELECT_MODE,
        ADD_POINT_MODE,
        TRANSLATE_MODE,
    }

    private Canvas canvas;
    private ObjectProperty<PolygonShape> selectedShape = new SimpleObjectProperty<>(null);
    private ObjectProperty<Mode> modeProperty = new SimpleObjectProperty<>(Mode.ADD_POINT_MODE);
    private List<PolygonShape> shapes = new ArrayList<>();
    private double centerX, centerY, lastX, lastY;
    private boolean dragStarted = true, lockedIn = false;

    public PolygonCanvas() {
        canvas = new Canvas(1000, 1000);

        canvas.setOnMouseClicked(event -> {
            if (getMode() != Mode.ADD_POINT_MODE || event.getButton() != MouseButton.PRIMARY) {
                return;
            }

            if (getSelectedShape() == null) {
                setSelectedShape(new PolygonShape(this));
                shapes.add(getSelectedShape());
            }

            PolygonPoint point = getSelectedShape().addPoint((int) event.getX(), (int) event.getY());
            getChildren().add(point);
            point.toFront();
            draw();
        });

        canvas.setOnMousePressed(e -> {
            if (getSelectedShape() == null) {
                return;
            }

            centerX = lastX = e.getX();
            centerY = lastY = e.getY();
            dragStarted = true;
            lockedIn = false;
        });

        canvas.setOnMouseReleased(e -> {
            dragStarted = false;
        });

        canvas.setOnMouseDragged(e -> {
            if (!dragStarted || e.getButton() != MouseButton.PRIMARY || getSelectedShape() == null) {
                return;
            }

            if (getMode() == Mode.TRANSLATE_MODE) {
                getSelectedShape().translate(e.getX() - lastX, e.getY() - lastY);
                draw();
            }
            lastX = e.getX();
            lastY = e.getY();
        });

        getChildren().addAll(canvas);
        draw();
    }

    public void draw() {
        if (shapes == null) {
            return;
        }

        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (PolygonShape polygonShape : shapes) {
            polygonShape.draw(graphicsContext);
        }
    }

    public void onPointRemoved(PolygonPoint point) {
        getChildren().remove(point);
        draw();
    }

    public void onPointClicked(PolygonPoint point) {
        if (getMode() == Mode.SELECT_MODE) {
            setSelectedShape(point.getShape());
        }
    }

    public void onPointDragged(PolygonPoint point, double x, double y) {
        if (getMode() == Mode.ADD_POINT_MODE) {
            point.setX(x);
            point.setY(y);
            draw();
        } else if (getMode() == Mode.TRANSLATE_MODE) {
            getSelectedShape().translate(x - lastX, y - lastY);
            lastX = x;
            lastY = y;
            draw();
        }
    }

    public void onPointDragStarted(PolygonPoint point, double x, double y) {
        lastX = x;
        lastY = y;
        dragStarted = true;
    }

    public void onPointDragStopped(PolygonPoint point, double x, double y) {
        dragStarted = false;
    }

    public void setMode(Mode mode) {
        modeProperty.setValue(mode);
    }

    public Mode getMode() {
        return modeProperty.get();
    }

    public ObjectProperty<Mode> modePropertyProperty() {
        return modeProperty;
    }

    public PolygonShape getSelectedShape() {
        return selectedShape.get();
    }

    public void setSelectedShape(PolygonShape selectedShape) {
        this.selectedShape.set(selectedShape);
    }

    public ObjectProperty<PolygonShape> selectedShapeProperty() {
        return selectedShape;
    }

    public List<PolygonShape> getShapes() {
        return shapes;
    }

}
