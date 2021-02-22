package com.rk.df.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rk.df.model.History;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class FileServiceImpl implements FileService {
    private static final Logger LOG = LoggerFactory.getLogger(FileServiceImpl.class);
    @Override
    public boolean archive(List<File> files, String destination) {
        return false;
    }

    @Override
    public boolean move(List<File> files, String destination) {
        AtomicBoolean isFileMovedSuccessfully = new AtomicBoolean(true);
        List<String> filesModified = new ArrayList<>();
        List<String> filesFailed = new ArrayList<>();
        files.stream().forEach(item -> {
            try {
                String destinationFile = destination + File.separator + item.getName();
                Files.move(item.toPath(), Paths.get(destinationFile), REPLACE_EXISTING);
                filesModified.add(item.getAbsolutePath());
            } catch (IOException e) {
                LOG.error("Could not move {} to {}", item.getPath(), destination, e);
                isFileMovedSuccessfully.set(false);
                filesFailed.add(item.getAbsolutePath());
            }
        });

        writeToJson(FileOperation.MOVE, filesModified, filesFailed);
        return isFileMovedSuccessfully.get();
    }

    @Override
    public boolean delete(List<File> files) {
        AtomicBoolean isFileMovedSuccessfully = new AtomicBoolean(true);
        List<String> filesDeleted = new ArrayList<>();
        List<String> filesFailed = new ArrayList<>();
        files.stream().forEach(item -> {
            try {
                Files.delete(item.toPath());
                filesDeleted.add(item.getAbsolutePath());
            } catch (IOException e) {
                LOG.error("Could not delete {}", item.getPath(), e);
                isFileMovedSuccessfully.set(false);
                filesFailed.add(item.getAbsolutePath());
            }
        });
        writeToJson(FileOperation.DELETE, filesDeleted, filesFailed);
        return isFileMovedSuccessfully.get();
    }

    private void writeToJson(FileOperation fileOperation, List<String> filesModified, List<String> filesFailed) {
        History history = new History(FileOperation.MOVE, filesModified, filesFailed);
        File historyFolder = new File("history");
        if(!historyFolder.exists()) {
            historyFolder.mkdir();
        }
        String destinationFile = "history/"+ LocalDateTime.now()+".json";
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(new File(destinationFile), history);
        } catch (IOException e) {
            LOG.error("Error writing history file", e);
        }
    }
}
