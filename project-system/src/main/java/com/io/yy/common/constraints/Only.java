package com.io.yy.common.constraints;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * <pre>
 * 数据库字段唯一性校验
 * </pre>
 *
 * @author chenPengfei
 * @since 2019-11-28
 */
@Documented
@Constraint(validatedBy = {OnlyValidator.class })
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Only {

    String message() default "数据已存在";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String tableName();

    String field();

}
