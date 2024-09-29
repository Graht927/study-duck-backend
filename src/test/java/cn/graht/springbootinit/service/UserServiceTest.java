package cn.graht.springbootinit.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/*
* 用户服务测试
* @author graht
*/
@SpringBootTest
class UserServiceTest {
    @Resource
    private UserService userService;
    @Test
    void testAddUser() {
        User user = new User();
        user.setUsername("graht");
        user.setUserAccount("123456");
        user.setAvatarUrl("http://121.40.231.89:4443/i/static/tx.jpg");
        user.setGender(0);
        user.setUserPassword("123456");
        user.setPhone("123");
        user.setEmail("2331");
        boolean save = userService.save(user);
        System.out.println(user.getId());
        Assertions.assertTrue(save);
    }
    @Test
    void testDigest(){
            String s = DigestUtils.md5DigestAsHex(("123456" + "password").getBytes());
            System.out.println(s);
    }

    @Test
    void userRegister() {
        String userAccount = "graht";
        String userPassword = "123456";
        String checkPassword = "123456";
        long result = userService.userRegister(userAccount, "", checkPassword);
        Assertions.assertEquals(-1,result);

        checkPassword = "12346";
        result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1,result);

        result = userService.userRegister("shj  dsjd", userPassword, checkPassword);
        Assertions.assertEquals(-1,result);

        result = userService.userRegister("graht", userPassword, checkPassword);
        Assertions.assertEquals(-1,result);

        result = userService.userRegister("gra", userPassword, checkPassword);
        Assertions.assertEquals(-1,result);
        checkPassword = "123456";
        result = userService.userRegister("admin", userPassword, checkPassword);
        Assertions.assertTrue(result > 0);
    }

    @Test
    void searchUserByTag() {
        List<String> lists = Arrays.asList("z");
        List<User> users = userService.searchUserByTag(lists);
        System.out.println(users);
    }
}