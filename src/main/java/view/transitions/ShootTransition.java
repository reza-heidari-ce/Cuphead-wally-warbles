package view.transitions;

import javafx.animation.Transition;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import view.GamePage;
import model.components.Bullet;

public class ShootTransition extends Transition {
    private Bullet bullet;
    private static MediaPlayer mediaPlayer;
    static {
        Media media = new Media(GamePage.class.getResource("/music/Default Fire Sound.wav").toExternalForm());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setCycleCount(1);
    }
    public ShootTransition(Bullet bullet) {
        this.bullet = bullet;
        setCycleCount(-1);
        setCycleDuration(Duration.millis(10));
        mediaPlayer.stop();
        if(!GamePage.isMuted())mediaPlayer.play();
    }

    @Override
    protected void interpolate(double v) {
        bullet.move(5);
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
