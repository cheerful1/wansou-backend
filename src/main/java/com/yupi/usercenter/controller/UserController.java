//package com.yupi.usercenter.controller;
//
///**
// * @author : wangshanjie
// * @date : 15:54 2023/5/31
// */
//
//import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
//import com.yupi.usercenter.common.BaseResponse;
//import com.yupi.usercenter.common.ResultUtils;
//import com.yupi.usercenter.model.domain.User;
//import com.yupi.usercenter.model.domain.request.UserRegisterRequest;
//import com.yupi.usercenter.service.UserService;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.web.bind.annotation.*;
//
//import javax.annotation.Resource;
//import javax.servlet.http.HttpServletRequest;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.Collectors;
//
//import static com.yupi.usercenter.constant.UserConstant.ADMIN_ROLE;
//import static com.yupi.usercenter.constant.UserConstant.USER_LOGIN_STATE;
//
///**
// * 打上@RestController注解 咱们这个类里面所有的请求的接口返回值，响应的数据类型都是application json
// * controller 层倾向于对请求参数本身的校验，不涉及业务逻辑本身（越少越好）
// * service 层是对业务逻辑的校验（有可能被 controller 之外的类调用）
// * @RestController 适用于编写 restful 风格的 api，返回值默认为 json 类型
// * 再加一个注解@RequestMapping，定义请求的路径，请求的路径就写个user就好了
// * @author wsj
// * 在UserController类内写两个请求，一个是用户注册，一个是用户登录
// */
//@RestController
//@RequestMapping("/user")
//public class UserController {
//    @Resource
//    private UserService userService;
//    private long id;
//
//    @PostMapping("/register")
//    //没打上的话，Springmvc框架不知道怎么把前端传来的json参数去合适的对象做一个关联
//    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
//        if (userRegisterRequest == null) {
//            return null;
//        }
//        String userAccount = userRegisterRequest.getUserAccount();
//        String userPassword = userRegisterRequest.getUserPassword();
//        String checkPassword = userRegisterRequest.getCheckPassword();
//        String planetCode = userRegisterRequest.getPlanetCode();
//        // 三者不能空，不进入业务逻辑层
//        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword,planetCode)) {
//            return null;
//        }
//        long result = userService.userRegister(userAccount, userPassword, checkPassword, planetCode);
//        return ResultUtils.success(result);
//    }
//    @PostMapping("/login")
//    //没打上的话，Springmvc框架不知道怎么把前端传来的json参数去合适的对象做一个关联
//    public BaseResponse<User> userLogin(@RequestBody UserRegisterRequest userRegisterRequest, HttpServletRequest request) {
//        if (userRegisterRequest == null) {
//            return null;
//        }
//        String userAccount = userRegisterRequest.getUserAccount();
//        String userPassword = userRegisterRequest.getUserPassword();
//
//        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
//            return null;
//        }
//        User user = userService.userLogin(userAccount, userPassword, request);
//        return ResultUtils.success(user);
//    }
//
//    @PostMapping("/logout")
//    //没打上的话，Springmvc框架不知道怎么把前端传来的json参数去合适的对象做一个关联
//    public BaseResponse<Integer> userLogout( HttpServletRequest request) {
//        if (request == null) {
//            return null;
//        }
//        int result = userService.userLogout(request);
//        return ResultUtils.success(result);
//    }
//
//
//
//    //在UserController中添加获取当前用户登录态、信息接口
//    @GetMapping("/current")
//    public BaseResponse<User> getCurrentUser(HttpServletRequest request){
//        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
//        User currentUser = (User) userObj;
//        if(currentUser ==null){
//            return null;
//        }
//        //这里不要直接返回当前用户的具体的信息，应为当前的用户的积分在更新，返回出去的可能是0分 前端和后端一定要注意交互的信息的跟新
//        long userId =currentUser.getId();
//        //todo 校验用户是否合法，如果封号了，要鉴别
//        User user = userService.getById(userId);
//        // 返回脱敏之后的user
//        User safetyUser =  userService.getSafetyUser(user);
//        return ResultUtils.success(safetyUser);
//
//    }
//
//
//
//    @GetMapping("/search")
//    public BaseResponse<List<User>> searchUsers(String username,HttpServletRequest request){
//        //鉴权 进管理员可以查询
//        if(!isAdmin(request)){
////            return new ArrayList<>();
//            return null;
//        }
//        // 从session中取用户的登陆态
//        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
//        if(StringUtils.isNoneBlank(username)){
//            //不能为空，根据条件来查询，传入的值是username
//            queryWrapper.like("username",username);
//        }
//        //首先转换成为一个数据流，然后将其中的密码值为空，最后再将值拼起来
//        List<User> userList = userService.list(queryWrapper);
//        List<User> list = userList.stream().map(user ->  userService.getSafetyUser(user)).collect(Collectors.toList());
//        return ResultUtils.success(list);
//    }
//
//    @PostMapping("/delete")
//    public BaseResponse<Boolean> deleteUser(@RequestBody long id, HttpServletRequest request){
//        if(!isAdmin(request)){
//            return null;
//        }
//        if(id<=0){
//            return null;
//        }
//        boolean b = userService.removeById(id);
//        return ResultUtils.success(b);
//    }
//
//    /**
//     * 是否是管理员
//     * @param request
//     * @return
//     */
//    private boolean isAdmin(HttpServletRequest request){
//        Object userobj = request.getSession().getAttribute(USER_LOGIN_STATE);
//        User user = (User) userobj;
//        return user != null && user.getUserRole() == ADMIN_ROLE;
//
//    }
//}
//
package com.yupi.usercenter.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.databind.ser.Serializers;
import com.yupi.usercenter.common.BaseResponse;
import com.yupi.usercenter.common.ErrorCode;
import com.yupi.usercenter.common.ResultUtils;
import com.yupi.usercenter.exception.BusinessException;
import com.yupi.usercenter.model.domain.User;
import com.yupi.usercenter.model.domain.request.UserLoginRequest;
import com.yupi.usercenter.model.domain.request.UserRegisterRequest;
import com.yupi.usercenter.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.yupi.usercenter.constant.UserConstant.ADMIN_ROLE;
import static com.yupi.usercenter.constant.UserConstant.USER_LOGIN_STATE;

