package com.yu.springbootinit.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yu.springbootinit.model.dto.user.UserQueryRequest;
import com.yu.springbootinit.model.entity.User;
import com.yu.springbootinit.model.vo.LoginUserVO;
import com.yu.springbootinit.model.vo.UserVO;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import me.chanjar.weixin.common.bean.WxOAuth2UserInfo;

/**
 * 用户服务接口
 * 提供用户相关的服务接口定义
 */
public interface UserService extends IService<User> {
/**
 * IService是一个泛型接口，它定义了一组对实体进行CRUD（创建、读取、更新、删除）操作的方法。
 * <User>表示IService接口中的方法将以User对象为操作对象。
 * 因此，UserService接口继承了IService接口后，就可以使用IService中定义的方法对User实体进行操作，而不需要重新定义这些方法。
 */


    /**
     * 用户注册
     *
     * @param userAccount   用户账户
     * @param userPassword  用户密码
     * @param checkPassword 校验密码
     * @return 新用户 id
     */
    long userRegister(String userAccount, String userPassword, String checkPassword);

    /**
     * 用户登录
     *
     * @param userAccount  用户账户
     * @param userPassword 用户密码
     * @param request      HTTP请求对象
     * @return 脱敏后的用户信息
     */
    LoginUserVO userLogin(String userAccount, String userPassword, HttpServletRequest request);

    /**
     * 用户登录（微信开放平台）
     *
     * @param wxOAuth2UserInfo 从微信获取的用户信息
     * @param request          HTTP请求对象
     * @return 脱敏后的用户信息
     */
    LoginUserVO userLoginByMpOpen(WxOAuth2UserInfo wxOAuth2UserInfo, HttpServletRequest request);

    /**
     * 获取当前登录用户
     *
     * @param request HTTP请求对象
     * @return 当前登录用户对象
     */
    User getLoginUser(HttpServletRequest request);

    /**
     * 获取当前登录用户（允许未登录）
     *
     * @param request HTTP请求对象
     * @return 当前登录用户对象，若未登录则返回null
     */
    User getLoginUserPermitNull(HttpServletRequest request);

    /**
     * 判断当前用户是否为管理员
     *
     * @param request HTTP请求对象
     * @return 如果当前用户是管理员则返回true，否则返回false
     */
    boolean isAdmin(HttpServletRequest request);

    /**
     * 判断指定用户是否为管理员
     *
     * @param user 用户对象
     * @return 如果指定用户是管理员则返回true，否则返回false
     */
    boolean isAdmin(User user);

    /**
     * 用户注销
     *
     * @param request HTTP请求对象
     * @return 如果注销成功则返回true，否则返回false
     */
    boolean userLogout(HttpServletRequest request);

    /**
     * 获取脱敏的已登录用户信息
     *
     * @return 脱敏后的已登录用户信息对象
     */
    LoginUserVO getLoginUserVO(User user);

    /**
     * 获取脱敏的用户信息
     *
     * @param user 用户对象
     * @return 脱敏后的用户信息对象
     */
    UserVO getUserVO(User user);

    /**
     * 获取脱敏的用户信息列表
     *
     * @param userList 用户对象列表
     * @return 脱敏后的用户信息列表
     */
    List<UserVO> getUserVO(List<User> userList);

    /**
     * 获取查询条件
     *
     * @param userQueryRequest 用户查询请求对象
     * @return 查询条件对象
     */
    QueryWrapper<User> getQueryWrapper(UserQueryRequest userQueryRequest);

}