package com.cocu;

import com.cocu.config.JwtRequestFilter;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

/**
 * Hello world!
 *
 */

@SpringBootApplication
@MapperScan("com.cocu.mapper")
public class CocuAdminApp {
    public static void main(String[] args){
        SpringApplication.run(CocuAdminApp.class, args);
    }

    @Bean
    public FilterRegistrationBean<JwtRequestFilter> jwtFilter() {
        FilterRegistrationBean<JwtRequestFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new JwtRequestFilter());
        registrationBean.addUrlPatterns("/*"); // Apply the filter to all routes
        return registrationBean;
    }
}
