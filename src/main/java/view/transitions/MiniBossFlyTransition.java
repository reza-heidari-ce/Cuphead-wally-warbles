package view.transitions;

import javafx.animation.Transition;
import javafx.util.Duration;
import view.GamePage;
import model.components.MiniBoss;

public class MiniBossFlyTransition extends Transition {

    private MiniBoss miniBoss;

    public MiniBossFlyTransition(MiniBoss miniBoss) {
        this.miniBoss = miniBoss;
        setCycleCount(-1);
        setCycleDuration(Duration.millis(400));
    }

    @Override
    protected void interpolate(double v) {
        miniBoss.move(-5);
        int frameNumber = (int) Math.floor(v * 3) + 1;
        miniBoss.changeFill(frameNumber);
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
