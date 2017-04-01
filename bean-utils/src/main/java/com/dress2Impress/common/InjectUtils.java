package com.dress2Impress.common;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.text.WordUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Objects;

@Component
public class InjectUtils implements ApplicationContextAware {

    private static final Logger LOG = LoggerFactory.getLogger(InjectUtils.class);

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
        LOG.debug("Initializing inject utilities.");

        InjectUtils.applicationContext = applicationContext;
    }

    public static <B> B inject(final Class<B> bean) {
        return InjectUtils.inject(bean, WordUtils.uncapitalize(bean.getSimpleName()));
    }

    public static <B> B inject(final Class<B> bean, final String name) {
        return InjectUtils.applicationContext.getBean(name, bean);
    }

    public static void setValueToField(final Object bean, final String fieldName, final Object value) {
        setValueToField(bean, FieldUtils.getField(bean.getClass(), fieldName, true), value);
    }

    public static void setValueToField(final Object bean, final Field field, final Object value) {
        final boolean needsToBeInvisible;

        Objects.requireNonNull(bean);
        Objects.requireNonNull(field);

        if (!field.isAccessible()) {
            field.setAccessible(true);
            needsToBeInvisible = true;
        } else {
            needsToBeInvisible = false;
        }

        try {
            field.set(bean, value);
        } catch (final IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        if (needsToBeInvisible) {
            field.setAccessible(false);
        }
    }
}
