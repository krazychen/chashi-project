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

package com.io.yy.system.convert;

import com.io.yy.shiro.vo.LoginSysUserRedisVo;
import com.io.yy.shiro.vo.LoginSysUserVo;
import com.io.yy.system.entity.SysUser;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author kris
 * @date 2019-10-05
 **/
@Mapper
public interface SysUserConvert {

    SysUserConvert INSTANCE = Mappers.getMapper(SysUserConvert.class);

    /**
     * 系统用户实体对象转换成登陆用户VO对象
     * @param sysUser
     * @return
     */
    LoginSysUserVo sysUserToLoginSysUserVo(SysUser sysUser);

    /**
     * LoginSysUserVo对象转换成LoginSysUserRedisVo
     *
     * @param loginSysUserVo
     * @return
     */
    LoginSysUserRedisVo loginSysUserVoToLoginSysUserRedisVo(LoginSysUserVo loginSysUserVo);

}
