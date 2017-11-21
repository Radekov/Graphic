package sample.controller;

import javafx.fxml.Initializable;
import sample.Main;

public abstract class AbstractController implements Initializable {

    protected Main main;

    public void setMainApp(Main main) {
        this.main = main;
    }


}
