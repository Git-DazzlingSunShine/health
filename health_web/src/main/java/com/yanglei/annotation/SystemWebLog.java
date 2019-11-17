package com.yanglei.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SystemWebLog {

    /**
     * 描述操作业务，Xx - 时间 -执行了Xx
     * @return
     */
    String description() default "";

}
