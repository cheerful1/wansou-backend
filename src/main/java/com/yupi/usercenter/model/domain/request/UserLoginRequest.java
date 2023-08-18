package com.yupi.usercenter.model.domain.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @author : wangshanjie
 * @date : 14:35 2023/6/2
 * 用户登录请求体
 * 用户登录只需要传账号和密码
 */
@Data
public class UserLoginRequest implements Serializable {
    private static final long serialVersionUID = 3191241716373120793L;
    private String userAccount;
    private String userPassword;

}
