package com.example.demo._core.config;

import jakarta.servlet.Servlet;
import org.h2.server.web.JakartaWebServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class H2ConsoleConfig {

    @Bean
    public ServletRegistrationBean<Servlet> h2Console() {
        // H2 콘솔 서블릿을 /h2-console/* 경로에 명시적으로 등록 (Jakarta EE 지원)
        ServletRegistrationBean<Servlet> bean = new ServletRegistrationBean<>(new JakartaWebServlet());
        bean.addUrlMappings("/h2-console/*");
        return bean;
    }
}
