package com.example.community;

import com.example.community.service.AlphaService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashMap;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class TransactionTest {

    @Autowired
    private AlphaService alphaService;

    @Test
    public void testSave1() {
        final Object obj = alphaService.save1();
        System.out.println(obj);
    }

    @Test
    public void testSave2() {
        final Object obj = alphaService.save2();
        System.out.println(obj);
    }

}
