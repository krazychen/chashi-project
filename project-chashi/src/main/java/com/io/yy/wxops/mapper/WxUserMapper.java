package com.io.yy.wxops.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.io.yy.wxops.entity.WxUser;
import com.io.yy.wxops.param.WxUserQueryParam;
import com.io.yy.wxops.vo.WxUserQueryVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * <pre>
 * 微信用户 Mapper 接口
 * </pre>
 *
 * @author kris
 * @since 2021-08-06
 */
@Repository
public interface WxUserMapper extends BaseMapper<WxUser> {

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     */
    WxUserQueryVo getWxUserById(Serializable id);

    /**
     * 获取分页对象
     *
     * @param page
     * @param wxUserQueryParam
     * @return
     */
    IPage<WxUserQueryVo> getWxUserPageList(@Param("page") Page page, @Param("param") WxUserQueryParam wxUserQueryParam);

    /**
     * 更新状态
     *
     * @param wxUserQueryParam
     * @return
     */
    Integer updateStatus(@Param("param") WxUserQueryParam wxUserQueryParam);

    /**
     * 根据 openId获取查询对象
     *
     * @param openId
     * @return
     */
    WxUser getWxUserByOpenId(String openId);

    /**
     * 根据 Id获取查询对象
     *
     * @param openId
     * @return
     */
    WxUserQueryVo getWxUserByOpenid(Serializable openId);
}
