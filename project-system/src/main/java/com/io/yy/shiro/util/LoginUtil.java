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

package com.io.yy.shiro.util;


import com.io.yy.constant.CommonRedisKey;
import com.io.yy.shiro.vo.LoginSysUserRedisVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;


/**
 * 获取登陆信息工具类
 *
 * @author kris
 * @date 2019-10-24
 */
@Slf4j
@Component
public class LoginUtil {

    private static RedisTemplate redisTemplate;

    public LoginUtil(RedisTemplate redisTemplate) {
        LoginUtil.redisTemplate = redisTemplate;
    }


    /**
     * 获取当前登陆用户对象
     *
     * @return
     */
    public static LoginSysUserRedisVo getLoginSysUserRedisVo() {
        // 获取当前登陆用户
        String token = JwtTokenUtil.getToken();
        String username = JwtUtil.getUsername(token);
        if (StringUtils.isBlank(username)) {
            return null;
        }
        return (LoginSysUserRedisVo) redisTemplate.opsForValue().get(String.format(CommonRedisKey.LOGIN_USER, username));
    }

    /**
     * 获取当前登陆用户的ID
     *
     * @return
     */
    public static Long getUserId() {
        LoginSysUserRedisVo loginSysUserRedisVo = getLoginSysUserRedisVo();
        if (loginSysUserRedisVo == null) {
            return null;
        }
        return loginSysUserRedisVo.getId();
    }

    /**
     * 获取当前登陆用户的账号
     *
     * @return
     */
    public static String getUsername() {
        LoginSysUserRedisVo loginSysUserRedisVo = getLoginSysUserRedisVo();
        if (loginSysUserRedisVo == null) {
            return null;
        }
        return loginSysUserRedisVo.getUsername();
    }

    /**
     * 获取当前登陆用户的机构id
     *
     * @return
     */
    public static String getOfficeCode() {
        LoginSysUserRedisVo loginSysUserRedisVo = getLoginSysUserRedisVo();
        if (loginSysUserRedisVo == null) {
            return null;
        }
        return loginSysUserRedisVo.getOfficeCode();
    }

    /**
     * 获取账户默认规则
     *
     * @return
     */
    public static String getDefaultAccountRules() {
        LoginSysUserRedisVo loginSysUserRedisVo = getLoginSysUserRedisVo();
        if (loginSysUserRedisVo == null) {
            return null;
        }
        return loginSysUserRedisVo.getDefaultAccountRules();
    }

    /**
     * 获取当前登陆用户的机构名称
     *
     * @return
     */
    public static String getOfficeName() {
        LoginSysUserRedisVo loginSysUserRedisVo = getLoginSysUserRedisVo();
        if (loginSysUserRedisVo == null) {
            return null;
        }
        return loginSysUserRedisVo.getOfficeName();
    }

}
