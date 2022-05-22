package view.components;

import javafx.event.ActionEvent;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import view.GamePage;
import view.transitions.BulletSparkTransition;
import view.transitions.ShootTransition;

import java.util.ArrayList;

public class Bullet extends Rectangle {

    public AnchorPane pane;

    private ShootTransition shootTransition;

    private static ArrayList<Bullet> bullets = new ArrayList<>();

    private boolean isDestroyed;

    private Bullet(AnchorPane pane , double x, double y, Image image) {
        super(image.getWidth(), image.getHeight(), new ImagePattern(image));
        isDestroyed = false;
        this.pane = pane;
        pane.getChildren().add(this);
        setX(x);
        setY(y);
        bullets.add(this);
        shootTransition = new ShootTransition(this);
        shootTransition.play();
    }
    public static void createBullet(AnchorPane pane , double x, double y){
        new Bullet(pane, x, y, new Image(Bullet.class.getResource("/frames/images/bullet.png").toExternalForm()));
    }

    public void move(double amount){
        checkCollision();
        if(getX() + getWidth() < pane.getWidth() + amount)setX(getX() + amount);
        else {
            bullets.remove(this);
            shootTransition.stop();
            pane.getChildren().remove(this);
        }
    }

    public static ArrayList<Bullet> getBullets() {
        return bullets;
    }

    public ShootTransition getShootTransition() {
        return shootTransition;
    }

    public void checkCollision(){
        BigBoss bigBoss = BigBoss.getInstance();
        Bounds bounds = new BoundingBox(bigBoss.getX() + 80 ,bigBoss.getY() + 100, bigBoss.getWidth(), bigBoss.getHeight() - 150);
        if(this.getBoundsInParent().intersects(bounds) && !isDestroyed) {
            isDestroyed = true;
            bigBoss.setHealth(bigBoss.getHealth() - (0.5 * (4 - GamePage.getDifficultyLevel())));
            bullets.remove(this);
            shootTransition.stop();
            BulletSparkTransition bulletSparkTransition = new BulletSparkTransition(this);
            bulletSparkTransition.play();
            bulletSparkTransition.setOnFinished(ActionEvent -> {
                pane.getChildren().remove(this);
            });
        }
    }
    public void changeFillForBullet(int frameNumber){
        setFill(new ImagePattern(new Image(getClass().getResource("/frames/BulletSpark/" + frameNumber + ".png").toExternalForm())));
    }
}
