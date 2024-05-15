package com.yu.springbootinit.config;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.region.Region;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 腾讯云对象存储客户端配置类
 *
 * 该配置类用于创建腾讯云对象存储（COS）客户端，以便在应用中使用 COS 服务。
 */
@Configuration // 声明该类是一个配置类
@ConfigurationProperties(prefix = "cos.client") // 从配置文件中读取以 "cos.client" 开头的配置信息
@Data // Lombok 注解，自动生成 getter、setter 等方法
public class CosClientConfig {

    /**
     * COS 访问密钥
     */
    private String accessKey;

    /**
     * COS 密钥
     */
    private String secretKey;

    /**
     * COS 存储桶所在的区域
     */
    private String region;

    /**
     * COS 存储桶名称
     */
    private String bucket;

    /**
     * 创建 COS 客户端 Bean
     *
     * @return 返回一个配置好的 COSClient 对象，用于操作 COS 服务
     */
    @Bean // 声明该方法返回一个 Bean 对象，Spring 将会管理该对象的生命周期
    public COSClient cosClient() {
        // 初始化 COS 访问凭证
        COSCredentials cred = new BasicCOSCredentials(accessKey, secretKey);
        // 设置 COS 客户端配置
        ClientConfig clientConfig = new ClientConfig(new Region(region));
        // 创建 COS 客户端
        return new COSClient(cred, clientConfig);
    }
}