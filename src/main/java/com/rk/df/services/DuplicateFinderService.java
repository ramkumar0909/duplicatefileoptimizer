package com.rk.df.services;

import com.rk.df.model.DuplicateFiles;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface DuplicateFinderService {
    Map<String, List<File>> listDuplicateFiles(String path) throws IOException;

    DuplicateFiles getDuplicateFilesAndMetrics(String path) throws IOException;
}
