package com.yu.springbootinit.common;

import java.io.Serializable;
import lombok.Data;

/**
 * 删除请求类
 *
 * 该类用于表示删除操作的请求对象，包含待删除数据的唯一标识符。
 */
@Data // Lombok 注解，自动生成 getter、setter 等方法
public class DeleteRequest implements Serializable {

    /**
     * 待删除数据的唯一标识符
     */
    private Long id;

    /**
     * 序列化版本号
     */
    private static final long serialVersionUID = 1L;
}