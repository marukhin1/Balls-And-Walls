package main.java.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.java.Main;
import main.java.XMLManipulator;
import main.java.game.Ball;
import main.java.game.Wall;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class DoubleGameController implements Initializable {

    private Parent newParent;
    private Scene newScene;
    private Stage appStage;

    private static Set<String> pressedKeys = new HashSet<>();
    private static Timer gameTimer;
    private static Timer repaintTimer;

    private static Boolean start = false;

    private static Ball ball;
    private static Wall leftWall;
    private static Wall rightWall;

    private static final int ballSpeed = 9;
    private static final int wallSpeed = 12;
    private static int score1 = 0, score2 = 0;

    @FXML
    private Canvas gameCanvas;

    private static GraphicsContext graphicsContext;

    @FXML
    private Button DoubleGameReturnButton;

    @FXML
    private Button DoubleGameInfoButton;

    @FXML
    private Label scoreLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        score1 = 0;
        score2 = 0;
        startGame();
    }

    public void startGame(){
        System.out.println(SettingsController.ball);
        System.out.println(SettingsController.wall);
        graphicsContext = gameCanvas.getGraphicsContext2D();
        ball = new Ball(graphicsContext);
        leftWall = new Wall("Left", graphicsContext);
        rightWall = new Wall("Right", graphicsContext);
        pressedKeys.clear();
        if (gameTimer != null){
            gameTimer.cancel();
            gameTimer.purge();
        }
        if (repaintTimer != null){
            repaintTimer.cancel();
            repaintTimer.purge();
        }
        leftWall.start();
        rightWall.start();
        ball.start();

        repaint();
        updateScore();

        gameTimer = new Timer("keyTimer",true);
        repaintTimer = new Timer("repaintTimer",true);
        repaintTimer.schedule(new repaintTask(),0,15);
        gameTimer.schedule(new GameTask(),0,25);
    }

    public void actionDoubleGameReturnButton(ActionEvent event) throws IOException {
        newParent = FXMLLoader.load(getClass().getResource("/scenes/Menu.fxml"));
        newScene = new Scene(newParent);
        appStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        gameTimer.cancel();
        gameTimer.purge();
        repaintTimer.cancel();
        repaintTimer.purge();
        pressedKeys.clear();
        start = false;
        appStage.setScene(newScene);
    }

    public void actionDoubleGameInfoButton(ActionEvent event) throws IOException{
        Stage newWindow = new Stage();
        Parent newParent2 = FXMLLoader.load(getClass().getResource("/scenes/Info.fxml"));
        Scene newScene2 = new Scene(newParent2);
        newWindow.setTitle("Info");
        newWindow.setScene(newScene2);
        newWindow.setX(Main.primaryStage.getX() + 50);
        newWindow.setY(Main.primaryStage.getY() + 50);
        newWindow.initModality(Modality.APPLICATION_MODAL);
        newWindow.setResizable(false);
        newWindow.show();
    }

    public void updateScore(){
        Platform.runLater(new Runnable() {
            @Override public void run() {
                scoreLabel.setText(score1 + " : " + score2);
            }
        });
    }

    public class repaintTask extends TimerTask{
        @Override
        public void run() {
            if (start){
                repaint();
            }
        }
    }

    public void repaint(){
        graphicsContext.clearRect(0, 0, 570, 330);
        graphicsContext.setFill(Color.BLACK);
        graphicsContext.fillRoundRect(0,0,570,3,0,0);
        graphicsContext.fillRoundRect(0,327,570,3,0,0);
        graphicsContext.setFill(Color.RED);
        graphicsContext.fillRoundRect(0,0,3,330,0,0);
        graphicsContext.fillRoundRect(567,0,3,330,0,0);

        ball.paint();
        leftWall.paint();
        rightWall.paint();
    }

    public static class KeyPressedHandler implements EventHandler<KeyEvent> {
        @Override
        public void handle(KeyEvent event) {
            String keyCode = event.getCode().getName();
            if (keyCode.equals("Shift")){
                start = true;
            }
            pressedKeys.add(keyCode);
        }
    }

    public static class KeyReleasedHandler implements EventHandler<KeyEvent>{
        @Override
        public void handle(KeyEvent event) {
            String keyCode = event.getCode().getName();
            pressedKeys.remove(keyCode);
        }
    }

    public class GameTask extends TimerTask{
        @Override
        public void run() {
            if(start){
                if (pressedKeys.contains("W")){
                    leftWall.changeY(wallSpeed);
                }
                if (pressedKeys.contains("S")){
                    leftWall.changeY(-wallSpeed);
                }
                if (pressedKeys.contains("I")){
                    rightWall.changeY(wallSpeed);
                }
                if (pressedKeys.contains("K")){
                    rightWall.changeY(-wallSpeed);
                }

                if (ball.getDirection().equals("LeftUp")){
                    ball.changeX(ballSpeed);
                    ball.changeY(ballSpeed);
                    if (ball.getY() <= 5){
                        ball.setDirection("LeftDown");
                    }
                    if (ball.getX() <= 5){
                        score2++;
                        start = false;
                        startGame();
                    }
                    if ((((ball.getX()) >= (leftWall.getX() + 17) && ((ball.getX()) <= leftWall.getX() + 23))) && (((ball.getY() + 20) >= leftWall.getY() - 20) && (((ball.getY() + 20)) <= (leftWall.getY() + 98)))){
                        ball.setDirection("RightUp");
                    }
                }
                else if (ball.getDirection().equals("LeftDown")){
                    ball.changeX(ballSpeed);
                    ball.changeY(-ballSpeed);
                    if (ball.getY() >= 285){
                        ball.setDirection("LeftUp");
                    }
                    if (ball.getX() <= 5){
                        score2++;
                        start = false;
                        startGame();
                    }
                    if ((((ball.getX()) >= (leftWall.getX() + 17) && ((ball.getX()) <= leftWall.getX() + 23))) && (((ball.getY() + 20) >= leftWall.getY() - 20) && (((ball.getY() + 20)) <= (leftWall.getY() + 98)))){
                        ball.setDirection("RightDown");
                    }
                }
                else if (ball.getDirection().equals("RightUp")){
                    ball.changeX(-ballSpeed);
                    ball.changeY(ballSpeed);
                    if (ball.getY() <= 5){
                        ball.setDirection("RightDown");
                    }
                    if (ball.getX() >= 510){
                        score1++;
                        start = false;
                        startGame();
                    }
                    if ((((ball.getX() + 40) >= (rightWall.getX() -3) && ((ball.getX() + 40) <= rightWall.getX() + 3))) && (((ball.getY() + 20) >= rightWall.getY() - 20) && (((ball.getY() + 20)) <= (rightWall.getY() + 98)))){
                        ball.setDirection("LeftUp");
                    }
                }
                else if (ball.getDirection().equals("RightDown")){
                    ball.changeX(-ballSpeed);
                    ball.changeY(-ballSpeed);
                    if (ball.getY() >= 285){
                        ball.setDirection("RightUp");
                    }
                    if (ball.getX() >=510){
                        score1++;
                        start = false;
                        startGame();
                    }
                    if ((((ball.getX() + 40) >= (rightWall.getX() -3) && ((ball.getX() + 40) <= rightWall.getX() + 3))) && (((ball.getY() + 20) >= rightWall.getY() - 20) && (((ball.getY() + 20)) <= (rightWall.getY() + 98)))){
                        ball.setDirection("LeftDown");
                    }
                }

                if (score1 == 7){
                    start = false;
                    graphicsContext.setFill(Color.BLACK);
                    graphicsContext.fillText("Win first (Left Wall) player!",200,50);
                }
                else if (score2 == 7){
                    start = false;
                    graphicsContext.setFill(Color.BLACK);
                    graphicsContext.fillText("Win second (Right Wall) player!",200,50);
                }
            }
        }
    }
}
