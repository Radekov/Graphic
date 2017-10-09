package sample.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import sample.primitive.CircleFigure;
import sample.primitive.Figure;
import sample.primitive.LineFigure;
import sample.primitive.RectangleFigure;


public class BoardController {

    @FXML
    private AnchorPane anchorPane;

    @FXML
    BorderPane borderPane;

    @FXML
    private Canvas canvas;

    @FXML
    private TextField brushSize;

    @FXML
    private ColorPicker colorPicker;

    @FXML
    private CheckBox eraser;

    @FXML
    private Text shape;

    @FXML
    private TextField xOne;

    @FXML
    private TextField yOne;

    @FXML
    private TextField xTwo;

    @FXML
    private TextField yTwo;

    @FXML
    private HBox bottom;


    GraphicsContext g;
    Figure figure;

    double x1, y1;

    public void initialize() {
        anchorPane.prefWidthProperty().addListener((ov, oldValue, newValue) ->
                canvas.setWidth(newValue.doubleValue())
        );

        anchorPane.prefHeightProperty().addListener((ov, oldValue, newValue) ->
                canvas.setHeight(newValue.doubleValue() - 9 * bottom.getHeight())
        );
        canvas.setCursor(Cursor.CROSSHAIR);
        canvas.setOnMousePressed(event -> {
            x1 = event.getX();
            y1 = event.getY();
            System.out.println("setOnMousePressed:\t" + x1 + "\t" + y1);
        });
        canvas.setOnMouseReleased(event -> {
            figure.draw(x1, y1, event.getX(), event.getY());
            System.out.println("setOnMouseReleased:\t" + event.getX() + "\t" + event.getY());
            xOne.setText(Double.toString(x1));
            yOne.setText(Double.toString(y1));
            xTwo.setText(Double.toString(event.getX()));
            yTwo.setText(Double.toString(event.getY()));
        });
        g = canvas.getGraphicsContext2D();
        figure = new LineFigure(g);
        shape.setText("LINIA");
        g.setLineWidth(1);
        System.out.println(canvas.getWidth() + " " + canvas.getHeight());
    }

    @FXML
    public void handleSubmitButtonAction(ActionEvent actionEvent) {
        figure = new LineFigure(g);
        shape.setText("LINIA");
    }

    @FXML
    protected void drawRectangle(ActionEvent actionEvent) {
        figure = new RectangleFigure(g);
        shape.setText("PROSTOKĄT");
    }

    @FXML
    protected void drawCircle(ActionEvent actionEvent) {
        figure = new CircleFigure(g);
        shape.setText("TORNADO");
    }

    @FXML
    protected void drawWithValues(ActionEvent actionEvent) {
        figure.drawWithValues(Double.parseDouble(xOne.getText()),
                Double.parseDouble(yOne.getText()),
                Double.parseDouble(xTwo.getText()),
                Double.parseDouble(yTwo.getText()));
    }


}
