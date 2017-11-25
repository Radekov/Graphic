package sample.controller.dialog;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.image.Image;
import sample.utils.histogram.Histogram;

import java.net.URL;
import java.util.ResourceBundle;

public class HistogramDialogController extends AbstractDialogController {

    @FXML BarChart<String, Number> histogramChart;

    private Image image;

    public HistogramDialogController(Image image) {
        this.image = image;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        result.clear();
        Histogram histogram = Histogram.calculateHistogram(image);
        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        series1.setName("Histogram");
        for (int i = 0; i < histogram.getHistogram().length; i++) {
            series1.getData().add(new XYChart.Data<>(Integer.toString(i), histogram.getHistogram()[i]));
        }

        histogramChart.getData().addAll(series1);
        result.put("histogram", histogram);
    }

    public void stretchPicture(ActionEvent event) {
        result.put("method", 1);
        closeStage();
    }

    public void equalizationPicture(ActionEvent event) {
        result.put("method", 0);
        closeStage();
    }

    public void cancel(ActionEvent event) {
        closeStage();
    }

}
