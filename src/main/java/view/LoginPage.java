package view;


import controller.UserController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

import java.io.IOException;

public class LoginPage{
    @FXML
    private Text message;
    @FXML
    private BorderPane borderPane;
    @FXML
    private TextField username;
    @FXML
    private TextField password;

    public void initialize(){
        Platform.runLater( () -> borderPane.requestFocus() );
    }

    public void loginGuest(MouseEvent mouseEvent) throws IOException {
        UserController.getInstance().loginGuest();
        App.changeScene("mainMenuPage", false);
    }
    public void register(MouseEvent mouseEvent){
        String message = UserController.getInstance().register(username.getText(), password.getText());
        this.message.setText(message);
        if(message.contains("successful")){
            username.setText("");
            password.setText("");
        }
    }

    public void login(MouseEvent mouseEvent) throws IOException {
        String message = UserController.getInstance().login(username.getText(), password.getText());
        if(message.contains("successful")) App.changeScene("mainMenuPage", false);
        this.message.setText(message);
    }

    public void exit(MouseEvent mouseEvent) {
        System.exit(0);
    }
}
