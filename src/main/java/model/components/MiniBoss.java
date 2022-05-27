package model.components;

import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import view.GamePage;
import view.transitions.BulletSparkTransition;
import view.transitions.MiniBossFlyTransition;

import java.util.Random;


public class MiniBoss extends Rectangle {
    public AnchorPane pane;
    private String color;

    private int health;
    MiniBossFlyTransition miniBossFlyTransition;


    private MiniBoss(AnchorPane pane , Image image, String color) {
        super(image.getWidth(), image.getHeight(), new ImagePattern(image));
        this.pane = pane;
        this.color = color;
        this.health = 2;
        pane.getChildren().add(this);
        Random random = new Random();
        setX(1280);
        setY(random.nextDouble(650));
        miniBossFlyTransition = new MiniBossFlyTransition(this);
        miniBossFlyTransition.play();
    }

    public static MiniBoss createMiniBoss(AnchorPane pane, String color){
        return new MiniBoss(pane, new Image(MiniBoss.class.getResource("/frames/MiniBossFly/" + color + "/1.png").toExternalForm()), color);
    }

    public void move(double amount){
        checkCollision();
        if(getX() + amount + 100 > 0){
            setX(getX() + amount);
        }
        else {
            pane.getChildren().remove(this);
            miniBossFlyTransition.stop();
        }
    }
    public void changeFill(int frameNumber){
        setFill(new ImagePattern(new Image(getClass().getResource("/frames/MiniBossFly/" + color + "/" +  frameNumber + ".png").toExternalForm())));
    }

    public void checkCollision(){
        for(Bullet bullet : Bullet.getBullets()){
            if(bullet.getBoundsInParent().intersects(getBoundsInParent())){
                Bullet.getBullets().remove(bullet);
                bullet.getShootTransition().stop();

                BulletSparkTransition bulletSparkTransition = new BulletSparkTransition(bullet);
                bulletSparkTransition.play();
                bulletSparkTransition.setOnFinished(ActionEvent -> {
                    pane.getChildren().remove(bullet);
                });

                health -= (0.5 * (4 - GamePage.getDifficultyLevel()));
                if(health <= 0){
                    miniBossFlyTransition.stop();
                    pane.getChildren().remove(this);
                    GamePage.setScore(GamePage.getScore() + 3 * GamePage.getDifficultyLevel());
                }
                break;
            }
        }
        if(Airplane.getInstance().getBoundsInParent().intersects(getBoundsInParent())){
            Airplane.getInstance().setHealth(Airplane.getInstance().getHealth() - (0.5 * GamePage.getDifficultyLevel()));
            Airplane.getInstance().setBlinking(true);
            setX(-20);
            miniBossFlyTransition.stop();
            pane.getChildren().remove(this);
        }
    }
}
