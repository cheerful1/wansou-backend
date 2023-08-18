package com.yupi.usercenter.model.domain.request;

/**
 * @author : wangshanjie
 * @date : 16:02 2023/5/31
 */

import lombok.Data;

import java.io.Serializable;

/**
 * 这里我们先封装一个对象，专门用来接收请求参数，在model.domain包下创建一个request包
 * 如果说要使用json格式的参数的话，我们最好封装一个对象来记录所有的请求参数，然后我们在request包下新增一个对象叫UserRegisterRequest
 * 写上注释，然后这里可以继承 Serializable 就是序列化
 * @author wsj
 * 打上lombok的注解，让它生成get、set方法
 */
@Data
public class UserRegisterRequest implements Serializable {
    //实现序列化接口，右键选择Generate ->serialVersionUID，生成序列化ID，不过我发现点击Generate 并没有出现serialVersionUID
    //光标放在UserRegisterRequest，按下快捷键alt+enter，选择add'serialVersionUID'fileld，或者点击黄色灯泡也行
    //定义一下前端需要接收的请求、前端需要传入的参数
    //把刚刚在UserController.java自动生成的参数复制一下，粘贴过来
    private static final long serialVersionUID = 3191241716373120793L;
    private String userAccount;
    private String userPassword;
    private String checkPassword;
    private String planetCode;
}
