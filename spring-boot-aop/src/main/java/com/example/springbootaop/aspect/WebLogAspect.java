package com.example.springbootaop.aspect;

import com.google.gson.Gson;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

@Aspect
@Component
public class WebLogAspect {
    private final static Logger logger = LoggerFactory.getLogger(WebLogAspect.class);

    /**
     * 以自定义WebLog 注解为切点
     */
    @Pointcut("@annotation(com.example.springbootaop.aspect.WebLog)")
    public void WebLog(){}

    /**
     * 环绕
     */
    @Around("WebLog()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
        long startTime = System.currentTimeMillis();
        Object result = proceedingJoinPoint.proceed();
        //打印出参
        logger.info("Response Args : {}",new Gson().toJson(result));
        //执行耗时
        logger.info("Time-Consuming : {}",System.currentTimeMillis() - startTime);
        return result;
    }

    /**
     * 在切点之前
     */
    @Before("WebLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable{
        //开始打印请求日志
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        //获取@WebLog 注解的描述信息
        String methodDescription = getAspectLogDescription(joinPoint);;

        //打印请求相关参数
        logger.info("========== start ============");
        //打印请求url
        logger.info("URL : {}",request.getRequestURI().toString());
        // 打印注解描述信息
        logger.info("Description : {}", methodDescription);
        //打印http method
        logger.info("HTTP Method : {}",request.getMethod());
        //打印调用controller 的全部路径以及执行方法
        logger.info("Class Method : {}.{}",joinPoint.getSignature().getDeclaringTypeName(),joinPoint.getSignature().getName());
        //打印请求的IP
        logger.info("IP : {}",request.getRemoteAddr());
        //打印请求入参
        logger.info("Request Args : {}",new Gson().toJson(joinPoint.getArgs()));
    }

    /**
     * 在切点之后
     */
    @After("WebLog()")
    public void doAfter() throws Throwable{
        logger.info("========== end ============");
    }

    /**
     * 获取切面注解的描述
     *
     * @param joinPoint 切点
     * @return 描述信息
     * @throws Exception
     */
    public String getAspectLogDescription(JoinPoint joinPoint) throws Exception {
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        Class targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();
        StringBuilder description = new StringBuilder("");
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Class[] clazzs = method.getParameterTypes();
                if (clazzs.length == arguments.length) {
                    description.append(method.getAnnotation(WebLog.class).description());
                    break;
                }
            }
        }
        return description.toString();
    }
}
