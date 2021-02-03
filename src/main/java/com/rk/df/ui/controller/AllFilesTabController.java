package com.rk.df.ui.controller;

import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AllFilesTabController implements Initializable {

    public TableView duplicateAllFileTableView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public TableView getDuplicateAllFileTableView() {
        return duplicateAllFileTableView;
    }

    public void setDuplicateAllFileTableView(TableView duplicateAllFileTableView) {
        this.duplicateAllFileTableView = duplicateAllFileTableView;
    }

    public void setTableData(List<DuplicateFileFinderController.DuplicateFile> collect){
        duplicateAllFileTableView.getItems().addAll(FXCollections.observableList(collect));
    }
}
