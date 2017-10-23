package sample.controller;

import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.paint.Color;


public class RgbCmykController {

    private static final int MAX = 255;

    @FXML
    Label red, green, blue, cyan, magenta, yellow, black;

    @FXML
    Slider sred, sgreen, sblue, scyan, smagenta, syellow, sblack;

    @FXML
    Canvas rectColor;
    GraphicsContext g;

    public void initialize() {
        g = rectColor.getGraphicsContext2D();
        g.fillRect(0, 0, rectColor.getWidth(), rectColor.getHeight());

        sred.valueProperty().addListener(changeListenerRGB);
        sgreen.valueProperty().addListener(changeListenerRGB);
        sblue.valueProperty().addListener(changeListenerRGB);

        scyan.valueProperty().addListener(changeListenerCMYK);
        smagenta.valueProperty().addListener(changeListenerCMYK);
        syellow.valueProperty().addListener(changeListenerCMYK);
        sblack.valueProperty().addListener(changeListenerCMYK);


    }

    ChangeListener<Number> changeListenerRGB = (observable, oldValue, newValue) -> {
        updateRect();

        double r = sred.getValue() / 255.0,
                g = sgreen.getValue() / 255.0,
                b = sblue.getValue() / 255.0;

//        setCMYKValue(cyanSpinner, (1 - getRGBValue(redSpinner) - getCMYKValue(blackSpinner)) / (1 - getCMYKValue(blackSpinner)));
//        setCMYKValue(magentaSpinner, (1 - getRGBValue(greenSpinner) - getCMYKValue(blackSpinner)) / (1 - getCMYKValue(blackSpinner)));
//        setCMYKValue(yellowSpinner, (1 - getRGBValue(blueSpinner) - getCMYKValue(blackSpinner)) / (1 - getCMYKValue(blackSpinner)));

        double k = (Math.min(1 - r, Math.min(1 - g, 1 - b)));
        sblack.setValue(Math.round(100 * k));
        scyan.setValue(Math.round(100 * (1 - r - k) / (1 - k)));
        smagenta.setValue(Math.round(100 * (1 - g - k) / (1 - k)));
        syellow.setValue(Math.round(100 * (1 - b - k) / (1 - k)));

        updateLabels();

    };

    ChangeListener<Number> changeListenerCMYK = (observable, oldValue, newValue) -> {
        double c = scyan.getValue(),
                m = smagenta.getValue(),
                y = syellow.getValue(),
                k = sblack.getValue();

        int r = (int) Math.round(255 * (1 - Math.min(1, c / 100.0 * (1 - k / 100.0) + k / 100.)));
        int g = (int) Math.round(255 * (1 - Math.min(1, m / 100.0 * (1 - k / 100.0) + k / 100.)));
        int b = (int) Math.round(255 * (1 - Math.min(1, y / 100.0 * (1 - k / 100.0) + k / 100.)));

        sred.setValue(r);
        sgreen.setValue(g);
        sblue.setValue(b);

        updateRect();
        updateLabels();
    };

    private void updateRect() {
        Color color = Color.rgb((int) sred.getValue(), (int) sgreen.getValue(), (int) sblue.getValue());
        g.setFill(color);
        g.fillRect(0, 0, rectColor.getWidth(), rectColor.getHeight());
    }

    private void updateLabels() {
        red.setText("" + (int) sred.getValue());
        green.setText("" + (int) sgreen.getValue());
        blue.setText("" + (int) sblue.getValue());

        black.setText("" + (int) sblack.getValue());
        cyan.setText("" + (int) scyan.getValue());
        magenta.setText("" + (int) smagenta.getValue());
        yellow.setText("" + (int) syellow.getValue());
    }
}
