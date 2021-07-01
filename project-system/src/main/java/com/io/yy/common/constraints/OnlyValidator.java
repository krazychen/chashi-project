package com.io.yy.common.constraints;

import cn.hutool.core.util.StrUtil;
import com.io.yy.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * <pre>
 * 数据库字段唯一性校验实现类
 * </pre>
 *
 * @author chenPengfei
 * @since 2019-11-28
 */
public class OnlyValidator implements ConstraintValidator<Only,String> {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private String sql = "";

    @Override
    public void initialize(Only constraintAnnotation) {
        sql = "select count(*) from "+ constraintAnnotation.tableName() +" where " +constraintAnnotation.field() +" = ?";
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        HttpServletRequest request = HttpServletRequestUtil.getRequest();
        String url = request.getServletPath();
        if(StrUtil.isNotBlank(url)){
            if(url.contains("/update")){
                return true;
            }
        }
        return jdbcTemplate.queryForObject(sql, Integer.class,value)<1;
    }
}
