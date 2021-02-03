package com.rk.df.ui.controller;

import com.rk.df.model.DuplicateFiles;
import com.rk.df.model.DuplicateMetrics;
import com.rk.df.services.DuplicateFinderService;
import com.rk.df.services.DuplicateFinderServiceImpl;
import com.rk.df.services.FileService;
import com.rk.df.services.FileServiceImpl;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class DuplicateFileFinderController implements Initializable {

    private static final Logger LOG = LoggerFactory.getLogger(DuplicateFileFinderController.class);

    public Label totalFiles;
    public Label totalDuplicateFiles;
    public PieChart piechart;
    public TableView<DuplicateFile> duplicateFileTableView;
    public Button chooseBtn;
    public Button scanBtn;
    public TableView<DuplicateFile> duplicateDocFileTableView;
    public TableView<DuplicateFile> duplicateImageFileTableView;
    public TableView<DuplicateFile> duplicateMediaFileTableView;
    public CheckBox isDryRunEnabled;
    @FXML
    private TextField pathTextField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        duplicateFileTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    public void scanDirectory() {
        DuplicateFinderService duplicateFinder = new DuplicateFinderServiceImpl();
        try {
            String path = pathTextField.getText();
            DuplicateFiles duplicateFiles = duplicateFinder.getDuplicateFilesAndMetrics(path);
            Map<String, List<File>> listOfDuplicates = duplicateFiles.getDuplicateFilesGroupByChecksum();
            if (!listOfDuplicates.isEmpty()) {
                final List<File> data = listOfDuplicates.values()
                        .stream()
                        .flatMap(Collection::stream)
                        .collect(Collectors.toList());

                List<DuplicateFile> allFiles = filter(data, ".*");
                duplicateFileTableView.getItems().addAll(FXCollections.observableList(allFiles));

                List<DuplicateFile> images = filter(data, ".*\\.(jpg|png)$");
                duplicateImageFileTableView.getItems().addAll(FXCollections.observableList(images));

                List<DuplicateFile> media = filter(data, ".*\\.(mp4|wmv|mkv)$");
                duplicateMediaFileTableView.getItems().addAll(FXCollections.observableList(media));

                List<DuplicateFile> documents = filter(data, ".*\\.(pdf|ppt|pptx)$");
                duplicateDocFileTableView.getItems().addAll(FXCollections.observableList(documents));

                //add piechart
                addPieChart(duplicateFiles);
            } else {
                duplicateFileTableView.setPlaceholder(new Label("No duplicate files"));
            }
            totalFiles.setText(String.valueOf(duplicateFiles.getDuplicateMetrics().getTotalNoOfFilesScanned()));
            totalDuplicateFiles.setText(String.valueOf(duplicateFiles.getDuplicateMetrics().getTotalNoOfDuplicateFiles()));

            //disable button
            chooseBtn.setDisable(true);
            scanBtn.setDisable(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private List<DuplicateFile> filter(List<File> data, String imageFilter) {
        return data
                .stream()
                .filter(item -> item.getName().matches(imageFilter))
                .map(item -> new DuplicateFile(item.getName(), item.getAbsolutePath()))
                .collect(Collectors.toList());
    }

    private void addPieChart(DuplicateFiles duplicateFiles) {

        DuplicateMetrics metrics = duplicateFiles.getDuplicateMetrics();
        long noOfOtherDupFiles = metrics.getTotalNoOfDuplicateFiles() - (metrics.getNoOfDocuments() + metrics.getNoOfImages() + metrics.getNoOfMedia());

        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("Other duplicate files", noOfOtherDupFiles),
//                        new PieChart.Data("Duplicate files", metrics.getTotalNoOfDuplicateFiles()),
                        new PieChart.Data("Duplicate Images", metrics.getNoOfImages()),
                        new PieChart.Data("Duplicate media", metrics.getNoOfMedia()),
                        new PieChart.Data("Duplicate Documents", metrics.getNoOfDocuments())
                );


        piechart.setLabelsVisible(true);
        piechart.setLabelLineLength(5);
        piechart.setData(pieChartData);
    }

    public void selectDirectory() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File file = directoryChooser.showDialog(null);
        pathTextField.setText(file.getAbsolutePath());
    }

    public void reset() {

        duplicateFileTableView.getItems().clear();
        duplicateImageFileTableView.getItems().clear();
        duplicateDocFileTableView.getItems().clear();
        duplicateMediaFileTableView.getItems().clear();

        pathTextField.clear();
        totalFiles.setText("");
        totalDuplicateFiles.setText("");
        piechart.getData().clear();

        //enable button
        chooseBtn.setDisable(false);
        scanBtn.setDisable(false);
    }

    public void archiveFiles() {
        fileAction("archive");
    }

    public void moveFiles() {
        fileAction("move");
    }

    public void deleteFiles() {
        fileAction("delete");
    }

    private void fileAction(String action) {
        FileService fileService = new FileServiceImpl();
        ObservableList<DuplicateFile> selectedRows = duplicateFileTableView.getSelectionModel().getSelectedItems();
        if (selectedRows.isEmpty()) {
            alertEmptySelection();
        }
        List<File> files = selectedRows
                .stream()
                .map(item -> new File(item.getLocation()))
                .collect(Collectors.toList());

        boolean isActionSuccessful = false;
        switch (action) {
            case "archive":
                LOG.info("archiving {} files", selectedRows.stream().count());
                if (isDryRunEnabled.isSelected()) {
                    selectedRows.stream().forEach(item -> LOG.info("Dry run: archiving... {}", item.location.getValue()));
                } else {
                    isActionSuccessful = fileService.archive(files, pathTextField.getText());
                }
                break;
            case "move":
                LOG.info("Moving {} files", selectedRows.stream().count());
                if (isDryRunEnabled.isSelected()) {
                    selectedRows.stream().forEach(item -> LOG.info("Dry run: moving... {}", item.location.getValue()));
                } else {
                    isActionSuccessful = fileService.move(files, pathTextField.getText());
                }
                break;
            case "delete":
                LOG.info("Deleting {} files", selectedRows.stream().count());
                if (isDryRunEnabled.isSelected()) {
                    selectedRows.stream().forEach(item -> LOG.info("Dry run: deleting... {}", item.location.getValue()));
                } else {
                    isActionSuccessful = fileService.delete(files);
                }
                break;
            default:
                LOG.warn("No action has been selected");
                throw new IllegalStateException("Unexpected value: " + action);
        }
        if (!isActionSuccessful) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error alert!!!");
            alert.setHeaderText("Error while processing your request.");
            alert.setContentText("Some or all of the files have failed to " + action + ". Please check your log files for more details");
            alert.showAndWait();
        }
    }

    private void alertEmptySelection() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Selection alert!!!");
        alert.setHeaderText("File selection alert!!!");
        alert.setContentText("Please select (multiple) files to archive.");

        alert.showAndWait();
    }

    public static class DuplicateFile {
        private final SimpleStringProperty file;
        private final SimpleStringProperty location;

        public DuplicateFile(String file, String location) {
            this.file = new SimpleStringProperty(file);
            this.location = new SimpleStringProperty(location);
        }

        public void setLocation(String location) {
            this.location.set(location);
        }

        public void setFile(String file) {
            this.file.set(file);
        }

        public String getFile() {
            return file.get();
        }

        public SimpleStringProperty fileProperty() {
            return file;
        }

        public String getLocation() {
            return location.get();
        }

        public SimpleStringProperty locationProperty() {
            return location;
        }
    }

    public static void main(String[] args) {
        List<String> testList = new ArrayList() {{
            add("file1.jpg");
            add("file1.jpg");
            add("file1.png");
            add("file1.xyz");
        }};
        for (String str : testList
        ) {
            System.out.println(str.matches(".*\\.(jpg|png)$"));
        }

//        System.out.println(Pattern.matches(".*\\.(jpg|png)$", "1.jpg"));
    }
}
