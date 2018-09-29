package com.hnair.bifile.service;

import com.hnair.bifile.vo.FileListResult;

import java.io.File;

/**
 * Using IntelliJ IDEA.
 *
 * @author XIANYINGDA at 8/29/2018 2:12 PM
 */
public interface FileService {
    FileListResult getFileList();

    File getFile(String filename);
}
