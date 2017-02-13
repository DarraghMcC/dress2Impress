package com.dress2Impress.common;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.util.ReflectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public final class AOPUtils {

	public static List<Annotation> resolveAnnotations(final ProceedingJoinPoint joinPoint) {
		return Arrays.asList(AOPUtils.resolveMethod(joinPoint).getDeclaredAnnotations());
	}

	public static Method resolveMethod(final ProceedingJoinPoint joinPoint) {
		final MethodSignature signature = AOPUtils.resolveMethodSignature(joinPoint);
		final Class<?> targetClass = AOPUtils.resolveClass(joinPoint);

		return ReflectionUtils.findMethod(targetClass, signature.getName(), signature.getParameterTypes());
	}

	public static Class<?> resolveClass(final ProceedingJoinPoint joinPoint) {
		return joinPoint.getTarget().getClass();
	}

	public static MethodSignature resolveMethodSignature(final ProceedingJoinPoint joinPoint) {
		return AOPUtils.resolveMethodSignature(joinPoint.getSignature());
	}

	public static MethodSignature resolveMethodSignature(final Signature signature) {
		Objects.requireNonNull(signature);

		if (signature instanceof MethodSignature) {
			return (MethodSignature) signature;
		}

		throw new RuntimeException(String.format("Signature '%s' with class '%s' is not supported yet.",
				signature, signature.getClass()));
	}

}
