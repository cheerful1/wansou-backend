package com.yupi.usercenter.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yupi.usercenter.model.domain.User;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author wsj1997
 * @description 针对表【user(用户表)】的数据库操作Service
 * @createDate 2023-02-23 20:44:27
 * 这里写  业务逻辑
 */
public interface UserService extends IService<User> {
    /**
     * @param userAccount   用户账户
     * @param userPassword  用户密码
     * @param checkPassword 校验密码
     * @Param planetCode  星球编号
     *                      如果使用数据库中的几个字段建议这么写
     * @return
     */
    long userRegister(String userAccount, String userPassword, String checkPassword, String planetCode);
    //用户登陆逻辑

    /**  打上这两个  快捷返回 参数信息
     *  功能：用户登陆
     * @param userAccount
     * @param userPassword
     * @return  脱敏之后的参数信息
     */
    // 方法上面按alt +enter  选择实现这个方法
    User userLogin(String userAccount, String userPassword, HttpServletRequest request);   //按下 alt + ctrl +L 键可以快捷格式化

    /**
     * 用户脱敏
     * @param originUser
     * @return
      */
    User getSafetyUser(User originUser);

    /**
     *
     * @param request
     * @return
     */
    int userLogout(HttpServletRequest request);

    /**
     * 通过用户标签来搜索用户
     * @param tagNameList
     * @return
     */
    List<User> searchUserByTags(List<String> tagNameList);
}
