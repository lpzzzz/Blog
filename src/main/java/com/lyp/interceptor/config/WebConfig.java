package com.lyp.interceptor.config;

import com.lyp.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 配置需要拦截的内容
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter{

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor()).
                addPathPatterns("/admin/**").
                excludePathPatterns("/admin"). // 排除的路径
                excludePathPatterns("/admin/login");
    }
}
