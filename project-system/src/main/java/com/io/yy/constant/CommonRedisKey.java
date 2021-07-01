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

package com.io.yy.constant;

/**
 * <p>
 *  redis key 常量
 * </p>
 * @author kris
 * @date 2019-05-23
 **/
public interface CommonRedisKey {

    /**
     * 登陆用户token信息key
     */
    String LOGIN_TOKEN = "login:token:%s";

    /**
     * 登陆用户信息key
     */
    String LOGIN_USER = "login:user:%s";

    /**
     * 登陆用户盐值信息key
     */
    String LOGIN_SALT= "login:salt:%s";

    /**
     * 登陆用户username token
     */
    String LOGIN_USER_TOKEN = "login:user:token:%s:%s";

    /**
     * 验证码
     */
    String VERIFY_CODE = "verify.code:%s";

    /**
     * 数据字典key
     */
    String DICT_DATA_KEY = "dict:data:cache";

    /**
     * 参数配置key
     */
    String CONFIG_DATA_KEY = "config:data:cache";

    /**
     * 角色菜单配置key
     */
    String ROLE_MENU_DATA_KEY = "config:menu:cache";

    /**
     *行政区划key
     */
    String SYS_AREA = "sys.area";

    /**
     * access_token 信息
     */
    String WX_ACCESS_TOKEN = "wx.access_token";

    /**
     * 公众号登陆信息
     */
    String WX_LOGIN = "wx.login";

    String WX_TEMPLATE_KEY = "wx.template";


    /**
     * 系统对接最近更新的时间
     */
    String CCTALK_UPDATE_TIME = "cctalk:update:time";

    /**
     * 系统对接更新是否进行的key
     */
    String CCTALK_UPDATE_EXIST = "cctalk:update:exist";


}
