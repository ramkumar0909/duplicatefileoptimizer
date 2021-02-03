package com.rk.df.services;

import com.rk.df.model.History;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface FileService {
    boolean archive(List<File> files, String destination);
    boolean move(List<File> files, String destination);
    boolean delete(List<File> files);

}
