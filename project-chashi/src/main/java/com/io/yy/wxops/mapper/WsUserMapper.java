package com.io.yy.wxops.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.io.yy.wxops.entity.WsUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <pre>
 * 用户信息表 Mapper 接口
 * </pre>
 *
 * @author wuzhixiong
 * @since 2020-07-11
 */
@Repository
public interface WsUserMapper extends BaseMapper<WsUser> {

    /**
     * 根据 openId获取查询对象
     *
     * @param openId
     * @return
     */
    WsUser getWsUserByOpenId(String openId);

}
