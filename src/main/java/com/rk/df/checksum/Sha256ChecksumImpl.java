package com.rk.df.checksum;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Sha256ChecksumImpl implements Checksum {
    private static final Logger LOG = LoggerFactory.getLogger(Sha256ChecksumImpl.class);

    public String generateChecksum(File file) throws IOException {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
            FileInputStream fis = new FileInputStream(file);

            byte[] byteArray = new byte[1024];
            int bytesCount = 0;

            while ((bytesCount = fis.read(byteArray)) != -1) {
                md.update(byteArray, 0, bytesCount);
            }
            fis.close();

            byte[] bytes = md.digest();

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }

            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            LOG.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public Map<String, List<File>> generateChecksumForAllFiles(String path) throws IOException {
        Map<String, List<File>> duplicateList = new HashMap<String, List<File>>();
        List<File> files = Files.walk(Paths.get(path))
                .filter(Files::isRegularFile).map(item -> item.toFile())
                .collect(Collectors.toList());
        for (File _file : files) {
            String checksumValue = generateChecksum(_file);
            if (duplicateList.containsKey(checksumValue)) {
                duplicateList.get(checksumValue).add(_file);
            } else {
                duplicateList.put(checksumValue, new ArrayList<File>() {{
                    add(_file);
                }});
            }
        }
        return duplicateList;
    }

}
