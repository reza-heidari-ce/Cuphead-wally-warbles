package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class App extends Application {
    private static Stage stage;

    private static Scene gameScene;

    @Override
    public void start(Stage stage) throws Exception {
        App.stage = stage;
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/loginPage.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        //stage.setFullScreen(true);
    }
    public static void main(String[] args) {
        launch(args);
    }

    public static void changeScene(String name, boolean isFullScreen) throws IOException {
        Parent root = FXMLLoader.load(App.class.getResource("/fxml/" + name + ".fxml"));
        Scene scene = new Scene(root);
        if(name.equals("gamePage")) gameScene = scene;
        stage.setScene(scene);
        stage.setFullScreen(isFullScreen);
    }
    public static void resumeGameScene(){
        stage.setScene(gameScene);
        stage.setFullScreen(true);
    }

}
