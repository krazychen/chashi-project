package com.io.yy.wxops.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import com.io.yy.system.vo.SysConfigDataRedisVo;
import com.io.yy.util.ConfigDataUtil;
import com.io.yy.util.lang.ObjectUtils;
import com.io.yy.wxops.entity.WxUser;
import com.io.yy.wxops.mapper.WxUserMapper;
import com.io.yy.wxops.param.WxLoginQueryParam;
import com.io.yy.wxops.service.WxUserService;
import com.io.yy.wxops.param.WxUserQueryParam;
import com.io.yy.wxops.utils.EncryptedUtil;
import com.io.yy.wxops.utils.TrueFlagEnum;
import com.io.yy.wxops.utils.WxMappingJackson2HttpMessageConverter;
import com.io.yy.wxops.vo.WxLoginQueryVo;
import com.io.yy.wxops.vo.WxUserQueryVo;
import com.io.yy.common.service.impl.BaseServiceImpl;
import com.io.yy.common.vo.Paging;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.client.RestTemplate;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;


/**
 * <pre>
 * 微信用户 服务实现类
 * </pre>
 *
 * @author kris
 * @since 2021-08-06
 */
@Slf4j
@Service
public class WxUserServiceImpl extends BaseServiceImpl<WxUserMapper, WxUser> implements WxUserService {

    @Autowired
    private WxUserMapper wxUserMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveWxUser(WxUser wxUser) throws Exception {
        return super.save(wxUser);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateWxUser(WxUser wxUser) throws Exception {
        return super.updateById(wxUser);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteWxUser(Long id) throws Exception {
        return super.removeById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteWxUsers(List<String> idList) throws Exception {
    return super.removeByIds(idList);
    }

    @Override
    public WxUserQueryVo getWxUserById(Serializable id) throws Exception {
        return wxUserMapper.getWxUserById(id);
    }

    @Override
    public Paging<WxUserQueryVo> getWxUserPageList(WxUserQueryParam wxUserQueryParam) throws Exception {
        Page page = setPageParam(wxUserQueryParam, OrderItem.desc("create_time"));
        IPage<WxUserQueryVo> iPage = wxUserMapper.getWxUserPageList(page, wxUserQueryParam);
        return new Paging(iPage);
    }

    @Override
    public boolean updateStatus(WxUserQueryParam wxUserQueryParam) {
        return wxUserMapper.updateStatus(wxUserQueryParam) > 0;
    }

    public WxUserQueryVo wxLogin(WxLoginQueryParam wxLoginQueryParam){

        WxUserQueryVo wxUserQueryVo = new WxUserQueryVo();
        // 获取微信配置
        List<SysConfigDataRedisVo> sysConfigDataList = ConfigDataUtil.getAllSysConfigData();

        List<SysConfigDataRedisVo> appids = sysConfigDataList.stream().filter(item -> item.getConfigKey().equals("appid")).collect(Collectors.toList());
        List<SysConfigDataRedisVo> appSecrets = sysConfigDataList.stream().filter(item -> item.getConfigKey().equals("appSecret")).collect(Collectors.toList());
        List<SysConfigDataRedisVo> loginUrlappids = sysConfigDataList.stream().filter(item -> item.getConfigKey().equals("loginUrl")).collect(Collectors.toList());

        String appid = appids.get(0).getConfigValue();
        String appSecret =appSecrets.get(0).getConfigValue();
        String loginUrl = loginUrlappids.get(0).getConfigValue();
        loginUrl = loginUrl+"?"+"appid="+appid+"&secret="+appSecret+"&js_code="+wxLoginQueryParam.getJsCode()+"&grant_type=authorization_code";
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new WxMappingJackson2HttpMessageConverter());
        WxLoginQueryVo wxLoginQueryVo = restTemplate.getForObject(loginUrl, WxLoginQueryVo.class);
        JSONObject userInfo = EncryptedUtil.getUserInfo(wxLoginQueryParam.getEncryptedData(),wxLoginQueryVo.getSession_key(),wxLoginQueryParam.getIv());
        System.out.println(userInfo);
        if(userInfo!=null){
            WxUser wxUser =  JSONObject.toJavaObject(userInfo, WxUser.class);
            WxUser userExist = wxUserMapper.getWxUserByOpenId(wxUser.getOpenId());
            if(ObjectUtil.isNull(userExist)){
                wxUser.setDeleted(TrueFlagEnum.NO_FLAG.getCode());
                super.save(wxUser);
                wxUserQueryVo = WxUserQueryVo.toVo(wxUser);
            }else{
                BeanUtils.copyProperties(wxUser,userExist, ObjectUtils.getNullPropertyNames(wxUser));
                super.updateById(userExist);
                wxUserQueryVo = WxUserQueryVo.toVo(userExist);
            }
        }

        return wxUserQueryVo;
    }
}
