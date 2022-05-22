package view.transitions;

import javafx.animation.Transition;
import javafx.util.Duration;
import view.GamePage;
import view.components.BigBoss;

public class BigBossFlyTransition extends Transition {
    private int direction;
    public BigBossFlyTransition() {
        direction = 1;
        setCycleCount(-1);
        setCycleDuration(Duration.millis(600));
    }

    @Override
    protected void interpolate(double v) {
        int frameNumber = (int) Math.floor(v * 5) + 1;
        BigBoss.getInstance().changeFillForFly(frameNumber);
        direction = BigBoss.getInstance().move(3 , direction);
        //System.out.println(this);
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
