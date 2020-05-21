package com.jxyzh11.myframework.constant;

/**
 * 错误码接口
 *
 * @author JXYZH11
 * @version 1.0
 * @description TODO
 * @date 2020/1/18 10:20
 */
public interface ErrorCode {

    /**
     * 获取ErrorCode名称
     *
     * @param
     * @return 返回枚举名称
     * @author JXYZH11
     * @version 1.0
     * @description TODO
     * @date 2020/1/18 10:20
     */
    String getName();

    /**
     * 获取错误code
     *
     * @param
     * @return 错误code
     * @author JXYZH11
     * @version 1.0
     * @description TODO
     * @date 2020/1/18 10:21
     */
    String getCode();

    /**
     * 获取错误信息
     *
     * @param
     * @return 错误信息
     * @author JXYZH11
     * @version 1.0
     * @description TODO
     * @date 2020/1/18 10:21
     */
    String getMsg();
}
