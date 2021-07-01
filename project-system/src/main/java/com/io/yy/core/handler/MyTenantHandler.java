package com.io.yy.core.handler;

import com.baomidou.mybatisplus.extension.plugins.tenant.TenantHandler;

/**
 * @author kris
 * @ClassName MyTenantHandler
 * @Description TODO
 * @date 07/30
 */
public interface MyTenantHandler extends TenantHandler {
    boolean doUserFilter();
}