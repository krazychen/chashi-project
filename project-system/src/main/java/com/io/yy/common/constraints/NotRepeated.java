package com.io.yy.common.constraints;


import java.lang.annotation.*;

@Inherited
@Documented
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface NotRepeated {

}