/**
 * 用户接口
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 用户注册
     *
     * @param userRegisterRequest
     * @return
     */
    @PostMapping("/register")
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        // 校验
        if (userRegisterRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        String planetCode = userRegisterRequest.getPlanetCode();
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword, planetCode)) {
            return null;
        }
        long result = userService.userRegister(userAccount, userPassword, checkPassword, planetCode);
        return ResultUtils.success(result);
    }

    /**
     * 用户登录
     *
     * @param userLoginRequest
     * @param request
     * @return
     */
    @PostMapping("/login")
    public BaseResponse<User> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        if (userLoginRequest == null) {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
        }
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.userLogin(userAccount, userPassword, request);
        return ResultUtils.success(user);
    }

    /**
     * 用户注销
     *
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public BaseResponse<Integer> userLogout(HttpServletRequest request) {
        if (request == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        int result = userService.userLogout(request);
        return ResultUtils.success(result);
    }

    /**
     * 获取当前用户
     *
     * @param request
     * @return
     */
    @GetMapping("/current")
    public BaseResponse<User> getCurrentUser(HttpServletRequest request) {
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User currentUser = (User) userObj;
        if (currentUser == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN);
        }
        long userId = currentUser.getId();
        // TODO 校验用户是否合法
        User user = userService.getById(userId);
        User safetyUser = userService.getSafetyUser(user);
        return ResultUtils.success(safetyUser);
    }

    // https://yupi.icu/

    @GetMapping("/search")
    public BaseResponse<List<User>> searchUsers(String username, HttpServletRequest request) {
        if (!isAdmin(request)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(username)) {
            queryWrapper.like("username", username);
        }
        List<User> userList = userService.list(queryWrapper);
        List<User> list = userList.stream().map(user -> userService.getSafetyUser(user)).collect(Collectors.toList());
        return ResultUtils.success(list);
    }

    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteUser(@RequestBody long id, HttpServletRequest request) {
        if (!isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean b = userService.removeById(id);
        return ResultUtils.success(b);
    }

    // [鱼皮的学习圈](https://yupi.icu) 从 0 到 1 求职指导，斩获 offer！1 对 1 简历优化服务、2000+ 求职面试经验分享、200+ 真实简历和建议参考、25w 字前后端精选面试题

    /**
     * 是否为管理员
     *
     * @param request
     * @return
     */
    private boolean isAdmin(HttpServletRequest request) {
        // 仅管理员可查询
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User user = (User) userObj;
        return user != null && user.getUserRole() == ADMIN_ROLE;
    }

}
