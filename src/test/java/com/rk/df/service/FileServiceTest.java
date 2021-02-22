package com.rk.df.service;

import com.rk.df.model.History;
import com.rk.df.services.FileOperation;
import com.rk.df.services.FileService;
import com.rk.df.services.FileServiceImpl;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FileServiceTest {

    @Test
    public void testFileMoveAndDelete() throws IOException {

        String fileName = "fileToMove.file";
        String filePath = "src/test/resources/";
        File fileToMove = new File(filePath + fileName);
        if (!fileToMove.exists()) {
            fileToMove.createNewFile();
        }
        Assert.assertTrue(fileToMove.exists());
        Assert.assertTrue(fileToMove.isFile());

        String destinationPath = filePath + "toMoveFolder";
        File destinationFolder = new File(destinationPath);
        if (!destinationFolder.exists()) {
            destinationFolder.mkdirs();
        }
        Assert.assertTrue(destinationFolder.exists());
        Assert.assertTrue(destinationFolder.isDirectory());

        FileService fileService = new FileServiceImpl();
        List<File> files = new ArrayList<File>() {{
            add(fileToMove);
        }};

        fileService.move(files, destinationFolder.getAbsolutePath());

        Assert.assertFalse(new File(filePath + fileName).exists());

        String newPath = destinationPath + File.separator + fileName;
        File movedFile = new File(newPath);
        Assert.assertTrue(new File(destinationPath).exists());
        Assert.assertTrue(movedFile.exists());

        //delete
        List<File> deleteList = new ArrayList<File>() {
            {
                add(movedFile);
                add(new File(destinationPath));
            }
        };
        fileService.delete(deleteList);
        Assert.assertFalse(new File(destinationPath).exists());
        Assert.assertFalse(movedFile.exists());

    }

//    @Test
//    public void testWriteToJson() throws IOException {
//        FileService fileService = new FileServiceImpl();
//        History history = new History();
//        history.setFileOperation(FileOperation.ARCHIVE);
//        history.setFilesModified(new ArrayList<String>(){{add("testlocation1");add("testlocation2");}});
//        history.setFilesFailed(new ArrayList<String>(){{add("testFailed1");add("testFailed2");}});
//
//        fileService.writeToJson(history, "history/"+ LocalDateTime.now()+".json");
//    }
}
