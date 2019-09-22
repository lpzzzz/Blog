package com.lyp.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * 异常处理
 */
@ControllerAdvice // 会拦截错误信息
public class ControllerExceptionHandler {

    // 获取日志
    private final Logger logger = LoggerFactory.getLogger(this.getClass()); // 这个包下面的org.slf4j.Logger

    @ExceptionHandler(Exception.class)
    public ModelAndView exceptionHandler(HttpServletRequest request, Exception e) throws Exception {
        logger.error("Request URL : {} , Exception : {}", request.getRequestURL(), e);

        // 判断异常的方法上是否存在一个注解 如果存在该注解则不进行拦截
        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class)!=null) {
            throw e;
        }
        // 记录日志信息
        ModelAndView mv = new ModelAndView();
        mv.addObject("exception", e);
        mv.setViewName("error/error");
        return mv;
    }
}
