package com.dmm.middleware.governance.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Mean
 * @date 2025/2/17 18:10
 * @description 自定义注解：自定义拦截方法
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DoMethodExt {
	String method() default "";
	String returnJson() default "";
}
