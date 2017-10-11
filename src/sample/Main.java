package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.format_image.PPM;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        PPM ppm = new PPM("C:\\Users\\R\\Desktop\\PoissonImageEditing-master\\images\\fish.ppm");

        AnchorPane root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Scene scene = new Scene(root);

        primaryStage.setTitle("Program Radka");
        primaryStage.setScene(scene);
        primaryStage.show();

        root.prefWidthProperty().bind(scene.widthProperty());
        root.prefHeightProperty().bind(scene.heightProperty());


    }


    public static void main(String[] args) {
        launch(args);
    }
}
