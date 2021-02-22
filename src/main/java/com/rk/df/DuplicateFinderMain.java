package com.rk.df;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class DuplicateFinderMain extends Application {

    private static final Logger LOG = LoggerFactory.getLogger(DuplicateFinderMain.class);

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/duplicate-file-finder-view.fxml"));
        BorderPane anchorPane = loader.<BorderPane>load();
        Scene scene = new Scene(anchorPane);
        primaryStage.setTitle("Duplicate Files finder");
        primaryStage.setScene(scene);
        primaryStage.show();
        LOG.info("Duplicate finder successfully launched!");
    }


    public static void main(String[] args) {
        launch();
    }

}
