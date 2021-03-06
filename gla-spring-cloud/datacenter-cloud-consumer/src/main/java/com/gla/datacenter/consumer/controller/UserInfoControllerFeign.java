package com.gla.datacenter.consumer.controller;

import com.gla.datacenter.consumer.utils.VerifyCodeUtils;
import com.gla.datacenter.core.constant.ConstantGla;
import com.gla.datacenter.domain.UserInfo;
import com.gla.datacenter.service.PlugCacheClientService;
import com.gla.datacenter.service.UserInfoClientService;
import com.limp.framework.core.annotation.Access;
import com.limp.framework.core.constant.OPERATION;
import com.limp.framework.utils.TextUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 用户
 */
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@Api(value = "用户管理",description = "生产者")
public class UserInfoControllerFeign {
    private Logger LOG = LoggerFactory.getLogger(UserInfoControllerFeign.class);

    /**
     * 用户管理客户端Service
     */
    @Autowired
    private UserInfoClientService userInfoClientService;
    /**
     * 调用缓存服务
     */
    @Autowired
    private PlugCacheClientService plugCacheClientService;

    /**
     * 功能描述: 新增用户
     * @param:
     * @param userInfo 用户实体
     * @return: com.gla.datacenter.domain.UserInfo
     * @auther: zhengshien
     * @date: 2018/11/16 15:18
     */
    @ApiOperation(value="新增用户", notes="根据userInfo对象新增用户")
    @ApiImplicitParam(name = "user", value = "消息详细实体user", required = true, dataType = "UserInfo")
    @RequestMapping(value = "/user", method = RequestMethod.POST)
    @Access(login = true)
    public String saveUserInfo(@RequestBody UserInfo userInfo) {
        LOG.debug(TextUtils.format("/***新增用户**/"));
        return this.userInfoClientService.saveUserInfo(userInfo);
    }

    /**
     * 功能描述: 根据id删除用户（可多选）
     * @param:
     * @param id 用户id
     * @return: boolean
     * @auther: zhengshien
     * @date: 2018/11/16 15:15
     */
    @ApiOperation(value="删除用户", notes="根据id删除用户（批量）")
    @ApiImplicitParam(name = "id", value = "用户id", required = true, dataType = "String")
    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
    @Access(login = true)
    public String delUserInfo(@PathVariable("id") String id) {
        LOG.debug(TextUtils.format("/***删除用户**/"));
        return this.userInfoClientService.delUserInfo(id);
    }

    /**
     * 功能描述: 修改密码
     * @param:
     * @param userInfo
     * @return: java.lang.String
     * @auther: zhengshien
     * @date: 2019/1/22 9:56
     */
    @ApiOperation(value="修改密码", notes="修改密码")
    @ApiImplicitParam(name = "userInfo", value = "用户实体", required = true, dataType = "UserInfo")
    @RequestMapping(value = "/user/password/open", method = RequestMethod.PUT)
    public String updatePassword(@RequestBody UserInfo userInfo) {
        LOG.debug(TextUtils.format("/***用户修改密码{0}**/",userInfo));
        return userInfoClientService.updatePassword(userInfo);
    }

    /**
     * 功能描述: 修改用户信息
     * @param:
     * @param userInfo 用户实体
     * @return: boolean
     * @auther: zhengshien
     * @date: 2018/11/16 15:16
     */
    @ApiOperation(value="更新用户信息", notes="根据条件修改用户信息")
    @ApiImplicitParam(name = "userInfo", value = "消息详细实体user", required = true, dataType = "UserInfo")
    @RequestMapping(value = "/user", method = RequestMethod.PUT)
//    @Access(login = true)
    public String updateUserInfo(@RequestBody UserInfo userInfo) {
        LOG.debug(TextUtils.format("/***更新用户信息**/"));
        return this.userInfoClientService.updateUserInfo(userInfo);
    }

    /**
     * 功能描述: 启用/禁用 用户
     * @param:
     * @param  userInfo 用户实体
     * @return: boolean
     * @auther: zhengshien
     * @date: 2018/11/16 15:15
     */
    @ApiOperation(value="更新用户状态", notes="启用、禁用用户")
    @RequestMapping(value = "/user/state", method = RequestMethod.PUT)
    @Access(login = true)
    public String updateUserInfoState(@RequestBody UserInfo userInfo) {
        LOG.debug(TextUtils.format("/***更新用户状态(启用、禁用用户)**/"));
        return this.userInfoClientService.updateUserInfoState(userInfo);
    }

