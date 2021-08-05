package com.io.yy.wxops.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.setting.Setting;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.io.yy.common.service.impl.BaseServiceImpl;
import com.io.yy.common.vo.Paging;
import com.io.yy.util.lang.ObjectUtils;
import com.io.yy.wxops.entity.WsUser;
import com.io.yy.wxops.mapper.WsUserMapper;
import com.io.yy.wxops.param.WxLoginQueryParam;
import com.io.yy.wxops.service.WsUserService;
import com.io.yy.wxops.utils.EncryptedUtil;
import com.io.yy.wxops.utils.TrueFlagEnum;
import com.io.yy.wxops.utils.WxMappingJackson2HttpMessageConverter;
import com.io.yy.wxops.vo.WsUserQueryVo;
import com.io.yy.wxops.vo.WxLoginQueryVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.HtmlUtils;

import java.util.List;


/**
 * <pre>
 * 用户信息表 服务实现类
 * </pre>
 *
 * @author wuzhixiong
 * @since 2020-07-11
 */
@Slf4j
@Service
public class WsUserServiceImpl extends BaseServiceImpl<WsUserMapper, WsUser> implements WsUserService {

    @Autowired
    private WsUserMapper wsUserMapper;

    public WsUserQueryVo wxLogin(WxLoginQueryParam wxLoginQueryParam){
        WsUserQueryVo wsUserQueryVo = new WsUserQueryVo();
        Setting setting = new Setting("wxProperties/wx.properties");
        String appid = setting.getStr("appid");
        String appSecret = setting.getStr("appSecret");
        String loginUrl = setting.getStr("loginUrl");
        loginUrl = loginUrl+"?"+"appid="+appid+"&secret="+appSecret+"&js_code="+wxLoginQueryParam.getJsCode()+"&grant_type=authorization_code";
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new WxMappingJackson2HttpMessageConverter());
        WxLoginQueryVo wxLoginQueryVo = restTemplate.getForObject(loginUrl, WxLoginQueryVo.class);
        JSONObject userInfo = EncryptedUtil.getUserInfo(wxLoginQueryParam.getEncryptedData(),wxLoginQueryVo.getSession_key(),wxLoginQueryParam.getIv());
        if(userInfo!=null){
            WsUser wsUser =  JSONObject.toJavaObject(userInfo, WsUser.class);
            WsUser userExist = wsUserMapper.getWsUserByOpenId(wsUser.getOpenId());
            if(ObjectUtil.isNull(userExist)){
                wsUser.setDeleted(TrueFlagEnum.NO_FLAG.getCode());
                super.save(wsUser);
                wsUserQueryVo = WsUserQueryVo.toVo(wsUser);
            }else{
                BeanUtils.copyProperties(wsUser,userExist, ObjectUtils.getNullPropertyNames(wsUser));
                super.updateById(userExist);
                wsUserQueryVo = WsUserQueryVo.toVo(userExist);
            }
        }

        return wsUserQueryVo;
    }

}
