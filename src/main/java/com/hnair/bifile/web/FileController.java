package com.hnair.bifile.web;

import com.hnair.bifile.service.FileService;
import com.hnair.bifile.vo.FileListResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * Using IntelliJ IDEA.
 *
 * @author XIANYINGDA at 8/29/2018 2:03 PM
 */
@Controller
@Slf4j
public class FileController {

    @Autowired
    private FileService fileService;

    @RequestMapping({"/", "/files"})
    public String toFileList(Model model, HttpServletRequest request) {
        String remoteUser = request.getRemoteUser();
        String remoteAddr = request.getRemoteAddr();
        String remoteHost = request.getRemoteHost();
        FileListResult fileList = fileService.getFileList();
        model.addAttribute("remoteUser", remoteUser);
        model.addAttribute("remoteAddr", remoteAddr);
        model.addAttribute("remoteHost", remoteHost);
        model.addAttribute("fileList", fileList);
        return "file/list";
    }

    @GetMapping("/download")
    @ResponseBody
    public String downloadFile(String filename, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String remoteUser = request.getRemoteUser();
        String remoteAddr = request.getRemoteAddr();
        String remoteHost = request.getRemoteHost();
        log.info("请求下载文件：{}-{}-{}-{}", filename, remoteUser, remoteAddr, remoteHost);
        File file = fileService.getFile(filename);
        if (file == null) {
            return "文件未找到";
        }
        response.setHeader("content-type", "application/octet-stream");
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Length", String.valueOf(new FileInputStream(file).available()));
        response.setHeader("Content-Disposition", "attachment;filename=" + new String(file.getName().getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1));
        byte[] buff = new byte[1024];
        BufferedInputStream bis = null;
        OutputStream os = null;
        try {
            os = response.getOutputStream();
            bis = new BufferedInputStream(new FileInputStream(file));
            int len = 0;
            while ((len = bis.read(buff)) > 0) {
                os.write(buff, 0, len);
            }
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
            return "内部错误";
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "ok";
    }
}
