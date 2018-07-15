package de.laliluna.example.advice;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class LoggingAspect {

	private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(LoggingAspect.class);

	//Before("execution(* de.laliluna.example.service.*.*(..))")
	public void test(){
		log.debug("huhu");
	}
	@Around("execution(* de.laliluna.example.service.*.*(..))")
	public Object logMethodAccess(ProceedingJoinPoint joinPoint) throws Throwable {

		log.debug("Entering: "+ joinPoint.toShortString());
		Object result = joinPoint.proceed();
		log.debug("Finished: "+ joinPoint.toShortString());
		
		
		return result;
	}
}
