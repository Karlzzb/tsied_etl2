package com.etl.aspect;

import java.util.Arrays;
import java.util.UUID;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.etl.es.BaseESOption;
import com.etl.es.entity.AspectEntity;

/**
 * 任务执行状况收集器
 * 
 * @author kerl
 *
 */
@Component
@Aspect
public class ServiceAspect {
	private static Logger log = LoggerFactory.getLogger(ServiceAspect.class);

	private static String serviceAspectIndex = "etl-logs";

	private static String serviceAspectIndexType = "service_aspect";

	@Autowired
	private BaseESOption baseESOption;

	@Before("execution (* com.etl.task.*.*(..))")
	public void before() {

	}

	@AfterReturning("execution (* com.etl.service.impl.*.*(..))")
	public void after(JoinPoint joinPoint) {
		AspectEntity aspectEntity = null;
		try {
			aspectEntity = new AspectEntity(UUID.randomUUID().toString(), joinPoint.getSignature()
					.getDeclaringTypeName() + "." + joinPoint.getSignature().getName(), Arrays.toString(joinPoint
					.getArgs()));

			baseESOption.saveOrUpdateMultiObjests(serviceAspectIndex, serviceAspectIndexType, aspectEntity);
		} catch (Exception e) {
			log.error("Record service excute Log failed!", e);
		} finally {
			aspectEntity = null;
		}

	}

	@AfterThrowing(pointcut = "execution (* com.etl.service.impl.*.*(..))", throwing = "ex")
	public void afterThrowing(JoinPoint joinPoint, Throwable ex) {
		AspectEntity aspectEntity = null;
		try {
			aspectEntity = new AspectEntity(UUID.randomUUID().toString(), joinPoint.getSignature()
					.getDeclaringTypeName() + "." + joinPoint.getSignature().getName(), Arrays.toString(joinPoint
					.getArgs()), ex.getMessage());

			baseESOption.saveOrUpdateMultiObjests(serviceAspectIndex, serviceAspectIndexType, aspectEntity);
		} catch (Exception e) {
			log.error("Record service excute Log failed!", e);
		} finally {
			aspectEntity = null;
		}
	}
}
