package sample.controller;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import sample.format_image.PPM;
import sample.primitive.CircleFigure;
import sample.primitive.Figure;
import sample.primitive.LineFigure;
import sample.primitive.RectangleFigure;

import javax.imageio.ImageIO;
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

    private FileChooser openFileChooser;
    private FileChooser saveFileChooser;

    private GraphicsContext g;
    private Figure figure;

    private double x1, y1;
    private double x2, y2;

    public void initialize() {
//        anchorPane.prefWidthProperty().addListener((ov, oldValue, newValue) -> {canvas.setWidth(newValue.doubleValue())});
//        anchorPane.prefHeightProperty().addListener((ov, oldValue, newValue) -> {canvas.setHeight(newValue.doubleValue())});
        canvas.setCursor(Cursor.CROSSHAIR);
        g = canvas.getGraphicsContext2D();
        figure = new LineFigure(g);
        shape.setText("LINIA");
        g.setLineWidth(1);
        drawLine(null);
        openFileChooser = new FileChooser();
        openFileChooser.setTitle("Otwórzcie plik");
        openFileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PPM6 P3", "*.ppm"),
                new FileChooser.ExtensionFilter("JPG JPEG", "*.jpg", "*.jpeg"));

        saveFileChooser = new FileChooser();
        saveFileChooser.setTitle("Zapiszcie plik");
        saveFileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPG JPEG", "*.jpg", "*.jpeg"));
    }

    @FXML
    private void drawLine(ActionEvent actionEvent) {
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
        File selectedFile = openFileChooser.showOpenDialog(anchorPane.getScene().getWindow());
        switch (getFileExtension(selectedFile)) {
            case "PPM":
                drawPPMToCanva(selectedFile);
                break;
            case "JPG":
            case "JPEG":
                Image image = new Image("file:" + selectedFile.getAbsolutePath());
                canvas.setHeight(image.getHeight());
                canvas.setWidth(image.getWidth());
                g.drawImage(image, 0, 0);
                break;
        }

    }

    private void drawPPMToCanva(File file) {
        try {
            PPM ppm = new PPM(file.getAbsolutePath(), canvas);
            Image image = SwingFXUtils.toFXImage(ppm.getBufferedImage(), null);
            canvas.setWidth(image.getWidth());
            canvas.setHeight(image.getHeight());
            g.drawImage(image, 0, 0);
        } catch (Exception e) {
            //TODO wyśweitl bład
            System.out.println("blad");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Problemowe czyny");
            alert.setHeaderText("Mamy kłopoto w tymże pliku");
            alert.setContentText(e.getMessage());
            alert.show();
            e.printStackTrace();
        }
    }

    private static String getFileExtension(File file) {
        String fileName = file.getName();
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
            return fileName.substring(fileName.lastIndexOf(".") + 1).toUpperCase();
        else return "";
    }

    @FXML
    protected void saveFile(ActionEvent event) {
        File saveFile = saveFileChooser.showSaveDialog(anchorPane.getScene().getWindow());
        SnapshotParameters sp = new SnapshotParameters();
        sp.setFill(Color.TRANSPARENT);
        if (saveFile != null) {
            WritableImage wi = new WritableImage((int) canvas.getWidth(), (int) canvas.getHeight());
            try {
                ImageIO.write(SwingFXUtils.fromFXImage(canvas.snapshot(sp, wi), null), "png", saveFile);
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Problemowe czyny");
                alert.setHeaderText("Mamy kłopoto w tymże pliku");
                alert.setContentText(e.getMessage());
                alert.show();
            }
        }
    }

}
