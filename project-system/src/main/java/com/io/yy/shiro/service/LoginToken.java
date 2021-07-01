package com.io.yy.shiro.service;

import java.io.Serializable;

/**
 * 获取登录token
 *
 * @author kris
 * @date 2020/09/27
 **/
public interface LoginToken extends Serializable {

    /**
     * 获取登录token
     *
     * @return
     */
    String getToken();

}
