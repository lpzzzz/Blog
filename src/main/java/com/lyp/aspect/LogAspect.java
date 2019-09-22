package com.lyp.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;


/**
 * 日志处理切面
 */

@Aspect
@Component  // 组件扫描
public class LogAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass()); // 日志记录器

    @Pointcut("execution(* com.lyp.web.*.*(..))") // 编写切入点表达式 * com.lyp.web.*.*(..))"
    public void log() {}



    /**
     * 在切面执行之前执行一些东西
     */
    @Before("log()")
    public void doBefore (JoinPoint joinPoint) {
        // 如何获取值
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        // 获取Request对象 通过该对象获取
        HttpServletRequest request = attributes.getRequest();

        // 获取url
        String url = request.getRequestURL().toString();

        // 获取ip
        String ip = request.getRemoteAddr();
        // 获取调用的方法
        String classMethod = joinPoint.getSignature().
                getDeclaringTypeName()+ "." + joinPoint.getSignature().getName();

        // 获取传递的参数
        Object[] args = joinPoint.getArgs();

        // 构造对象
        RequestLog log = new RequestLog(url,ip,classMethod,args);

        logger.info("Request  : {} ",log);
    }

    /**
     * 在请求之后执行
     */

    @After("log()")
    public void doAfter(){
        logger.info("-------doAfter-------");
    }


    /**
     * 捕获返回值
     * @param result
     */
    @AfterReturning(returning = "result",pointcut = "log()")
    public void doAfterReturn(Object result) {
        logger.info("Result : {}" + result);
    }

    /**
     * 定义一个内部类用于封装 日志信息
     */

    private  class RequestLog {
        private String url ;
        private String ip;
        private String classMethod;
        private Object [] args;

        public RequestLog(String url, String ip, String classMethod, Object[] args) {
            this.url = url;
            this.ip = ip;
            this.classMethod = classMethod;
            this.args = args;
        }

        @Override
        public String toString() {
            return "RequestLog{" +
                    "url='" + url + '\'' +
                    ", ip='" + ip + '\'' +
                    ", classMethod='" + classMethod + '\'' +
                    ", args=" + Arrays.toString(args) +
                    '}';
        }
    }
}
