package com.yu.springbootinit.model.vo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 用户视图对象（脱敏）
 *
 * 该类用于表示用户的部分信息，经过脱敏处理后的视图对象，包括用户的 id、昵称、头像、简介、角色和创建时间。
 */
@Data
public class UserVO implements Serializable {

    /**
     * 用户 id
     */
    private Long id;

    /**
     * 用户昵称
     */
    private String userName;

    /**
     * 用户头像
     */
    private String userAvatar;

    /**
     * 用户简介
     */
    private String userProfile;

    /**
     * 用户角色，可以是 "user"、"admin" 或 "ban"
     */
    private String userRole;

    /**
     * 用户创建时间
     */
    private Date createTime;

    /**
     * 序列化版本号
     */
    private static final long serialVersionUID = 1L;
}
