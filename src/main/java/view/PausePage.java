package view;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.io.IOException;

public class PausePage {
    @FXML
    private Text message;

    public void resumeGame(MouseEvent mouseEvent) {
        GamePage.resumeGame();
        App.resumeGameScene();
    }

    public void changeMute(MouseEvent mouseEvent) {
        if(!GamePage.isMuted()){
            message.setText("Game is muted");
            GamePage.setIsMuted(true);
        }else {
            message.setText("Game is unmuted");
            GamePage.setIsMuted(false);
        }
    }

    public void restartGame(MouseEvent mouseEvent) throws IOException {
        GamePage.endGame();
        App.changeScene("gamePage", true);
    }

    public void endGame(MouseEvent mouseEvent) throws IOException {
        GamePage.endGame();
        App.changeScene("mainMenuPage", false);
    }
}
