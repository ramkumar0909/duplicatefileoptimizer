package com.rk.df.model;

public class DuplicateMetrics {
    private long totalNoOfFilesScanned;
    private long totalNoOfDuplicateFiles;
    private long sizeOfAllScannedFiles;
    private long sizeOfDuplicateFiles;
    private long noOfImages;
    private long noOfDocuments;
    private long noOfMedia;

    public long getTotalNoOfDuplicateFiles() {
        return totalNoOfDuplicateFiles;
    }

    public void setTotalNoOfDuplicateFiles(long totalNoOfDuplicateFiles) {
        this.totalNoOfDuplicateFiles = totalNoOfDuplicateFiles;
    }

    public long getSizeOfAllScannedFiles() {
        return sizeOfAllScannedFiles;
    }

    public void setSizeOfAllScannedFiles(long sizeOfAllScannedFiles) {
        this.sizeOfAllScannedFiles = sizeOfAllScannedFiles;
    }

    public long getSizeOfDuplicateFiles() {
        return sizeOfDuplicateFiles;
    }

    public void setSizeOfDuplicateFiles(long sizeOfDuplicateFiles) {
        this.sizeOfDuplicateFiles = sizeOfDuplicateFiles;
    }

    public long getTotalNoOfFilesScanned() {
        return totalNoOfFilesScanned;
    }

    public void setTotalNoOfFilesScanned(long totalNoOfFilesScanned) {
        this.totalNoOfFilesScanned = totalNoOfFilesScanned;
    }

    public long getNoOfImages() {
        return noOfImages;
    }

    public void setNoOfImages(long noOfImages) {
        this.noOfImages = noOfImages;
    }

    public long getNoOfDocuments() {
        return noOfDocuments;
    }

    public void setNoOfDocuments(long noOfDocuments) {
        this.noOfDocuments = noOfDocuments;
    }

    public long getNoOfMedia() {
        return noOfMedia;
    }

    public void setNoOfMedia(long noOfMedia) {
        this.noOfMedia = noOfMedia;
    }

    @Override
    public String toString() {
        return "DuplicateMetrics{" +
                "totalNoOfFilesScanned=" + totalNoOfFilesScanned +
                ", totalNoOfDuplicateFiles=" + totalNoOfDuplicateFiles +
                ", sizeOfAllScannedFiles=" + sizeOfAllScannedFiles +
                ", sizeOfDuplicateFiles=" + sizeOfDuplicateFiles +
                '}';
    }
}
