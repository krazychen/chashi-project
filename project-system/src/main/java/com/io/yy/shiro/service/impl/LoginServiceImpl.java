/*
 * Copyright 2019-2029 kris
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.io.yy.shiro.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.setting.Setting;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.io.yy.constant.CommonConstant;
import com.io.yy.constant.CommonRedisKey;
import com.io.yy.core.properties.WhyySystemProperties;
import com.io.yy.enums.StateEnum;
import com.io.yy.shiro.cache.LoginRedisService;
import com.io.yy.shiro.jwt.JwtProperties;
import com.io.yy.shiro.jwt.JwtToken;
import com.io.yy.shiro.param.LoginParam;
import com.io.yy.shiro.service.LoginService;
import com.io.yy.shiro.util.JwtTokenUtil;
import com.io.yy.shiro.util.JwtUtil;
import com.io.yy.shiro.util.SaltUtil;
import com.io.yy.shiro.vo.LoginSysUserVo;
import com.io.yy.shiro.vo.WxOpenQueryVo;
import com.io.yy.system.convert.SysUserConvert;
import com.io.yy.system.entity.*;
import com.io.yy.system.exception.VerificationCodeException;
import com.io.yy.system.mapper.SysUserMapper;
import com.io.yy.system.mapper.SysUserRoleMapper;
import com.io.yy.system.param.WxQueryParam;
import com.io.yy.system.service.*;
import com.io.yy.system.vo.LoginSysUserTokenVo;
import com.io.yy.system.vo.SysConfigDataRedisVo;
import com.io.yy.system.vo.SysMenuQueryVo;
import com.io.yy.util.ConfigDataUtil;
import com.io.yy.util.PasswordUtil;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Duration;
import java.util.*;

/**
 * <p>
 * 登录服务实现类
 * </p>
 *
 * @author kris
 * @date 2019-05-23
 **/
