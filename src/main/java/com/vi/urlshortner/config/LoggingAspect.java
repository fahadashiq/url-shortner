package com.vi.urlshortner.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;

import java.util.Arrays;

/**
 * A simple aspect to log on debug level for the given point cuts.
 */
@Aspect
public class LoggingAspect
{
  // TODO Could be used to determine environment and disable/enable debug level logging accordingly.
  private final Environment env;

  public LoggingAspect(Environment env) {
    this.env = env;
  }

  /**
   * Point cut to log in spring classes.
   */
  @Pointcut("within(@org.springframework.stereotype.Service *)" +
          " || within(@org.springframework.web.bind.annotation.RestController *)"
  )
  public void springBeanPointcut() {
  }

  /**
   * Pointcut to log in controllers and services.
   */
  @Pointcut("within(com.vi.urlshortner.controller..*)" +
          " || within(com.vi.urlshortner.service..*)")
  public void applicationPackagePointcut() {

  }

  private Logger logger(JoinPoint joinPoint) {
    return LoggerFactory.getLogger(joinPoint.getSignature().getDeclaringTypeName());
  }

  /**
   * To log in case of exception
   * @param joinPoint join point where this aspect is triggered.
   * @param e thrown exception.
   */
  @AfterThrowing(pointcut = "applicationPackagePointcut() && springBeanPointcut()", throwing = "e")
  public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
    logger(joinPoint)
            .error(
                    "Exception in {}() with cause = {}",
                    joinPoint.getSignature().getName(),
                    e.getMessage() != null ? e.getMessage() : "NULL"
            );
  }


  /**
   * To log around given point cuts.
   * @param joinPoint join point where this aspect is triggered.
   * @return object to continue processing.
   * @throws IllegalArgumentException in case something goes wrong in this method.
   */
  @Around("applicationPackagePointcut() && springBeanPointcut()")
  public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
    Logger log = logger(joinPoint);
    if (log.isDebugEnabled()) {
      log.debug("Enter: {}() with argument[s] = {}", joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));
    }
    try {
      Object result = joinPoint.proceed();
      if (log.isDebugEnabled()) {
        log.debug("Exit: {}() with result = {}", joinPoint.getSignature().getName(), result);
      }
      return result;
    } catch (IllegalArgumentException e) {
      log.error("Illegal argument: {} in {}()", Arrays.toString(joinPoint.getArgs()), joinPoint.getSignature().getName());
      throw e;
    }
  }
}
