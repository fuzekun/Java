package com.springboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author: Zekun Fu
 * @date: 2023/4/19 21:02
 * @Description:
 *  spring security的配置功能
 *  1. 加上注解
 *  2. 继承websecurityAdapter
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    /**
     *
     * 进行用户认证的操作
     * 配置用户名、密码、身份。 使用and配置多个
     * */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        super.configure(auth);      // 继承的用户，也就是在日志中生成的user，以及密码
        auth
                .inMemoryAuthentication()
                .withUser("fuzekun")
                .password("1230")
                .authorities("ROLE_admin");
//                .and()
//                .withUser("zhangjiao")
//                .password("1230");
//                .authorities("ROLE_USER");

    }

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests()     // 开启登录配置
//                .antMatchers("/test").hasRole("admin")
//                .anyRequest().authenticated(); // 只要认证了，任意都可以访问
////                .and()
////                // 处理登录
////                .formLogin()
////                // 登录页面
////                .loginPage("/login")
////                // 处理登录逻辑
////                .loginProcessingUrl("/doLogin")
////                // 默认为username, password
////                .usernameParameter("username")
////                .passwordParameter("password")
////                // 成功后的处理器,可以进行缓存啊，session的保存啊等
////                .successHandler(new AuthenticationSuccessHandler() {
////                    @Override
////                    public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse resp, Authentication auth) throws IOException, ServletException {
////                        resp.setContentType("application/json;charset=utf-8");
////                        PrintWriter out = resp.getWriter();
////                        out.write("登录成功");
////                        out.flush();
////                    }
////                })
//                // 和登录表单相关的统统通过
////                .permitAll()
////                .and()
////                .logout()
////                .logoutUrl("/logout")
////                .and()
////                .httpBasic()
////                .and()
//                // 不启用csrf防范
////                .csrf().disable();
//    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
