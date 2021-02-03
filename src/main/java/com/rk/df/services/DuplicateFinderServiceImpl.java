package com.rk.df.services;

import com.rk.df.checksum.Checksum;
import com.rk.df.checksum.Sha256ChecksumImpl;
import com.rk.df.model.DuplicateFiles;
import com.rk.df.model.DuplicateMetrics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DuplicateFinderServiceImpl implements DuplicateFinderService {
    public static final Logger LOG = LoggerFactory.getLogger(DuplicateFinderServiceImpl.class);
    public static final String DOC_FILTER = ".*\\.(pdf|ppt|pptx)$";
    public static final String IMG_FILTER = ".*\\.(jpg|png)$";
    public static final String MEDIA_FILTER = ".*\\.(mp4|wmv|mkv)$";
    private Checksum checksum = new Sha256ChecksumImpl();

    public Map<String, List<File>> listDuplicateFiles(String path) throws IOException {
        Map<String, List<File>> allFiles = checksum.generateChecksumForAllFiles(path);
        return getDuplicates(allFiles);
    }

    private Map<String, List<File>> getDuplicates(Map<String, List<File>> allFiles) {
        return allFiles.entrySet().stream().filter(entry -> entry.getValue().size() > 1).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    public DuplicateFiles getDuplicateFilesAndMetrics(String path) throws IOException {
        Map<String, List<File>> allFiles = checksum.generateChecksumForAllFiles(path);

        Map<String, List<File>> allDuplicates = getDuplicates(allFiles);
        Map<String, List<File>> documents = filter(allDuplicates, DOC_FILTER);
        Map<String, List<File>> images = filter(allDuplicates, IMG_FILTER);
        Map<String, List<File>> mediaFiles = filter(allDuplicates, MEDIA_FILTER);

        Integer totalFilesScanned = allFiles.values().stream().mapToInt(List::size).sum();
//        Integer totalDuplicateFiles = allFiles.values().stream().filter(item -> item.size() > 1).mapToInt(List::size).sum();
        long totalDuplicateFiles = allDuplicates.values().stream().mapToInt(List::size).sum();
        long duplicateImagesCount = images.values().stream().mapToInt(List::size).sum();
        long duplicateDocumentsCount = documents.values().stream().mapToInt(List::size).sum();
        long duplicateMediaCount = mediaFiles.values().stream().mapToInt(List::size).sum();

        DuplicateMetrics metrics = new DuplicateMetrics();
        metrics.setTotalNoOfFilesScanned(totalFilesScanned);
        metrics.setTotalNoOfDuplicateFiles(totalDuplicateFiles);
        metrics.setNoOfDocuments(duplicateDocumentsCount);
        metrics.setNoOfImages(duplicateImagesCount);
        metrics.setNoOfMedia(duplicateMediaCount);

        DuplicateFiles duplicateFiles = new DuplicateFiles();
        duplicateFiles.setDuplicateFilesGroupByChecksum(allDuplicates);
        duplicateFiles.setDuplicateMetrics(metrics);
        duplicateFiles.setDuplicateDocuments(documents);
        duplicateFiles.setDuplicateImages(images);
        duplicateFiles.setDuplicateMediaFiles(mediaFiles);

        return duplicateFiles;
    }

    private Map<String, List<File>> filter(Map<String, List<File>> allFiles, String imageFilter) {
        return allFiles
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue().get(0).getName().matches(imageFilter))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

}
