package com.example.demo.Configuration;

import com.example.demo.Interceptor.ArticleClickInterceptor;
import com.example.demo.Interceptor.LoginRequestInterceptor;
import com.example.demo.Interceptor.PassportInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {
    @Autowired
    private PassportInterceptor passportInterceptor;

    @Autowired
    private LoginRequestInterceptor loginRequestInterceptor;

    @Autowired
    private ArticleClickInterceptor articleClickInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(passportInterceptor).excludePathPatterns("/js/*").excludePathPatterns("/css/*").
                excludePathPatterns("/img/*").excludePathPatterns("/fonts/*");
        // 用户可以创建文章，权限：认证、赋权。认证使用cookie保存登录凭证，每次请求带着具体的header。赋权使用拦截器
        registry.addInterceptor(loginRequestInterceptor).addPathPatterns("/create").addPathPatterns("/admins/mdf");
        registry.addInterceptor(articleClickInterceptor).addPathPatterns("/article/*");
        super.addInterceptors(registry);
    }
}
