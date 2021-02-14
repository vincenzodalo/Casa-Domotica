package gui;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * Gestione del layout della GUI
 */

public class GUIView {
    // Table Views column name
    public final String[] monitoringColumns = {"Sensore monitoraggio", "Stato", "Allarme"};
    public final String[] interventionColumns = {"Sensore intervento", "Stato"};
    public final String[] allarmiColumns = {"Sensore allarme", "Data"};
    public final String[] compColumns = {"Sensore", "Componenti"};
    //Tables
    public TableView sensMonTable;
    public TableView sensIntTable = new TableView();
    public TableView alarmTable;
    public TableView compTable;
    public SplitPane tableViews = new SplitPane();
    // Buttons
    public Button btnAddSens = new Button("Add Sensor");
    public Button btnAddComp = new Button("Add Component");
    public Button btnViewComp = new Button("View Components");
    public Button btnViewStats = new Button("View Stats");
    public Button btnStatC = new Button("Collaudo");
    public Button btnStatA = new Button("Attivato");
    public Button btnReset = new Button("Reset");
    //Text fields
    public TextField tfSensMon = new TextField();
    public TextField tfSensInt = new TextField();
    public TextField tfComp = new TextField();
    public TextField tfSensName = new TextField();
    //Status
    public Text casaStatus = new Text();

    /**
     * Costruisce la schermata iniziale
     *
     * @return Layout
     */
    public BorderPane buildMain() {
        // Build the top panel
        HBox topPanel = this.buildTopPanel();
        topPanel.getStyleClass().add("topPanel");
        // Build main panel
        BorderPane borderPane = new BorderPane();
        // Set border pane panels
        borderPane.setTop(topPanel);
        borderPane.setCenter(tableViews);
        return borderPane;
    }
    /**
     * Costruisce la schermata per la visualizzazione degli allarmi
     *
     * @return Layout
     */
    public BorderPane buildAlarm() {
        BorderPane borderPane = new BorderPane();
        String[][] data =  {{"",""}};

        alarmTable = TableManager.createTableView(data, allarmiColumns);
        alarmTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        alarmTable.getItems().clear();
        borderPane.setCenter(alarmTable);
        return borderPane;
    }
    /**
     * Costruisce la schermata per la visualizzazione delle componenti
     *
     * @return Layout
     */
    public BorderPane buildComps() {
        BorderPane borderPane = new BorderPane();
        String[][] data =  {{"",""}};

        compTable = TableManager.createTableView(data, compColumns);
        compTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        compTable.getItems().clear();
        borderPane.setCenter(compTable);
        return borderPane;
    }

    /**
     * Build the TopPanel
     *
     * @return topPanel
     */
    private HBox buildTopPanel() {

        HBox topPanel = new HBox();

        VBox directBox = this.buildDirectBOX();
        directBox.setTranslateX(30);
        VBox statusBox = this.buildStatusBox();
        statusBox.setTranslateX(200);

        topPanel.getChildren().addAll(directBox, statusBox);
        

        return topPanel;

    }

    /**
     * Build the direct box
     *
     * @return directBox
     */
    private VBox buildDirectBOX() {
        VBox directBox = new VBox();
        HBox.setHgrow(directBox, Priority.ALWAYS);
        tfSensMon.setPromptText("Sensor moni name");
        tfSensInt.setPromptText("Sensor inter name");
        tfComp.setPromptText("Component name");
        tfSensName.setPromptText("Wich Sensor");

        HBox innerBcBox = new HBox();
        innerBcBox.getChildren().addAll(tfSensMon, tfSensInt, btnAddSens);
        innerBcBox.setSpacing(10);
        
        HBox secondBcBox = new HBox();
        secondBcBox.getChildren().addAll(tfComp, tfSensName, btnAddComp, btnViewComp);
        secondBcBox.setSpacing(10);

        directBox.getChildren().addAll(new Text("Inserire un sensore di montioraggio e di intervento associato"), innerBcBox, new Text("Inserire un componente"), secondBcBox);
        directBox.getStyleClass().add("padding-10");

        return directBox;
    }

    /**
     * Build status box
     *
     * @return statusBox
     */
    private VBox buildStatusBox() {
        VBox statusBox = new VBox();
        HBox.setHgrow(statusBox, Priority.ALWAYS);

        HBox validationBox = new HBox();
        validationBox.setSpacing(10);
        casaStatus.getStyleClass().add("status");
        casaStatus.setText("COLLAUDO");
        casaStatus.setFill(Color.RED);
        validationBox.getChildren().addAll(new Text("System status : "), casaStatus);

        HBox buttonBox = new HBox();
        buttonBox.setSpacing(10);
        buttonBox.getChildren().addAll(btnStatC, btnStatA, btnReset, btnViewStats);

        statusBox.getChildren().addAll(validationBox, buttonBox);
        statusBox.getStyleClass().add("padding-10");

        return statusBox;
    }


}