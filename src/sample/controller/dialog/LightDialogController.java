package sample.controller.dialog;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import sample.enums.Mode;

import java.net.URL;
import java.util.ResourceBundle;

public class LightDialogController extends AbstractDialogController {

    @FXML TextField light;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void manipulatePicture(ActionEvent event) {
        result.clear();
        Double l = Double.parseDouble(light.getCharacters().toString()) / 255.0;
        Double[] colorArray = {l, l, l};
        result.put("mode", Mode.ADD);
        result.put("colorArray", colorArray);
        closeStage();
    }

    public void cancel(ActionEvent event) {

    }
}
