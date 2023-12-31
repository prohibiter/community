package com.example.community;

import com.example.community.dao.DiscussPostMapper;
import com.example.community.dao.LoginTicketMapper;
import com.example.community.dao.MessageMapper;
import com.example.community.dao.UserMapper;
import com.example.community.entity.DiscussPost;
import com.example.community.entity.LoginTicket;
import com.example.community.entity.Message;
import com.example.community.entity.User;
import com.example.community.util.CommunityUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class MapperTest {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private DiscussPostMapper discussPostMapper;

    @Autowired
    private LoginTicketMapper loginTicketMapper;

    @Autowired
    private MessageMapper messageMapper;

    @Test
    public void testSelectUser() {
        final User user = userMapper.selectById(101);
        System.out.println(user);

        final User liubei = userMapper.selectByName("liubei");
        System.out.println(liubei);

        final User user1 = userMapper.selectByEmail("nowcoder101@sina.com");
        System.out.println(user1);
    }

    @Test
    public void testInsertUser() {
        User user = new User();
        user.setUsername("test");
        user.setPassword("123456");
        user.setSalt("abc");
        user.setEmail("test@qq.com");
        user.setHeaderUrl("http://www.nowcoder.com/101.png");
        user.setCreateTime(new Date());

        int rows = userMapper.insertUser(user);
        System.out.println(rows);
        System.out.println(user.getId());

    }

    @Test
    public void updateUser() {
        int rows = userMapper.updateStatus(150, 1);
        System.out.println(rows);

        rows = userMapper.updateHeader(150, "http://www.nowcoder.com/102.png");
        System.out.println(rows);

        rows = userMapper.updatePassword(150, "hello");
        System.out.println(rows);
    }

    @Test
    public void testSelectPosts() {
        final List<DiscussPost> discussPosts = discussPostMapper.selectDiscussPosts(149, 0, 20);
        final Iterator<DiscussPost> iterator = discussPosts.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }

        final int i = discussPostMapper.selectDiscussPostRows(149);
        System.out.println(i);
    }

    @Test
    public void testInsertLoginTicket() {
        final LoginTicket loginTicket = new LoginTicket();
        loginTicket.setUserId(10);
        loginTicket.setTicket("abc");
        loginTicket.setStatus(0);
        loginTicket.setExpired(new Date(System.currentTimeMillis() + 1000 * 60));

        loginTicketMapper.insetLoginTicket(loginTicket);
    }

    @Test
    public void testSelectLoginTicket() {
        LoginTicket loginTicket = loginTicketMapper.selectByTicket("abc");
        System.out.println(loginTicket);
        loginTicketMapper.updateStatus("abc", 1);
        loginTicket = loginTicketMapper.selectByTicket("abc");
        System.out.println(loginTicket);
    }

    @Test
    public void testUpdatePassword() {
        String password = "123";
        User user = userMapper.selectByName("test2");
        password = CommunityUtil.md5(password + user.getSalt());
        userMapper.updatePassword(user.getId(), password);
    }

    @Test
    public void testSelectLetters() {
        List<Message> list = messageMapper.selectConversations(111, 0, 20);
        for (Message message : list) {
            System.out.println(message);
        }

        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++");

        int count = messageMapper.selectConversationCount(111);
        System.out.println(count);

        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++");

        list = messageMapper.selectLetters("111_112", 0, 10);
        for (Message message : list) {
            System.out.println(message);
        }

        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++");

        count = messageMapper.selectLetterCount("111_112");
        System.out.println(count);

        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++");

        count = messageMapper.selectLetterUnreadCount(131, "111_131");
        System.out.println(count);

    }

}
