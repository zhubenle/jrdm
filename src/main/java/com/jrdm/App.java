package com.jrdm;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

/**
 * <br/>
 * Created on 2018/7/2 17:24.
 *
 * @author zhubenle
 */
public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Web View");
        MenuBar menuBar = new MenuBar();
        menuBar.setUseSystemMenuBar(true);
        Menu menu1 = new Menu("父标题1");
        Menu menu2 = new Menu("父标题2");
        menu1.getItems().addAll(new MenuItem("子标题1"), new MenuItem("子标题2"));
        menuBar.getMenus().addAll(menu1, menu2);
        Scene scene = new Scene(menuBar, 200, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
