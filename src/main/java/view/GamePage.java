package view;

import javafx.animation.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.util.Duration;
import model.User;
import model.components.Airplane;
import model.components.BigBoss;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class GamePage {
    @FXML
    private AnchorPane pane;
    private static MediaPlayer mediaPlayer;

    static {
        Media media = new Media(GamePage.class.getResource("/music/Cuphead OST - Aviary Action.mp3").toExternalForm());
        mediaPlayer = new MediaPlayer(media);
    }

    private static boolean isMuted = false;

    private static int difficultyLevel = 2;
    private static boolean isPaused = false;

    private static ArrayList<Animation> allAnimations = new ArrayList<>();

    Random random = new Random();

    private static int score = 0;
    private static long timeDifference;

    private Text scoreText;
    public void initialize(){
        timeDifference = System.currentTimeMillis();
        score = 0;
        isPaused = false;
        allAnimations = new ArrayList<>();
        Image image = new Image(getClass().getResource("/frames/backgrounds/02.png").toExternalForm());
        Image image2 = new Image(getClass().getResource("/frames/backgrounds/08.png").toExternalForm());
        BackgroundSize backgroundSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, true, true, true);
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,backgroundSize);
        BackgroundImage backgroundImage2 = new BackgroundImage(image2, BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,backgroundSize);
        pane.setBackground(new Background(backgroundImage2, backgroundImage));

        String avatar = User.getCurrentUser().getAvatar();
        Airplane airplane = Airplane.getNewInstance(avatar, pane);
        BigBoss bigBoss = BigBoss.getNewInstance(pane);

        if(!isMuted){
            mediaPlayer.stop();
            mediaPlayer.play();
        //    System.out.println(mediaPlayer);
            mediaPlayer.setCycleCount(-1);
        }
        //mediaPlayer.setAutoPlay(true);

        Platform.runLater(() ->airplane.requestFocus());

        scoreText = new Text("Score : " + score);
        scoreText.getStyleClass().add("gameText");
        pane.getChildren().add(scoreText);
        scoreText.setX(400);
        scoreText.setY(50);

        Timeline healthTimeline = new Timeline(new KeyFrame(Duration.millis(10), actionEvent -> {
            if(Airplane.getInstance().getHealth() <= 0){
                score += ((100 - BigBoss.getInstance().getHealth()) / 8) * difficultyLevel;
                User.getCurrentUser().setScore(User.getCurrentUser().getScore() + score);
                try {
                    endGame();
                    App.changeScene("endGamePage", true);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }else if(BigBoss.getInstance().getHealth() <= 0){
                score += 25 * difficultyLevel;
                score += Airplane.getInstance().getHealth() * 5 * difficultyLevel;
                User.getCurrentUser().setScore(User.getCurrentUser().getScore() + score);
                try {
                    endGame();
                    App.changeScene("endGamePage", true);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            Airplane.getInstance().getHealthText().setText("Cuphead health: " + Airplane.getInstance().getHealth());
            BigBoss.getInstance().getHealthText().setText("Boss health: " + BigBoss.getInstance().getHealth());
            //System.out.println(score);
            double showingScore = score + ((100 - BigBoss.getInstance().getHealth()) / 8) * difficultyLevel + Airplane.getInstance().getHealth() * 5 * difficultyLevel;
            scoreText.setText("Score : " + showingScore);
            airplane.requestFocus();
        }));
        healthTimeline.setCycleCount(-1);
        healthTimeline.play();
        healthTimeline.setDelay(Duration.millis(100));
        GamePage.getAllAnimations().add(healthTimeline);
    }

    public static void setIsMuted(boolean isMuted) {
        GamePage.isMuted = isMuted;
    }

    public static void setDifficultyLevel(int difficultyLevel) {
        GamePage.difficultyLevel = difficultyLevel;
    }

    public static int getDifficultyLevel() {
        return difficultyLevel;
    }

    public void pauseGame(MouseEvent mouseEvent) throws IOException {
        ArrayList<Animation> allAnimationsCopy = new ArrayList<>(allAnimations);
        //System.out.println(allAnimations);
        for (Animation animation : allAnimationsCopy) {
            animation.pause();
        }
        allAnimations = allAnimationsCopy;
        isPaused = true;
        mediaPlayer.pause();
        App.changeScene("pausePage",true);
    }
    public static void endGame() {
        timeDifference = System.currentTimeMillis() - timeDifference;
        mediaPlayer.stop();
        ArrayList<Animation> allAnimationsCopy = new ArrayList<>(allAnimations);
        for (Animation animation : allAnimationsCopy) {
            animation.stop();
        }
        allAnimations = allAnimationsCopy;
        allAnimations.clear();
        allAnimationsCopy.clear();
    }

    public static void resumeGame(){
        for (Animation animation : allAnimations) {
            animation.play();
        }
        if(!isMuted)mediaPlayer.play();
        isPaused = false;
    }
    public static ArrayList<Animation> getAllAnimations() {
        return allAnimations;
    }

    public static boolean isMuted() {
        return isMuted;
    }

    public static void setScore(int score) {
        GamePage.score = score;
    }

    public static int getScore() {
        return score;
    }

    public static boolean isPaused() {
        return isPaused;
    }

    public static long getTimeDifference() {
        return timeDifference;
    }
}