@Api
@Slf4j
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private LoginRedisService loginRedisService;

    @Autowired
    private JwtProperties jwtProperties;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private WhyySystemProperties whyySystemProperties;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private SysDictDataRedisService sysDictDataRedisService;

    @Autowired
    private SysDictDataService sysDictDataService;

    @Autowired
    private SysConfigService sysConfigService;

    @Autowired
    private SysUserOfficeService sysUserOfficeService;

    @Autowired
    private SysOfficeService sysOfficeService;

    @Autowired
    private SysUserRoleService sysUserRoleService;

    @Autowired
    private SysMenuService sysMenuService;

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public LoginSysUserTokenVo login(LoginParam loginParam) throws Exception {
        // 校验验证码
        checkVerifyCode(loginParam.getVerifyToken(), loginParam.getCode());

        String username = loginParam.getUsername();
        // 从数据库中获取登陆用户信息
        SysUser sysUser = getSysUserByUsername(username);
        if (sysUser == null) {
            log.error("登陆失败,loginParam:{}", loginParam);
            throw new AuthenticationException("用户名或密码错误");
        }
        if (StateEnum.DISABLE.getCode().equals(sysUser.getState())) {
            throw new AuthenticationException("账号已禁用");
        }
        //微信标识，false标识未绑定微信，true标识已绑定微信
        Boolean iswx = false;
        if (StrUtil.isNotBlank(sysUser.getIsWx()) && sysUser.getIsWx().equals("1")) {
            iswx = true;
        }

        // 实际项目中，前端传过来的密码应先加密
        // 原始密码明文：123456
        // 原始密码前端加密：sha256(123456)
        // 后台加密规则：sha256(sha256(123456) + salt)
        String encryptPassword = PasswordUtil.encrypt(loginParam.getPassword(), sysUser.getSalt());
        if (!encryptPassword.equals(sysUser.getPassword())) {
            throw new AuthenticationException("用户名或密码错误");
        }
        return this.unifiedLogin(sysUser, iswx, username, "0");
    }

    /**
     * 统一登录认证成功后的逻辑
     *
     * @param sysUser
     * @param iswx
     * @param username
     * @param type
     * @return
     */
    public LoginSysUserTokenVo unifiedLogin(SysUser sysUser, Boolean iswx, String username, String type) {
        // 将系统用户对象转换成登陆用户对象
        LoginSysUserVo loginSysUserVo = SysUserConvert.INSTANCE.sysUserToLoginSysUserVo(sysUser);
        loginSysUserVo.setIswx(iswx);
        // 获取机构 20191210 修改成根据用户机构表获取机构
        QueryWrapper<SysUserOffice> sysUserOfficeQueryWrapper = new QueryWrapper<SysUserOffice>();
        sysUserOfficeQueryWrapper.eq("user_id", loginSysUserVo.getId());
        SysUserOffice sysUserOffice = sysUserOfficeService.getOne(sysUserOfficeQueryWrapper);

        if (sysUserOffice != null) {
            QueryWrapper<SysOffice> sysOfficeQueryWrapper = new QueryWrapper<SysOffice>();
            sysOfficeQueryWrapper.eq("office_code", sysUserOffice.getOfficeCode());
            SysOffice sysOffice = sysOfficeService.getOne(sysOfficeQueryWrapper);
            if (sysOffice == null) {
                throw new AuthenticationException("机构不存在");
            }
            if (!StateEnum.ENABLE.getCode().toString().equals(sysOffice.getStatus())) {
                throw new AuthenticationException("机构已禁用");
            }
            loginSysUserVo.setOfficeCode(sysOffice.getOfficeCode()).setOfficeName(sysOffice.getOfficeName()).setDefaultAccountRules(sysOffice.getDefaultAccountRules());
        } else {
            throw new AuthenticationException("用户机构不存在，请检查!");
        }


//        SysDepartment sysDepartment = sysDepartmentService.getById(sysUser.getDepartmentId());
//        if (sysDepartment == null) {
//            throw new AuthenticationException("机构不存在");
//        }
//        if (!StateEnum.ENABLE.getCode().equals(sysDepartment.getState())) {
//            throw new AuthenticationException("机构已禁用");
//        }
//        loginSysUserVo.setDepartmentId(sysDepartment.getId())
//                .setDepartmentName(sysDepartment.getName());

        // 获取当前角色列表 20191210 修改成根据用户角色表获取角色
        QueryWrapper<SysUserRole> sysUserRoleQueryWrapper = new QueryWrapper<SysUserRole>();
        sysUserRoleQueryWrapper.eq("user_id", loginSysUserVo.getId());
        List<SysUserRole> sysUserRoleList = sysUserRoleService.list(sysUserRoleQueryWrapper);
        Set<SysRole> sysRoleSet = null;

        //如果是学员并且没有绑定微信就直接改未绑定微信的角色
        if (sysUserRoleList != null && sysUserRoleList.size() != 0) {
            sysRoleSet = new HashSet<SysRole>();
            Boolean flag = true;
            if ((sysUser.getUserType().equals("2") || sysUser.getUserType().equals("3")) && whyySystemProperties.isEnableHwXueyuanWx()) {
                //如果是密码登陆或者是未绑定
                if (type.equals("0") || !loginSysUserVo.getIswx()) {
                    String roleType = "studentnowx";
                    if (sysUser.getUserType().equals("3")) {
                        roleType = "teachernowx";
                    }
                    SysRole sysRole = sysRoleService.getSysRoleByType(roleType);
                    sysRoleSet.add(sysRole);
                    flag = false;
                }
                loginSysUserVo.setIsSuperAdmin(false);
            }
            if (flag) {
                for (SysUserRole sur : sysUserRoleList) {
                    QueryWrapper<SysRole> sysRoleQueryWrapper = new QueryWrapper<SysRole>();
//                    sysRoleQueryWrapper.eq("id",sur.getRoleId());
//                    sysRoleSet.addAll(sysRoleService.list(sysRoleQueryWrapper));
                    SysRole sysRole = sysRoleService.getById(sur.getRoleId());
                    sysRoleSet.add(sysRole);
                    // 判断是否超级管理员
                    if (sysRole.getCode().equals("superadmin")) {
                        loginSysUserVo.setIsSuperAdmin(true);
                    } else {
                        loginSysUserVo.setIsSuperAdmin(false);
                    }
                }
            }
        } else {
            throw new AuthenticationException("角色不存在");
        }
        loginSysUserVo.setSysRoleList(sysRoleSet);
        // 获取当前用户角色
//        Long roleId = sysUser.getRoleId();
//        SysRole sysRole = sysRoleService.getById(roleId);
//        if (sysRole == null) {
//            throw new AuthenticationException("角色不存在");
//        }
//        if (StateEnum.DISABLE.getCode().equals(sysRole.getState())) {
//            throw new AuthenticationException("角色已禁用");
//        }
//        loginSysUserVo.setRoleId(sysRole.getId())
//                .setRoleName(sysRole.getName())
//                .setRoleCode(sysRole.getCode());

        // 获取当前角色对应的菜单和权限列表 20191210 修改成根据角色菜单表获取菜单和权限
        // 由于角色菜单较少变动，为了登陆速度，先缓存redis，todo 需要增加启动缓存和修改角色菜单时清空缓存的操作
        // 缓存角色菜单到Redis
        Set<SysMenuQueryVo> sysMenuSet = null;
        if (sysRoleSet != null) {
            sysMenuSet = new HashSet<SysMenuQueryVo>();
            QueryWrapper<SysRoleMenu> sysRoleMenuQueryWrapper = new QueryWrapper<SysRoleMenu>();
            for (SysRole sr : sysRoleSet) {
                String redisKey = CommonRedisKey.ROLE_MENU_DATA_KEY + ":" + sr.getCode();
                List<SysMenuQueryVo> listenu = (List) redisTemplate.opsForValue().get(redisKey);
                if (listenu == null || listenu.size() == 0) {
                    listenu = sysMenuService.getSysMenuListByRoleId(String.valueOf(sr.getId()));
                    redisTemplate.opsForValue().set(redisKey, listenu);
                }
                sysMenuSet.addAll(listenu);
            }
        } else {
            throw new AuthenticationException("角色不存在");
        }
        loginSysUserVo.setSysMenuList(sysMenuSet);

//        // 获取当前用户权限
//        Set<String> permissionCodes = sysRolePermissionService.getPermissionCodesByRoleId(roleId);
//        if (CollectionUtils.isEmpty(permissionCodes)) {
//            throw new AuthenticationException("权限列表不能为空");
//        }
//        loginSysUserVo.setPermissionCodes(permissionCodes);

        // 获取数据库中保存的盐值
        String newSalt = SaltUtil.getSalt(sysUser.getSalt(), jwtProperties);

        // 生成token字符串并返回
        Long expireSecond = jwtProperties.getExpireSecond();
        if (!sysUser.getUsername().equals(loginSysUserVo.getUsername())) {
            username = username.toLowerCase();
        }
        String token = JwtUtil.generateToken(username, newSalt, Duration.ofSeconds(expireSecond));
        log.debug("token:{}", token);

        // 创建AuthenticationToken
        JwtToken jwtToken = JwtToken.build(token, username, newSalt, expireSecond);
        // 从SecurityUtils里边创建一个 subject
        Subject subject = SecurityUtils.getSubject();
        // 执行认证登陆
        subject.login(jwtToken);

        // 缓存登陆信息到Redis
        loginRedisService.cacheLoginInfo(jwtToken, loginSysUserVo);
        // 在登陆判断是否有缓存数据字典，无则缓存（系统启动时加载数据字典缓存，在这边加只是为了以防没有加载）
        Map dictDataCacheMap = (Map) redisTemplate.opsForHash().entries(CommonRedisKey.DICT_DATA_KEY);
        if (dictDataCacheMap == null || dictDataCacheMap.size() == 0) {
            dictDataCacheMap = sysDictDataService.getAllSysDictData();
            sysDictDataRedisService.cacheSysDictDataInfo(dictDataCacheMap);
        }
        // 在登陆判断是否有缓存参数配置，无则缓存（系统启动时加载参数配置缓存，在这边加只是为了以防没有加载）
        List<SysConfigDataRedisVo> sysConfigDataList = ConfigDataUtil.getAllSysConfigData();
        if (sysConfigDataList == null || sysConfigDataList.size() == 0) {
            sysConfigDataList = sysConfigService.getAllSysConfigData();
            ConfigDataUtil.cacheSysDictDataInfo(sysConfigDataList);
        }
        log.debug("登陆成功,username:{}", username);

        // 返回token和登陆用户信息对象
        LoginSysUserTokenVo loginSysUserTokenVo = new LoginSysUserTokenVo();
        loginSysUserTokenVo.setToken(token);
        loginSysUserTokenVo.setLoginSysUserVo(loginSysUserVo);
        loginSysUserTokenVo.setDictDataCacheMap(dictDataCacheMap);
        loginSysUserTokenVo.setConfigDataCacheList(sysConfigDataList);
        return loginSysUserTokenVo;

    }

