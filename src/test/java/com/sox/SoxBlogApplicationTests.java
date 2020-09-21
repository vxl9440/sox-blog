package com.sox;

import com.sox.mapper.UserMapper;
import com.sox.pojo.BlogUserAssociation;
import com.sox.service.BlogServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

@SpringBootTest
class SoxBlogApplicationTests {

    @Autowired
    BlogServiceImpl blogService;

    @Test
    void test() {

    }

}
