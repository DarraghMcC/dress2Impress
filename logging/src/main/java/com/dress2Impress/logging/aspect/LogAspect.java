package com.dress2Impress.logging.aspect;

import com.dress2Impress.common.AOPUtils;
import com.dress2Impress.logging.LogLevel;
import com.dress2Impress.logging.LogType;
import com.dress2Impress.logging.annotation.Log;
import com.dress2Impress.logging.annotation.Logs;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import java.util.*;

@Aspect
@Configuration
public class LogAspect {

	@Pointcut("execution(* *.*(..))")
	private void anyOperation() {
	}

	@Pointcut("@annotation(com.dress2Impress.logging.annotation.Log)")
	private void anyLogAnnotation() {
	}

	@Pointcut("@annotation(com.dress2Impress.logging.annotation.Log)")
	private void anyLogsAnnotation() {
	}

	@Around(value = "anyOperation() && (anyLogAnnotation() || anyLogsAnnotation())")
	public Object log(final ProceedingJoinPoint joinPoint) throws Throwable {
		return this.proceed(joinPoint);
	}

	private Object proceed(final ProceedingJoinPoint joinPoint) throws Throwable {
		final Set<Log> logAnnotations = new HashSet<>();

		AOPUtils.resolveAnnotations(joinPoint).forEach((a) -> {
			if (a instanceof Log) {
				logAnnotations.add((Log) a);
			} else if (a instanceof Logs) {
				Collections.addAll(logAnnotations, ((Logs) a).value());
			}
		});

		return this.proceedAll(logAnnotations, joinPoint);
	}

	private Object proceedAll(final Set<Log> logAnnotations, final ProceedingJoinPoint joinPoint) throws Throwable {
		final Object result;
		final MethodSignature signature = AOPUtils.resolveMethodSignature(joinPoint);
		final Class<?> pointClass = AOPUtils.resolveClass(joinPoint);
		final StopWatch stopWatch = new StopWatch();
		final Logger logger = this.resolveLogger(pointClass);
		final Log logAnnotation = this.resolveMinimumLevel(logAnnotations, logger);
		final List<LogType> logTypes;

		if (Objects.nonNull(logAnnotation)) {
			logTypes = Arrays.asList(logAnnotation.types());
		} else {
			logTypes = Collections.emptyList();
		}

		if (Objects.nonNull(logAnnotation) && logTypes.contains(LogType.SIGNATURE)) {
			this.logSignature(signature, joinPoint.getArgs(), logger, logAnnotation.level(), logAnnotation.prefix());
		}

		try {
			stopWatch.start();
			result = this.proceedJoinPoint(joinPoint);
		} catch (final Throwable ex) {
			if (Objects.nonNull(logAnnotation) && logTypes.contains(LogType.EXCEPTION)) {
				this.logException(signature, ex, logger, logAnnotation.prefix());
			}
			throw ex;
		} finally {
			stopWatch.stop();
			if (Objects.nonNull(logAnnotation) && logTypes.contains(LogType.PERFORMANCE)) {
				this.logPerformance(signature, stopWatch.getTime(), logger, logAnnotation.level(), logAnnotation.prefix());
			}
		}

		if (Objects.nonNull(logAnnotation)
				&& logTypes.contains(LogType.RETURNING)
				&& !StringUtils.equals("void", signature.getReturnType().getSimpleName())) {
			this.logResult(signature, result, logger, logAnnotation.level(), logAnnotation.prefix());
		}

		return result;
	}

	private Logger resolveLogger(final Class<?> pointClass) {
		return LoggerFactory.getLogger(pointClass);
	}

	private Log resolveMinimumLevel(final Set<Log> logAnnotations, final Logger logger) {
		final Optional<Log> logTrace;
		final Optional<Log> logDebug;
		final Optional<Log> logInfo;
		final Optional<Log> logWarn;
		final Optional<Log> logError;

		logTrace = logAnnotations.stream().filter((a) -> Objects.equals(LogLevel.TRACE, a.level())).findFirst();

		if (logTrace.isPresent() && logger.isTraceEnabled()) {
			return logTrace.get();
		}

		logDebug = logAnnotations.stream().filter((a) -> Objects.equals(LogLevel.DEBUG, a.level())).findFirst();

		if (logDebug.isPresent() && logger.isDebugEnabled()) {
			return logDebug.get();
		}

		logInfo = logAnnotations.stream().filter((a) -> Objects.equals(LogLevel.INFO, a.level())).findFirst();

		if (logInfo.isPresent() && logger.isInfoEnabled()) {
			return logInfo.get();
		}

		logWarn = logAnnotations.stream().filter((a) -> Objects.equals(LogLevel.WARN, a.level())).findFirst();

		if (logWarn.isPresent() && logger.isWarnEnabled()) {
			return logWarn.get();
		}

		logError = logAnnotations.stream().filter((a) -> Objects.equals(LogLevel.ERROR, a.level())).findFirst();

		if (logError.isPresent() && logger.isErrorEnabled()) {
			return logError.get();
		}

		return null;
	}

