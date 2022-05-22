package view;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class ScoreBoardPage {
    @FXML
    private VBox box;

    public void initialize(){
        ArrayList<User> users = User.getUsers();
        Collections.sort(users);
        Collections.reverse(users);
        int id = 0;
        for(User user : users){
            Text text = new  Text(user.getUsername() + " : " + user.getScore());
            text.getStyleClass().add("scoreBoardText");
            HBox hBox = new HBox(text);
            hBox.getStyleClass().add("scoreBoardBox");
            if(id == 0)hBox.setStyle("-fx-background-color: gold");
            else if(id == 1)hBox.setStyle("-fx-background-color: silver");
            else if(id == 2)hBox.setStyle("-fx-background-color: #cd7f32");

            box.getChildren().add(hBox);
            id++;
        }
    }

    public void enterPreviousMenu(MouseEvent mouseEvent) throws IOException {
        App.changeScene("mainMenuPage", false);
    }
}