//    @Override
//    public LoginSysUserTokenVo wxLogin1(WxQueryParam wxQueryParam) {
//        //验证code的合法性
//        Setting setting = new Setting("wxProperties/wxWeb.properties");
//        String url = setting.getStr("url");
//        String appid = setting.getStr("appid");
//        String appSecret = setting.getStr("appSecret");
//        url = url + "?appid=" + appid + "&secret=" + appSecret + "&code=" + wxQueryParam.getCode() + "&grant_type=authorization_code";
//        RestTemplate restTemplate = new RestTemplate();
//        String result = restTemplate.getForObject(url, String.class);
//        Map<String, Object> jsonToMap = JSONObject.parseObject(result);
//        if (ObjectUtil.isNull(jsonToMap.get("openid"))) {
//            throw new AuthenticationException("授权失败");
//        }
//        String openid = jsonToMap.get("openid").toString();
//        //根据openid 获取用户信息，若获取到则对比前台传入的id和用户id是否相等，若id不等错误提示，若相等直接下一步
//        WxOpenQueryVo wxOpenQueryVo = sysUserMapper.getSysUserByOpenid(openid);
//        Integer flag = 1;
//
//        if (ObjectUtil.isNull(wxOpenQueryVo)) {
//            if (ObjectUtil.isNull(wxQueryParam.getId())) {
//                throw new AuthenticationException("非系统用户");
//            }
//            SysUser sysUser = sysUserMapper.selectById(wxQueryParam.getId());
//            if (ObjectUtil.isNull(sysUser)) {
//                throw new AuthenticationException("非系统用户");
//            }
//            if (ObjectUtil.isNotNull(sysUser.getWxOpenid())) {
//                throw new AuthenticationException("请使用该账号对应的微信");
//            }
//
//            flag = sysUserMapper.updateSysUserOpenidById(wxQueryParam.getId(), openid);
//            if (flag == 1) {
//                wxOpenQueryVo = new WxOpenQueryVo();
//                wxOpenQueryVo.setId(wxQueryParam.getId());
//                SysRole sysRole = sysRoleService.getSysRoleByType("studentnowx");
//                if (ObjectUtil.isNotNull(sysRole)) {
//                    sysUserRoleMapper.deleteRoleUserByRoleId(sysRole.getId());
//                }
//            }
//        }
//        if (flag != 1) {
//            throw new AuthenticationException("微信绑定失败");
//        }
//
//        // 从数据库中获取登陆用户信息
//        SysUser sysUser = sysUserMapper.selectById(wxOpenQueryVo.getId());
//        if (sysUser == null) {
//            throw new AuthenticationException("用户不存在");
//        }
//        if (StateEnum.DISABLE.getCode().equals(sysUser.getState())) {
//            throw new AuthenticationException("账号已禁用");
//        }
//        //微信标识，false标识未绑定微信，true标识已绑定微信
//        Boolean iswx = false;
//        if (StrUtil.isNotBlank(sysUser.getWxOpenid())) {
//            iswx = true;
//        }
//        return this.unifiedLogin(sysUser, iswx, sysUser.getUsername(),"1");
//    }

    @Override
    public LoginSysUserTokenVo checkLoginQr(String code) throws Exception {
        //判断redis中是否有code值，有的话就获取对应的用户id
        WxOpenQueryVo wxOpenQueryVo = (WxOpenQueryVo) redisTemplate.opsForHash().get(CommonRedisKey.WX_LOGIN, code);
        if (ObjectUtil.isNull(wxOpenQueryVo)) {
            throw new AuthenticationException("120");
        }
        //判定是否为绑定
        if(StrUtil.isNotBlank(wxOpenQueryVo.getType()) && wxOpenQueryVo.getType().equals("1")){
            if(StrUtil.isBlank(wxOpenQueryVo.getWxOpenid())){
                throw new AuthenticationException("获取Unionid出错！");
            }
            WxOpenQueryVo wxOpenQueryVo1 = sysUserMapper.getSysUserByOpenid(wxOpenQueryVo.getWxOpenid());
            if(ObjectUtil.isNotNull(wxOpenQueryVo1)){
                redisTemplate.opsForHash().delete(CommonRedisKey.WX_LOGIN, code);
                throw new AuthenticationException("110");
            }else{
                sysUserMapper.updateSysUserUnionidOpenidById(wxOpenQueryVo.getId(), wxOpenQueryVo.getWxOpenid(), wxOpenQueryVo.getWxOpenidTwo());
            }
        }
        redisTemplate.opsForHash().delete(CommonRedisKey.WX_LOGIN, code);
        // 从数据库中获取登陆用户信息
        SysUser sysUser = new SysUser().setId(wxOpenQueryVo.getId());
        sysUser = sysUserMapper.selectOne(new QueryWrapper(sysUser));
        /* SysUser sysUser = getSysUserByUsername("");*/
        String username = sysUser.getUsername();
        if (sysUser == null) {
            throw new AuthenticationException("用户名或密码错误");
        }
        if (StateEnum.DISABLE.getCode().equals(sysUser.getState())) {
            throw new AuthenticationException("账号已禁用");
        }
        //微信标识，false标识未绑定微信，true标识已绑定微信
        Boolean iswx = false;
        if (StrUtil.isNotBlank(sysUser.getIsWx()) && sysUser.getIsWx().equals("1")) {
            iswx = true;
        }
        return this.unifiedLogin(sysUser, iswx, username, "1");
    }


