package com.yu.springbootinit.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yu.springbootinit.annotation.AuthCheck;
import com.yu.springbootinit.common.BaseResponse;
import com.yu.springbootinit.common.DeleteRequest;
import com.yu.springbootinit.common.ErrorCode;
import com.yu.springbootinit.common.ResultUtils;
import com.yu.springbootinit.config.WxOpenConfig;
import com.yu.springbootinit.constant.UserConstant;
import com.yu.springbootinit.exception.BusinessException;
import com.yu.springbootinit.exception.ThrowUtils;
import com.yu.springbootinit.model.dto.user.UserAddRequest;
import com.yu.springbootinit.model.dto.user.UserLoginRequest;
import com.yu.springbootinit.model.dto.user.UserQueryRequest;
import com.yu.springbootinit.model.dto.user.UserRegisterRequest;
import com.yu.springbootinit.model.dto.user.UserUpdateMyRequest;
import com.yu.springbootinit.model.dto.user.UserUpdateRequest;
import com.yu.springbootinit.model.entity.User;
import com.yu.springbootinit.model.vo.LoginUserVO;
import com.yu.springbootinit.model.vo.UserVO;
import com.yu.springbootinit.service.UserService;

import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.bean.WxOAuth2UserInfo;
import me.chanjar.weixin.common.bean.oauth2.WxOAuth2AccessToken;
import me.chanjar.weixin.mp.api.WxMpService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.yu.springbootinit.service.impl.UserServiceImpl.SALT;


