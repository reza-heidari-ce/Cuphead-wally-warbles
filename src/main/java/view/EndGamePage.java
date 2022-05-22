package view;

import controller.UserController;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import model.User;
import view.components.BigBoss;

import java.io.IOException;

public class EndGamePage {

    @FXML
    private Text progress;
    @FXML
    private Text score;
    @FXML
    private Text totalScore;

    public void initialize(){
        UserController.saveInfo();
        progress.setText("progress : " + (100 - BigBoss.getInstance().getHealth()) + "%");
        score.setText("You scored : " + GamePage.getScore());
        totalScore.setText("Your total score is : " + User.getCurrentUser().getScore());
    }

    public void restartGame(MouseEvent mouseEvent) throws IOException {
        App.changeScene("gamePage", true);
    }

    public void enterMainMenu(MouseEvent mouseEvent) throws IOException {
        App.changeScene("mainMenuPage", false);
    }


}
