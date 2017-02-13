package com.dress2Impress.logging.annotation;

import com.dress2Impress.logging.LogLevel;
import com.dress2Impress.logging.LogType;
import java.lang.annotation.*;
import org.apache.commons.lang3.StringUtils;

@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.METHOD)
@Repeatable(value = Logs.class)
public @interface Log {

	LogType[] types() default {LogType.SIGNATURE, LogType.PERFORMANCE};

	LogLevel level() default LogLevel.DEBUG;

	String prefix() default StringUtils.EMPTY;

}