/**
 * 用户控制器，处理用户相关请求
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Resource
    private UserService userService;

    @Resource
    private WxOpenConfig wxOpenConfig;

    // 登录相关接口

    /**
     * 用户注册接口
     * 接收用户注册请求，验证参数，调用 userService.userRegister 方法进行用户注册，并返回注册结果
     *
     * @param userRegisterRequest 用户注册请求体
     * @return 注册结果，返回用户的唯一标识
     */
    @PostMapping("/register")
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        // 如果请求体为空，则抛出参数错误异常
        if (userRegisterRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 获取用户账号、密码和确认密码
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        // 如果用户账号、密码或确认密码为空，则返回空值
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
            return null;
        }
        // 调用 userService.userRegister 方法进行用户注册，并获取注册结果
        long result = userService.userRegister(userAccount, userPassword, checkPassword);
        // 返回注册结果
        return ResultUtils.success(result);
    }

    /**
     * 用户登录接口
     * 接收用户登录请求，验证参数，调用 userService.userLogin 进行登录操作，并返回登录结果
     *
     * @param userLoginRequest 用户登录请求体
     * @param request          HTTP 请求对象
     * @return 登录结果，返回登录用户的信息
     */
    @PostMapping("/login")
    public BaseResponse<LoginUserVO> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        // 如果请求体为空，则抛出参数错误异常
        if (userLoginRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 获取用户账号和密码
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        // 如果用户账号或密码为空，则抛出参数错误异常
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 调用 userService.userLogin 方法进行用户登录，并获取登录结果
        LoginUserVO loginUserVO = userService.userLogin(userAccount, userPassword, request);
        // 返回登录结果
        return ResultUtils.success(loginUserVO);
    }


    /**
     * 用户登录（微信开放平台）接口
     * 接收微信登录请求，获取用户信息，调用 userService.userLoginByMpOpen 进行登录操作，并返回登录结果
     *
     * @param request  HTTP 请求对象
     * @param response HTTP 响应对象
     * @param code     微信授权码
     * @return 登录结果，返回登录用户的信息
     */
    @GetMapping("/login/wx_open")
    public BaseResponse<LoginUserVO> userLoginByWxOpen(HttpServletRequest request, HttpServletResponse response,
                                                       @RequestParam("code") String code) {
        // 定义变量保存微信授权令牌
        WxOAuth2AccessToken accessToken;
        try {
            // 获取微信服务对象
            WxMpService wxService = wxOpenConfig.getWxMpService();
            // 获取微信授权令牌
            accessToken = wxService.getOAuth2Service().getAccessToken(code);
            // 获取用户信息
            WxOAuth2UserInfo userInfo = wxService.getOAuth2Service().getUserInfo(accessToken, code);
            // 获取用户 unionId 和公众号 openId
            String unionId = userInfo.getUnionId();
            String mpOpenId = userInfo.getOpenid();
            // 如果 unionId 或 openId 为空，则抛出系统错误异常
            if (StringUtils.isAnyBlank(unionId, mpOpenId)) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "登录失败，系统错误");
            }
            // 调用 userService.userLoginByMpOpen 方法进行微信登录，并返回登录结果
            return ResultUtils.success(userService.userLoginByMpOpen(userInfo, request));
        } catch (Exception e) {
            // 捕获异常并记录日志
            log.error("userLoginByWxOpen error", e);
            // 抛出系统错误异常
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "登录失败，系统错误");
        }
    }

    /**
     * 用户注销接口
     * 接收用户注销请求，调用 userService.userLogout 进行注销操作，并返回注销结果
     *
     * @param request HTTP 请求对象
     * @return 注销结果，返回 true 表示注销成功，false 表示注销失败
     */
    @PostMapping("/logout")
    public BaseResponse<Boolean> userLogout(HttpServletRequest request) {
        // 如果请求对象为空，则抛出参数错误异常
        if (request == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 调用 userService.userLogout 方法进行用户注销，并返回注销结果
        boolean result = userService.userLogout(request);
        return ResultUtils.success(result);
    }


    /**
     * 获取当前登录用户接口
     * 获取当前登录用户信息，返回当前登录用户的基本信息
     *
     * @param request HTTP 请求对象
     * @return 当前登录用户的基本信息
     */
    @GetMapping("/get/login")
    public BaseResponse<LoginUserVO> getLoginUser(HttpServletRequest request) {
        // 调用 userService.getLoginUser 方法获取当前登录用户信息
        User user = userService.getLoginUser(request);
        // 将获取到的用户信息转换为 LoginUserVO 对象，并封装为 BaseResponse 返回
        return ResultUtils.success(userService.getLoginUserVO(user));
    }


    // 用户的增删改查接口

    /**
     * 创建用户接口
     * 接收管理员创建用户请求，验证参数，调用 userService.save 创建用户，并返回创建结果
     *
     * @param userAddRequest 包含新用户信息的请求对象
     * @param request         HTTP 请求对象
     * @return 创建用户操作的结果，包含新用户的 ID
     */
    @PostMapping("/add")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Long> addUser(@RequestBody UserAddRequest userAddRequest, HttpServletRequest request) {
        // 检查请求体是否为空
        if (userAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 创建 User 对象并将请求对象中的属性复制到 User 对象中
        User user = new User();
        BeanUtils.copyProperties(userAddRequest, user);
        // 设置默认密码为 12345678，并对密码进行加密
        String defaultPassword = "12345678";
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + defaultPassword).getBytes());
        user.setUserPassword(encryptPassword);
        // 调用 userService.save 方法保存用户信息
        boolean result = userService.save(user);
        // 抛出业务异常，如果保存失败
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        // 返回创建用户的 ID
        return ResultUtils.success(user.getId());
    }

    /**
     * 删除用户接口
     * 接收管理员删除用户请求，验证参数，调用 userService.removeById 删除用户，并返回删除结果
     *
     * @param deleteRequest 包含待删除用户 ID 的请求对象
     * @param request       HTTP 请求对象
     * @return 删除用户操作的结果，布尔值表示删除成功与否
     */
    @PostMapping("/delete")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> deleteUser(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        // 检查请求体是否为空，以及待删除用户 ID 是否合法
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 调用 userService.removeById 方法删除用户
        boolean b = userService.removeById(deleteRequest.getId());
        // 返回删除用户的操作结果
        return ResultUtils.success(b);
    }

    /**
     * 更新用户接口
     * 接收管理员更新用户请求，验证参数，调用 userService.updateById 更新用户信息，并返回更新结果
     *
     * @param userUpdateRequest 包含待更新用户信息的请求对象
     * @param request           HTTP 请求对象
     * @return 更新用户操作的结果，布尔值表示更新成功与否
     */
    @PostMapping("/update")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> updateUser(@RequestBody UserUpdateRequest userUpdateRequest,
                                            HttpServletRequest request) {
        // 检查请求体是否为空，以及待更新用户 ID 是否为空
        if (userUpdateRequest == null || userUpdateRequest.getId() == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 将请求对象转换为 User 实体对象
        User user = new User();
        BeanUtils.copyProperties(userUpdateRequest, user);
        // 调用 userService.updateById 方法更新用户信息
        boolean result = userService.updateById(user);
        // 如果更新操作失败，抛出业务异常
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        // 返回更新用户的操作结果
        return ResultUtils.success(true);
    }

    /**
     * 根据 id 获取用户接口（仅管理员）
     * 根据用户 id 获取用户信息，并返回用户信息
     *
     * @param id      待查询用户的 ID
     * @param request HTTP 请求对象
     * @return 包含用户信息的响应对象
     */
    @GetMapping("/get")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<User> getUserById(long id, HttpServletRequest request) {
        // 检查 ID 是否合法
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 调用 userService.getById 方法获取用户信息
        User user = userService.getById(id);
        // 如果未找到用户，抛出业务异常
        ThrowUtils.throwIf(user == null, ErrorCode.NOT_FOUND_ERROR);
        // 返回包含用户信息的成功响应对象
        return ResultUtils.success(user);
    }


    /**
     * 根据 id 获取用户视图对象接口
     * 根据用户 id 获取用户信息并封装为视图对象，返回用户视图对象
     *
     * @param id      待查询用户的 ID
     * @param request HTTP 请求对象
     * @return 包含用户视图对象的响应对象
     */
    @GetMapping("/get/vo")
    public BaseResponse<UserVO> getUserVOById(long id, HttpServletRequest request) {
        // 调用 getUserById 方法获取用户信息的响应对象
        BaseResponse<User> response = getUserById(id, request);
        // 从响应对象中获取用户信息
        User user = response.getData();
        // 调用 userService.getUserVO 方法将用户信息转换为用户视图对象
        return ResultUtils.success(userService.getUserVO(user));
    }


    /**
     * 分页获取用户列表接口（仅管理员）
     * 接收分页查询用户列表请求，验证参数，调用 userService.page 方法分页查询用户列表，并返回用户分页数据
     *
     * @param userQueryRequest 查询用户列表的请求对象
     * @param request          HTTP 请求对象
     * @return 包含用户分页数据的响应对象
     */
    @PostMapping("/list/page")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Page<User>> listUserByPage(@RequestBody UserQueryRequest userQueryRequest,
                                                   HttpServletRequest request) {
        // 获取当前页号和页面大小
        long current = userQueryRequest.getCurrent();
        long size = userQueryRequest.getPageSize();
        // 调用 userService.page 方法分页查询用户列表
        Page<User> userPage = userService.page(new Page<>(current, size),
                userService.getQueryWrapper(userQueryRequest));
        // 返回用户分页数据的响应对象
        return ResultUtils.success(userPage);
    }

    /**
     * 分页获取用户封装列表接口
     * 接收分页查询用户列表请求，验证参数，调用 userService.page 方法分页查询用户列表，并将查询结果封装为用户视图对象列表，返回用户视图对象分页数据
     *
     * @param userQueryRequest 查询用户列表的请求对象
     * @param request          HTTP 请求对象
     * @return 包含用户视图对象分页数据的响应对象
     */
    @PostMapping("/list/page/vo")
    public BaseResponse<Page<UserVO>> listUserVOByPage(@RequestBody UserQueryRequest userQueryRequest,
                                                       HttpServletRequest request) {
        // 如果查询请求对象为空，抛出参数错误异常
        if (userQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 获取当前页号和页面大小
        long current = userQueryRequest.getCurrent();
        long size = userQueryRequest.getPageSize();
        // 限制爬虫，若页面大小超过20，抛出参数错误异常
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        // 调用 userService.page 方法分页查询用户列表
        Page<User> userPage = userService.page(new Page<>(current, size),
                userService.getQueryWrapper(userQueryRequest));
        // 创建一个用户视图对象分页对象，将查询结果封装为用户视图对象列表
        Page<UserVO> userVOPage = new Page<>(current, size, userPage.getTotal());
        List<UserVO> userVO = userService.getUserVO(userPage.getRecords());
        userVOPage.setRecords(userVO);
        // 返回用户视图对象分页数据的响应对象
        return ResultUtils.success(userVOPage);
    }

    /**
     * 更新个人信息接口
     * 接收用户更新个人信息请求，验证参数，从请求中获取当前登录用户信息，调用 userService.updateById 方法更新用户信息，并返回更新结果
     *
     * @param userUpdateMyRequest 用户更新个人信息的请求对象
     * @param request              HTTP 请求对象
     * @return 包含更新结果的响应对象
     */
    @PostMapping("/update/my")//表示映射到"/update/my"路径的POST请求。告诉Spring收到一个HTTP POST请求时，调用名为updateMyUser的方法来处理该请求。
    public BaseResponse<Boolean> updateMyUser(@RequestBody UserUpdateMyRequest userUpdateMyRequest,//这是一个UserUpdateMyRequest类型的请求体，通常用于包含用户更新个人信息的数据这个请求体通过@RequestBody注解从HTTP请求中提取，然后作为参数传递给方法。
                                              HttpServletRequest request) {//request是一个HttpServletRequest类型的对象，它用于获取当前HTTP请求的信息，比如请求头、请求参数等。
        // 如果更新个人信息请求对象为空，抛出参数错误异常
        if (userUpdateMyRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 从请求中获取当前登录用户信息
        User loginUser = userService.getLoginUser(request);
        // 创建一个新的用户对象，并将请求对象的属性复制到新的用户对象中
        User user = new User();
        BeanUtils.copyProperties(userUpdateMyRequest, user);
        // 设置用户 id 为当前登录用户的 id
        user.setId(loginUser.getId());
        // 调用 userService.updateById 方法更新用户信息
        boolean result = userService.updateById(user);
        // 如果更新操作失败，则抛出操作失败异常
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        // 返回更新结果的响应对象
        return ResultUtils.success(true);
    }
}