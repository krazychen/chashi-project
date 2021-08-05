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

package com.io.yy.wxops.utils;

import com.io.yy.common.enums.BaseEnum;

/**
 * 是否 枚举
 *
 * @author wuzhixiong
 * @date 2020-06-25
 **/
public enum TrueFlagEnum implements BaseEnum {
    YES_FLAG(1, "是"),
    NO_FLAG(0, "否");

    private Integer code;
    private String desc;

    TrueFlagEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }

    @Override
    public String getDesc() {
        return this.desc;
    }
}
