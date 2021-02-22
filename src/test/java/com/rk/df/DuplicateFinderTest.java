package com.rk.df;

import com.rk.df.model.DuplicateFiles;
import com.rk.df.services.DuplicateFinderService;
import com.rk.df.services.DuplicateFinderServiceImpl;
import org.junit.Test;

import java.io.IOException;

public class DuplicateFinderTest {

    @Test
    public void testListDuplicateFiles () throws IOException {
        DuplicateFinderService df = new DuplicateFinderServiceImpl();
        DuplicateFiles duplicateFiles = df.getDuplicateFilesAndMetrics("src/test");
        duplicateFiles.getDuplicateFilesGroupByChecksum().entrySet().stream().forEach(item -> System.out.println(item.getKey() + ":" + item.getValue()));
        System.out.println(duplicateFiles.getDuplicateMetrics());
    }

    @Test
    public void testListDuplicateSingleFile () throws IOException {
//        DuplicateFinder df = new DuplicateFinderImpl();
//        Map<String, List<File>> listOfduplicateFiles = df.listDuplicateFiles("src/test/resources/test.file");
//        listOfduplicateFiles.forEach(item -> item.forEach(innerItem -> System.out.println(innerItem)));
    }

}
