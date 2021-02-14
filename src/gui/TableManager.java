package gui;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/*
 * Thanks to the user John Damien Smith from Coderanch
 * Credits: https://coderanch.com/t/663384/java/Populating-TableView-method
 * */

/**
 * Class helper for table views
 */
public class TableManager {


    /**
     * Creates a TableView.
     *
     * @param dataArray Data
     * @param colName   Columns Name
     * @return TableView
     */

    public static TableView<ObservableList<String>> createTableView(String[][] dataArray, String[] colName) {

        TableView<ObservableList<String>> tableView = new TableView<>();
        tableView.setItems(buildData(dataArray));

        for (int i = 0; i < dataArray[0].length; i++) {
            final int curCol = i;
            final TableColumn<ObservableList<String>, String> column = new TableColumn<>(colName[i]);
            column.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().get(curCol)));
            tableView.getColumns().add(column);
        }

        return tableView;
    }

    /**
     * Creates a List required for the TableViews
     *
     * @param dataArray data
     * @return List of data
     */
    public static ObservableList<ObservableList<String>> buildData(String[][] dataArray) {
        ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();

        for (String[] row : dataArray)
            data.add(FXCollections.observableArrayList(row));

        return data;
    }


}