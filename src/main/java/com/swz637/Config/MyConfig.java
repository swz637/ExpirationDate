package com.swz637.Config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;

/**
 * @ author: lsq637
 * @ since: 2022-09-21 14:46:44
 * @ describe:
 */
@Configuration
public class MyConfig implements WebMvcConfigurer {//通过实现接口，再实现其中的方法，来配置webmvc
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {//自定义导航页
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/index").setViewName("index");
        registry.addViewController("/index.html").setViewName("index");
    }

    @Bean
    public CommonsMultipartResolver commonsMultipartResolver() {//用于文件上传的配置类
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(17367648787L);
        multipartResolver.setDefaultEncoding("UTF-8");
        return multipartResolver;
    }
}
