package com.jrdm.controller;

import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lombok.extern.slf4j.Slf4j;

/**
 * <br/>
 * Created on 2018/7/6 15:29.
 *
 * @author zhubenle
 */
@Slf4j
public class AppController {

    public TreeView<String> connListTreeView;
    public Button btnAddConn;

    public void initialize() {
        log.info("initialize......");
        ImageView link = new ImageView(new Image("image/link.png", 30, 30, true, true));
        btnAddConn.setGraphic(link);

        TreeItem<String> root = new TreeItem<>("Root");
        connListTreeView.setRoot(root);
    }
}
