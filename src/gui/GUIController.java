package gui;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.concurrent.*;
import java.*;
import java.io.IOException;
import java.text.ParseException;
/**
 * Manage events that occur on the GUI
 */
public class GUIController {

    private GUIView view;
    private GUIModel model;
	private Service<Void> aggiornaTab= new Service<Void>() {
    	protected Task<Void> createTask(){
    		return new Task<Void>() {
    			protected Void call() throws Exception {
    				while(!isCancelled())
    				{
    					refreshTableViews();
    				}
    				return null;
    			}
    		};
    	}
    };
    

    public GUIController() throws IOException, ClassNotFoundException, ParseException{
        this.view = new GUIView();
        this.model = new GUIModel();
    }

    /**
     * Costruisce una scena utilizzando la BorderPane dal GUIView
     *
     * @param stage primary stage
     */
    public void buildView(Stage stage) throws IOException{
        Scene scene = new Scene(view.buildMain(), 1280, 720);
        scene.getStylesheets();
        stage.setTitle("Casa Domotica GUI");
        stage.setScene(scene);

        this.populateTableViews();
        this.setupHandler();
        this.aggiornaTab.reset();
        this.aggiornaTab.start();

        stage.show();
    }

    /**
     * Gestore degli eventi
     */
    private void setupHandler() throws IOException {
    	Alert errorAlert = new Alert(Alert.AlertType.ERROR);
    	// imposta lo stato del sistema a "Attivato"
    	view.btnStatA.setOnAction(e -> {
            view.casaStatus.setText("ATTIVATO");
            view.casaStatus.setFill(Color.GREEN);
            model.setStato("Attivato");
            this.refreshTableViews();
    	});
    	//imposta lo stato del sistema a "Collaudo"
    	view.btnStatC.setOnAction(e -> {
    		view.casaStatus.setText("COLLAUDO");
    		view.casaStatus.setFill(Color.RED);
    		model.setStato("Collaudo");
    		this.refreshTableViews();
    	});
    	// visualizza le statistiche dei sensori
    	view.btnViewStats.setOnAction(e -> {
    		if(model.getStato()=="Collaudo")
    		{
        		Stage s2 = new Stage();
        		Scene scene = new Scene(view.buildAlarm(), 600, 600);
                scene.getStylesheets();
                s2.setTitle("Statistiche sensori");
                s2.setScene(scene);
                view.alarmTable.setItems(TableManager.buildData(model.getAlarm()));
                s2.show();
    		}
    		else
    		{
    			errorAlert.setHeaderText("Il sistema è attivo");
                errorAlert.setContentText("Il sistema deve essere nello stato 'Collaudo' per inserire vedere le statistiche");
                errorAlert.showAndWait();
    		}
    	});
    	// visualizza le componenti dei sensori
    	view.btnViewComp.setOnAction(e -> {
    		Stage s2 = new Stage();
    		Scene scene = new Scene(view.buildComps(), 600, 600);
            scene.getStylesheets();
            s2.setTitle("Componenti Sensori");
            s2.setScene(scene);
            view.compTable.setItems(TableManager.buildData(model.getSensorDataComp()));
            s2.show();
    	});
    	// aggiungi un sensore al sistema
    	view.btnAddSens.setOnAction(e -> {
    		if(model.getStato()=="Collaudo")
    		{
    			if(view.tfSensMon.getText().isEmpty()==false && view.tfSensInt.getText().isEmpty()==false)
    			{
    				try {
    					model.addSens(view.tfSensMon.getText(),view.tfSensInt.getText());
    				} catch (IOException e1) {
    					e1.printStackTrace();
    				}
            		view.tfSensMon.clear();
            		view.tfSensInt.clear();
            		this.refreshTableViews();
    			}
    			else
    			{
    				errorAlert.setHeaderText("Uno o più campi è vuoto");
                    errorAlert.setContentText("Bisogna inserire i nomi dei sensori per poterli aggiungere");
                    errorAlert.showAndWait();
    			}
    		}
    		else
    		{
    			errorAlert.setHeaderText("Il sistema è attivo");
                errorAlert.setContentText("Il sistema deve essere nello stato 'Collaudo' per inserire un sensore");
                errorAlert.showAndWait();
    		}
    		
    	});
    	// aggiungi un componente ad un sensore
    	view.btnAddComp.setOnAction(e -> {
    		if(model.getStato()=="Collaudo")
    		{
    			if(model.addComp(view.tfComp.getText(),view.tfSensName.getText())==false)
				{
    				errorAlert.setHeaderText("Componente non aggiunta");
                    errorAlert.setContentText("Non esiste un sensore di nome "+view.tfSensName.getText());
                    errorAlert.showAndWait();
				}
        		view.tfComp.clear();
        		view.tfSensName.clear();
    		}
    		else
    		{
    			errorAlert.setHeaderText("Il sistema è attivo");
                errorAlert.setContentText("Il sistema deve essere nello stato 'Collaudo' per inserire una componente");
                errorAlert.showAndWait();
    		}
    	});
    	view.btnReset.setOnAction(e -> {
    		if(model.getStato()=="Collaudo")
    		{
        		model.resetSensori();
        		this.refreshTableViews();
    		}
    		else
    		{
    			errorAlert.setHeaderText("Il sistema è attivo");
                errorAlert.setContentText("Il sistema deve essere nello stato 'Collaudo' per resettare i sensori");
                errorAlert.showAndWait();
    		}
    	});
    }

    /**
     * Popola le table view 
     */
    private void populateTableViews() {

        String[][] Data =  {{"","",""}};
        String[][] Data2 = {{"",""}};

        view.sensMonTable = TableManager.createTableView(Data, view.monitoringColumns);
        view.sensMonTable.getItems().clear();
        view.sensIntTable = TableManager.createTableView(Data2, view.interventionColumns);
        view.sensIntTable.getItems().clear();
        view.alarmTable = TableManager.createTableView(Data2, view.allarmiColumns);
        view.alarmTable.getItems().clear();

        view.tableViews.getItems().addAll(view.sensMonTable, view.sensIntTable);
        view.sensMonTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        view.sensIntTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    /**
     * Aggiorna i dati delle table view
     */
    private void refreshTableViews() {
        view.sensIntTable.setItems(TableManager.buildData(model.getSensorDataI()));
        view.sensMonTable.setItems(TableManager.buildData(model.getSensorData()));
    }
    
}