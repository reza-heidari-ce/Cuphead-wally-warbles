package view.transitions;

import javafx.animation.Transition;
import javafx.util.Duration;
import view.GamePage;
import model.components.Egg;

public class EggTransition extends Transition {
    private Egg egg;
    public EggTransition(Egg egg) {
        this.egg = egg;
        setCycleCount(-1);
        setCycleDuration(Duration.millis(500));
    }

    @Override
    protected void interpolate(double v) {
        egg.move(-8);
        egg.setRotate(v * 360);
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
