package sample.controller.dialog;

import javafx.stage.Stage;
import sample.controller.AbstractController;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractDialogController extends AbstractController implements DialogController {

    protected Map<String, Object> result = new HashMap<String, Object>();

    protected Stage stage = null;

    public Map<String, Object> getResult() {
        return this.result;
    }

    /**
     * setting the stage of this view
     *
     * @param stage
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Closes the stage of this view
     */
    protected void closeStage() {
        if (stage != null) {
            stage.close();
        }
    }
}
