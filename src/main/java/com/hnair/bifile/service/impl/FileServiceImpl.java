package com.hnair.bifile.service.impl;

import com.hnair.bifile.service.FileService;
import com.hnair.bifile.vo.FileListResult;
import com.hnair.bifile.vo.FileVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Using IntelliJ IDEA.
 *
 * @author XIANYINGDA at 8/29/2018 2:12 PM
 */
@Service
@Slf4j
public class FileServiceImpl implements FileService {
    @Override
    public FileListResult getFileList() {
        String os = System.getProperty("os.name");
        String path;
        if (os != null && os.contains("Windows")) {
            path = "d:/excel/excel";
        } else {
            path = "/root/job/excel";
        }
        File file = new File(path);
        File[] files = file.listFiles();
        List<File> fileList = Arrays.stream(files).collect(Collectors.toList());
        List<String> stringList = fileList.stream().map(File::getName).sorted(String::compareTo).collect(Collectors.toList());
        Collections.reverse(stringList);
        List<FileVo> list = new LinkedList<>();
        for (String filename : stringList) {
            try {
                FileVo vo = new FileVo();
                vo.setFileName(filename);
                vo.setEncodedFileName(URLEncoder.encode(filename, "UTF-8"));
                list.add(vo);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        FileListResult result = new FileListResult();
        result.setFileNameList(list);
        return result;
    }

    @Override
    public File getFile(String filename) {
        String os = System.getProperty("os.name");
        String path;
        if (os != null && os.contains("Windows")) {
            path = "d:/excel/excel";
        } else {
            path = "/root/job/excel";
        }
        File file = new File(path + "/" + filename);
        if (!file.exists()) {
            return null;
        }
        if (file.isDirectory()) {
            return null;
        }
        return file;
    }
}
