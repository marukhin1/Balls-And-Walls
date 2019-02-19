package main.java.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class InfoController implements Initializable {
    private Stage stage;
    @FXML
    private Button InfoReturnButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void actionInfoReturnButton(ActionEvent event){
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.close();
    }
}
