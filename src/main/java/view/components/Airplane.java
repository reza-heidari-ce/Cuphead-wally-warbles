package view.components;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import view.GamePage;

public class Airplane extends Rectangle {
    private static Airplane instance;
    private AnchorPane pane;

    private double health;
    private Text healthText;

    private Airplane(String color, AnchorPane pane, Image image) {
        super(image.getWidth(), image.getHeight(), new ImagePattern(image));
        if(GamePage.getDifficultyLevel() == 1) health = 10;
        if(GamePage.getDifficultyLevel() == 2) health = 5;
        if(GamePage.getDifficultyLevel() == 3) health = 2;
        this.pane = pane;
        pane.getChildren().add(this);
        healthText = new Text("Cuphead health: " + health);
        healthText.setX(50);
        healthText.setY(50);
        healthText.getStyleClass().add("gameText");
        pane.getChildren().add(healthText);
        //setX(50);
        new ImageView(new Image(getClass().getResource("/frames/images/" + color + ".png").toExternalForm()));
        setX(50);
        setY(100);
        setOnKeyPressed(keyEvent -> {
            String keyName = keyEvent.getCode().getName();
            switch (keyName) {
                case "Left":
                    moveLeft(10);
                    break;
                case "Right":
                    moveRight(10);
                    break;
                case "Up":
                    moveUp(10);
                    break;
                case "Down":
                    moveDown(10);
                    break;
                case "Space":
                    Bullet.createBullet(pane, getBoundsInParent().getMaxX(), getBoundsInLocal().getCenterY());
                    break;
            }
        });
    }

    public static Airplane getNewInstance(String color, AnchorPane pane) {
        instance = new Airplane(color, pane, new Image(Airplane.class.getResource("/frames/images/" + color + ".png").toExternalForm()));
        return instance;
    }

    private void moveLeft(double amount) {
        if (getX() > amount) setX(getX() - amount);
    }

    private void moveRight(double amount) {
        if (getX() + getBoundsInParent().getWidth() < pane.getWidth() + amount) setX(getX() + amount);
    }

    private void moveUp(double amount) {
        if (getY() > amount) setY(getY() - amount);
    }

    private void moveDown(double amount) {
        if (getY() + getBoundsInParent().getHeight() < pane.getHeight() + amount)
            setY(getY() + amount);
    }

    public static Airplane getInstance() {
        return instance;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public double getHealth() {
        return health;
    }

    public Text getHealthText() {
        return healthText;
    }
}
