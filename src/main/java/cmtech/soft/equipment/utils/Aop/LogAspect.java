package cmtech.soft.equipment.utils.Aop;

import cmtech.soft.equipment.config.LogConfig;
import com.alibaba.fastjson.JSON;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * 定义切面
 */
@Aspect
@Component
@Order(1)
public class LogAspect {
    @Autowired
    private LogConfig log;

    /**
     * 拦截controller
     */
    @Pointcut("execution(public * cmtech.soft.equipment.controller..*.*(..))")
    private void ControllerAspect() {
    }

    /**
     * 用注解定义切点
     */
    @Pointcut("@annotation(cmtech.soft.equipment.utils.Aop.InterceptAction)")
    public void ControllerAspectByAnno(){ }

    @Before(value = "ControllerAspectByAnno()")
    public void ControllerBefore(JoinPoint joinPoint) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        MethodSignature signature=(MethodSignature)joinPoint.getSignature();
        Method method=signature.getMethod();
        InterceptAction action=method.getAnnotation(InterceptAction.class);
        // 记录请求内容
        log.logMethodWithContent("===========请求接口方法前，请求内容开始============");
        log.logMethodWithContent("请求:" + action.name());
        log.logMethodWithContent("请求主机地址:" + request.getRemoteAddr());
        log.logMethodWithContent("请求url:" + request.getRequestURL().toString());
        log.logMethodWithContent("请求方式:" + request.getMethod());
        log.logMethodWithContent("请求类方法:" + joinPoint.getSignature());
        log.logMethodWithContent("请求类方法参数:" + Arrays.toString(joinPoint.getArgs()));
        log.logMethodWithContent("===============方法前，请求内容结束===============");
    }

    /**
     *  方法结束拦截
     * @param joinPoint 拦截点
     */
    @After(value = "ControllerAspectByAnno()")
    public void ControllerAfter(JoinPoint joinPoint) {

    }

    @SuppressWarnings("unchecked")
    @AfterReturning(returning = "o", pointcut = "ControllerAspectByAnno()",argNames = "joinPoint,o")
    public void ControllerAfterReturn(JoinPoint joinPoint, Object o) throws IOException {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        MethodSignature signature=(MethodSignature)joinPoint.getSignature();
        Method method=signature.getMethod();
        InterceptAction action=method.getAnnotation(InterceptAction.class);
        String str = JSON.toJSONString(o);
        // 记录请求内容
        log.logMethodWithContent("===============请求接口方法后，请求内容开始===============");
        log.logMethodWithContent("请求:" + action.name());
        log.logMethodWithContent("请求主机地址:" + request.getRemoteAddr());
        log.logMethodWithContent("请求url:" + request.getRequestURL().toString());
        log.logMethodWithContent("请求方式:" + request.getMethod());
        log.logMethodWithContent("请求类方法:" + joinPoint.getSignature());
        log.logMethodWithContent("请求类方法参数:" + Arrays.toString(joinPoint.getArgs()));
        log.logMethodWithContent("Response内容:" + str);
        log.logMethodWithContent("===============方法后，请求内容结束===============");
    }


}
