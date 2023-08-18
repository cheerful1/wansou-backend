//package com.yupi.usercenter.service.impl;
//import java.util.Date;
//
//import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
//import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
//import com.yupi.usercenter.model.domain.User;
//import com.yupi.usercenter.service.UserService;
//import com.yupi.usercenter.mapper.UserMapper;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.stereotype.Service;
//import org.springframework.util.DigestUtils;
//
//import javax.annotation.Resource;
//import javax.servlet.http.HttpServletRequest;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
///**
//* @author wsj1997
//* @description 针对表【user(用户表)】的数据库操作Service实现  用户操作实现类
//* @createDate 2023-02-23 20:44:27
//*/
//@Service
//@Slf4j
//public class UserServiceImpl extends ServiceImpl<UserMapper, User>
//    implements UserService{
//
//    @Resource
//    private  UserMapper userMapper;
//    //这里是有两个盐值为了避免自己写错，定义成常量，  这个一般打prsf就行
//    private static final  String SALT = "yupi";
//
//    /**
//     * 用户登录键 （可以理解成key，通过一个键找到唯一的一条数据 ）
//     */
////  setAttribute它是一个map，它可以存储键值对，我们就需要设置一个键值对，给用户的登录状态分配一个键
//            // 往setAttribute传入这个键，塞入user值
//    private static final String USER_LOGIN_STATE = "userLoginState";
//
//
//    // 这里要实现 这个类的话，邮件userRegister 然后选择 implement method ** 即实现类
//    @Override
//    public long userRegister(String userAccount, String userPassword, String checkPassword , String planetCode) {
//        // 这里编写业务逻辑
////        if (userAccount == null  || userPassword == null || checkPassword == null)
//        //1、校验
//        //直接使用这个工具来判断所有的变量是否为空
//        if (StringUtils.isAnyBlank(userAccount,userPassword,checkPassword, planetCode)) {
//            // TODO: 2023/6/2 修改自定义异常
//        return -1;
//        }
//        // 2、校验账户
//        if (userAccount.length() < 4) {
//            return -1;
//        }
//        if (userPassword.length() < 8 || checkPassword.length() < 8){
//            return -1;
//        }
//        if (planetCode.length() > 5) {
//            return -1;
//        }
//        // 账户不能包含特殊字符
//        String validPattern = "[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
//        // userAccount 需要校验的字符
//        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
//        if (matcher.find()) {
//            return -1;
//        }
//        // 密码和校验密码相同 不能使用等于 来判断两个字符串
//        if (!userPassword.equals(checkPassword)) {
//            return -1;
//        }
//
//        // 账户不能重复  这个放在判断 账户是否是存在特殊的字符，这样就省去了对资源的占用
//        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("userAccount", userAccount);
//        long count = userMapper.selectCount(queryWrapper);
//        if (count > 0) {
//            return -1;
//        }
//        // 星球编号不能重复
//        queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("planetCode", planetCode);
//        count = userMapper.selectCount(queryWrapper);
//        if (count > 0) {
//            return -1;
//        }
//
//        // 2.加密
//        final String SALT = "yupi";
//        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
//        // 3.插入数据
//        User user = new User();
//        user.setUserAccount(userAccount);
//        user.setUserPassword(encryptPassword);
//        user.setPlanetCode(planetCode);
//        boolean saveResult = this.save(user);
//        if (!saveResult) {
//            return -1;
//        }
//        return user.getId();
//    }
//
//    @Override
//    public User userLogin(String userAccount, String userPassword, HttpServletRequest request) {
//        //1、校验
//        //直接使用这个工具来判断所有的变量是否为空
//        if (StringUtils.isAnyBlank(userAccount,userPassword,userPassword)) {
//            return null;
//        }
//        // 2、校验账户
//        if (userAccount.length() < 4) {
//            return null;
//        }
//        if (userPassword.length() < 8 ){
//            return null;
//        }
//
//        // 账户不能包含特殊字符
//        String validPattern = "[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
//        // userAccount 需要校验的字符
//        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
//        if (matcher.find()) {
//            return null;
//        }
//        // 2.加密
//        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
//        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("userAccount", userAccount);
//        queryWrapper.eq("userPassword", encryptPassword);
//
//        //调用dao层的usermapper，检测数据库中的信息q
//        //查询用户信息
//        User user = userMapper.selectOne(queryWrapper);
//        //用户不存在
//        if (user == null) {
//            //用log记录一个信息！
//            log.info("user login failed, userAccount cannot match userpPssword");
//            return null;
//        }
//        //4、用户脱敏
//        // 先new一个user
//        // 防止数据库中的字段返回给前端
//        // 返回脱敏之后的用户信息
//        User safetyUser =getSafetyUser(user);
//
//        //3、记录用户的登陆态
//        // 如何使用session保存用户的信息？
//        // 使用getSession（）包里面的atribute（实际上是一个map）
//        // session中的属性是一个map 可以存储键值对
//
//        request.getSession().setAttribute(USER_LOGIN_STATE, safetyUser);
//
//
//        return safetyUser;
//    }
//
//    /**
//     * 用户脱敏
//     * @param originUser
//     * @return
//     */
//    @Override
//    public User getSafetyUser(User originUser){
//        if(originUser == null){
//            return null;
//        }
//
//        User safetyUser = new User();
//        safetyUser.setId(originUser.getId());
//        safetyUser.setUsername(originUser.getUsername());
//        safetyUser.setUserAccount(originUser.getUserAccount());
//        safetyUser.setAvatarUrl(originUser.getAvatarUrl());
//        safetyUser.setGender(originUser.getGender());
//        safetyUser.setEmail(originUser.getEmail());
//        safetyUser.setUserRole(originUser.getUserRole());
//        safetyUser.setUserStatus(0);
//        safetyUser.setPlanetCode(originUser.getPlanetCode());
//        safetyUser.setPhone(originUser.getPhone());
//        safetyUser.setUserStatus(originUser.getUserStatus());
//        safetyUser.setCreateTime(originUser.getCreateTime());
//        return safetyUser;
//    }
//
//    /**
//     * 用户注销
//     * @param request
//     */
//    @Override
//    public int userLogout(HttpServletRequest request) {
//        // 移除登陆态
//        request.getSession().removeAttribute(USER_LOGIN_STATE);
//        return 1;
//    }
//
//
//}
//
//
//
//
package com.yupi.usercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yupi.usercenter.common.ErrorCode;
import com.yupi.usercenter.exception.BusinessException;
import com.yupi.usercenter.model.domain.User;
import com.yupi.usercenter.service.UserService;
import com.yupi.usercenter.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.yupi.usercenter.constant.UserConstant.USER_LOGIN_STATE;

