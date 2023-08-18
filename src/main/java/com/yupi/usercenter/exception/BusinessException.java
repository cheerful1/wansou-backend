package com.yupi.usercenter.exception;

// [鱼皮的知识星球](https://t.zsxq.com/0emozsIJh) 从 0 到 1 求职指导，斩获 offer！1 对 1 简历优化服务、200+ 真实简历和建议参考、2000+ 求职面试经验分享、25w 字前后端精选面试题

import com.yupi.usercenter.common.ErrorCode;

/**
 * 自定义异常类
 * 给原本的父类扩充了两个字段
 */
public class BusinessException extends RuntimeException {

    /**
     * 异常码
     * 表示异常的错误码，是一个int类型的私有变量。
     */
    private final int code;

    /**
     * 描述
     * 表示异常的描述信息，是一个String类型的私有变量。
     */
    private final String description;

    /**
     * 构造方法
     * BusinessException(String message, int code, String description)：接受异常消息、错误码和描述作为参数，并调用父类的构造方法super(message)来设置异常消息。
     * @param message
     * @param code
     * @param description
     */
    public BusinessException(String message, int code, String description) {
        super(message);
        this.code = code;
        this.description = description;
    }

    /**
     * 构造方法重载：
     *
     * BusinessException(ErrorCode errorCode)：接受ErrorCode枚举类型的参数，并根据枚举中定义的错误码和描述信息来初始化异常对象。
     * BusinessException(ErrorCode errorCode, String description)：接受ErrorCode枚举类型和额外的描述信息作为参数，并根据枚举中定义的错误码和传入的描述信息来初始化异常对象。
     * @param errorCode
     */
    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.description = errorCode.getDescription();
    }

    public BusinessException(ErrorCode errorCode, String description) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.description = description;
    }
    //用于获取异常的错误码，返回值为int类型。
    public int getCode() {
        return code;
    }


    //用于获取异常的描述信息，返回值为String类型。
    public String getDescription() {
        return description;
    }
}
