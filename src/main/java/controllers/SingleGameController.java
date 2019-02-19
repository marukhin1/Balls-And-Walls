package main.java.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SingleGameController implements Initializable {
    Parent newParent;
    Scene newScene;
    Stage appStage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private Button SingleGameReturnButton;

    public void actionSingleGameReturnButton(ActionEvent event) throws IOException {
        newParent = FXMLLoader.load(getClass().getResource("/scenes/Menu.fxml"));
        newScene = new Scene(newParent);
        appStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        appStage.setScene(newScene);
    }
}
