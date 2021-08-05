package com.io.yy.wxops.service;

import com.io.yy.common.service.BaseService;
import com.io.yy.common.vo.Paging;
import com.io.yy.wxops.entity.WsUser;
import com.io.yy.wxops.param.WxLoginQueryParam;
import com.io.yy.wxops.vo.WsUserQueryVo;

import java.util.List;


/**
 * <pre>
 * 用户信息表 服务类
 * </pre>
 *
 * @author wuzhixiong
 * @since 2020-07-11
 */
public interface WsUserService extends BaseService<WsUser> {

    /**
     * 微信登录
     * @param wxLoginQueryParam
     * @return
     */
    WsUserQueryVo wxLogin(WxLoginQueryParam wxLoginQueryParam);

}