/**
 * 用户服务实现类
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

    @Resource
    private UserMapper userMapper;

    // https://www.code-nav.cn/

    /**
     * 盐值，混淆密码
     */
    private static final String SALT = "yupi";

    /**
     * 用户注册
     *
     * @param userAccount   用户账户
     * @param userPassword  用户密码
     * @param checkPassword 校验密码
     * @param planetCode    星球编号
     * @return 新用户 id
     */
    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword, String planetCode) {
        // 1. 校验
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword, planetCode)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        if (userAccount.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户账号过短");
        }
        if (userPassword.length() < 8 || checkPassword.length() < 8) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户密码过短");
        }
        if (planetCode.length() > 5) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "星球编号过长");
        }
        // 账户不能包含特殊字符
        String validPattern = "[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        if (matcher.find()) {
            return -1;
        }
        // 密码和校验密码相同
        if (!userPassword.equals(checkPassword)) {
            return -1;
        }
        // 账户不能重复
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        long count = userMapper.selectCount(queryWrapper);
        if (count > 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号重复");
        }
        // 星球编号不能重复
        queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("planetCode", planetCode);
        count = userMapper.selectCount(queryWrapper);
        if (count > 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "编号重复");
        }
        // 2. 加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        // 3. 插入数据
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(encryptPassword);
        user.setPlanetCode(planetCode);
        boolean saveResult = this.save(user);
        if (!saveResult) {
            return -1;
        }
        return user.getId();
    }

    // [加入星球](https://www.code-nav.cn/) 从 0 到 1 项目实战，经验拉满！10+ 原创项目手把手教程、7 日项目提升训练营、60+ 编程经验分享直播、1000+ 项目经验笔记

    /**
     * 用户登录
     *
     * @param userAccount  用户账户
     * @param userPassword 用户密码
     * @param request
     * @return 脱敏后的用户信息
     */
    @Override
    public User userLogin(String userAccount, String userPassword, HttpServletRequest request) {
        // 1. 校验
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            return null;
        }
        if (userAccount.length() < 4) {
            return null;
        }
        if (userPassword.length() < 8) {
            return null;
        }
        // 账户不能包含特殊字符
        String validPattern = "[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        if (matcher.find()) {
            return null;
        }
        // 2. 加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        // 查询用户是否存在
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        queryWrapper.eq("userPassword", encryptPassword);
        User user = userMapper.selectOne(queryWrapper);
        // 用户不存在
        if (user == null) {
            log.info("user login failed, userAccount cannot match userPassword");
            return null;
        }
        // 3. 用户脱敏
        User safetyUser = getSafetyUser(user);
        // 4. 记录用户的登录态
        request.getSession().setAttribute(USER_LOGIN_STATE, safetyUser);
        return safetyUser;
    }

    /**
     * 用户脱敏
     *
     * @param originUser
     * @return
     */
    @Override
    public User getSafetyUser(User originUser) {
        if (originUser == null) {
            return null;
        }
        User safetyUser = new User();
        safetyUser.setId(originUser.getId());
        safetyUser.setUsername(originUser.getUsername());
        safetyUser.setUserAccount(originUser.getUserAccount());
        safetyUser.setAvatarUrl(originUser.getAvatarUrl());
        safetyUser.setGender(originUser.getGender());
        safetyUser.setPhone(originUser.getPhone());
        safetyUser.setEmail(originUser.getEmail());
        safetyUser.setPlanetCode(originUser.getPlanetCode());
        safetyUser.setUserRole(originUser.getUserRole());
        safetyUser.setUserStatus(originUser.getUserStatus());
        safetyUser.setCreateTime(originUser.getCreateTime());
        return safetyUser;
    }

    /**
     * 用户注销
     *
     * @param request
     */
    @Override
    public int userLogout(HttpServletRequest request) {
        // 移除登录态
        request.getSession().removeAttribute(USER_LOGIN_STATE);
        return 1;
    }

    @Override
    public List<User> searchUserByTags(List<String> tagNameList){
        if(CollectionUtils.isEmpty(tagNameList)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        //1. 先查询所有用户
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        List<User> userList = userMapper.selectList(queryWrapper);
        Gson gson = new Gson();
        //2. 在内存中判断是否包含要求的标签
        return userList.stream().filter( user -> {
            String tagsStr = user.getTags();
            if(StringUtils.isBlank(tagsStr)){
                return false;
            }
            // tags json字符串换string 数组
            Set<String> tempTagNameSet = gson.fromJson(tagsStr, new TypeToken<Set<String>>() {}.getType());
            tempTagNameSet = Optional.ofNullable(tempTagNameSet).orElse(new HashSet<>());
            for (String tagName : tagNameList) {
                if(!tempTagNameSet.contains(tagName)){
                    return false;
                }
            }
            return true;
        }).map(this::getSafetyUser).collect(Collectors.toList());
    }

}
