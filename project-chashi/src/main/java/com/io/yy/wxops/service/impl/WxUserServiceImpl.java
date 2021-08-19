package com.io.yy.wxops.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import com.io.yy.marketing.entity.CsCouponReleased;
import com.io.yy.marketing.mapper.CsCouponMapper;
import com.io.yy.marketing.service.CsCouponReleasedService;
import com.io.yy.marketing.service.CsCouponService;
import com.io.yy.marketing.vo.CsCouponQueryVo;
import com.io.yy.system.vo.SysConfigDataRedisVo;
import com.io.yy.util.ConfigDataUtil;
import com.io.yy.util.lang.ObjectUtils;
import com.io.yy.util.lang.StringUtils;
import com.io.yy.util.reflect.ReflectUtils;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.client.RestTemplate;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    @Autowired
    private CsCouponReleasedService csCouponReleasedService;

    @Autowired
    private CsCouponService csCouponService;

    @Autowired
    private RedisTemplate redisTemplate;

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

    public WxUserQueryVo wxLogin(WxLoginQueryParam wxLoginQueryParam) throws Exception {
        boolean flag = false;
        WxUserQueryVo wxUserQueryVo = new WxUserQueryVo();
        // 获取微信配置
        List<SysConfigDataRedisVo> sysConfigDataList = ConfigDataUtil.getAllSysConfigData();

        List<SysConfigDataRedisVo> appids = sysConfigDataList.stream().filter(item -> item.getConfigKey().equals("appid")).collect(Collectors.toList());
        List<SysConfigDataRedisVo> appSecrets = sysConfigDataList.stream().filter(item -> item.getConfigKey().equals("appSecret")).collect(Collectors.toList());
        List<SysConfigDataRedisVo> loginUrlappids = sysConfigDataList.stream().filter(item -> item.getConfigKey().equals("loginUrl")).collect(Collectors.toList());

        String sessionKey = wxLoginQueryParam.getSession_key();
        String openid = wxLoginQueryParam.getOpenid();
        String unionid = wxLoginQueryParam.getUnionid();
        if(StringUtils.isEmpty(sessionKey)){
            String appid = appids.get(0).getConfigValue();
            String appSecret =appSecrets.get(0).getConfigValue();
            String loginUrl = loginUrlappids.get(0).getConfigValue();
            loginUrl = loginUrl+"?"+"appid="+appid+"&secret="+appSecret+"&js_code="+wxLoginQueryParam.getJsCode()+"&grant_type=authorization_code";
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new WxMappingJackson2HttpMessageConverter());
            WxLoginQueryVo wxLoginQueryVo = restTemplate.getForObject(loginUrl, WxLoginQueryVo.class);
            sessionKey = wxLoginQueryVo.getSession_key();
            openid = wxLoginQueryVo.getOpenid();
            unionid = wxLoginQueryVo.getUnionid();
        }
        JSONObject userInfo = EncryptedUtil.getUserInfo(wxLoginQueryParam.getEncryptedData(),sessionKey,wxLoginQueryParam.getIv());
        System.out.println(userInfo);
        if(userInfo!=null){
            WxUser wxUser =  JSONObject.toJavaObject(userInfo, WxUser.class);
            wxUser.setOpenid(openid);
            wxUser.setUnionid(unionid);
            WxUser userExist = wxUserMapper.getWxUserByOpenId(wxUser.getOpenid());
            if(ObjectUtil.isNull(userExist)){
                wxUser.setDeleted(TrueFlagEnum.NO_FLAG.getCode());
                flag = super.save(wxUser);
                wxUserQueryVo = WxUserQueryVo.toVo(wxUser);
                //如果保存成功，查看是否有新人优惠卷，有的话发放
                if(flag){
                    WxUser newUser = wxUserMapper.getWxUserByOpenId(wxUser.getOpenid());
                    List<CsCouponQueryVo> csCouponQueryVos = csCouponService.getCsCouponOfNewMember();
                    csCouponQueryVos.stream().forEach(cc -> {
                        CsCouponReleased csCouponReleased = new CsCouponReleased();
                        csCouponReleased.setCouponId(cc.getId());
                        csCouponReleased.setWxuserId(newUser.getId());
                        csCouponReleased.setIsUsed(0);
                        csCouponReleased.setReleasedTime(new Date());
                        try {
                            csCouponReleasedService.saveCsCouponReleased(csCouponReleased);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                }
            }else{
                BeanUtils.copyProperties(wxUser,userExist, ObjectUtils.getNullPropertyNames(wxUser));
                super.updateById(userExist);
                wxUserQueryVo = WxUserQueryVo.toVo(userExist);
            }
        }
        return wxUserQueryVo;
    }

    /**
     * 根据openid获取查询对象
     *
     * @param openid
     * @return
     * @throws Exception
     */
    @Override
    public WxUserQueryVo getWxUserByOpenid(Serializable openid) throws Exception {
        return wxUserMapper.getWxUserObjByOpenid(openid);
    }


    /**
     * 微信登录，获取sessinonKey
     * @param wxLoginQueryParam
     * @return
     * @throws Exception
     */
    public WxLoginQueryVo wxLoginForSessionKey(WxLoginQueryParam wxLoginQueryParam) throws Exception {
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
        if(wxLoginQueryVo!=null){
            // 小程序 wx.checksession通过，currentSessionObj为空时。重新从服务端缓存中获取标识。1 为重新获取
            if(StringUtils.isNotEmpty(wxLoginQueryParam.getReGetFlag()) && wxLoginQueryParam.getReGetFlag().equals("1")){
                WxLoginQueryVo wxLoginQueryVoTemp = (WxLoginQueryVo) redisTemplate.opsForValue().get(wxLoginQueryVo.getOpenid());
                if(wxLoginQueryVoTemp!=null){
                    wxLoginQueryVo = wxLoginQueryVoTemp;
                }else{
                    redisTemplate.opsForValue().set(wxLoginQueryVo.getOpenid(), wxLoginQueryVo);
                }
            }else{
                redisTemplate.opsForValue().set(wxLoginQueryVo.getOpenid(), wxLoginQueryVo);
            }
            return wxLoginQueryVo;
        }
        return null;
    }

    /**
     * 更新用户头像跟昵称
     * @param wxLoginQueryParam
     * @return
     * @throws Exception
     */
    public WxUserQueryVo wxUserUpdateNickName(WxLoginQueryParam wxLoginQueryParam) throws Exception {
        WxUserQueryVo wxUserQueryVo = new WxUserQueryVo();
        WxUser userExist = wxUserMapper.getWxUserByOpenId(wxLoginQueryParam.getOpenid());
        if(userExist!=null){
            userExist.setAvatarUrl(wxLoginQueryParam.getAvatarUrl());
            userExist.setNickname(wxLoginQueryParam.getNickName());
            super.updateById(userExist);
            wxUserQueryVo = WxUserQueryVo.toVo(userExist);
        }
        return wxUserQueryVo;
    }

    /**
     * 更新余额和积分
     *
     * @param wxUserQueryParam
     * @return
     */
    @Override
    public Integer updateBalanceAIntegral(WxUserQueryParam wxUserQueryParam) {
        return wxUserMapper.updateBalanceAIntegral(wxUserQueryParam);
    }

}
