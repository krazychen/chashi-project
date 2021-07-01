package com.io.yy.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.setting.Setting;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.io.yy.common.exception.BusinessException;
import com.io.yy.common.service.impl.BaseServiceImpl;
import com.io.yy.common.vo.Paging;
import com.io.yy.job.entity.SysScheduleJob;
import com.io.yy.job.util.ScheduleManager;
import com.io.yy.shiro.util.LoginUtil;
import com.io.yy.system.entity.SysMsg;
import com.io.yy.system.entity.SysMsgRecord;
import com.io.yy.system.entity.SysUser;
import com.io.yy.system.mapper.SysMsgMapper;
import com.io.yy.system.mapper.SysMsgRecordMapper;
import com.io.yy.system.mapper.SysRoleMapper;
import com.io.yy.system.mapper.SysUserMapper;
import com.io.yy.system.param.StudentSysMsgQueryParam;
import com.io.yy.system.param.SysMsgQueryParam;
import com.io.yy.system.service.SysMsgRecordService;
import com.io.yy.system.service.SysMsgService;
import com.io.yy.system.vo.*;
import com.io.yy.util.SignUtil;
import com.io.yy.util.collect.ListUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.*;

//import com.io.yy.homework.entity.HwHomework;
//import com.io.yy.homework.mapper.HwStudentClassMapper;


/**
 * <pre>
 * 系统消息表 服务实现类
 * </pre>
 *
 * @author zhaochao
 * @since 2019-12-17
 */
@Slf4j
@Service
public class SysMsgServiceImpl extends BaseServiceImpl<SysMsgMapper, SysMsg> implements SysMsgService {

    @Autowired
    private SysMsgMapper sysMsgMapper;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private SysMsgRecordServiceImpl sysMsgRecordServiceImpl;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysMsgRecordMapper sysMsgRecordMapper;

    @Autowired
    private SysRoleMapper getSysRoleMapper;

    @Autowired
    private SysMsgRecordService sysMsgRecordService;

//    @Autowired
//    private HwStudentClassMapper hwStudentClassMapper;

    @Autowired
    private ScheduleManager scheduleManager;

    public static Setting setting = new Setting("wxProperties/wxWeb.properties");


    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveSysMsg(SysMsg sysMsg) throws Exception {
        boolean b = false;
        Date date = new Date();
        if (sysMsg.getNotifiedType().equals("0")) {
            sysMsg.setNotifiedTime(date);
        } else {
            if (sysMsg.getNotifiedTime().getTime() <= date.getTime()) {
                throw new BusinessException("定时时间不能小于当前时间");
            }
        }
        if (ObjectUtil.isNull(sysMsg.getPublishTime())) {
            sysMsg.setPublishTime(date);
        }
        if (sysMsg.getStatus().equals("1")) {
            sysMsg.setSendUserName(LoginUtil.getUsername())
                    .setSendUserId(LoginUtil.getUserId());
        }
        b = super.save(sysMsg);
        if (b) {
            if (sysMsg.getStatus().equals("1")) {
                if (sysMsg.getNotifiedType().equals("0")) {
                    this.publishMsg(sysMsg);
                } else {
                    //定时发送通知
                    //根据job名称获取job
                    SysScheduleJob sysScheduleJobOld = scheduleManager.getJobByJobName("sysMsg" + sysMsg.getId());
                    SysScheduleJob sysScheduleJob = new SysScheduleJob();
                    sysScheduleJob.setParams(sysMsg.getId() + "");
                    sysScheduleJob.setJobName("sysMsg" + sysMsg.getId());
                    sysScheduleJob.setJobGroup("sysMsgGroup");
                    sysScheduleJob.setJobType("cron");
                    sysScheduleJob.setBeanName("SysMsgRecordTask");
                    SimpleDateFormat sdf = new SimpleDateFormat("ss mm HH dd MM ? yyyy");
                    String cronExpression = null;
                    if (ObjectUtil.isNotNull(sysMsg.getNotifiedTime())) {
                        cronExpression = sdf.format(sysMsg.getNotifiedTime());
                    }
                    sysScheduleJob.setCronExpression(cronExpression);
                    //若job已经存在则更新这个ob，否则直接添加job
                    if (ObjectUtil.isNotNull(sysScheduleJobOld)) {
                        sysScheduleJob = scheduleManager.updateJob(sysScheduleJobOld, sysScheduleJob);
                    } else {
                        sysScheduleJob = scheduleManager.createJobByTaskLog(sysScheduleJob, null);
                    }
                    return ObjectUtil.isNotNull(sysScheduleJob);
                }
            }
        }
        return b;
    }

//    /**
//     * 下发通知
//     *
//     * @param hwHomework
//     * @param userIds
//     * @return
//     * @throws Exception
//     */
//    public Boolean sendMsg(HwHomework hwHomework, String userIds) throws Exception {
//        System.out.println("执行了一次--------------------------------------------------------");
//        Date date = new Date();
//        SysMsg sysMsg = new SysMsg()
//                .setMsgContent("您有新的作业,可在个人中心查看详情")
//                .setMsgLevelCode("1")
//                .setMsgNotifyObjCode("USER")
//                .setMsgNotifyTypeCode("1")
//                .setMsgTitle("您有新的作业")
//                .setMsgTypeCode("1")
//                .setPublishTime(date)
//                .setUpdateBy("1")
//                .setDeleted(0)
//                .setUpdateTime(date).setCreateBy("1")
//                .setCreateTime(date).setSendUserId(1l)
//                .setSendUserName("admin")
//                .setStatus("1")
//                .setCancelTime(date);
//        if (StrUtil.isBlank(userIds)) {
//            if (ObjectUtil.isNotNull(hwHomework) && StrUtil.isNotBlank(hwHomework.getClassId().toString())) {
//                List<Long> listDbStu = hwStudentClassMapper.getHwStudentClassClassId(hwHomework.getClassId());
//                userIds = "";
//                for (Long id : listDbStu) {
//                    userIds = ((userIds + ",").equals(",") ? "" : (userIds + ",")) + id;
//                }
//            }
//        }
//        sysMsg.setUserIds(userIds);
//        return this.saveSysMsg(sysMsg);
//    }

