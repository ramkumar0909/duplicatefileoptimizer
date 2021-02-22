package com.rk.df.model;

import java.io.File;
import java.util.List;
import java.util.Map;

public class DuplicateFiles {
    private Map<String, List<File>> duplicateFilesGroupByChecksum;
    private DuplicateMetrics duplicateMetrics;
    private Map<String, List<File>> duplicateImages;
    private Map<String, List<File>> duplicateMediaFiles;
    private Map<String, List<File>> duplicateDocuments;

    public Map<String, List<File>> getDuplicateFilesGroupByChecksum() {
        return duplicateFilesGroupByChecksum;
    }

    public void setDuplicateFilesGroupByChecksum(Map<String, List<File>> duplicateFilesGroupByChecksum) {
        this.duplicateFilesGroupByChecksum = duplicateFilesGroupByChecksum;
    }

    public DuplicateMetrics getDuplicateMetrics() {
        return duplicateMetrics;
    }

    public void setDuplicateMetrics(DuplicateMetrics duplicateMetrics) {
        this.duplicateMetrics = duplicateMetrics;
    }

    public Map<String, List<File>> getDuplicateImages() {
        return duplicateImages;
    }

    public void setDuplicateImages(Map<String, List<File>> duplicateImages) {
        this.duplicateImages = duplicateImages;
    }

    public Map<String, List<File>> getDuplicateMediaFiles() {
        return duplicateMediaFiles;
    }

    public void setDuplicateMediaFiles(Map<String, List<File>> duplicateMediaFiles) {
        this.duplicateMediaFiles = duplicateMediaFiles;
    }

    public Map<String, List<File>> getDuplicateDocuments() {
        return duplicateDocuments;
    }

    public void setDuplicateDocuments(Map<String, List<File>> duplicateDocuments) {
        this.duplicateDocuments = duplicateDocuments;
    }
}
