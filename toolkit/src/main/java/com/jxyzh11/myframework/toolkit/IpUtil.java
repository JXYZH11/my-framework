package com.jxyzh11.myframework.toolkit;

import javax.servlet.http.HttpServletRequest;

/**
 * 网络工具类
 */
public class IpUtil {

    /**
     * 获取用户真实IP地址，request.getRemoteAddr()代理
     * 多个代理时，X-Forwarded-For的值并不止一个
     *
     * @return ip
     */
    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (!isIpEmpty(ip)) {
            if (ip.contains(",")) {
                ip = ip.split(",")[0];
            }
        }
        if (isIpEmpty(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (isIpEmpty(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (isIpEmpty(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (isIpEmpty(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (isIpEmpty(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (isIpEmpty(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    private static boolean isIpEmpty(String ip) {
        return ip == null || ip.length() == 0 || "unknown".equals(ip);
    }
}
