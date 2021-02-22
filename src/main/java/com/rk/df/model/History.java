package com.rk.df.model;

import com.rk.df.services.FileOperation;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class History {
    private FileOperation fileOperation;
    private String time;
    private List<String> filesModified;
    private List<String> filesFailed;

    public History(FileOperation fileOperation, List<String> filesModified, List<String> filesFailed) {
        this.fileOperation = fileOperation;
        this.filesModified = filesModified;
        this.filesFailed = filesFailed;
        this.time = LocalDateTime.now().toString();
    }

    public String getTime() {
        return time;
    }

    public List<String> getFilesModified() {
        return filesModified;
    }

    public void setFilesModified(List<String> filesModified) {
        this.filesModified = filesModified;
    }

    public List<String> getFilesFailed() {
        return filesFailed;
    }

    public void setFilesFailed(List<String> filesFailed) {
        this.filesFailed = filesFailed;
    }

    public FileOperation getFileOperation() {
        return fileOperation;
    }

    public void setFileOperation(FileOperation fileOperation) {
        this.fileOperation = fileOperation;
    }
}
