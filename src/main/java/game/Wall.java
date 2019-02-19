package main.java.game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import main.java.controllers.SettingsController;

public class Wall {
    //private Image image = new Image("images/walls/" + SettingsController.wall);
    private Image image = new Image("images/walls/Bricks_Black.png");
    private int start_x, start_y = 126;
    private int x, y = start_y;

    private static GraphicsContext graphicsContext;

    public Wall(String position, GraphicsContext gC) {
        if (position.equals("1") || position.toLowerCase().equals("first") || position.toLowerCase().equals("left")){
            start_x = 7;
        }
        else if (position.equals("2") || position.toLowerCase().equals("second") || position.toLowerCase().equals("right")){
            start_x = 543;
        }
        else{
            start_x = 300;
        }
        x = start_x;
        graphicsContext = gC;
    }

    public void paint(){
        graphicsContext.drawImage(image, x, y);
    }

    public void changeY(int value) {
        int newValue = y - value;
        if ((newValue != -6) && (newValue != 258)){
            y -= value;
        }
    }

    public int getX() {
        return x;
    }

    public int getY(){
        return y;
    }

    public void start() {
        x = start_x;
        y = start_y;
    }
}
