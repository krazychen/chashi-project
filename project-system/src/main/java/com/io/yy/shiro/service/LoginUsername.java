
package com.io.yy.shiro.service;

import java.io.Serializable;

/**
 * 获取登录用户名称
 *
 * @author kris
 * @date 2020/09/27
 **/
public interface LoginUsername extends Serializable {

    /**
     * 获取用户名
     *
     * @return
     */
    String getUsername();

}
