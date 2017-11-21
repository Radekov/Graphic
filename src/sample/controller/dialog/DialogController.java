package sample.controller.dialog;

import javafx.stage.Stage;

import java.util.Map;

public interface DialogController {

    Map<String, Object> getResult();

    void setStage(Stage stage);

}