    @Override
    public Boolean saveSysMsgRecord(String msgId) throws Exception {
        SysMsg sysMsg = getById(msgId);
        boolean b = saveSysMsgRecord(sysMsg);
        System.out.println("定时发布成功！==============================================================================");
        return b;
    }

    @Override
    public Paging<SysMsgQueryVo> getStudentPageList(StudentSysMsgQueryParam studentSysMsgQueryParam) {

        Page page = setPageParam(studentSysMsgQueryParam, OrderItem.desc("m.create_time"));
        IPage<SysMsgQueryVo> iPage = sysMsgMapper.getStudentPageList(page, studentSysMsgQueryParam);
        return new Paging(iPage);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean publish(Long id) throws Exception {
        SysMsg msg = super.getById(id);
        msg.setStatus("1");
        //sysMsgMapper.updateById(msg);
        return this.updateSysMsg(msg);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateSysMsg(SysMsg sysMsg) throws Exception {
        SysMsg msg = getById(sysMsg.getId());
/*        if (msg.getStatus().equals("1")) {
            throw new BusinessException("消息已发布，不能编辑!");
        }*/
        Date date = new Date();
        if (sysMsg.getNotifiedType().equals("0")) {
            sysMsg.setNotifiedTime(date);
        } else {
            if (sysMsg.getNotifiedTime().getTime() <= date.getTime()) {
                throw new BusinessException("定时时间不能小于当前时间");
            }
        }
        if (ObjectUtil.isNull(sysMsg.getPublishTime())) {
            sysMsg.setPublishTime(date);
        }
        if (sysMsg.getStatus().equals("1")) {
            sysMsg.setSendUserName(LoginUtil.getUsername())
                    .setSendUserId(LoginUtil.getUserId())
                    .setPublishTime(date);
        }
        boolean b = super.updateById(sysMsg);
        if (b) { //修改成功
            if (sysMsg.getStatus().equals("1")) {
                if (sysMsg.getNotifiedType().equals("0")) {
                    this.publishMsg(sysMsg);
                } else {
                    //定时发送通知
                    //根据job名称获取job
                    SysScheduleJob sysScheduleJobOld = scheduleManager.getJobByJobName("sysMsg" + sysMsg.getId());
                    SysScheduleJob sysScheduleJob = new SysScheduleJob();
                    sysScheduleJob.setParams(sysMsg.getId() + "");
                    sysScheduleJob.setJobName("sysMsg" + sysMsg.getId());
                    sysScheduleJob.setJobGroup("sysMsgGroup");
                    sysScheduleJob.setJobType("cron");
                    sysScheduleJob.setBeanName("SysMsgRecordTask");
                    SimpleDateFormat sdf = new SimpleDateFormat("ss mm HH dd MM ? yyyy");
                    String cronExpression = null;
                    if (ObjectUtil.isNotNull(sysMsg.getNotifiedTime())) {
                        cronExpression = sdf.format(sysMsg.getNotifiedTime());
                    }
                    sysScheduleJob.setCronExpression(cronExpression);
                    //若job已经存在则更新这个ob，否则直接添加job
                    if (ObjectUtil.isNotNull(sysScheduleJobOld)) {
                        sysScheduleJob = scheduleManager.updateJob(sysScheduleJobOld, sysScheduleJob);
                    } else {
                        sysScheduleJob = scheduleManager.createJobByTaskLog(sysScheduleJob, null);
                    }
                    return ObjectUtil.isNotNull(sysScheduleJob);
                }
            }
        }
        return b;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteSysMsg(Long id) throws Exception {
        SysMsg sysMsg = getById(id);
        if (sysMsg.getStatus().equals("1")) {
            throw new BusinessException("消息已发布，不能删除!");
        }
        return super.removeById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteSysMsgs(List<String> idList) throws Exception {
        return super.removeByIds(idList);
    }

    @Override
    public SysMsgQueryVo getSysMsgById(Long id) throws Exception {
        return sysMsgMapper.getSysMsgById(id);
    }

    @Override
    public Paging<SysMsgQueryVo> getSysMsgPageList(SysMsgQueryParam sysMsgQueryParam) throws Exception {
        Page page = setPageParam(sysMsgQueryParam, OrderItem.desc("create_time"));
        IPage<SysMsgQueryVo> iPage = sysMsgMapper.getSysMsgPageList(page, sysMsgQueryParam);
        return new Paging(iPage);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean publishMsg(SysMsg sysMsg) throws Exception {
        boolean b = false;
        List<SysMsgRecord> sysMsgRecords = CollUtil.newArrayList();
        List<SysUser> listUser = null;
        if (sysMsg.getMsgNotifyObjCode().equals("user")) {
            if (StrUtil.isNotBlank(sysMsg.getUserIds())) {
                String[] userIds = sysMsg.getUserIds().split(",");
                QueryWrapper<SysUser> sysUserQueryWrapper = new QueryWrapper<>();
                sysUserQueryWrapper.select(new String[]{"username", "id", "wx_openid", "wx_openid_two"}).in("id", userIds).eq("state",1);
                listUser = sysUserMapper.selectList(sysUserQueryWrapper);
            } else {
                throw new BusinessException("未选择用户！");
            }
        } else if (sysMsg.getMsgNotifyObjCode().equals("roles")) {
            if (StrUtil.isNotBlank(sysMsg.getRoleIds())) {
                SysRoleQueryVo sysRoleQueryVo = getSysRoleMapper.getSysRoleById(sysMsg.getRoleIds());
                if (ObjectUtil.isNull(sysRoleQueryVo)) {
                    throw new BusinessException("角色不存在");
                }
                if (sysRoleQueryVo.getName().equals("一级老师")) {
                    //根据班级和题型获取对应的所有一级老师
                    if (StrUtil.isBlank(sysMsg.getClassIds())) {
                        throw new BusinessException("未选择班级");
                    }
                    if (StrUtil.isBlank(sysMsg.getClassSubjectIds())) {
                        throw new BusinessException("未选择题型");
                    }
                    String[] classIds = sysMsg.getClassIds().split(",");
                    String[] classSubjectIds = sysMsg.getClassSubjectIds().split(",");
                    listUser = sysUserMapper.getMsgListUser(classIds, classSubjectIds, sysMsg.getRoleIds());
                } else if (sysRoleQueryVo.getName().equals("学员")) {
                    //根据班级，获取班级下所有的学员
                    if (StrUtil.isBlank(sysMsg.getClassIds())) {
                        throw new BusinessException("未选择班级");
                    }
                    String[] classIds = sysMsg.getClassIds().split(",");
                    listUser = sysUserMapper.getStudentListUserByClassIds(classIds);
                } else {
                    //根据角色获取对应的用户
                    listUser = sysUserMapper.getListUserByRoleId(sysMsg.getRoleIds());
                }
            }
        } else if (sysMsg.getMsgNotifyObjCode().equals("classs")) {
            //获取班级对应的所有用户
            if (StrUtil.isBlank(sysMsg.getClassIds())) {
                throw new BusinessException("未选择班级");
            }
            String[] classIds = sysMsg.getClassIds().split(",");
            listUser = sysUserMapper.getTeacherListUserByClassIds(classIds);
            listUser.addAll(sysUserMapper.getStudentListUserByClassIds(classIds));
        } else if (sysMsg.getMsgNotifyObjCode().equals("all")) {
            //获取所有用户
            QueryWrapper<SysUser> sysUserQueryWrapper = new QueryWrapper<>();
            sysUserQueryWrapper.select(new String[]{"username", "id", "wx_openid", "wx_openid_two"}).eq("state",1);
            listUser = sysUserMapper.selectList(sysUserQueryWrapper);
        } else if (sysMsg.getMsgNotifyObjCode().equals("homework")) {
            //获取作业对应的所有学员
            if (StrUtil.isBlank(sysMsg.getHomeworkIds())) {
                throw new BusinessException("未选择题目");
            }
            String[] homeworkIds = sysMsg.getHomeworkIds().split(",");
            listUser = sysUserMapper.getlistUserByHomeworkIds(homeworkIds);
        }
        Boolean isWx = false;
        List<String> listUserWx = null;
        //发布微信消息
        if (StrUtil.isNotBlank(sysMsg.getMsgNotifyTypeCode()) && sysMsg.getMsgNotifyTypeCode().indexOf("2") != -1) {
            isWx = true;
            listUserWx = CollUtil.newArrayList();
        }
        //遍历listUser组装sysMsgRecord
        for (SysUser sysUser : listUser) {
            if (isWx && StrUtil.isNotBlank(sysUser.getWxOpenidTwo())) {
                listUserWx.add(sysUser.getWxOpenidTwo());
            }
            SysMsgRecord sysMsgRecord = new SysMsgRecord();
            sysMsgRecord.setReceiveUserId(sysUser.getId())
                    .setReceiveUserName(sysUser.getUsername())
                    .setStatus("2")
                    .setMsgId(sysMsg.getId())
                    .setMsgNotifyTypeCode(sysMsg.getMsgNotifyTypeCode())
                    .setMsgTypeCode(sysMsg.getMsgTypeCode());
            sysMsgRecords.add(sysMsgRecord);
        }

        //保存sysMsgRecord
        if (CollUtil.isNotEmpty(sysMsgRecords)) {
            b = sysMsgRecordService.saveBatch(sysMsgRecords);
        }
        //发送微信模板消息
        if (CollUtil.isNotEmpty(listUserWx)) {
            try {
                List<String> finalListUserWx = listUserWx;
                new Thread() {
                    public void run() {
                        sendWxTemplate(finalListUserWx, sysMsg);
                    }
                }.start();
            } catch (Exception e) {

            }
        }
        return b;
    }

    /**
     * 发送微信模板消息
     *
     * @param listUserWx
     * @param sysMsg
     */
    public void sendWxTemplate(List<String> listUserWx, SysMsg sysMsg) {
        SysMessageAndWxTemplateQueryVo sysMessageAndWxTemplateQueryVo = sysMsgRecordMapper.getWxTemplate(sysMsg.getConfigId());
        if (ObjectUtil.isNotNull(sysMessageAndWxTemplateQueryVo)) {
            String url = setting.get("templateUrl");
            String accessToken = SignUtil.getAccessToken();
            String urlend = url + accessToken;
            Map<String, Object> data = this.getMappingData(sysMessageAndWxTemplateQueryVo, sysMsg.getPublishTime());
            data.put("url", sysMsg.getHyperLinks());
            HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(data);
            HttpMethod method = HttpMethod.POST;
            for (String openId : listUserWx) {
                data.put("touser", openId);
                ResponseEntity<SysWxTemplateMessageVo> response = null;
                response = restTemplate.exchange(urlend, method, requestEntity, SysWxTemplateMessageVo.class);
                SysWxTemplateMessageVo wxTemplateMessageVo = response.getBody();
                //如果是因为不是最新的access_token，则重新获取
                if (wxTemplateMessageVo.getErrcode() != 0 && wxTemplateMessageVo.getErrcode() == 40001 && wxTemplateMessageVo.getErrmsg().indexOf("access_token") != -1) {
                    accessToken = SignUtil.getAccessTokenRealTime();
                    urlend = url + accessToken;
                    response = restTemplate.exchange(urlend, method, requestEntity, SysWxTemplateMessageVo.class);
                }
            }
        }
    }

    /**
     * 组装微信模板数据
     *
     * @param sysMessageAndWxTemplateQueryVo
     * @return
     */
    public Map<String, Object> getMappingData(SysMessageAndWxTemplateQueryVo sysMessageAndWxTemplateQueryVo, Date date) {
        String template_id = sysMessageAndWxTemplateQueryVo.getWxConfigId();
        Map<String, Object> data = MapUtil.newHashMap();
        data.put("template_id", template_id);
        Map<String, Object> dataMap = MapUtil.newHashMap();
        for (SysMessageWxTemplateQueryVo hwMessageWxTemplate : sysMessageAndWxTemplateQueryVo.getTableData()) {
            Map<String, Object> dataValue = MapUtil.newHashMap();
            //判断类型,如果为1的时候则为文本，直接赋值，否则判断对象，进行映射
            if (hwMessageWxTemplate.getType().equals("1")) {
                if (hwMessageWxTemplate.getTeimplateFiledObj().equals("当前时间")) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String value = sdf.format(date);
                    dataValue.put("value", value);
                } else {
                    dataValue.put("value", hwMessageWxTemplate.getTeimplateFiledObj());
                }
            }
            dataMap.put(hwMessageWxTemplate.getTemplateField(), dataValue);

        }
        data.put("data", dataMap);
        return data;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean cancelMsg(Long id) throws Exception {
        //撤销消息需要把消息记录表的记录全部删除
        sysMsgRecordMapper.deleteSysMsgRecordByMsgId(id);
        //删除成功后需要把消息表状态改成未发布
        SysMsg sysMsg = getById(id);
        sysMsg.setStatus("2")
                .setCancelTime(new Date());
        boolean b = sysMsgMapper.updateById(sysMsg) > 0;
        return b;
    }


    public boolean saveSysMsgRecord(SysMsg sysMsg) throws Exception {
        Map<Long, String> hm = new HashMap<>();
        if (sysMsg.getMsgNotifyObjCode().equals("ALL")) { //默认发布对象全体用户
            QueryWrapper<SysUser> userQueryWrapper = new QueryWrapper<>();
            List<SysUser> sysUsers = sysUserMapper.selectList(userQueryWrapper);
            for (SysUser user : sysUsers) {
                hm.put(user.getId(), user.getUsername());
            }
        } else if (sysMsg.getMsgNotifyObjCode().equals("USER")) { //发布对象为指定用户
            List<Long> ids = new ArrayList<>();
            String[] split = sysMsg.getUserIds().split(",");
            for (String id : split) {
                if (id != null && !id.equals("")) {
                    ids.add(Long.valueOf(id));
                }
            }
            if (ListUtils.isNotEmpty(ids)) {
                List<SysUser> sysUsers = sysUserMapper.selectBatchIds(ids);
                for (SysUser user : sysUsers) {
                    hm.put(user.getId(), user.getUsername());
                }
            }
        } else if (sysMsg.getMsgNotifyObjCode().equals("roles")) { //发布对象为指定角色，根据角色查找用户
            String roles = sysMsg.getRoleIds().substring(0, sysMsg.getRoleIds().length() - 1);
            List<SysUser> sysUsers = sysUserMapper.getUserByRoleIds(roles);
            for (SysUser user : sysUsers) {
                hm.put(user.getId(), user.getUsername());
            }
        }/*else if(sysMsg.getMsgNotifyObjCode().equals("classs")){ //发布对象为指定班级，根据班级角色查找用户
            String classRoleIds = sysMsg.getClassRoleIds().substring(0, sysMsg.getClassRoleIds().length() - 1);
            List<SysUser> sysUsers = sysUserMapper.getUserByRoleIds(classRoleIds);
            for(SysUser user : sysUsers){
                hm.put(user.getId(),user.getUsername());
            }
        }*/
        String[] msgNotifyTypeCodes = sysMsg.getMsgNotifyTypeCode().split(",");
        List<SysMsgRecord> sysMsgRecordList = new ArrayList<>();
        if (msgNotifyTypeCodes.length > 0) { //通知方式有多种
            for (int i = 0; i < msgNotifyTypeCodes.length; i++) {
                for (Map.Entry<Long, String> entry : hm.entrySet()) {
                    SysMsgRecord sysMsgRecord = new SysMsgRecord()
                            .setMsgId(sysMsg.getId())
                            .setMsgLevelCode(sysMsg.getMsgLevelCode())
                            .setMsgNotifyTypeCode(msgNotifyTypeCodes[i])
                            .setReceiveUserId(Long.valueOf(entry.getKey()))
                            .setReceiveUserName(entry.getValue())
                            .setMsgTypeCode(sysMsg.getMsgTypeCode())
                            .setStatus("2")
                            .setReadTime(new Date())
                            .setDeleted(0);
                    sysMsgRecordList.add(sysMsgRecord);
                }
            }
        }
        boolean b = sysMsgRecordServiceImpl.saveBatch(sysMsgRecordList);
        if (b) {
            //发布成功后需要把消息状态设置称已发布
            if (sysMsg.getStatus().equals("0")) {  //如果消息未发布
                sysMsg.setStatus("1");
                sysMsgMapper.updateById(sysMsg);
            }
        }
        return b;
    }

}