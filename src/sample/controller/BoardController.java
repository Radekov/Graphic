package sample.controller;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.controller.dialog.*;
import sample.enums.GrayLevelMethod;
import sample.enums.Mode;
import sample.format_image.PPM;
import sample.primitive.CircleFigure;
import sample.primitive.Figure;
import sample.primitive.LineFigure;
import sample.primitive.RectangleFigure;
import sample.utils.ModifyImage;
import sample.utils.binarization.*;
import sample.utils.filters.*;
import sample.utils.histogram.Histogram;
import sample.utils.morphology.Morphology;
import sample.utils.morphology.MorphologyMatrix;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;


public class BoardController extends AbstractController {

    @FXML private AnchorPane anchorPane;

    @FXML private Canvas canvas;

    @FXML private Text shape;

    @FXML private TextField xOne;

    @FXML private TextField yOne;

    @FXML private TextField xTwo;

    @FXML private TextField yTwo;

    private FileChooser openFileChooser;
    private FileChooser saveFileChooser;

    private GraphicsContext g;
    private Figure figure;

    private double x1, y1;
    private double x2, y2;

    public void initialize(URL url, ResourceBundle rb) {
//        anchorPane.prefWidthProperty().addListener((ov, oldValue, newValue) -> {canvas.setWidth(newValue.doubleValue())});
//        anchorPane.prefHeightProperty().addListener((ov, oldValue, newValue) -> {canvas.setHeight(newValue.doubleValue())});
        canvas.setCursor(Cursor.CROSSHAIR);
        g = canvas.getGraphicsContext2D();
        figure = new LineFigure(g);
        shape.setText("LINIA");
        g.setLineWidth(1);
        drawLine(null);
        openFileChooser = new FileChooser();
        openFileChooser.setTitle("Otwórzcie plik");
        openFileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PPM6 P3", "*.ppm"),
                new FileChooser.ExtensionFilter("JPG JPEG", "*.jpg", "*.jpeg"));

        saveFileChooser = new FileChooser();
        saveFileChooser.setTitle("Zapiszcie plik");
        saveFileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPG JPEG", "*.jpg", "*.jpeg"));
    }

    @FXML
    private void drawLine(ActionEvent actionEvent) {
        figure = new LineFigure(g);
        shape.setText("LINIA");
    }

    @FXML
    protected void drawRectangle(ActionEvent actionEvent) {
        figure = new RectangleFigure(g);
        shape.setText("PROSTOKĄT");
    }

    @FXML
    protected void drawCircle(ActionEvent actionEvent) {
        figure = new CircleFigure(g);
        shape.setText("TORNADO");
    }

    @FXML
    protected void canvasMousePressed(MouseEvent event) {
        x1 = event.getX();
        y1 = event.getY();
    }

    @FXML
    protected void canvasMouseDragged(MouseEvent event) {
        setCoordinates(event);
        updateValues();
    }


    @FXML
    protected void canvasMouseReleased(MouseEvent event) {
        setCoordinates(event);
        updateValues();
        figure.draw(x1, y1, x2, y2);
    }

    private void setCoordinates(MouseEvent event) {
        x2 = event.getX();
        y2 = event.getY();
    }

    //FIXME użyć StringProperty
    private void updateValues() {
        xOne.setText(Double.toString(x1));
        yOne.setText(Double.toString(y1));
        xTwo.setText(Double.toString(x2));
        yTwo.setText(Double.toString(y2));
    }

    @FXML
    protected void drawWithValues(ActionEvent actionEvent) {
        figure.drawWithValues(Double.parseDouble(xOne.getText()),
                Double.parseDouble(yOne.getText()),
                Double.parseDouble(xTwo.getText()),
                Double.parseDouble(yTwo.getText()));
    }

    @FXML
    protected void openFile(ActionEvent event) {
        File selectedFile = openFileChooser.showOpenDialog(anchorPane.getScene().getWindow());
        switch (getFileExtension(selectedFile)) {
            case "PPM":
                drawPPMToCanva(selectedFile);
                break;
            case "JPG":
            case "JPEG":
                Image image = new Image("file:" + selectedFile.getAbsolutePath());
                canvas.setHeight(image.getHeight());
                canvas.setWidth(image.getWidth());
                g.drawImage(image, 0, 0);
                break;
        }

    }

    private void drawPPMToCanva(File file) {
        try {
            PPM ppm = new PPM(file.getAbsolutePath(), canvas);
            Image image = SwingFXUtils.toFXImage(ppm.getBufferedImage(), null);
            canvas.setWidth(image.getWidth());
            canvas.setHeight(image.getHeight());
            g.drawImage(image, 0, 0);
        } catch (Exception e) {
            //TODO wyśweitl bład
            System.out.println("blad");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Problemowe czyny");
            alert.setHeaderText("Mamy kłopoto w tymże pliku");
            alert.setContentText(e.getMessage());
            alert.show();
            e.printStackTrace();
        }
    }

    private static String getFileExtension(File file) {
        String fileName = file.getName();
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
            return fileName.substring(fileName.lastIndexOf(".") + 1).toUpperCase();
        else return "";
    }

    @FXML
    protected void saveFile(ActionEvent event) {
        File saveFile = saveFileChooser.showSaveDialog(anchorPane.getScene().getWindow());
        SnapshotParameters sp = new SnapshotParameters();
        sp.setFill(Color.TRANSPARENT);
        if (saveFile != null) {
            WritableImage wi = new WritableImage((int) canvas.getWidth(), (int) canvas.getHeight());
            try {
                ImageIO.write(SwingFXUtils.fromFXImage(canvas.snapshot(sp, wi), null), "png", saveFile);
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Problemowe czyny");
                alert.setHeaderText("Mamy kłopoto w tymże pliku");
                alert.setContentText(e.getMessage());
                alert.show();
            }
        }
    }

    @FXML
    protected void openCube(ActionEvent event) {
        openWindow("cube.fxml");
    }

    @FXML
    protected void openCustomColorDialog(ActionEvent event) {
        openWindow("rgb_cmyk.fxml");
    }

    @FXML
    protected void openBezierWindow(ActionEvent event) {
        openWindow("bezier.fxml");
    }

    @FXML
    protected void open2DWindow(ActionEvent event) {
        openWindow("2D.fxml");
    }

    private void openWindow(String file) {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getClassLoader().getResource(file));
            Stage stage = new Stage();
            stage.setTitle("My New Stage Title");
            stage.setScene(new Scene(root, 450, 450));
            stage.show();
            // Hide this current window (if this is what you want)
