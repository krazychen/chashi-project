package com.io.yy.system.controller;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.io.yy.system.entity.WxMpProperties;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 2 * @Author: zhao
 * 3 * @Date: 2019/12/16 18:39
 * 4
 */
@Slf4j
@RestController
@RequestMapping("/wx")
//@EnableConfigurationProperties(WxMpProperties.class)
public class WxMpConfiguration {
    private static Map<String, WxMpService> mpServices = Maps.newHashMap();

    public static Map<String, WxMpService> getMpServices() {
        return mpServices;
    }

   /* @Autowired
    private WxMpProperties properties;*/

   /* @Autowired
    private WxMpInRedisConfigStorageMy configStorage;*/

   /* @PostConstruct
    public void initServices() {

        final List<WxMpProperties.MpConfig> configs = this.properties.getConfigs();
        if (configs == null) {
            throw new RuntimeException("");
        }

        mpServices = configs.stream().map(a -> {
            //redis
//            configStorage.setAppId(a.getAppId());
//            configStorage.setSecret(a.getSecret());
//            configStorage.setToken(a.getToken());
//            configStorage.setAesKey(a.getAesKey());

            WxMpService service = new WxMpServiceImpl();
            //service.setWxMpConfigStorage(configStorage);
            log.info("配置的appId={}",a.getAppId());
            return service;
        }).collect(Collectors.toMap(s -> s.getWxMpConfigStorage().getAppId(), a -> a, (o, n) -> o));
    }
*/
    /**
     * 获取用户信息
     *
     */
    @RequestMapping("/getUserInfo")
    public void getBase(HttpServletRequest request, HttpServletResponse response, @PathVariable String appid, @RequestParam("code") String code) {
        WxMpService mpService = WxMpConfiguration.getMpServices().get(appid);
        try {
            WxMpOAuth2AccessToken accessToken = mpService.oauth2getAccessToken(code);
            log.info("accessToken={}", JSON.toJSONString(accessToken));
            WxMpUser wxMpUser = mpService.oauth2getUserInfo(accessToken, null);
           log.info("wxMpUser={}", JSON.toJSONString(wxMpUser));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
