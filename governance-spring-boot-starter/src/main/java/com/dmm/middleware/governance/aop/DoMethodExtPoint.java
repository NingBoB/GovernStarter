package com.dmm.middleware.governance.aop;

import com.alibaba.fastjson.JSON;
import com.dmm.middleware.governance.annotation.DoMethodExt;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * @author Mean
 * @date 2025/2/17 18:17
 * @description DoMethodExtPoint
 */
@Aspect
public class DoMethodExtPoint {
	private Logger logger = LoggerFactory.getLogger(DoMethodExtPoint.class);

	// 	切入点表达式
	@Pointcut("@annotation(com.dmm.middleware.governance.annotation.DoMethodExt)")
	public void aopPoint() {
	}

	@Around("aopPoint()")
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
		// 	获取方法
		Method method = getMethod(joinPoint);
		DoMethodExt doMethodExt = method.getAnnotation(DoMethodExt.class);
		// 	获取拦截方法
		String methodName = doMethodExt.method();
		// 	功能处理
		Method methodExt = getClass(joinPoint).getMethod(methodName, method.getParameterTypes());
		Class<?> returnType = methodExt.getReturnType();

		// 	判断方法返回类型
		if (!returnType.getName().equals("boolean")) {
			throw new RuntimeException("拦截方法返回类型必须是boolean");
		}

		// 	拦截判断正常，继续执行
		boolean invoke = (boolean) methodExt.invoke(joinPoint.getThis(), joinPoint.getArgs());
		// 	返回结果
		return invoke ? joinPoint.proceed() : JSON.parseObject(doMethodExt.returnJson(), method.getReturnType());
	}

	private Class<? extends Object> getClass(ProceedingJoinPoint joinPoint) {
		return joinPoint.getTarget().getClass();
	}

	private Method getMethod(ProceedingJoinPoint joinPoint) throws NoSuchMethodException {
		Signature sig = joinPoint.getSignature();
		MethodSignature methodSignature = (MethodSignature) sig;
		return joinPoint.getTarget().getClass().getMethod(methodSignature.getName(), methodSignature.getParameterTypes());
	}

}