	@SuppressWarnings("ArraysAsListWithZeroOrOneArgument")
	private void logSignature(final MethodSignature signature, final Object[] args, final Logger logger, final LogLevel logLevel, final String prefix) {
		if (args.length > 0) {
			final StringBuilder argsBuilder = new StringBuilder();

			for (int i = 0; i < args.length; i++) {
				final Object arg = args[i];

				if (i != 0) {
					argsBuilder.append(",").append(StringUtils.SPACE);
				}

				if (arg instanceof Collection) {
					argsBuilder.append("(size)").append(StringUtils.SPACE).append(((Collection) arg).size());
				} else if (arg instanceof Map) {
					argsBuilder.append("(size)").append(StringUtils.SPACE).append(((Map) arg).size());
				} else if (Objects.nonNull(arg) && arg.getClass().isArray()) {
					argsBuilder.append("(length)").append(StringUtils.SPACE).append(Arrays.asList(arg).size());
				} else {
					argsBuilder.append(arg);
				}
			}
			this.log(logger, logLevel, prefix, String.format("%s with args=%s", signature.toShortString(), argsBuilder.toString()));
		} else {
			this.log(logger, logLevel, prefix, signature.toShortString());
		}
	}

	private Object proceedJoinPoint(final ProceedingJoinPoint joinPoint) throws Throwable {
		return joinPoint.proceed(joinPoint.getArgs());
	}

	private void logException(final MethodSignature signature, final Throwable ex, final Logger logger, final String prefix) {
		if (StringUtils.isNotBlank(prefix)) {
			logger.error(String.format("[%s] - Exception on %s.", prefix, signature.toShortString()), ex);
		} else {
			logger.error(String.format("Exception on %s.", signature.toShortString()), ex);
		}
	}

	private void logPerformance(final MethodSignature signature, final Long time, final Logger logger, final LogLevel logLevel, final String prefix) {
		this.log(logger, logLevel, prefix, String.format("%s executed in %s.",
				signature.toShortString(), DurationFormatUtils.formatDurationHMS(time)));
	}

	@SuppressWarnings("ArraysAsListWithZeroOrOneArgument")
	private void logResult(final MethodSignature signature, final Object result, final Logger logger, final LogLevel logLevel, final String prefix) {
		if (result instanceof Collection) {
			this.log(logger, logLevel, prefix, String.format("%s returns (size) : %s.",
					signature.toShortString(), ((Collection) result).size()));
		} else if (result instanceof Map) {
			this.log(logger, logLevel, prefix, String.format("%s returns (size) : %s.",
					signature.toShortString(), ((Map) result).size()));
		} else if (Objects.nonNull(result) && result.getClass().isArray()) {
			this.log(logger, logLevel, prefix, String.format("%s returns (length) : %s.",
					signature.toShortString(), Arrays.asList(result).size()));
		} else {
			this.log(logger, logLevel, prefix, String.format("%s returns : %s.",
					signature.toShortString(), result));
		}
	}

	private void log(final Logger logger, final LogLevel logLevel, final String prefix, final String message) {
		switch (logLevel) {
			case TRACE:
				if (StringUtils.isNotBlank(prefix)) {
					logger.trace("[{}] - {}", prefix, message);
				} else {
					logger.trace("{}", message);
				}
				break;
			case DEBUG:
				if (StringUtils.isNotBlank(prefix)) {
					logger.debug("[{}] - {}", prefix, message);
				} else {
					logger.debug("{}", message);
				}
				break;
			case INFO:
				if (StringUtils.isNotBlank(prefix)) {
					logger.info("[{}] - {}", prefix, message);
				} else {
					logger.info("{}", message);
				}
				break;
			case WARN:
				if (StringUtils.isNotBlank(prefix)) {
					logger.warn("[{}] - {}", prefix, message);
				} else {
					logger.warn("{}", message);
				}
				break;
			case ERROR:
				if (StringUtils.isNotBlank(prefix)) {
					logger.error("[{}] - {}", prefix, message);
				} else {
					logger.error("{}", message);
				}
				break;
		}
	}

}