    /**
     * 功能描述: 根据id查询用户信息
     * @param:
     * @param id 用户id
     * @return: com.gla.datacenter.domain.UserInfo
     * @auther: zhengshien
     * @date: 2018/11/16 15:17
     */
    @ApiOperation(value="根据用户id获取用户信息", notes="根据用户id获取用户信息")
    @ApiImplicitParam(name = "id", value = "用户id", required = true, dataType = "String")
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    @Access(login = true)
    public String selectUserInfo(@PathVariable("id") String id) {
        LOG.debug(TextUtils.format("/***获取用户信息根据用户id**/", id));
        return this.userInfoClientService.selectUserInfo(id);
    }

    /**
     * 功能描述: 查询用户列表（不分页）
     * @param:
     * @param userInfo 用户实体（携带查询条件）
     * @return: java.util.List<com.gla.datacenter.domain.UserInfo>
     * @auther: zhengshien
     * @date: 2018/11/16 15:14
     */
    @ApiOperation(value="查询用户列表（不分页）", notes="查询用户列表（不分页）")
    @ApiImplicitParam(name = "userInfo", value = "用户信息", required = true, dataType = "UserInfo")
    @RequestMapping(value = "/user/open",method= RequestMethod.GET)
//    @Access(login = true)
    public String getUserByAccount(UserInfo userInfo) {
        LOG.debug(TextUtils.format("/***根据用户account{0}，获取用户基本信息**/", userInfo));
        return this.userInfoClientService.getUsers(userInfo);
    }

    /**
     * 功能描述: 获取全部生产者信息（写信页面使用）
     * @param:
     * @param keyword 关键字
     * @return: java.util.List<com.gla.datacenter.domain.UserInfo>
     * @auther: zhengshien
     * @date: 2018/11/16 15:03
     */
    @RequestMapping(value = "/provider",method = RequestMethod.GET)
    @Access(login = true)
    public String selectProvider(@RequestParam(name = "keyword",required = false) String keyword) {
        LOG.debug(TextUtils.format("/***查询用户,返回用户列表**/"));
        return this.userInfoClientService.selectProvider(keyword);
    }

    /**
     * 功能描述: 获取用户列表（分页）
     * @param:
     * @param userInfo 用户实体（携带查询条件）
     * @return: com.limp.framework.core.bean.Pager<com.gla.datacenter.domain.UserInfo>
     * @auther: zhengshien
     * @date: 2018/11/16 15:09
     */
    @ApiOperation(value="根据条件查询用户列表", notes="根据条件查询用户列表")
    @ApiImplicitParam(name = "userInfo", value = "用户实体携带的条件信息", required = true, dataType = "UserInfo")
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    @Access(login = true)
    public String selectUserInfoList(UserInfo userInfo) {
        LOG.debug(TextUtils.format("/***根据消息回复id{0}，删除消息记录**/"));
        return this.userInfoClientService.selectUserInfoList(userInfo);
    }

    /**
     *
     * 功能描述: 分页查询用户列表(v2.0)
     *
     * @param: 
     * @param userInfo
     * @return: java.lang.String
     * @auther: zhangbo
     * @date: 2018/12/26 14:38
     */
    @RequestMapping(value = "/user/list")
    @Access(login = true)
    public String getPageUserList(UserInfo userInfo){
        LOG.debug(TextUtils.format("/***分页查询用户列表**/"));
        return userInfoClientService.getPageUserList(userInfo);
    }

    /**
     * 功能描述: 获取验证码
     * @param:
     * @param account 用户账号
     * @param userEmail 用户邮箱
     * @return: boolean
     * @auther: zhengshien
     * @date: 2018/11/16 15:08
     */
    @ApiOperation(value="获取验证码", notes="获取验证码")
    @ApiImplicitParam(name = "usaccount,userEmail", value = "用户账号，用户邮箱", required = true, dataType = "String，String")
    @RequestMapping(value = "/getCode",method = RequestMethod.GET)
    @Access(login = false)
    public String getCode(String account,String userEmail){
       LOG.debug(TextUtils.format("/***用户：{0}**/"),account);
       return this.userInfoClientService.getCode(account,userEmail);
    }

    /**
     * 功能描述: 密码找回
     * @param:
     * @param account 用户账号
     * @param userEmail 用户邮箱
     * @param code 验证码
     * @return: java.lang.Boolean
     * @auther: zhengshien
     * @date: 2018/11/16 15:08
     */
    @ApiOperation(value="修改密码", notes="修改密码")
    @ApiImplicitParam(name = "usaccount,userEmail，code", value = "用户账号，用户邮箱，验证码", required = true, dataType = "String，String，String")
    @RequestMapping(value = "/findPassword",method = RequestMethod.GET)
    @Access(login = false,operationLog = OPERATION.SELECT,operationIntro = "修改密码")
    public String findPassword(@RequestParam("account")String account, @RequestParam("userEmail") String userEmail,
                               @RequestParam("code")String code){
        LOG.debug(TextUtils.format("/***用户：{0}**/"),userEmail);
        return this.userInfoClientService.findPassword(account,userEmail,code);
    }

