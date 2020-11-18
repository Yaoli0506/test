package com.ksrcb.api.common.exception;

import lombok.Getter;

/**
 *
 * 业务错误码：返回结果的状态码
 *
 * 如果想要代码更具维护性一点,可以定义不同种类的错误码,都实现 BaseCodeInterface
 * @Author zhangyukang
 * @Date 2020/3/1 14:51
 * @Version 1.0
 **/
@Getter
public enum ErrorCodeEnum implements BaseCodeInterface{
    // 数据操作错误定义

    //用户相关：10000**
    USER_ACCOUNT_NOT_FOUND(10001, "账号不存在!");
    /** 错误码 */
    private int resultCode;

    /** 错误描述 */
    private String resultMsg;

    ErrorCodeEnum(int resultCode, String resultMsg) {
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
    }

}
