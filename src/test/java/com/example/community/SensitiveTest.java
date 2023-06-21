package com.example.community;

import com.example.community.util.SensitiveFilter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class SensitiveTest {

    @Autowired
    private SensitiveFilter sensitiveFilter;

    @Test
    public void testSensitiveFilter() {
        String test = "这里可以赌博，可以吸毒，可以嫖娼，可以开票，哈哈哈";
        String filter = sensitiveFilter.filter(test);
        System.out.println(filter);

        test = "这里可以赌✩博，可以吸✩毒，可以嫖✩娼，可以开✩票，哈哈哈";
        filter = sensitiveFilter.filter(test);
        System.out.println(filter);
    }

}
