//package com.yupi.usercenter.service;
//
//import com.yupi.usercenter.model.User;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import javax.annotation.Resource;
//
//import static org.junit.jupiter.api.Assertions.*;
//
///**
// * @author : wangshanjie
// * @date : 21:01 2023/2/22
// * *这里 test的目录路径和上面一样就好
// * 这里是一个简单的用户测试
// */
//@SpringBootTest
//public class UserServiceTest {
//
//    @Resource
//    private UserService userService;
//
//    @Test
//    //这里的Test类是选择上面的junit.jupiter.api包中的东西
//    public void testAddUser(){
//        User user = new User();
//        user.setId(0L);
//        user.setName("");
//        user.setAge(0);
//        user.setEmail("");
//
//
//
//
//        // alt + enter 生成对象的所有属性，空值
//
//
//        // 接下来是设置用户的信息
//        // 插件中的generateAllsetter 一键生成所有的方法
//
//
//
//    }
//
//
//}

package com.yupi.usercenter.service;

import com.yupi.usercenter.model.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/*
 * 用户服务测试
 *  元测试
 *
 * @author yupi
 */
@SpringBootTest
public class UserServiceTest {

    @Resource
    private UserService userService;

    @Test
    public void testAddUser() {
        User user = new User();

        user.setUsername("dogYupi");
        user.setUserAccount("123");
        user.setAvatarUrl("https://img1.baidu.com/it/u=1645832847,2375824523&fm=253&fmt=auto&app=138&f=JPEG?w=480&h=480");
        user.setGender(0);
        user.setUserPassword("xxx");
        user.setEmail("123");
        user.setPhone("456");


        boolean result = userService.save(user);
        // user 用户的id，新增的
        System.out.println(user.getId());
        // 断言程序的执行结果是否等于自己想要的结果
        Assertions.assertTrue(result);
    }

    @Test
    void userRegister() {
        // 右键这个方法  generate missed test mothed
        String userAccount = "yupi";
        String userPassword = "";
        String checkPassword = "123456";
        String planetCode = "1";
        long result = userService.userRegister(userAccount, userPassword, checkPassword,planetCode);
        Assertions.assertEquals(-1, result);

        userAccount = "yu";
        result = userService.userRegister(userAccount, userPassword, checkPassword,planetCode);
        Assertions.assertEquals(-1, result);

        userAccount = "yupi";
        userPassword = "123456";
        result = userService.userRegister(userAccount, userPassword, checkPassword,planetCode);
        Assertions.assertEquals(-1, result);

        userAccount = "yu pi";
        userPassword = "12345678";
        result = userService.userRegister(userAccount, userPassword, checkPassword,planetCode);
        Assertions.assertEquals(-1, result);

        checkPassword = "123456789";
        result = userService.userRegister(userAccount, userPassword, checkPassword,planetCode);
        Assertions.assertEquals(-1, result);

        userAccount = "dogyupi";
        checkPassword = "12345678";
        result = userService.userRegister(userAccount, userPassword, checkPassword,planetCode);
        Assertions.assertEquals(-1, result);

        userAccount = "yupi";
        result = userService.userRegister(userAccount, userPassword, checkPassword,planetCode);
        Assertions.assertTrue(result > 0);
    }
}