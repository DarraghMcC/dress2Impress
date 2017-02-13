package com.dress2Impress.logging.inject;

import com.dress2Impress.common.InjectUtils;
import com.dress2Impress.common.ProxyUtils;
import com.dress2Impress.logging.annotation.InjectLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ReflectionUtils;

import javax.annotation.PostConstruct;
import java.lang.reflect.Field;
import java.util.Objects;

@Configuration
public class LoggerInjector implements BeanPostProcessor {

	private static Logger LOG = LoggerFactory.getLogger(LoggerInjector.class);

	@PostConstruct
	public void init() {
		LOG.debug("Logger injector bean post processor initialized.");
	}

	@Override
	public Object postProcessBeforeInitialization(final Object bean, final String beanName) throws BeansException {
		ReflectionUtils.doWithFields(
				bean.getClass(),
				(f) -> this.injectLogger(bean, f),
				(f) -> Objects.nonNull(f)
						&& Objects.nonNull(f.getAnnotation(InjectLogger.class))
						&& Logger.class.isAssignableFrom(f.getType())
		);

		return bean;
	}

	private void injectLogger(final Object bean, final Field field) {
		try {
			InjectUtils.setValueToField(bean, field, LoggerFactory.getLogger(ProxyUtils.targetClass(bean)));
		} catch (final Exception e) {
			LOG.error(String.format("Something happened when trying to inject logger to field '%s' of bean '%s'.",
					field.getName(), bean.getClass().getSimpleName()), e);
		}
	}

	@Override
	public Object postProcessAfterInitialization(final Object bean, final String beanName) throws BeansException {
		return bean;
	}

}
