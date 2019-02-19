package main.java;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.java.controllers.SettingsController;

public class Main extends Application {

    public static XMLManipulator optionsXML;

    public static Stage primaryStage;

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/scenes/Menu.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Balls&Walls");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
        primaryStage = stage;
        optionsXML = new XMLManipulator("options.xml");
        optionsXML.open();
        SettingsController.ball = optionsXML.getTextElement("Ball");
        SettingsController.wall = optionsXML.getTextElement("Wall");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
