package com.smartosc.lending.lms.service.config;

import com.smartosc.lending.lms.service.util.IConst;
import com.smartosc.lending.lms.service.util.SMFLogger;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

@Aspect
@Configuration
public class SMFLoggerConfig {

	private static final Logger LOGGER = LoggerFactory.getLogger(SMFLoggerConfig.class);

	@Before("within(com.smartoscfintech.p2plending.lms.backend.loan..*) "
			+ "&& @annotation(com.smartosc.lending.lms.service.util.SMFLogger)")
	public void writeLogBefore(JoinPoint joinPoint) throws NoSuchMethodException {
		String url = getRequestUrl();
		if(!IConst.LOGGER_REQUEST_URL.equals(url)) {
			LOGGER.info("API called= {}. Message= {}", url, this.getMessage(joinPoint));	
		}else {
			LOGGER.info("Start:  {}", this.getMessage(joinPoint));
		}
	}

	@AfterReturning("within(com.smartoscfintech.p2plending.lms.backend.loan..*)"
			+ " && @annotation(com.smartosc.lending.lms.service.util.SMFLogger)")
	public void writeLogAfterReturn(JoinPoint joinPoint) throws NoSuchMethodException {
		LOGGER.info("End: {}", this.getMessage(joinPoint));
	}

	@AfterThrowing(value = "within(com.smartoscfintech.p2plending.lms.backend.loan..*) "
			+ "&& @annotation(com.smartosc.lending.lms.service.util.SMFLogger)", throwing = "e")
	public void writeLogAfterThrow(JoinPoint joinPoint, Exception e) throws NoSuchMethodException {
		LOGGER.error("Exeption in process", e);
		LOGGER.info("Failed: {}", this.getMessage(joinPoint));
	}

	private String getMessage(JoinPoint joinPoint) throws NoSuchMethodException {
		Method interfaceMethod = ((MethodSignature) joinPoint.getSignature()).getMethod();
		Method implementationMethod = joinPoint.getTarget().getClass().getMethod(interfaceMethod.getName(),
				interfaceMethod.getParameterTypes());
		String message = null;
		if (implementationMethod.isAnnotationPresent(SMFLogger.class)) {
			SMFLogger logger = implementationMethod.getAnnotation(SMFLogger.class);
			message = logger.message();
		}
		return message;
	}

	private String getRequestUrl() {
		try {
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
					.getRequest();
			String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
					+ request.getContextPath() + request.getRequestURI();
			if (StringUtils.isEmpty(request.getQueryString())) {
				url += "?" + request.getQueryString();
			}
			return url;
		} catch (Exception e) {
			return IConst.LOGGER_REQUEST_URL;
		}
	}
}
