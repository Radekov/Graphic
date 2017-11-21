package sample.controller.dialog;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import sample.enums.Mode;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class AddSubtractDialogController extends AbstractDialogController {

    @FXML TextField red, green, blue;

    @FXML ToggleGroup toggleGroup;

    public void initialize(URL url, ResourceBundle rb) {

    }

    public void manipulatePicture(ActionEvent event) {
        result.clear();
        Mode mode = Arrays.stream(Mode.values())
                .filter(e -> e.toString().equals(toggleGroup.getSelectedToggle().getUserData()))
                .findFirst().get();

        Double r = Double.parseDouble(red.getCharacters().toString());
        Double g = Double.parseDouble(green.getCharacters().toString());
        Double b = Double.parseDouble(blue.getCharacters().toString());
        if (!(mode.equals(Mode.MULTIPLY) || mode.equals(Mode.DIVIDE))) {
            r = r / 255.0;
            g = g / 255.0;
            b = b / 255.0;
        }
        Double[] colorArray = {r, g, b};
        result.put("mode", mode);
        result.put("colorArray", colorArray);
        closeStage();
    }

    public void cancel(ActionEvent event) {

    }


}
