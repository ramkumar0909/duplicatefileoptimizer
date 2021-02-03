package com.rk.df.checksum;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface Checksum {
    String generateChecksum(File file) throws IOException;
    Map<String, List<File>> generateChecksumForAllFiles(String path) throws IOException;
}
