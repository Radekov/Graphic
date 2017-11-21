package sample.controller.dialog;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ToggleGroup;
import sample.enums.GrayLevelMethod;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class GrayLevelDialog extends AbstractDialogController {

    @FXML ToggleGroup toggleGroup;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void manipulatePicture(ActionEvent event) {
        result.clear();
        GrayLevelMethod method = Arrays.stream(GrayLevelMethod.values())
                .filter(e -> e.toString().equals(toggleGroup.getSelectedToggle().getUserData()))
                .findFirst().get();

        result.put("method", method);
        closeStage();
    }

    public void cancel(ActionEvent event) {

    }


}
