package com.sox.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
public class DruidConfig {

    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean
    public DataSource dataSource(){
        return new DruidDataSource();
    }

    //后台监控功能
    //因为springBoot 内置了 servlet， 所以不需要配置web.xml
    @Bean
    public ServletRegistrationBean<StatViewServlet> statViewServlet(){
        ServletRegistrationBean<StatViewServlet> bean = new ServletRegistrationBean<>(new StatViewServlet(), "/druid/*");
        HashMap<String,String> map = new HashMap<>();

        //账号密码设置，key是固定的
        map.put("loginUsername","admin");
        map.put("loginPassword","123456");

        //允许谁访问
        map.put("allow","");

        //后台需要有人登录，账号密码设置
        bean.setInitParameters(map);
        return bean;
    }

    //filter
    @Bean
    public FilterRegistrationBean filterRegistrationBean(){
        FilterRegistrationBean bean= new FilterRegistrationBean();
        bean.setFilter(new WebStatFilter());
        HashMap<String, String> map= new HashMap<>();

        map.put("exclusion","*.js,*.css,/druid/*"); //排除

        bean.setInitParameters(map);

        return bean;
    }
}
