package com.jrdm;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * <br/>
 * Created on 2018/7/2 17:24.
 *
 * @author zhubenle
 */
public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("JRDM");
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/fxml/app.fxml"),
                ResourceBundle.getBundle("language.message", Locale.CHINESE)));
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
