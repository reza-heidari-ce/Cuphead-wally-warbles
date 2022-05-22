package view.components;

import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import view.GamePage;
import view.transitions.EggTransition;

public class Egg extends Circle {
    private AnchorPane pane;
    private EggTransition eggTransition;

    private Egg(AnchorPane pane, Image image) {
        super(image.getWidth() / 2);
        setCenterX(BigBoss.getInstance().getX());
        setCenterY(BigBoss.getInstance().getY() + 250);
        setFill(new ImagePattern(image));
        this.pane = pane;
        pane.getChildren().add(this);
        eggTransition = new EggTransition(this);
        eggTransition.play();
    }

    public void move(double amount){
        checkCollision();
        if(getCenterX() + amount + 50 > 0){
            setCenterX(getCenterX() + amount);
        }
        else {
            eggTransition.stop();
            pane.getChildren().remove(this);
        }
    }


    public static Egg createEgg(AnchorPane pane){
        return new Egg(pane, new Image(Egg.class.getResource("/frames/images/egg.png").toExternalForm()));
    }

    public void checkCollision(){
        if(Airplane.getInstance().getBoundsInParent().intersects(getBoundsInParent())){
            Airplane.getInstance().setHealth(Airplane.getInstance().getHealth() - (0.5 * GamePage.getDifficultyLevel()));
            setCenterX(-10);
            pane.getChildren().remove(this);
            eggTransition.stop();
        }
    }
}
