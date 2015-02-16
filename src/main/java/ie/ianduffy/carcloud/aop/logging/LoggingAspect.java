package ie.ianduffy.carcloud.aop.logging;

import ie.ianduffy.carcloud.config.Constants;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;

import java.util.Arrays;

/**
 * Aspect for logging execution of service and repository Spring components.
 */
@Aspect
@Profile(Constants.SPRING_PROFILE_DEVELOPMENT)
public class LoggingAspect {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @AfterThrowing(pointcut = "loggingPoincut()", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
        log.error("Exception in {}.{}() with cause = {}",
            joinPoint.getSignature().getDeclaringTypeName(),
            joinPoint.getSignature().getName(), e.getCause(), e);
    }

    @Around("loggingPoincut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        log.debug("Enter: {}.{}() with argument[s] = {}",
            joinPoint.getSignature().getDeclaringTypeName(),
            joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));

        try {
            Object result = joinPoint.proceed();
            log.debug("Exit: {}.{}() with result = {}",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(), result);

            return result;
        } catch (IllegalArgumentException e) {
            log.error("Illegal argument: {} in {}.{}()", Arrays.toString(joinPoint.getArgs()),
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName());

            throw e;
        }
    }

    @Pointcut("within(ie.ianduffy.carcloud.repository..*) || within(ie.ianduffy.carcloud.service..*) || within(ie.ianduffy.carcloud.web.rest..*)")
    public void loggingPoincut() {
    }
}
