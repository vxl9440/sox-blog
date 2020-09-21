package com.sox.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyConfig implements WebMvcConfigurer {
    //设置根目录
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/index.html").setViewName("index");
        registry.addViewController("/login.html").setViewName("login");
        registry.addViewController("/signup.html").setViewName("signup");
        registry.addViewController("/space.html").setViewName("space");
        registry.addViewController("/edit.html").setViewName("edit");
        registry.addViewController("/view.html").setViewName("view");
        registry.addViewController("/about.html").setViewName("about");
    }
}
