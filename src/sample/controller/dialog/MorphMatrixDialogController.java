package sample.controller.dialog;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.*;

public class MorphMatrixDialogController extends AbstractDialogController {

    @FXML GridPane grid;

    List<List<CheckBox>> matrix = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for (int col = 0; col < 5; col++) {
            matrix.add(new ArrayList<>());
            grid.addColumn(col);
            for (int row = 0; row < 5; row++) {
                CheckBox checkBox = new CheckBox();
                checkBox.setSelected(true);
                matrix.get(col).add(checkBox);
                grid.addRow(row);
            }
        }

        for (int col = 0; col < matrix.size(); col++) {
            for (int row = 0; row < matrix.get(col).size(); row++) {
                grid.add(matrix.get(col).get(row), col, row);
            }
        }
    }


    public void manipulatePicture(ActionEvent event) {
        List<List<Boolean>> resultMatrix = new ArrayList<>();
        matrix.get(0).forEach(e -> resultMatrix.add(new ArrayList<>()));

        for (int row = 0; row < resultMatrix.size(); row++) {
            for (int col = 0; col < matrix.size(); col++) {
                resultMatrix.get(row).add(matrix.get(col).get(row).isSelected());
            }
        }

        result.put("matrix", resultMatrix);

        closeStage();
    }

    public void addRow(ActionEvent event) {
        grid.addRow(matrix.size());
        for (int col = 0; col < matrix.size(); col++) {
            CheckBox checkBox = new CheckBox();
            checkBox.setSelected(true);
            matrix.get(col).add(checkBox);
            int row = matrix.get(col).size() - 1;
            grid.add(matrix.get(col).get(row), col, row);
        }
    }

    public void delRow(ActionEvent event) {
        int row = matrix.get(0).size() - 1;
        for (int col = 0; col < matrix.size(); col++) {
            matrix.get(col).remove(row);
        }
        deleteRow(grid, row);
    }

    public void addCol(ActionEvent event) {
        matrix.add(new ArrayList<>());
        int col = matrix.size() - 1;
        grid.addColumn(matrix.size());
        for (int row = 0; row < matrix.get(0).size(); row++) {
            CheckBox checkBox = new CheckBox();
            checkBox.setSelected(true);
            matrix.get(col).add(checkBox);
            grid.add(matrix.get(col).get(row), col, row);
        }
    }

    public void delCol(ActionEvent event) {
        int col = matrix.size() - 1;
        for (int row = 0; row < matrix.get(col).size(); row++) {
            matrix.get(col).remove(0);
        }
        matrix.remove(col);
        deleteCol(grid, col);

    }

    static void deleteRow(GridPane grid, final int row) {
        Set<Node> deleteNodes = new HashSet<>();
        for (Node child : grid.getChildren()) {
            // get index from child
            Integer rowIndex = GridPane.getRowIndex(child);

            // handle null values for index=0
            int r = rowIndex == null ? 0 : rowIndex;

            if (r > row) {
                // decrement rows for rows after the deleted row
                GridPane.setRowIndex(child, r - 1);
            } else if (r == row) {
                // collect matching rows for deletion
                deleteNodes.add(child);
            }
        }

        // remove nodes from row
        grid.getChildren().removeAll(deleteNodes);
    }

    static void deleteCol(GridPane grid, final int col) {
        Set<Node> deleteNodes = new HashSet<>();
        for (Node child : grid.getChildren()) {
            // get index from child
            Integer colIndex = GridPane.getColumnIndex(child);

            // handle null values for index=0
            int r = colIndex == null ? 0 : colIndex;

            if (r > col) {
                // decrement rows for rows after the deleted row
                GridPane.setColumnIndex(child, r - 1);
            } else if (r == col) {
                // collect matching rows for deletion
                deleteNodes.add(child);
            }
        }

        // remove nodes from row
        grid.getChildren().removeAll(deleteNodes);
    }

    //
//
//
//
//
//
//
//
//
//
//
//
//
//    @FXML private TableView<ObservableList<SimpleBooleanProperty>> tableView;
//
//    private ObservableList<ObservableList<SimpleBooleanProperty>> matrix = FXCollections.observableArrayList();
//
//    @Override
//    public void initialize(URL location, ResourceBundle resources) {
//        for (int i = 0; i < 2; i++) {
//            matrix.add(FXCollections.observableArrayList());
//            for (int j = 0; j < 1; j++) {
//                matrix.get(i).add(new SimpleBooleanProperty(true));
//            }
//        }
//        for (int i = 0; i < 5; i++) {
//            TableColumn<ObservableList<SimpleBooleanProperty>, SimpleBooleanProperty> tc = createColumn(i);
//            tableView.getColumns().add(tc);
//        }
//        tableView.setItems(matrix);
//    }
//
//    public void addRow(ActionEvent event) {
//matrix.forEach(
//        m -> m.add(new SimpleBooleanProperty(true))
//);
//
//        for (int i = 0; i < matrix.size(); i++) {
//            tableView.getColumns().add(createColumn(i));
//        }
//        tableView.setItems(matrix);
//        tableView.refresh();
//    }
//
//    public void addCol(ActionEvent event) {
//        ObservableList<SimpleBooleanProperty> col = FXCollections.observableArrayList();
//        for (int j = 0; j < matrix.get(0).size(); j++) {
//            col.add(new SimpleBooleanProperty(true));
//        }
//        matrix.add(col);
//        tableView.getItems().add(col);
//        tableView.getColumns().add(createColumn());
//        tableView.refresh();
//
//    }
//
//    private TableColumn<ObservableList<SimpleBooleanProperty>, SimpleBooleanProperty> createColumn(){
//        return createColumn(matrix.size()-1);
//    }
//
//    private TableColumn<ObservableList<SimpleBooleanProperty>, SimpleBooleanProperty> createColumn(int colNo){
//        TableColumn<ObservableList<SimpleBooleanProperty>, SimpleBooleanProperty> tc = new TableColumn<>(""+(colNo+1));
//
//        tc.setCellValueFactory(p -> {
//                    System.out.println(colNo);
//            return new ReadOnlyObjectWrapper(p.getValue());}
//            );
//
//        tc.setCellFactory(c -> {
//            TableCell<ObservableList<SimpleBooleanProperty>, SimpleBooleanProperty> cell = new TableCell<>() {
//                @Override
//                protected void updateItem(SimpleBooleanProperty item, boolean empty) {
//                    super.updateItem(item, empty);
//                    if (!empty)
//                        setText(String.valueOf(item.getValue()));
//                }
//            };
//            cell.setOnMouseClicked(e -> {
//                if (cell.getItem() != null) {
//                    cell.getItem().set(!cell.getItem().getValue());
//                    cell.getTableView().refresh();
//                    matrix.forEach(m -> {
//                        m.forEach(mi -> System.out.print(mi + " "));
//                        System.out.println();
//                    });
//                }
//            });
//            return cell;
//        });
//        tc.setPrefWidth(50);
//        return tc;
//    }
}