    /**
     * 功能描述: 新用户修改默认密码
     * @param:
     * @param account 用户账号或邮箱
     * @param oldPassword 原密码
     * @param newPassword 新密码
     * @return: java.lang.Boolean
     * @auther: zhengshien
     * @date: 2018/11/16 15:07
     */
    @ApiOperation(value="新用户修改默认密码", notes="新用户修改默认密码")
    @ApiImplicitParam(name = "usaccount,oldPassword，newPassword", value = "用户账号，原密码，新密码", required = true, dataType = "String，String，String")
    @RequestMapping(value = "/modifyDefaultPWD",method = RequestMethod.GET)
    @Access(login = false,operationLog = OPERATION.SELECT,operationIntro = "修改默认密码")
    public String modifyDefaultPWD(@RequestParam("account")String account,
                                   @RequestParam("oldPassword") String oldPassword,
                                   @RequestParam("newPassword") String newPassword){
        LOG.debug(TextUtils.format("/***用户：{0}**/"),account);
        return this.userInfoClientService.modifyDefaultPWD(account,oldPassword,newPassword);
    }

    /**
     * 功能描述: 密码重置（管理员使用）
     * @param:
     * @param userid 用户id
     * @return: java.lang.Boolean
     * @auther: zhengshien
     * @date: 2018/11/16 15:04
     */
    @ApiOperation(value="重置密码", notes="管理员为用户重置密码")
    @ApiImplicitParam(name = "userEmail，code", value = "用户邮箱，验证码", required = true, dataType = "String，String")
    @RequestMapping(value = "/resetPassword",method = RequestMethod.GET)
    @Access(login = true)
    public String resetPassword(@RequestParam("userid") String userid){
        LOG.debug(TextUtils.format("/***用户：{0}，校验验证码：{1}**/"),userid);
        return this.userInfoClientService.resetPassword(userid);
    }

    @Access(login = true)
    @GetMapping("/user/count")
    public String countRoleUserNum(){
        return userInfoClientService.countRoleUserNum();
    }

    /**
     * 功能描述: 获取图片验证码（返回流）
     * @param:
     * @param request 请求
     * @param response 响应
     * @return: void
     * @auther: zhengshien
     * @date: 2018/11/16 15:42
     */
    @ApiOperation(value="获取图片验证码", notes="登陆页面图片验证码")
    @ApiImplicitParam(name = "request，response", value = "请求参数，相应参数", required = true, dataType = "HttpServletRequest，HttpServletResponse")
    @RequestMapping("/imageCode")
    @Access(login = false)
    public void getImageCode(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");

        // 生成随机字串
        String verifyCode = VerifyCodeUtils.generateVerifyCode(4);
        LOG.info("生成验证码{}",verifyCode);
        LOG.info("获取验证码的sessionid->{}",request.getSession().getId());
        // 存入Redis
        plugCacheClientService.setCache(ConstantGla.YZM_IMAGECODE_PRE+request.getSession().getId(),verifyCode,Long.parseLong("300"));
        // 生成图片
        int w = 120, h = 50;
        try {
            VerifyCodeUtils.outputImage(w, h, response.getOutputStream(), verifyCode);
        }catch (Exception e){
            LOG.info("异常....");
            for(StackTraceElement stackTraceElement:e.getStackTrace()){
                LOG.error(stackTraceElement.toString());
            }
            e.printStackTrace();
        }
    }

    /**
     *
     * 功能描述: 获取消费者的apikey
     *
     * @param:
     * @return: java.lang.String
     * @auther: zhangbo
     * @date: 2018/12/21 16:46
     */

    @Access(login = true)
    @RequestMapping(value = "/user/apikey")
    public String getConsumerApikey(){
        return userInfoClientService.getConsumerApikey();
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        /**
         * 第一种方式：使用WebDataBinder注册一个自定义的编辑器，编辑器是日期类型
         * 使用自定义的日期编辑器，日期格式：yyyy-MM-dd,第二个参数为是否为空  true代表可以为空
         *  yyyy-MM-dd hh:mm:ss
         */
        binder.registerCustomEditor(Date.class, new CustomDateEditor(
                new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"), true));
    }
}
