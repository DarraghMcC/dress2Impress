package com.dress2Impress.common;

import org.springframework.aop.framework.Advised;
import org.springframework.cglib.proxy.Enhancer;

public final class ProxyUtils {

	private ProxyUtils() {
	}

	public static Class<?> targetClass(final Object proxy) throws Exception {
		if (proxy instanceof Advised) {
			return ((Advised) proxy).getTargetSource().getTarget().getClass();
		} else if (Enhancer.isEnhanced(proxy.getClass())) {
			return proxy.getClass().getSuperclass();
		}

		return proxy.getClass();
	}

}
