import gui.GUIController;
import java.io.IOException;
import java.text.ParseException;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    GUIController controller;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException, ClassNotFoundException, ParseException{
        controller = new GUIController();
        controller.buildView(primaryStage);
    }

}
