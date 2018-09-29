package com.hnair.bifile;

import com.hnair.bifile.service.FileService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BifileApplicationTests {

    @Autowired
    private FileService fileService;

    @Test
    public void contextLoads() throws UnsupportedEncodingException {
        String encode = URLEncoder.encode("各注册渠道统计表&付费用户统计2018-08-28.xls", "UTF-8");
        System.out.println(encode);
    }

}
