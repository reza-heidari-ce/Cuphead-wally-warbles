package view;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class MainMenuPage {

    @FXML
    private BorderPane pane;

    public void newGame(MouseEvent mouseEvent) throws IOException {
        App.changeScene("settingsPage", false);
    }

    public void enterProfile(MouseEvent mouseEvent) throws IOException {
        App.changeScene("profilePage", false);
    }

    public void exit(MouseEvent mouseEvent) {
        System.exit(0);
    }

    public void enterScoreBoard(MouseEvent mouseEvent) throws IOException {
        App.changeScene("scoreBoardPage", false);
    }
}
