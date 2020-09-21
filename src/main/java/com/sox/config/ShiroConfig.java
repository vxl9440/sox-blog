package com.sox.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig{

    //ShiroFilterFactoryBean
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("defaultWebSecurityManager")
                                                                     DefaultWebSecurityManager securityManager){
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        //设置安全管理器
        bean.setSecurityManager(securityManager);

        /*
           anon: 无需认证就可以访问
           authc: 必须认证了才可以访问
           user: 必须要有 记住我 功能才能用
           perms: 拥有对某个资源的权限才可以才能访问
           role: 拥有某个角色权限才能访问
        */

        Map<String,String> filterMap = new LinkedHashMap<>();
        filterMap.put("/edit","authc"); // Blog authentication*/
        filterMap.put("/mySpace/**","authc");
        bean.setFilterChainDefinitionMap(filterMap);


        //设置登录请求
        bean.setLoginUrl("/toLogin");
        return bean;
    }

    //DefaultWebSecurityManager:2
    @Bean
    public DefaultWebSecurityManager defaultWebSecurityManager(@Qualifier("realm")
                                                                           UserRealm userRealm){
        DefaultWebSecurityManager webSecurityManager = new DefaultWebSecurityManager();
        //关联user realm
        webSecurityManager.setRealm(userRealm);
        return webSecurityManager;
    }



    //创建 realm 对象，需要自定义类:3
    @Bean(name = "realm")
    public UserRealm userRealm(){
        return new UserRealm();
    }


    //Shiro整合thymeleaf
    @Bean
    public ShiroDialect shiroDialect(){
        return new ShiroDialect();
    }
}