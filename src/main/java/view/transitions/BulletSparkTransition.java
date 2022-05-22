package view.transitions;

import javafx.animation.Transition;
import javafx.util.Duration;
import view.GamePage;
import view.components.Bullet;

public class BulletSparkTransition extends Transition {
    Bullet bullet;
    public BulletSparkTransition(Bullet bullet) {
        this.bullet = bullet;
        setCycleCount(1);
        setCycleDuration(Duration.millis(100));
    }
    @Override
    protected void interpolate(double v) {
        int frameNumber = (int) Math.floor(v * 2) + 1;
        bullet.changeFillForBullet(frameNumber);
    }


}
