/*
 * Copyright 2019-2029 kris
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.io.yy.system.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.io.yy.common.exception.BusinessException;
import com.io.yy.common.service.impl.BaseServiceImpl;
import com.io.yy.common.vo.Paging;
import com.io.yy.enums.StateEnum;
//import com.io.yy.homework.entity.HwStudent;
//import com.io.yy.homework.entity.HwTeacher;
//import com.io.yy.homework.mapper.HwStudentMapper;
//import com.io.yy.homework.mapper.HwTeacherMapper;
//import com.io.yy.homework.service.HwStudentService;
//import com.io.yy.homework.service.HwTeacherService;
import com.io.yy.shiro.util.JwtTokenUtil;
import com.io.yy.shiro.util.JwtUtil;
import com.io.yy.shiro.util.SaltUtil;
import com.io.yy.system.entity.SysUser;
import com.io.yy.system.entity.SysUserOffice;
import com.io.yy.system.mapper.SysUserMapper;
import com.io.yy.system.mapper.SysUserOfficeMapper;
import com.io.yy.system.mapper.SysUserRoleMapper;
import com.io.yy.system.param.*;
import com.io.yy.system.service.*;
import com.io.yy.system.vo.SysUserQueryVo;
import com.io.yy.system.vo.SysUserVo;
import com.io.yy.util.PasswordUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.*;


/**
 * <pre>
 * 系统用户 服务实现类
 * </pre>
 *
 * @author kris
 * @since 2019-10-24
 */
@Slf4j
@Service
public class SysUserServiceImpl extends BaseServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysUserOfficeMapper sysUserOfficeMapper;

    @Autowired
    private SysOfficeService sysOfficeService;

    @Autowired
    private SysUserOfficeServiceImpl sysUserOfficeService;

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

