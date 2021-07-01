package com.io.yy.system.controller;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.io.yy.common.api.ApiResult;
import com.io.yy.constant.CommonRedisKey;
import com.io.yy.shiro.service.LoginService;
import com.io.yy.shiro.util.JwtTokenUtil;
import com.io.yy.shiro.vo.WxOpenQueryVo;
import com.io.yy.system.entity.MessageTextEntity;
import com.io.yy.system.entity.SysConfig;
import com.io.yy.system.entity.SysUser;
import com.io.yy.system.mapper.SysConfigMapper;
import com.io.yy.system.mapper.SysUserMapper;
import com.io.yy.system.param.QRcCodeQueryParam;
import com.io.yy.system.vo.LoginSysUserTokenVo;
import com.io.yy.system.vo.UserInfoVo;
import com.io.yy.system.vo.WxQRCodeResult;
import com.io.yy.util.SignUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Map;

/**
 * 微信接口开发
 * 2 * @Author: zhao
 * 3 * @Date: 2019/12/16 17:57
 * 4
 */
@RestController
@RequestMapping("/wx")
@Api("微信接口 API")
public class WxController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysConfigMapper sysConfigMapper;


    @RequestMapping("/add")
    @ResponseBody
    public Object add(HttpServletRequest request, HttpServletResponse response) {

        String method = request.getMethod();
        if (method.equals("GET")) {
            // get为微信平台校验
            String signature = request.getParameter("signature");
            String timestamp = request.getParameter("timestamp");
            String nonce = request.getParameter("nonce");
            String echostr = request.getParameter("echostr");
            if (StringUtils.isBlank(signature) || StringUtils.isBlank(timestamp)
                    || StringUtils.isBlank(nonce)) {
                String errorMsg = "参数为空，非微信平台请求";
                return errorMsg;
            }
            try {
                boolean b = false;
                // b = WXPublicUtils.verifyUrl(signature, timestamp, nonce);
                if (b) {
                    return echostr;
                } else {
                    return "token校验失败，非法请求";
                }
            } catch (Exception e) {
                e.printStackTrace();
                String errorMsg = "token校验失败，非法请求";
                return errorMsg;
            }
        }
        return null;
    }

    @GetMapping("/callback")
    @ResponseBody
    public void getCallback(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String signature = request.getParameter("signature");// 获得signature
        String timestamp = request.getParameter("timestamp");// 获得timestamp
        String nonce = request.getParameter("nonce");// 获得nonce
        String echostr = request.getParameter("echostr");// 获得echostr
        /*
         * 上面的四个参数都是微信服务器发送过来的，其中signature、timestamp、nonce是要参与服务器的验证的，
         * 而echostr是在我们通过验证后返回给服务器告诉服务器我们就是要通讯 的那个远程服务器
         */
        PrintWriter out = response.getWriter();
        if (SignUtil.checkSignature(signature, timestamp, nonce)) {//在SignUtil中使用checkSignature来进行验证，代码附在后面。
            out.print(echostr);//验证通过后就把echostr返回给微信服务器。
        }
        out.close();
        out = null;
    }

    @PostMapping("/callback")
    @ResponseBody
    public void callback(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PrintWriter out = response.getWriter();
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        InputStream in = request.getInputStream();
        String respMessage = "success";
        try {
            //获取解密后的明文xml
            String xml = SignUtil.decrypt(in, timestamp, nonce);
            //解析出明文xml文件标签中的值
            // xml请求解析
            Map<String, String> requestMap = SignUtil.xmlToMap(xml);
            // 发送方帐号（open_id）
            String fromUserName = requestMap.get("FromUserName");

            // 公众帐号
            String toUserName = requestMap.get("ToUserName");
            // 消息类型
            String msgType = requestMap.get("MsgType");

            MessageTextEntity messageTextEntity = new MessageTextEntity()
                    .setToUserName(fromUserName)
                    .setFromUserName(toUserName)
                    .setMsgType("text")
                    .setCreateTime(timestamp)
                    .setMsgId(6270699965354102946l);

            String respContent = "";
            // 事件类型
            String eventKey = requestMap.get("EventKey");
            if (msgType.equals(SignUtil.REQ_MESSAGE_TYPE_EVENT)) {
                //事件类型
                String eventType = requestMap.get("Event");
                //扫码后传入的scene_str场景字符串

                //微信用户信息
                UserInfoVo userInfo = SignUtil.getUserInfo(fromUserName);
                //已关注
                if (eventType.equals(SignUtil.EVENT_TYPE_SCAN)) {
                    if (StrUtil.isNotBlank(eventKey)) {
                        //如果是关注的授权的话，就查看数据库是否存在此用户，存在则把用户信息写入redis，让页面轮训，确定登陆
                        respContent = this.wxLogin(eventKey, userInfo);
                    } else {
                        //直接关注的判断有没有对应的学员账号，有的话就设置为已关注
                        respContent = this.getRespContent(eventKey);
                    }
                    messageTextEntity.setContent(respContent);
                    respMessage = SignUtil.beanToXml(messageTextEntity);
                    respMessage = SignUtil.encryption(respMessage, nonce, timestamp);
                } else if (eventType.equals(SignUtil.EVENT_TYPE_SUBSCRIBE)) {
                    if (StrUtil.isNotBlank(eventKey)) {
                        //未关注，点击关注
                        eventKey = eventKey.replaceAll("qrscene_", "");
                        respContent = this.wxLogin(eventKey, userInfo);
                    } else {
                        sysUserMapper.updateIsWxByOpenid(userInfo.getOpenid(),"1");
                        respContent = this.getRespContent(eventKey);
                    }
                    messageTextEntity.setContent(respContent);
                    respMessage = SignUtil.beanToXml(messageTextEntity);
                    respMessage = SignUtil.encryption(respMessage, nonce, timestamp);
                } else if (eventType.equals(SignUtil.EVENT_TYPE_UNSUBSCRIBE)) {
                    //为了杜绝公众号被取关后重新绑定用户，6-1改为不去掉unid，新增一个字段is_wx来控制，但是要确保在系统故障，修改不成功后的处理
                    sysUserMapper.updateIsWxByOpenid(userInfo.getOpenid(),"0");
                    // 取消关注,此时已经获取不到Unionid，所以在开发时候要存openid和Unionid，来确保删除用户的已关注记录
                    //WxOpenQueryVo wxOpenQueryVo = sysUserMapper.getSysUserByOpenid(userInfo.getUnionid());
                    //sysUserMapper.updateSysUserOpenidById(wxOpenQueryVo.getId(), null);
                }
            } else if (msgType.equals(SignUtil.EVENT_TYPE_TEXT)) {
                respContent = "加油，你是最棒的！！！\n";
                StringBuffer contentMsg = new StringBuffer();
                contentMsg.append("认真作业，好好学习，研究生离你不远了~").append("\n\n");
                respContent = respContent + contentMsg.toString();
                messageTextEntity.setContent(respContent);
                respMessage = SignUtil.beanToXml(messageTextEntity);
                respMessage = SignUtil.encryption(respMessage, nonce, timestamp);
            }

            //返回加密之后的字符串
            out.print(respMessage);
        } catch (Exception e) {
            out.print("success");
        } finally {
            out.close();
        }
    }

    @RequestMapping("/dataCallback")
    @ResponseBody
    public void dataCallback(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //校验token
        System.out.println(123);
    }


    /**
     * 获取二维码信息
     * @return
     */

    /**
     * 获取验证码
     */
    @GetMapping("/getQRCode/{code}")
    @ApiOperation(value = "获取验证码", notes = "获取验证码", response = ApiResult.class)
    public ApiResult<WxQRCodeResult> getQRCode(@PathVariable("code") String code) {
        if (StrUtil.isBlank(code)) {
            ApiResult.ok(false, "缺少code值");
        }
        QRcCodeQueryParam qRcCodeQueryParam = new QRcCodeQueryParam();
        Map<String, Object> mapActionInfo = MapUtil.newHashMap();
        Map<String, Object> mapScene = MapUtil.newHashMap();
        mapScene.put("scene_str", code);
        mapActionInfo.put("scene", mapScene);
        qRcCodeQueryParam.setAction_info(mapActionInfo);
        qRcCodeQueryParam.setAction_name(SignUtil.QR_STR_SCENE);
        //设置有效为5分钟
        qRcCodeQueryParam.setExpire_seconds(300);
        return ApiResult.ok(SignUtil.getQRCode(qRcCodeQueryParam));
    }

    @PostMapping("/checkLoginQr/{code}")
    @ApiOperation(value = "轮训确定是否登录", notes = "轮训确定是否登录", response = ApiResult.class)
    public ApiResult<WxQRCodeResult> checkLoginQr(@PathVariable("code") String code, HttpServletResponse response) {

        LoginSysUserTokenVo loginSysUserTokenVo = null;
        try {
            loginSysUserTokenVo = loginService.checkLoginQr(code);
            response.setHeader(JwtTokenUtil.getTokenName(), loginSysUserTokenVo.getToken());
            return ApiResult.ok(loginSysUserTokenVo, "登陆成功");
        } catch (Exception e) {
            return ApiResult.ok(false, e.getMessage());
        }
    }

    public String wxLogin(String eventKey, UserInfoVo userInfo) {
        //此处要加一个is_wx的判断
        WxOpenQueryVo wxOpenQueryVo = sysUserMapper.getSysUserByOpenid(userInfo.getUnionid());
        //判断是绑定还是登陆
        String[] eventKeys = eventKey.split(",");
        if (ObjectUtil.isNotNull(wxOpenQueryVo)) {
            if(wxOpenQueryVo.getIsWx().equals("0")){
                sysUserMapper.updateIsWxByOpenid(userInfo.getOpenid(), "1");
            }
            WxOpenQueryVo oldSceneStr = (WxOpenQueryVo) redisTemplate.opsForHash().get(CommonRedisKey.WX_LOGIN, eventKey);
            if (ObjectUtil.isNull(oldSceneStr)) {
                if(eventKeys.length>1){
                    wxOpenQueryVo.setType("1");
                }
                redisTemplate.opsForHash().put(CommonRedisKey.WX_LOGIN, eventKey, wxOpenQueryVo);
            }
            return this.getRespContent(eventKey);
        } else {
            if (eventKeys.length > 1) {
                //根据用户id获取用户判断用户是否有绑定过，有绑定过就判断open和unid是否一样，不一样就就要提醒不一致
                QueryWrapper<SysUser> sysUserWrapper = new QueryWrapper<>();
                sysUserWrapper.eq("id", eventKeys[1]);
                SysUser sysUser = sysUserMapper.selectOne(sysUserWrapper);
                if(StrUtil.isNotBlank(sysUser.getWxOpenid())){
                    if(sysUser.getWxOpenid().equals(userInfo.getUnionid())){
                        sysUserMapper.updateIsWxByOpenid(userInfo.getOpenid(), "1");
                    }else{
                        return "学员账号绑定的微信与初次绑定的微信不一致，请联系管理员后重试！";
                    }
                    wxOpenQueryVo = sysUserMapper.getSysUserByOpenid(userInfo.getUnionid());
                    redisTemplate.opsForHash().put(CommonRedisKey.WX_LOGIN, eventKey, wxOpenQueryVo);
                }else{
                    wxOpenQueryVo = new WxOpenQueryVo()
                            .setWxOpenid(userInfo.getUnionid())
                            .setIsWx("1")
                            .setWxOpenidTwo(userInfo.getOpenid())
                            .setId(Long.parseLong(eventKeys[1]))
                            .setType("1");
                    redisTemplate.opsForHash().put(CommonRedisKey.WX_LOGIN, eventKey, wxOpenQueryVo);
                }
                return this.getRespContent(eventKey);
            } else {
                //扫描登陆的时候查看openid
                wxOpenQueryVo = sysUserMapper.getSysUserByOpenidTwo(userInfo.getOpenid());
                if (ObjectUtil.isNotNull(wxOpenQueryVo)) {
                    Integer a = sysUserMapper.updateSysUserUnionidByOpenid(userInfo.getUnionid(), userInfo.getOpenid());
                    if (a > 0) {
                        wxOpenQueryVo.setWxOpenid(userInfo.getUnionid());
                        redisTemplate.opsForHash().put(CommonRedisKey.WX_LOGIN, eventKey, wxOpenQueryVo);
                    }
                    return this.getRespContent(eventKey);
                } else {
                    return "感谢关注，绑定学员账号后可扫码登录哦！";
                }
            }
        }
    }

    public String getRespContent(String eventKey) {
        String respContent = "";
        QueryWrapper<SysConfig> sysConfigQueryWrapper = new QueryWrapper<>();
        sysConfigQueryWrapper.eq("config_key", "wx_login_infor_message");
        SysConfig sysConfig = sysConfigMapper.selectOne(sysConfigQueryWrapper);
        if (StrUtil.isBlank(eventKey)) {
            respContent = "认真作业，好好学习，研究生离你不远了~";
        } else if (ObjectUtil.isNotNull(sysConfig)) {
            respContent = sysConfig.getConfigTextValue();
        } else {
            respContent = "登陆成功！\n";
            StringBuffer contentMsg = new StringBuffer();
            contentMsg.append("认真作业，好好学习，研究生离你不远了~").append("\n\n");
            respContent = respContent + contentMsg.toString();
        }
        return respContent;
    }
}
