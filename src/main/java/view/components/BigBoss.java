package view.components;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.Transition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;
import view.GamePage;
import view.transitions.BigBossFlyTransition;
import view.transitions.BossShootTransition;

import java.util.Random;

public class BigBoss extends Rectangle {
    private static BigBoss instance;
    private AnchorPane pane;

    private double health;
    private Text healthText;
    private BigBoss(AnchorPane pane, Image image) {
        super(image.getWidth(), image.getHeight() , new ImagePattern(image));
        this.pane = pane;
        setX(700);
        setY(150);
        health = 100;
        pane.getChildren().add(this);

        healthText = new Text("Boss health: " + health);
        healthText.setX(950);
        healthText.setY(50);
        healthText.getStyleClass().add("gameText");
        pane.getChildren().add(healthText);
       // System.out.println(pane.getWidth());
        BigBossFlyTransition flyTransition = new BigBossFlyTransition();
        flyTransition.play();
        BossShootTransition bossShootTransition = new BossShootTransition(pane);
        bossShootTransition.setOnFinished(ActionEvent -> {
            if(!GamePage.isPaused())flyTransition.play();
        });
        Timeline bossShootTimeline = new Timeline(new KeyFrame(Duration.seconds(5), actionEvent -> {
            if(!GamePage.isPaused()) {
                flyTransition.stop();
                bossShootTransition.play();
            }
        }));
        bossShootTimeline.setCycleCount(-1);
        bossShootTimeline.play();
        GamePage.getAllAnimations().add(bossShootTimeline);


        Timeline miniBossTimeline = new Timeline(new KeyFrame(Duration.seconds(3), actionEvent -> {
            String color = "purple";
            Random random = new Random();
            if(random.nextInt(2) == 0)color = "yellow";
            MiniBoss miniBoss = MiniBoss.createMiniBoss(pane, color);
        }));
        miniBossTimeline.setCycleCount(-1);
        miniBossTimeline.play();

        GamePage.getAllAnimations().add(miniBossTimeline);


    }

    public static BigBoss getNewInstance(AnchorPane pane){
        instance = new BigBoss(pane, new Image(BigBoss.class.getResource("/frames/BossFly/1.png").toExternalForm()));
        return instance;
    }
    public static BigBoss getInstance(){
        return instance;
    }
    public void changeFillForFly(int frameNumber){
        //this.setFill(Color.rgb(0,0,0));
    //    setStroke(Color.rgb(0,0,0));
        setFill(new ImagePattern(new Image(getClass().getResource("/frames/BossFly/" + frameNumber + ".png").toExternalForm())));
    }
    public void changeFillForShoot(int frameNumber){
        setFill(new ImagePattern(new Image(getClass().getResource("/frames/BossShoot/" + frameNumber + ".png").toExternalForm())));
    }
    public int move(double amount, int direction){
        checkCollision();
        if(direction == 1){
            if(getY()  + 120 < 0) return 0;
            setY(getY() - amount);
            return 1;
        }
        else if(direction == 0){
            if(getY() + getHeight()  > pane.getHeight() + 80) return 1;
            setY(getY() + amount);
            return 0;
        }
        return 1;
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public void checkCollision(){
        Bounds bounds = new BoundingBox(getX() + 80 ,getY() + 100, getWidth(), getHeight() - 150);
        if(Airplane.getInstance().intersects(bounds)){
            Airplane.getInstance().setHealth(Airplane.getInstance().getHealth() - (0.5 * GamePage.getDifficultyLevel()));
        }
    }

    public Text getHealthText() {
        return healthText;
    }
}
