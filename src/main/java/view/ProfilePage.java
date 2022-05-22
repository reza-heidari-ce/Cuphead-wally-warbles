package view;

import controller.UserController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import model.User;

import java.io.IOException;

public class ProfilePage {
    @FXML
    private GridPane avatarPane;
    @FXML
    private BorderPane borderPane;
    @FXML
    private Text message;
    @FXML
    private TextField currentPassword;
    @FXML
    private TextField newPassword;
    @FXML
    private TextField newUsername;

    public void changeUsername(MouseEvent mouseEvent) {
        String message = UserController.getInstance().changeUsername(newUsername.getText(), currentPassword.getText());
        this.message.setText(message);
        if(message.contains("successful")){
            currentPassword.setText("");
            newUsername.setText("");
            newUsername.setText("");
        }
    }

    public void changePassword(MouseEvent mouseEvent) {
        String message = UserController.getInstance().changePassword(currentPassword.getText(), newPassword.getText());
        this.message.setText(message);
        if(message.contains("successful")){
            currentPassword.setText("");
            newUsername.setText("");
            newUsername.setText("");
        }
    }

    public void logout(MouseEvent mouseEvent) throws IOException {
        UserController.getInstance().logout();
        App.changeScene("loginPage", false);
    }

    public void removeUser(MouseEvent mouseEvent) throws IOException {
        String message = UserController.getInstance().removeUser(currentPassword.getText());
        this.message.setText(message);
        if(message.contains("successful")) App.changeScene("loginPage", false);
    }

    public void exit(MouseEvent mouseEvent) {
        System.exit(0);
    }

    public void enterPreviousMenu(MouseEvent mouseEvent) throws IOException {
        App.changeScene("mainMenuPage", false);
    }

    public void initialize(){
        Platform.runLater( () -> borderPane.requestFocus() );

        Rectangle blueAvatar = new Rectangle(50, 50 , new ImagePattern(new Image(getClass().getResource("/frames/images/blue.png").toExternalForm())));
        Rectangle redAvatar = new Rectangle(50, 50 , new ImagePattern(new Image(getClass().getResource("/frames/images/red.png").toExternalForm())));
        avatarPane.add(blueAvatar,0, 0);
        avatarPane.add(redAvatar, 1, 0);
        avatarPane.setHgap(50);
        if(User.getCurrentUser().getAvatar().equals("blue"))blueAvatar.setStroke(Color.RED);
        if(User.getCurrentUser().getAvatar().equals("red"))redAvatar.setStroke(Color.RED);

        redAvatar.setOnMouseClicked(mouseEvent -> {
            User.getCurrentUser().setAvatar("red");
            UserController.saveInfo();
            redAvatar.setStroke(Color.RED);
            blueAvatar.setStroke(null);
        });
        blueAvatar.setOnMouseClicked(mouseEvent -> {
            User.getCurrentUser().setAvatar("blue");
            UserController.saveInfo();
            blueAvatar.setStroke(Color.RED);
            redAvatar.setStroke(null);
        });

    }

}
