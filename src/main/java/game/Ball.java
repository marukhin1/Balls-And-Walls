package main.java.game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import main.java.controllers.SettingsController;

public class Ball {
    //private Image image = new Image("images/balls/" + SettingsController.ball);
    private Image image = new Image("images/balls/game/Golf_Black.png");
    private int x = 258, y = 138;
    private int start_x = x, start_y = y;
    private String direction;

    private static GraphicsContext graphicsContext;

    public Ball(GraphicsContext gC){
        graphicsContext = gC;
    }

    public void paint() {
        graphicsContext.setFill(Color.RED);
        graphicsContext.fillOval(x,y,40,40);
        graphicsContext.drawImage(image, x, y);
    }

    public void changeX(int value){
        x -= value;
    }

    public void changeY(int value){
        y -= value;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public void start() {
        x = start_x;
        y = start_y;

        int dir = (int) (Math.random() * 4);
        switch (dir){
            case 0: direction = "LeftUp";
                    break;
            case 1: direction = "LeftDown";
                    break;
            case 2: direction = "RightUp";
                    break;
            case 3: direction = "RightDown";
                    break;
        }
    }
}
