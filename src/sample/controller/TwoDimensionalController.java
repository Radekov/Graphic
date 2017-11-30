package sample.controller;

import javafx.beans.property.IntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import sample.polygon.PolygonCanvas;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class TwoDimensionalController extends AbstractController {

    @FXML
    public AnchorPane centerPane;

    @FXML
    public VBox propertyPane;
    public Button addPointButton;
    @FXML public TextField x, y;

    private PolygonCanvas canvas;

    private Map<Spinner<Integer>, IntegerProperty> spinnerIntegerPropertyMap = new HashMap<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        x.textProperty().addListener(xListener);
        y.textProperty().addListener(yListener);

        canvas = new PolygonCanvas();
        centerPane.getChildren().add(canvas);
    }

    public void addPointPressed(ActionEvent actionEvent) {
        canvas.setMode(PolygonCanvas.Mode.ADD_POINT_MODE);
    }


    public void translateModePressed(ActionEvent actionEvent) {
        canvas.setMode(PolygonCanvas.Mode.TRANSLATE_MODE);
    }

    public void translate(ActionEvent actionEvent) {
        canvas.getSelectedShape().translate(Integer.parseInt(x.getText()), Integer.parseInt(y.getText()));
        canvas.draw();
    }


    public PolygonCanvas getCanvas() {
        return canvas;
    }

    ChangeListener<String> xListener = (observable, oldValue, newValue) -> {
        if (!newValue.matches("\\d*")) {
            x.setText(newValue.replaceAll("[^\\d]", ""));
        }
    };

    ChangeListener<String> yListener = (observable, oldValue, newValue) -> {
        if (!newValue.matches("\\d*")) {
            y.setText(newValue.replaceAll("[^\\d]", ""));
        }
    };
}
