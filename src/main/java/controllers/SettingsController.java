package main.java.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import main.java.Main;

import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class SettingsController implements Initializable {
    private Parent newParent;
    private Scene newScene;
    private Stage appStage;

    private String choiceBallType;
    private String choiceBallColor;
    private String choiceWallType;
    private String choiceWallColor;

    public static String ball;
    public static String wall;

    @FXML
    private Canvas settingsBallCanvas;
    private GraphicsContext settingsBallCanvasGC;
    @FXML
    private Canvas settingsWallCanvas;
    private GraphicsContext settingsWallCanvasGC;

    @FXML
    private ChoiceBox<String> ballColor;
    @FXML
    private ChoiceBox<String> ballType;
    @FXML
    private ChoiceBox<String> wallColor;
    @FXML
    private ChoiceBox<String> wallType;

    @FXML
    private Button settingsReturnButton;
    @FXML
    private Button settingsSaveButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        settingsBallCanvasGC = settingsBallCanvas.getGraphicsContext2D();
        settingsWallCanvasGC = settingsWallCanvas.getGraphicsContext2D();

        choiceBallType = ((ball.split("[.]"))[0].split("[_]"))[0];
        choiceBallColor = ((ball.split("[.]"))[0].split("[_]"))[1];
        choiceWallType = ((wall.split("[.]"))[0].split("[_]"))[0];
        choiceWallColor = ((wall.split("[.]"))[0].split("[_]"))[1];

        // Ball type
        String[] ballTypes = new String[]{"Golf", "Sphere"};
        String[] ballTypesRu = new String[]{"Гольф", "Сфера"};
        ballType.setItems(FXCollections.observableArrayList(ballTypesRu));
        ballType.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                choiceBallType = ballTypes[newValue.intValue()];
                ball = choiceBallType + "_" + choiceBallColor + ".png";
            }
        });
        ballType.setValue(ballTypesRu[Arrays.asList(ballTypes).indexOf(choiceBallType)]);

        // Ball color
        String[] ballColors = new String[]{"Black", "White", "Red", "Green", "Blue", "Purple"};
        String[] ballColorsRU = new String[]{"Черный", "Белый", "Красный", "Зелёный", "Синий", "Фиолетовый"};
        ballColor.setItems(FXCollections.observableArrayList(ballColorsRU));
        ballColor.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                choiceBallColor = ballColors[newValue.intValue()];
                ball = choiceBallType + "_" + choiceBallColor + ".png";
            }
        });
        ballColor.setValue(ballColorsRU[Arrays.asList(ballColors).indexOf(choiceBallColor)]);

        // Wall type
        String[] wallTypes = new String[]{"Bricks","Zigzag"};
        String[] wallTypesRu = new String[]{"Кирпичи","Зигзаг"};
        wallType.setItems(FXCollections.observableArrayList(wallTypesRu));
        wallType.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                choiceWallType = wallTypes[newValue.intValue()];
                wall = choiceWallType + "_" + choiceWallColor + ".png";
            }
        });
        wallType.setValue(wallTypesRu[Arrays.asList(wallTypes).indexOf(choiceWallType)]);

        //Wall color
        String[] wallColors = new String[]{"Black", "White", "Red", "Green", "Blue", "Purple"};
        String[] wallColorsRU = new String[]{"Черный", "Белый", "Красный", "Зелёный", "Синий", "Фиолетовый"};
        wallColor.setItems(FXCollections.observableArrayList(wallColorsRU));
        wallColor.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                choiceWallColor = wallColors[newValue.intValue()];
                wall = choiceWallType + "_" + choiceWallColor + ".png";
            }
        });
        wallColor.setValue(wallColorsRU[Arrays.asList(wallColors).indexOf(choiceWallColor)]);
    }

    public void repaint() {
        settingsBallCanvasGC.clearRect(0, 0, 180, 180);
        settingsWallCanvasGC.clearRect(0, 0, 180, 180);
        settingsBallCanvasGC.drawImage(new Image("images/balls/settings/Golf_Black.png"),0,0);
        //SettingsWallCanvasGC.drawImage(new Image("images/balls/Golf_Black.png"),0,0);
    }

    public void actionSettingsReturnButton(ActionEvent event) throws IOException{
        newParent = FXMLLoader.load(getClass().getResource("/scenes/Menu.fxml"));
        newScene = new Scene(newParent);
        appStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        appStage.setScene(newScene);
    }

    public void actionSettingsSaveButton(ActionEvent event) throws TransformerException, IOException{
        //ball = choiceBallType + "_" + choiceBallColor + ".png";
        //wall = choiceWallType + "_" + choiceWallColor + ".png";
        repaint();
        Main.optionsXML.editTextElement("Ball", ball, "all");
        Main.optionsXML.editTextElement("Wall", wall, "all");
    }
}
