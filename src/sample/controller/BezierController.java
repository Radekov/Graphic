package sample.controller;

import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static sample.utils.Binomial.binomial;

public class BezierController implements Initializable {

    @FXML
    BorderPane borderPane;
    @FXML TextField power;
    @FXML Slider precise;
    @FXML Canvas canvasCurve;
    @FXML private ListView<Circle> listView;

    private double borderX = 2.0;
    private double borderY = 42.0;
    private double step = 0.01;

    ObservableList<Circle> circleObservableList = FXCollections.observableArrayList();
    GraphicsContext g2d;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listView.setItems(circleObservableList);
        listView.setCellFactory(listV -> new ListViewCell());

        Circle a = prepareCircle(102, 142);
        Circle b = prepareCircle(142, 60);
        Circle c = prepareCircle(292, 312);
        Circle d = prepareCircle(300, 142);
        circleObservableList.addAll(a, b, c, d);
        g2d = canvasCurve.getGraphicsContext2D();

        borderPane.getChildren().addAll(a, b, c, d);
        power.textProperty().addListener(powerListener);
        precise.valueProperty().addListener(preciseListener);
        updateCurve();
    }

    private Circle prepareCircle(int x, int y) {
        Circle circle = new Circle(x, y, 5, Color.WHITE);
        circle.setStroke(Color.BLACK);
        circle.setOnMouseDragged(event -> {
            circle.setCenterX(event.getSceneX());
            circle.setCenterY(event.getSceneY());
            updateCurve();
        });
        return circle;
    }

    private void updateCurve() {
        g2d.beginPath();
        g2d.setLineWidth(2);
        g2d.setStroke(Color.BLACK);
        g2d.setFill(Color.BLACK);
        g2d.moveTo(circleObservableList.get(0).getCenterX() - borderX, circleObservableList.get(0).getCenterY() - borderY);
        g2d.fillRect(0, 0, canvasCurve.getWidth(), canvasCurve.getHeight());
        g2d.clearRect(0, 0, canvasCurve.getWidth(), canvasCurve.getHeight());
        boolean first = true;
        int n = circleObservableList.size() - 1;
        for (double t = 0.0; t <= 1.0; t += step) {
            int x = 0, y = 0;
            for (int i = 0; i <= n; i++) {
                Circle p = circleObservableList.get(i);
                double bi = (binomial(n, i) * Math.pow(t, i) * Math.pow(1 - t, n - i));
                x += (int) ((p.getCenterX() - borderX) * bi);
                y += (int) ((p.getCenterY() - borderY) * bi);
            }
            g2d.lineTo(x, y);
        }
        g2d.stroke();
        g2d.closePath();
    }

    ChangeListener<String> powerListener = (observable, oldValue, newValue) -> {
        if (!newValue.matches("\\d*")) {
            power.setText(newValue.replaceAll("[^\\d]", ""));
        }
        Integer countCircles = Integer.parseInt(newValue) + 1;
        if (circleObservableList.size() > countCircles) {
            for (int i = countCircles; i < circleObservableList.size(); i++) {
                borderPane.getChildren().remove(circleObservableList.get(i));
            }
            listView.getItems().remove(countCircles, circleObservableList.size());
//            circleObservableList.remove(countCircles, circleObservableList.size());
        } else if (circleObservableList.size() < countCircles) {
            for (int i = 0; i < (countCircles + 1) - circleObservableList.size(); i++) {
                Circle circle = prepareCircle(100, 152);
                circleObservableList.add(circle);
                borderPane.getChildren().add(circleObservableList.get(circleObservableList.size() - 1));
            }
        }
        listView.refresh();
        updateCurve();
    };

    ChangeListener<Number> preciseListener = (observable, oldValue, newValue) -> {
        step = 1 / Math.pow(10, newValue.intValue());
        updateCurve();
    };

    class ListViewCell extends ListCell<Circle> {
        @Override
        protected void updateItem(Circle item, boolean empty) {
            super.updateItem(item, empty);
            if (item != null) {
                PointCircleData pointCircle = new PointCircleData();
                pointCircle.setInfo(circleObservableList.indexOf(item), item);
                setGraphic(pointCircle.getBox());
            }
        }

    }

    class PointCircleData {
        @FXML private VBox vBox;
        @FXML private Label point;
        @FXML private TextField x;
        @FXML private TextField y;

        public PointCircleData() {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/point-circle-cell.fxml"));
            fxmlLoader.setController(this);
            try {
                fxmlLoader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        public void setInfo(Integer number, Circle item) {
            point.setText("P" + number);
            x.setText(String.valueOf(item.getCenterX()));
            y.setText(String.valueOf(item.getCenterY()));

            x.textProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue.matches("\\d*")) {
                    x.setText(newValue.replaceAll("[^\\d]", ""));
                }
                item.setCenterX(Double.parseDouble(newValue));
                updateCurve();
            });
            y.textProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue.matches("\\d*")) {
                    y.setText(newValue.replaceAll("[^\\d]", ""));
                }
                item.setCenterY(Double.parseDouble(newValue));
                updateCurve();
            });

            item.centerXProperty().addListener((observable, oldValue, newValue) -> {
                x.setText(String.valueOf(newValue.intValue()));
            });

            item.centerYProperty().addListener((observable, oldValue, newValue) -> {
                y.setText(String.valueOf(newValue.intValue()));
            });
        }

        public VBox getBox() {
            return vBox;
        }
    }
}
