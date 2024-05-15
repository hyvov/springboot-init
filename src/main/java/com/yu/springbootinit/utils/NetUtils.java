package com.yu.springbootinit.utils;

import java.net.InetAddress;
import javax.servlet.http.HttpServletRequest;
/**
 * 网络工具类，提供获取客户端 IP 地址的方法
 */
public class NetUtils {

    /**
     * 获取客户端 IP 地址
     *
     * @param request HTTP 请求对象
     * @return 客户端 IP 地址
     */
    public static String getIpAddress(HttpServletRequest request) {
        // 尝试从请求头中获取客户端 IP
        String ip = request.getHeader("x-forwarded-for");
        // 如果请求头中获取不到，则尝试其他常见的获取方式
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        // 如果还是获取不到，则从请求对象中获取
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            // 如果获取到的是本地回环地址，则尝试获取本机配置的 IP 地址
            if (ip.equals("127.0.0.1")) {
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (inet != null) {
                    ip = inet.getHostAddress();
                }
            }
        }
        // 如果获取到的 IP 地址包含多个地址，取第一个地址作为真实 IP 地址
        if (ip != null && ip.length() > 15) {
            if (ip.indexOf(",") > 0) {
                ip = ip.substring(0, ip.indexOf(","));
            }
        }
        // 如果仍然获取不到有效的 IP 地址，则返回本地回环地址
        if (ip == null) {
            return "127.0.0.1";
        }
        return ip;
    }

}