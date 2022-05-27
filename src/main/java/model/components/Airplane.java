package model.components;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;
import view.GamePage;

public class Airplane extends Rectangle {
    private static Airplane instance;
    private AnchorPane pane;

    private boolean isBlinking = false;
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
        if(isBlinking)return;
        this.health = health;
    }

    public double getHealth() {
        return health;
    }

    public Text getHealthText() {
        return healthText;
    }

    public boolean isBlinking() {
        return isBlinking;
    }

    public void setBlinking(boolean blinking) {
        if(!blinking){
            this.isBlinking = false;
            return;
        }
        if(isBlinking)return;
        isBlinking = true;
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.1), this);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        fadeTransition.setCycleCount(10);
        fadeTransition.setOnFinished(actionEvent -> {
            isBlinking = false;
        });
        fadeTransition.play();
    }
}
