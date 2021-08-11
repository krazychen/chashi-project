package com.io.yy.wxops.service;

import com.io.yy.wxops.entity.WxUser;
import com.io.yy.common.service.BaseService;
import com.io.yy.wxops.param.WxLoginQueryParam;
import com.io.yy.wxops.param.WxUserQueryParam;
import com.io.yy.wxops.vo.WxUserQueryVo;
import com.io.yy.common.vo.Paging;

import java.io.Serializable;
import java.util.List;


/**
 * <pre>
 * 微信用户 服务类
 * </pre>
 *
 * @author kris
 * @since 2021-08-06
 */
public interface WxUserService extends BaseService<WxUser> {

    /**
     * 保存
     *
     * @param wxUser
     * @return
     * @throws Exception
     */
    boolean saveWxUser(WxUser wxUser) throws Exception;

    /**
     * 修改
     *
     * @param wxUser
     * @return
     * @throws Exception
     */
    boolean updateWxUser(WxUser wxUser) throws Exception;

    /**
     * 删除
     *
     * @param id
     * @return
     * @throws Exception
     */
    boolean deleteWxUser(Long id) throws Exception;

    /**
     * 批量删除
     *
     * @param idList
     * @return
     * @throws Exception
     */
    boolean deleteWxUsers(List<String> idList) throws Exception;

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     * @throws Exception
     */
    WxUserQueryVo getWxUserById(Serializable id) throws Exception;

    /**
     * 获取分页对象
     *
     * @param wxUserQueryParam
     * @return
     * @throws Exception
     */
    Paging<WxUserQueryVo> getWxUserPageList(WxUserQueryParam wxUserQueryParam) throws Exception;

    /**
     * 更新状态
     *
     * @param wxUserQueryParam
     * @return
     */
     boolean updateStatus(WxUserQueryParam wxUserQueryParam);

    /**
     * 微信登录
     * @param wxLoginQueryParam
     * @return
     */
    WxUserQueryVo wxLogin(WxLoginQueryParam wxLoginQueryParam) throws Exception;

}