//    @Autowired
//    private HwStudentService hwStudentService;
//
//    @Autowired
//    private HwTeacherService hwTeacherService;
//
//    @Autowired
//    private HwStudentMapper hwStudentMapper;
//
//    @Autowired
//    private HwTeacherMapper hwTeacherMapper;

    @Lazy
    @Autowired
    private SysRoleService sysRoleService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveSysUser(SysUser sysUser) throws Exception {
        // 校验用户名是否存在
        boolean isExists = isExistsByUsername(sysUser.getUsername());
        if (isExists) {
            throw new BusinessException("用户名已存在");
        }
        // 校验机构
        if (sysUser.getOfficeId() != null) {
            checkOffice(sysUser.getOfficeId());
        }
        // 生成盐值
        String salt = SaltUtil.generateSalt();
        sysUser.setSalt(salt);
        sysUser.setId(null);

        // 密码加密
        String newPassword = PasswordUtil.encrypt(sysUser.getPassword(), salt);
        sysUser.setPassword(newPassword);

        // 设置用户的租户
        sysUser.setCorpCode(sysUser.getOfficeId());
        sysUser.setCorpName(sysUser.getOfficeName());

        // 保存系统用户
        boolean flag = super.save(sysUser);
        if (flag) {
            //新建用户成功如果用户选择了机构则同时向sys_user_office插入数据
            if (sysUser.getOfficeId() != null) {
                SysUserOffice suo = new SysUserOffice();
                suo.setOfficeCode(sysUser.getOfficeId().toString());
                suo.setUserId(sysUser.getId());
                suo.setDeleted(0);//未删除
                suo.setCorpCode(sysUser.getOfficeId());
                suo.setCorpName(sysUser.getOfficeName());
                sysUserOfficeService.saveSysUserOffice(suo);
            }
        }
        return flag;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateSysUser(SysUser sysUser) throws Exception {
        // 校验部门和角色
        //checkDepartmentAndRole(sysUser.getDepartmentId(), sysUser.getRoleId());
        // 获取系统用户
        SysUser updateSysUser = getById(sysUser.getId());
        if (updateSysUser == null) {
            throw new BusinessException("修改的用户不存在");
        }

        //修改成功后同时修改sys_user_office表中数据

        if (sysUser.getOfficeId() != null) {
            //如果不相同则根据用户id删除sys_user_office中的数据，重新插入
            QueryWrapper<SysUserOffice> sysUserOfficeQueryWrapper = new QueryWrapper<SysUserOffice>();
            sysUserOfficeQueryWrapper.eq("user_id", sysUser.getId());
            SysUserOffice sysUserOffice = sysUserOfficeService.getOne(sysUserOfficeQueryWrapper);
            if (ObjectUtil.isNull(sysUserOffice)) {
                SysUserOffice suo = new SysUserOffice();
                suo.setOfficeCode(sysUser.getOfficeId().toString());
                suo.setUserId(sysUser.getId());
                suo.setDeleted(0);//未删除
                suo.setVersion(0);
                sysUserOfficeService.saveSysUserOffice(suo);
            } else if (!sysUserOffice.getOfficeCode().equals(sysUser.getOfficeId())) {
                //更新sys_user_office中的数据
                sysUserOffice.setOfficeCode(sysUser.getOfficeId().toString());
                sysUserOfficeService.updateSysUserOffice(sysUserOffice);
            }
            //deleteSysUser(sysUser.getId());

        }

        // 修改系统用户
        updateSysUser.setNickname(sysUser.getNickname())
                .setPhone(sysUser.getPhone())
                .setGender(sysUser.getGender())
                .setRemarks(sysUser.getRemarks())
                .setState(sysUser.getState())
                .setOfficeId(sysUser.getOfficeId())
                .setPassword(sysUser.getPassword());
        boolean flag = super.updateById(updateSysUser);
        return flag;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteSysUser(Long id) throws Exception {
        SysUser user = getById(id);
        boolean flag = super.removeById(id);
        //删除用户的同时删除用户机构中间表以及用户角色中间表数据
        if (flag) {
            List<Long> idsList = new ArrayList<>();
            idsList.add(id);
            sysUserOfficeMapper.deleteByUser(idsList);
            List<String> userIdsList = new ArrayList<>();
            userIdsList.add(String.valueOf(id));
            sysUserRoleMapper.deleteListByUserIds(userIdsList);
//            //删除用户的同时需要把学员或者老师表中的数据删除掉
//            if(user.getUserType().equals("2")){ //是学生
//                hwStudentService.deleteHwStudent(id);
//            }else if(user.getUserType().equals("3")){ //是老师
//                hwTeacherService.deleteHwTeacher(id);
//            }
        }
        return flag;
    }

    @Override
    public SysUserVo getSysUserById(Serializable id) throws Exception {
        SysUserVo user = null;
        user = sysUserMapper.getSysUserById(id);
        if (user == null) {
            throw new BusinessException("查询的用户不存在");
        }
//        if(user.getUserType().equals("2")){
//            user = hwStudentMapper.findByUserId(user.getId());
//        }else if(user.getUserType().equals("3")){
//            user = hwTeacherMapper.findByUserId(user.getId());
//        }
        return user;
    }

    @Override
    public Paging<SysUserQueryVo> getSysUserPageList(SysUserQueryParam sysUserQueryParam) throws Exception {
        Page page = setPageParam(sysUserQueryParam, OrderItem.desc("create_time"));
        IPage<SysUserQueryVo> iPage = sysUserMapper.getSysUserPageList(page, sysUserQueryParam);
        return new Paging(iPage);
    }

    @Override
    public boolean isExistsByUsername(String username) {
        SysUser selectSysUser = new SysUser().setUsername(username);
        return sysUserMapper.selectCount(new QueryWrapper<>(selectSysUser)) > 0;
    }

    @Override
    public void checkOffice(String officeId) throws Exception {
        // 校验部门是否存在并且可用
        boolean isEnableDepartment = sysOfficeService.isEnableSysOffice(officeId);
        if (!isEnableDepartment) {
            throw new BusinessException("该机构不存在或已禁用");
        }
    }

    @Override
    public boolean updatePassword(UpdatePasswordParam updatePasswordParam) throws Exception {
        String token = JwtTokenUtil.getToken();
        String username = JwtUtil.getUsername(token);
        String oldPassword = updatePasswordParam.getOldPassword();
        String newPassword = updatePasswordParam.getNewPassword();
        String confirmPassword = updatePasswordParam.getConfirmPassword();
        if (!newPassword.equals(confirmPassword)) {
            throw new BusinessException("两次输入的密码不一致");
        }
        if (newPassword.equals(oldPassword)) {
            throw new BusinessException("新密码和旧密码不能一致");
        }

        // 判断原密码是否正确
        QueryWrapper<SysUser> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("username", username);
        SysUser sysUser = sysUserMapper.selectOne(userQueryWrapper);
        if (sysUser == null) {
            throw new BusinessException("用户不存在");
        }
        if (StateEnum.DISABLE.getCode().equals(sysUser.getState())) {
            throw new BusinessException("用户已禁用");
        }
        // 密码加密处理
        String salt = sysUser.getSalt();
        String encryptOldPassword = PasswordUtil.encrypt(oldPassword, salt);
        if (!sysUser.getPassword().equals(encryptOldPassword)) {
            throw new BusinessException("原密码错误");
        }
        // 新密码加密
        String encryptNewPassword = PasswordUtil.encrypt(newPassword, salt);

        // 修改密码
        sysUser.setPassword(encryptNewPassword)
                .setUpdateTime(new Date());
        return updateById(sysUser);
    }

    @Override
    public boolean updateSysUserHead(Long id, String headPath) throws Exception {
        SysUser sysUser = new SysUser()
                .setId(id)
                .setAvatar(headPath);
        return updateById(sysUser);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteSysUsers(List<Long> idList) throws Exception {
        List<SysUser> sysUsers = sysUserMapper.selectBatchIds(idList);
        List<String> ids = new ArrayList<>();
        List<Long> studentIds = new ArrayList<>();
        List<Long> teacherIds = new ArrayList<>();
        if (sysUsers.size() > 0) {
            for (SysUser user : sysUsers) {
                ids.add(String.valueOf(user.getId()));//添加删除用户id集合
                if (user.getUserType().equals("2")) { //学员
                    studentIds.add(user.getId()); //添加学员id集合
                } else if (user.getUserType().equals("3")) { //老师
                    teacherIds.add(user.getId()); //添加老师id集合
                }
            }
        }
        boolean flag = super.removeByIds(idList);
        if (flag) {
            //批量删除用户机构中间表
            sysUserOfficeMapper.deleteByUser(idList);
            //批量删除用户角色中间表
            sysUserRoleMapper.deleteListByUserIds(ids);
//            //批量删除学员表或者老师表
//            if(studentIds.size() > 0){
//                hwStudentService.deleteHwStudents(studentIds);
//            }
//            if(teacherIds.size() > 0){
//                hwTeacherService.deleteHwTeachers(teacherIds);
//            }
        }
        return flag;
    }

    @Override
    public boolean updateByState(Long id) {
        SysUser user = getById(id);
        if (user.getState() == 1) {
            user.setState(0);
        } else if (user.getState() == 0) {
            user.setState(1);
        }
        return super.updateById(user);
    }

    @Override
    public boolean uPassWord(Long id) throws Exception {
        String newPassword = "123456";
        SysUser sysUser = getById(id);
        String salt = sysUser.getSalt();
        // 新密码加密
        String encryptNewPassword = PasswordUtil.encrypt(newPassword, salt);
        // 修改密码
        sysUser.setPassword(encryptNewPassword)
                .setUpdateTime(new Date());
        return updateById(sysUser);
    }


    @Override
    public boolean passwordSetting(PasswordQueryParam passwordQueryParam) {
        String newPassword = passwordQueryParam.getPass();
        String salt = SaltUtil.generateSalt();
        String encryptNewPassword = PasswordUtil.encrypt(newPassword, salt);
        passwordQueryParam.setPass(encryptNewPassword);
        passwordQueryParam.setSalt(salt);
        // 新密码加密
        return sysUserMapper.updatePasswordById(passwordQueryParam) > 0;
    }

    @Override
    public Boolean checkUserNameValid(CheckUserNameParam checkUserNameParam) {
        QueryWrapper<SysUser> sysUserQueryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotNull(checkUserNameParam.getUserId())){
            sysUserQueryWrapper
                    .ne("id", checkUserNameParam.getUserId())
                    .eq("username", checkUserNameParam.getUserName());
        }else{
            sysUserQueryWrapper
                    .eq("username", checkUserNameParam.getUserName());
        }
        return !(sysUserMapper.selectCount(sysUserQueryWrapper) >= 1);
    }

    @Override
    public boolean updateByStatus(List<String> idList, String status) {
        boolean flag = sysUserMapper.updateStatus(idList, status);
        return flag;
    }

    @Override
    public boolean checkUserName(String username) {
        // 校验用户名是否存在
        boolean isExists = isExistsByUsername(username);
        if (isExists) {
            throw new BusinessException("用户名已存在");
        }
        return true;
    }

    @Override
    public List<SysUserQueryVo> getSysRoleAndUserList(Map<String, Object> map) {
        if (ObjectUtil.isEmpty(map)) {
            throw new BusinessException("角色id不能为空");
        }
        return sysUserMapper.getSysRoleAndUserList(map.get("id").toString());
    }

    @Override
    public Paging<SysUserQueryVo> getSysRoleAndUserPageList(SysUserQueryParam sysUserQueryParam) {
        Page page = setPageParam(sysUserQueryParam, OrderItem.desc("create_time"));
        IPage<SysUserQueryVo> iPage = sysUserMapper.getSysRoleAndUserPageList(page, sysUserQueryParam);
        return new Paging(iPage);
    }

    @Override
    public boolean updateUserInformation(UpdateUserParam updateUserParam) throws Exception {
        String token = JwtTokenUtil.getToken();
        String username = JwtUtil.getUsername(token);
        QueryWrapper<SysUser> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("username", username);
        SysUser sysUser = sysUserMapper.selectOne(userQueryWrapper);
        //SysUser sysUser = getById(updateUserParam.getId());
        if (sysUser == null) {
            throw new BusinessException("修改对象不存在！");
        }
        //修改昵称.手机.性别.电话.邮箱
        if (StrUtil.isNotBlank(updateUserParam.getNickname())) {
            sysUser.setNickname(updateUserParam.getNickname());
        }
        if (StrUtil.isNotBlank(updateUserParam.getPhone())) {
            sysUser.setPhone(updateUserParam.getPhone());
        }
        if (null != updateUserParam.getGender()) {
            sysUser.setGender(updateUserParam.getGender());
        }
        if (StrUtil.isNotBlank(updateUserParam.getEmail())) {
            sysUser.setEmail(updateUserParam.getEmail());
        }
        if (StrUtil.isNotBlank(updateUserParam.getMobile())) {
            sysUser.setMobile(updateUserParam.getMobile());
        }
        boolean b = updateById(sysUser);
        //判断地址是否有值，有值就修改
        if (StrUtil.isNotBlank(updateUserParam.getProvinceName()) && StrUtil.isNotBlank(updateUserParam.getCityName()) && StrUtil.isNotBlank(updateUserParam.getDistrictName())) {
//            //判断该用户是学员还是老师
//            if (sysUser.getUserType().equals("2")) { //学员
//                //修改学员地址
//                HwStudent student = hwStudentMapper.findById(updateUserParam.getId());
//                student.setCityName(updateUserParam.getCityName())
//                        .setCityCode(updateUserParam.getCityCode())
//                        .setProvinceCode(updateUserParam.getProvinceCode())
//                        .setProvinceName(updateUserParam.getProvinceName())
//                        .setDistrictName(updateUserParam.getDistrictName())
//                        .setDistrictCode(updateUserParam.getDistrictCode())
//                        .setSchoolId(updateUserParam.getSchoolId())
//                        .setSchoolCode(updateUserParam.getSchoolCode())
//                        .setSchoolName(updateUserParam.getSchoolName())
//                        .setCollegeId(updateUserParam.getCollegeId())
//                        .setCollegeCode(updateUserParam.getCollegeCode())
//                        .setCollegeName(updateUserParam.getCollegeName())
//                        .setAddress(updateUserParam.getAddress());
//                hwStudentMapper.updateById(student);
//            } else if (sysUser.getUserType().equals("3")) { //老师
//                //修改老师地址
//                HwTeacher teacher = hwTeacherMapper.findTeacherById(updateUserParam.getId());
//                teacher.setCityName(updateUserParam.getCityName())
//                        .setCityCode(updateUserParam.getCityCode())
//                        .setProvinceCode(updateUserParam.getProvinceCode())
//                        .setProvinceName(updateUserParam.getProvinceName())
//                        .setDistrictName(updateUserParam.getDistrictName())
//                        .setDistrictCode(updateUserParam.getDistrictCode())
//                        .setAddress(updateUserParam.getAddress());
//                hwTeacherMapper.updateById(teacher);
//            }
        }
        return b;
    }

    @Override
    public List<SysUserQueryVo> findUserListByOfficeCode(String id) throws Exception {
        return sysUserMapper.findUserListByOfficeCode(id);
    }

    @Override
    public List<SysUser> findUserListByIds(String ids) throws Exception {
        String[] split = ids.split(",");
        List<String> userIds = new ArrayList<>();
        for (String id : split) {
            userIds.add(id);
        }
        return sysUserMapper.selectBatchIds(userIds);
    }

    @Override
    public boolean noWx(Long id) {
        return sysUserMapper.noWx(id);
    }

    @Override
    public boolean nowxs(List<Long> idList) {
        return sysUserMapper.nowxs(idList);
    }


}
