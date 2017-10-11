package sample.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sample.primitive.CircleFigure;
import sample.primitive.Figure;
import sample.primitive.LineFigure;
import sample.primitive.RectangleFigure;

import java.io.File;


public class BoardController {

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Canvas canvas;

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

    FileChooser fileChooser;

    private GraphicsContext g;
    private Figure figure;

    double x1, y1;
    double x2, y2;

    public void initialize() {
        canvas.setCursor(Cursor.CROSSHAIR);
        g = canvas.getGraphicsContext2D();
        drawLine(null);
        g.setLineWidth(1);
        fileChooser = new FileChooser();
        fileChooser.setTitle("Otwórzcie plik");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PPM6 P3", "*.ppm6", "*.p3"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
    }

    @FXML
    public void drawLine(ActionEvent actionEvent) {
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
    protected void canvasMousePressed(MouseEvent event) {
        x1 = event.getX();
        y1 = event.getY();
    }

    @FXML
    protected void canvasMouseDragged(MouseEvent event) {
        setCoordinates(event);
        updateValues();
    }


    @FXML
    protected void canvasMouseReleased(MouseEvent event) {
        setCoordinates(event);
        updateValues();
        figure.draw(x1, y1, x2, y2);
    }

    private void setCoordinates(MouseEvent event) {
        x2 = event.getX();
        y2 = event.getY();
    }

    //FIXME użyć StringProperty
    private void updateValues() {
        xOne.setText(Double.toString(x1));
        yOne.setText(Double.toString(y1));
        xTwo.setText(Double.toString(x2));
        yTwo.setText(Double.toString(y2));
    }

    @FXML
    protected void drawWithValues(ActionEvent actionEvent) {
        figure.drawWithValues(Double.parseDouble(xOne.getText()),
                Double.parseDouble(yOne.getText()),
                Double.parseDouble(xTwo.getText()),
                Double.parseDouble(yTwo.getText()));
    }

    @FXML
    protected void openFile(ActionEvent event) {
        File selectedFile = fileChooser.showOpenDialog((Stage) anchorPane.getScene().getWindow());
        Image image = new Image("file:" + selectedFile.getAbsolutePath());
        canvas.setWidth(image.getWidth());
        canvas.setHeight(image.getHeight());
        g.drawImage(image, 0, 0);
    }
}
