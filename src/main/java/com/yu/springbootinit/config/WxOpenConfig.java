package com.yu.springbootinit.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.config.impl.WxMpDefaultConfigImpl;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 微信开放平台配置类
 *
 * 该配置类用于配置微信开放平台相关的参数，并提供获取微信公众号服务的方法。
 */
@Slf4j // Lombok 注解，自动生成日志对象
@Configuration // 声明该类是一个配置类
@ConfigurationProperties(prefix = "wx.open") // 从配置文件中读取以 "wx.open" 开头的配置信息
@Data // Lombok 注解，自动生成 getter、setter 等方法
public class WxOpenConfig {

    /**
     * 微信开放平台应用ID
     */
    private String appId;

    /**
     * 微信开放平台应用密钥
     */
    private String appSecret;

    /**
     * 微信公众号服务对象
     */
    private WxMpService wxMpService;

    /**
     * 获取微信公众号服务对象的方法
     *
     * @return 返回一个配置好的 WxMpService 对象，用于调用微信公众号相关接口
     */
    public WxMpService getWxMpService() {
        if (wxMpService != null) { // 如果已经创建了 wxMpService 对象，则直接返回
            return wxMpService;
        }
        synchronized (this) { // 使用同步锁确保线程安全
            if (wxMpService != null) { // 再次检查 wxMpService 对象是否已被其他线程创建
                return wxMpService;
            }
            // 创建 WxMpDefaultConfigImpl 对象，并设置应用ID和应用密钥
            WxMpDefaultConfigImpl config = new WxMpDefaultConfigImpl();
            config.setAppId(appId);
            config.setSecret(appSecret);
            // 创建 WxMpService 对象，并设置 WxMpConfigStorage 为上面创建的 config 对象
            WxMpService service = new WxMpServiceImpl();
            service.setWxMpConfigStorage(config);
            // 将创建好的 WxMpService 对象赋值给 wxMpService 属性
            wxMpService = service;
            return wxMpService; // 返回创建好的 WxMpService 对象
        }
    }
}