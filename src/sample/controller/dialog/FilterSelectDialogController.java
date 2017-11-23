package sample.controller.dialog;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import sample.utils.filters.FilterType;
import sample.utils.filters.MatrixConstans;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FilterSelectDialogController extends AbstractDialogController {

    @FXML private TableView<int[]> matrix;
    @FXML private ChoiceBox<FilterType> choiceFilter;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        choiceFilter.getItems().addAll(FilterType.values());
        choiceFilter.getSelectionModel().selectFirst();
    }

    public void manipulatePicture(ActionEvent event) {
        result.clear();
        result.put("filterType", choiceFilter.getValue());
        closeStage();
    }

    public void changeSelected(ActionEvent event) {
        fillTable();
    }

    private void fillTable() {
        List<RadioButton> result = new ArrayList<>();
        int[] m = {};
        switch (choiceFilter.getValue()) {
            case AVERAGING_3x3:
                m = MatrixConstans.averagingFilter3x3;
                break;
            case AVERAGING_5x5:
                m = MatrixConstans.averagingFilter5x5;
                break;
            case AVERAGING_7x7:
                m = MatrixConstans.averagingFilter7x7;
                break;
            case MEDIAN:
                break;
            case SOBEL_HORIZONTAL:
                m = MatrixConstans.horizontalSobel;
                break;
            case SOBEL_VERTICAL:
                m = MatrixConstans.verticalSobel;
                break;
            case UNSHARP:
                break;
            case GAUSS_1:
                m = MatrixConstans.gauss1;
                break;
            case GAUSS_2:
                m = MatrixConstans.gauss2;
                break;
            case GAUSS_3:
                m = MatrixConstans.gauss3;
                break;
            case GAUSS_4:
                m = MatrixConstans.gauss4;
                break;
            case GAUSS_5:
                m = MatrixConstans.gauss5;
                break;
            case SPLOT:
                break;
        }
        fillTable(m);
    }

    private void fillTable(int[] m) {
        if (m.length == 0) {
            matrix.getItems().clear();
        } else {
            matrix.setItems(generateData(m));
            matrix.getColumns().setAll(createColumns(m));
        }
    }

    private ObservableList<int[]> generateData(int[] m) {
        int size = (int) Math.sqrt(m.length);

        return FXCollections.observableArrayList(
                IntStream.range(0, size).mapToObj(r ->
                        IntStream.range(0, size).map(c -> m[r * size + c])
                                .toArray()
                ).collect(Collectors.toList())
        );
    }

    private List<TableColumn<int[], Integer>> createColumns(int[] m) {
        int size = (int) Math.sqrt(m.length);
        return IntStream.range(0, size)
                .mapToObj(this::createColumn)
                .collect(Collectors.toList());
    }

    private TableColumn<int[], Integer> createColumn(int c) {
        TableColumn<int[], Integer> col = new TableColumn<>("C" + (c + 1));
        col.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()[c]));
        return col;
    }
}
