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

package com.io.yy.resource.controller;

import com.io.yy.common.api.ApiResult;
import com.io.yy.common.param.IdParam;
import com.io.yy.core.properties.WhyySystemProperties;
import com.io.yy.util.UploadUtil;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 上传控制器
 * @author kris
 * @date 2019/8/20
 * @since 1.2.1-RELEASE
 */
@Slf4j
@RestController
@RequestMapping("/upload")
public class UploadController {

    @Autowired
    private WhyySystemProperties whyySystemProperties;

    @GetMapping("/hello")
    public ApiResult hello(){
        log.info("hello...");
        return ApiResult.ok();
    }

    /**
     * 上传单个文件
     */
    @PostMapping("/")
    @ApiOperation(value = "上传单个文件",notes = "上传单个文件",response = ApiResult.class)
    public ApiResult<Boolean> upload(@RequestParam("img") MultipartFile multipartFile) throws Exception{
        log.info("multipartFile = " + multipartFile);
        log.info("ContentType = " + multipartFile.getContentType());
        log.info("OriginalFilename = " + multipartFile.getOriginalFilename());
        log.info("Name = " + multipartFile.getName());
        log.info("Size = " + multipartFile.getSize());

        // 上传文件，返回保存的文件名称
        String saveFileName = UploadUtil.upload(whyySystemProperties.getUploadPath(), multipartFile, originalFilename -> {
            // 文件后缀
            String fileExtension= FilenameUtils.getExtension(originalFilename);
            // 这里可自定义文件名称，比如按照业务类型/文件格式/日期
            String dateString = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssS"));
            String fileName = dateString + "." +fileExtension;
            return fileName;
        });

        // 上传成功之后，返回访问路径，请根据实际情况设置

        String fileAccessPath = whyySystemProperties.getResourceAccessUrl() + saveFileName;
        log.info("fileAccessPath:{}",fileAccessPath);

        return ApiResult.ok(fileAccessPath);
    }

}
