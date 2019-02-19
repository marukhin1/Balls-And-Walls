package main.java.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MenuController implements Initializable {
    private Parent newParent;
    private Scene newScene;
    private Stage appStage;

    @FXML
    private Button SingleGameButton;
    @FXML
    private Button DoubleGameButton;
    @FXML
    private Button SettingsButton;
    @FXML
    private Button ExitButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void actionSingleGameButton(ActionEvent event) throws IOException{
        newParent = FXMLLoader.load(getClass().getResource("/scenes/SingleGame.fxml"));
        newScene = new Scene(newParent);
        uploadScene(event);
    }

    public void actionDoubleGameButton(ActionEvent event) throws IOException{
        newParent = FXMLLoader.load(getClass().getResource("/scenes/DoubleGame.fxml"));
        newScene = new Scene(newParent);
        EventHandler<KeyEvent> keyPressedHandler = new DoubleGameController.KeyPressedHandler();
        EventHandler<KeyEvent> keyReleasedHandler = new DoubleGameController.KeyReleasedHandler();
        newScene.setOnKeyPressed(keyPressedHandler);
        newScene.setOnKeyReleased(keyReleasedHandler);
        newScene.onKeyPressedProperty();
        uploadScene(event);
    }

    public void actionSettingsButton(ActionEvent event) throws IOException{
        newParent = FXMLLoader.load(getClass().getResource("/scenes/Settings.fxml"));
        newScene = new Scene(newParent);
        uploadScene(event);
    }

    public void actionExitButton(ActionEvent event){
        System.exit(0);
    }

    public void uploadScene(ActionEvent event){
        appStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        appStage.setScene(newScene);
    }
}