//    @Override
//    public LoginSysUserTokenVo wxLogin(WxQueryParam wxQueryParam) {
//        //验证code的合法性
//        Setting setting = new Setting("wxProperties/wxWeb.properties");
//        String url = setting.getStr("url");
//        String appid = setting.getStr("appid");
//        String appSecret = setting.getStr("appSecret");
//        url = url + "?appid=" + appid + "&secret=" + appSecret + "&code=" + wxQueryParam.getCode() + "&grant_type=authorization_code";
//        RestTemplate restTemplate = new RestTemplate();
//        String result = restTemplate.getForObject(url, String.class);
//        Map<String, Object> jsonToMap = JSONObject.parseObject(result);
//        if (ObjectUtil.isNull(jsonToMap.get("openid"))) {
//            throw new AuthenticationException("授权失败");
//        }
//        String openid = jsonToMap.get("openid").toString();
//        //根据openid 获取用户信息，若获取到则对比前台传入的id和用户id是否相等，若id不等错误提示，若相等直接下一步
//        WxOpenQueryVo wxOpenQueryVo = sysUserMapper.getSysUserByOpenid(openid);
//        Integer flag = 1;
//
//        if (ObjectUtil.isNull(wxOpenQueryVo)) {
//            if (ObjectUtil.isNull(wxQueryParam.getId())) {
//                throw new AuthenticationException("非系统用户");
//            }
//            SysUser sysUser = sysUserMapper.selectById(wxQueryParam.getId());
//            if (ObjectUtil.isNull(sysUser)) {
//                throw new AuthenticationException("非系统用户");
//            }
//            if (ObjectUtil.isNotNull(sysUser.getWxOpenid())) {
//                throw new AuthenticationException("请使用该账号对应的微信");
//            }
//
//            flag = sysUserMapper.updateSysUserOpenidById(wxQueryParam.getId(), openid);
//            if (flag == 1) {
//                wxOpenQueryVo = new WxOpenQueryVo();
//                wxOpenQueryVo.setId(wxQueryParam.getId());
//                SysRole sysRole = sysRoleService.getSysRoleByType("studentnowx");
//                if (ObjectUtil.isNotNull(sysRole)) {
//                    sysUserRoleMapper.deleteRoleUserByRoleId(sysRole.getId());
//                }
//            }
//        } else {
//            SysUser sysUser = sysUserMapper.selectById(wxQueryParam.getId());
//            if (ObjectUtil.isNotNull(sysUser) || sysUser.getWxOpenid() == null || !sysUser.getWxOpenid().equals(openid)) {
//                throw new AuthenticationException("微信号已经被绑定，请重新进行绑定");
//            }
//        }
//        if (flag != 1) {
//            throw new AuthenticationException("微信绑定失败");
//        }
//
//        // 从数据库中获取登陆用户信息
//        SysUser sysUser = sysUserMapper.selectById(wxOpenQueryVo.getId());
//        if (sysUser == null) {
//            throw new AuthenticationException("用户不存在");
//        }
//        if (StateEnum.DISABLE.getCode().equals(sysUser.getState())) {
//            throw new AuthenticationException("账号已禁用");
//        }
//        //微信标识，false标识未绑定微信，true标识已绑定微信
//        Boolean iswx = false;
//        if (StrUtil.isNotBlank(sysUser.getWxOpenid())) {
//            iswx = true;
//        }
//        return this.unifiedLogin(sysUser, iswx, sysUser.getUsername(),"1");
//    }

    @Override
    public void checkVerifyCode(String verifyToken, String code) throws Exception {
        // 如果没有启用验证码，则返回
        if (!whyySystemProperties.isEnableVerifyCode()) {
            return;
        }
        // 校验验证码
        if (StringUtils.isBlank(code)) {
            throw new VerificationCodeException("请输入验证码");
        }
        // 从redis中获取
        String redisKey = String.format(CommonRedisKey.VERIFY_CODE, verifyToken);
        String generateCode = (String) redisTemplate.opsForValue().get(redisKey);
        if (StringUtils.isBlank(generateCode)) {
            throw new VerificationCodeException("验证码已过期或不正确");
        }
        // 不区分大小写
        if (!generateCode.equalsIgnoreCase(code)) {
            throw new VerificationCodeException("验证码错误");
        }
        // 验证码校验成功，删除Redis缓存
        redisTemplate.delete(redisKey);
    }

    @Override
    public void refreshToken(JwtToken jwtToken, HttpServletResponse httpServletResponse) throws Exception {
        if (jwtToken == null) {
            return;
        }
        String token = jwtToken.getToken();
        if (StringUtils.isBlank(token)) {
            return;
        }
        // 判断是否刷新token
        boolean isRefreshToken = jwtProperties.isRefreshToken();
        if (!isRefreshToken) {
            return;
        }
        // 获取过期时间
        Date expireDate = JwtUtil.getExpireDate(token);
        // 获取倒计时
        Integer countdown = jwtProperties.getRefreshTokenCountdown();
        // 如果(当前时间+倒计时) > 过期时间，则刷新token
        boolean refresh = DateUtils.addSeconds(new Date(), countdown).after(expireDate);

        if (!refresh) {
            return;
        }

        // 如果token继续发往后台，则提示，此token已失效，请使用新token，不在返回新token，返回状态码：461
        // 如果Redis缓存中没有，JwtToken没有过期，则说明，已经刷新token
        boolean exists = loginRedisService.exists(token);
        if (!exists) {
            httpServletResponse.setStatus(CommonConstant.JWT_INVALID_TOKEN_CODE);
            throw new AuthenticationException("token已无效，请使用已刷新的token");
        }
        String username = jwtToken.getUsername();
        String salt = jwtToken.getSalt();
        Long expireSecond = jwtProperties.getExpireSecond();
        // 生成新token字符串
        String newToken = JwtUtil.generateToken(username, salt, Duration.ofSeconds(expireSecond));
        // 生成新JwtToken对象
        JwtToken newJwtToken = JwtToken.build(newToken, username, salt, expireSecond);
        // 更新redis缓存
        loginRedisService.refreshLoginInfo(token, username, newJwtToken);
        log.debug("刷新token成功，原token:{}，新token:{}", token, newToken);
        // 设置响应头
        // 刷新token
        httpServletResponse.setStatus(CommonConstant.JWT_REFRESH_TOKEN_CODE);
        httpServletResponse.setHeader(JwtTokenUtil.getTokenName(), newToken);
    }

    @Override
    public void logout(HttpServletRequest request) throws Exception {
        Subject subject = SecurityUtils.getSubject();
        //注销
        subject.logout();
        // 获取token
        String token = JwtTokenUtil.getToken(request);
        String username = JwtUtil.getUsername(token);
        // 删除Redis缓存信息
        loginRedisService.deleteLoginInfo(token, username);
        log.info("登出成功,username:{},token:{}", username, token);
    }

    @Override
    public SysUser getSysUserByUsername(String username) throws Exception {
        SysUser sysUser = new SysUser().setUsername(username);
        return sysUserMapper.selectOne(new QueryWrapper(sysUser));
    }

    private void loginCommon() {

    }


}
