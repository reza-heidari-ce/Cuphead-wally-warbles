package view;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.io.IOException;

public class SettingsPage {
    @FXML
    private Text message;
    @FXML
    private ChoiceBox difficulty;
    public void changeMute(MouseEvent mouseEvent) {
        if(!GamePage.isMuted()){
            message.setText("Game is muted");
            GamePage.setIsMuted(true);
        }else {
            message.setText("Game is unmuted");
            GamePage.setIsMuted(false);
        }
    }

    public void startGame(MouseEvent mouseEvent) throws IOException {
        GamePage.setDifficultyLevel(difficulty.getSelectionModel().getSelectedIndex() + 1);
        App.changeScene("gamePage", true);
    }

    public void initialize(){
        difficulty.getItems().add("Easy");
        difficulty.getItems().add("Medium");
        difficulty.getItems().add("Hard");
        difficulty.setValue("Medium");
    }

    public void enterPreviousMenu(MouseEvent mouseEvent) throws IOException {
        App.changeScene("mainMenuPage", false);
    }
}