//            ((Node)(event.getSource())).getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void openAddSubstractDialog(ActionEvent event) {
        Map<String, Object> resultMap = openDialog(new AddSubtractDialogController(), "/add-substract-dialog.fxml");
        Double[] c = (Double[]) resultMap.get("colorArray");
        Mode m = (Mode) resultMap.get("mode");
        modifyForegroundImage(c, m);
    }

    @FXML
    private void openLightDialog(ActionEvent event) {
        Map<String, Object> resultMap = openDialog(new LightDialogController(), "/light-dialog.fxml");
        Double[] c = (Double[]) resultMap.get("colorArray");
        Mode m = (Mode) resultMap.get("mode");
        modifyForegroundImage(c, m);
    }

    @FXML
    private void openGrayLevelDialog(ActionEvent event) {
        Map<String, Object> resultMap = openDialog(new GrayLevelDialog(), "/gray-level-dialog.fxml");
        GrayLevelMethod m = (GrayLevelMethod) resultMap.get("method");
        modifyGrayLevelForegroundImage(m);
    }


    private Map<String, Object> openDialog(DialogController controller, String file) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(file));
        // initializing the controller
        loader.setController(controller);
        Parent layout;
        try {
            layout = loader.load();
            Scene scene = new Scene(layout);
            // this is the popup stage
            Stage popupStage = new Stage();
            // Giving the popup controller access to the popup stage (to allow the controller to close the stage)
            controller.setStage(popupStage);
            if (this.main != null) {
                popupStage.initOwner(main.getPrimaryStage());
            }
            popupStage.initModality(Modality.WINDOW_MODAL);
            popupStage.setScene(scene);
            popupStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return controller.getResult();
    }

    private void modifyForegroundImage(Double[] c, Mode m) {
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);
        WritableImage wi = canvas.snapshot(params, null);
        ModifyImage modifyImage = new ModifyImage(wi.getPixelReader(), (int) wi.getWidth(), (int) wi.getHeight());
        long start = System.currentTimeMillis();
        modifyImage.modifySelf(c, m);
        g.drawImage(modifyImage, 0, 0);
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }

    private void modifyGrayLevelForegroundImage(GrayLevelMethod m) {
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);
        WritableImage wi = canvas.snapshot(params, null);
        ModifyImage modifyImage = new ModifyImage(wi.getPixelReader(), (int) wi.getWidth(), (int) wi.getHeight());
        long start = System.currentTimeMillis();
        modifyImage.modifySelfGrayLevel(m);
        g.drawImage(modifyImage, 0, 0);
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }

    @FXML
    private void openFilterDialog(ActionEvent event) {
        Map<String, Object> resultMap = openDialog(new FilterSelectDialogController(), "/filter-select-dialog.fxml");
        FilterType filterType = (FilterType) resultMap.get("filterType");
        filterImage(filterType);
    }

    private void filterImage(FilterType filterType) {
        Filter filter = AverageFilter.averraFilter3x3();
        switch (filterType) {
            case AVERAGING_3x3:
                filter = AverageFilter.averraFilter3x3();
                break;
            case AVERAGING_5x5:
                filter = AverageFilter.averraFilter5x5();
                break;
            case AVERAGING_7x7:
                filter = AverageFilter.averraFilter7x7();
                break;
            case MEDIAN:
                filter = new MedianFilter();
                break;
            case SOBEL_HORIZONTAL:
                filter = SobelFilter.horizontalSobel();
                break;
            case SOBEL_VERTICAL:
                filter = SobelFilter.verticalSobel();
                break;
            case UNSHARP:
                break;
            case GAUSS_1:
                filter = GaussFilter.gauss1();
                break;
            case GAUSS_2:
                filter = GaussFilter.gauss2();
                break;
            case GAUSS_3:
                filter = GaussFilter.gauss3();
                break;
            case GAUSS_4:
                filter = GaussFilter.gauss4();
                break;
            case GAUSS_5:
                filter = GaussFilter.gauss5();
                break;
            case SPLOT:
                break;
        }
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);
        WritableImage image = filter.filter(canvas.snapshot(params, null));
        g.drawImage(image, 0, 0);
    }

    @FXML
    private void openHistogramDialog(ActionEvent event) {
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);
        WritableImage wi = canvas.snapshot(params, null);
        ModifyImage modifyImage = new ModifyImage(wi.getPixelReader(), (int) wi.getWidth(), (int) wi.getHeight());
        Map<String, Object> resultMap = openDialog(new HistogramDialogController(wi), "/histogram-dialog.fxml");
        Histogram histogram = (Histogram) resultMap.get("histogram");
        switch ((Integer) resultMap.get("method")) {
            case 0:
                g.drawImage(histogram.equalizeHistogram(modifyImage), 0, 0);
                break;
            case 1:
                g.drawImage(histogram.stretchImageHistogram(modifyImage), 0, 0);
        }
    }

    @FXML
    private void openBinarizationDialog(ActionEvent event) {
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);
        WritableImage wi = canvas.snapshot(params, null);
        Map<String, Object> resultMap = openDialog(new BinarizationDialogController(), "/binarization-dialog.fxml");
        BinarizationType type = (BinarizationType) resultMap.get("mode");
        Integer val = 0;
        if (type.equals(BinarizationType.MANUAL) || type.equals(BinarizationType.PERCENT_BLACK_SELECTION))
            val = (Integer) resultMap.get("value");
        Binarization b = new MeanIterativeBinarization();
        switch (type) {
            case MANUAL:
                b = new ManualBinarization(val);
                break;
            case PERCENT_BLACK_SELECTION:
                b = new PercentBlackBinarization(val);
                break;
            case MEAN_ITERATIVE_SELECTION:
                b = new MeanIterativeBinarization();
        }
        g.drawImage(b.binarize(wi), 0, 0);
    }

    private Map<String, Object> openMorphDialog(DialogController controller) {
        return openDialog(controller, "/morph-matrix-dialog.fxml");
    }

    @FXML
    private void openDilationDialog(ActionEvent event) {
        Map<String, Object> resultMap = openMorphDialog(new MorphMatrixDialogController());
        MorphologyMatrix matrix = new MorphologyMatrix((List<List<Boolean>>) resultMap.get("matrix"));
        g.drawImage(Morphology.getInstance().dilation(getImage(), matrix), 0, 0);

    }

    @FXML
    private void openErosionDialog(ActionEvent event) {
        Map<String, Object> resultMap = openMorphDialog(new MorphMatrixDialogController());
        MorphologyMatrix matrix = new MorphologyMatrix((List<List<Boolean>>) resultMap.get("matrix"));
        g.drawImage(Morphology.getInstance().erosion(getImage(), matrix), 0, 0);

    }

    @FXML
    private void openOpeningDialog(ActionEvent event) {
        Map<String, Object> resultMap = openMorphDialog(new MorphMatrixDialogController());
        MorphologyMatrix matrix = new MorphologyMatrix((List<List<Boolean>>) resultMap.get("matrix"));
        g.drawImage(Morphology.getInstance().opening(getImage(), matrix), 0, 0);

    }

    @FXML
    private void openClosingDialog(ActionEvent event) {
        Map<String, Object> resultMap = openMorphDialog(new MorphMatrixDialogController());
        MorphologyMatrix matrix = new MorphologyMatrix((List<List<Boolean>>) resultMap.get("matrix"));
        g.drawImage(Morphology.getInstance().closing(getImage(), matrix), 0, 0);

    }

    private WritableImage getImage() {
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);
        return canvas.snapshot(params, null);
    }

}
