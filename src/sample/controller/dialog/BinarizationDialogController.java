package sample.controller.dialog;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import sample.utils.binarization.BinarizationType;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class BinarizationDialogController extends AbstractDialogController {

    @FXML ToggleGroup toggleGroup;
    @FXML Label label;
    @FXML TextField value;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        result.clear();
    }

    public void manipulatePicture(ActionEvent event) {
        result.clear();
        BinarizationType mode = Arrays.stream(BinarizationType.values())
                .filter(e -> e.toString().equals(toggleGroup.getSelectedToggle().getUserData()))
                .findFirst().get();

        Integer v = Integer.parseInt(value.getCharacters().toString());

        result.put("mode", mode);
        if (mode.equals(BinarizationType.MANUAL) || mode.equals(BinarizationType.PERCENT_BLACK_SELECTION))
            result.put("value", v);
        closeStage();
    }

    public void cancel(ActionEvent event) {
        closeStage();
    }
}
