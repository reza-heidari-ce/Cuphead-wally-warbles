package view.transitions;

import javafx.animation.Transition;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import view.GamePage;
import view.components.BigBoss;
import view.components.Egg;

import java.awt.event.ActionEvent;

public class BossShootTransition extends Transition {
    boolean hasShot;
    AnchorPane pane;
    public BossShootTransition(AnchorPane anchorPane) {
        setCycleCount(1);
        setCycleDuration(Duration.millis(1200));
        hasShot = false;
        this.pane = anchorPane;

    }

    @Override
    protected void interpolate(double v) {
        int frameNumber = (int) Math.floor(v * 11) + 1;
        if(frameNumber == 4 && !hasShot){
            hasShot = true;
            Egg.createEgg(pane);
        }
        if(v == 1.0)hasShot = false;
        BigBoss.getInstance().changeFillForShoot(frameNumber);
    }

    @Override
    public void stop(){
        GamePage.getAllAnimations().remove(this);
        super.stop();
    }
    @Override
    public void play(){
        if(!(GamePage.getAllAnimations().contains(this)))GamePage.getAllAnimations().add(this);
        super.play();
    }
}
