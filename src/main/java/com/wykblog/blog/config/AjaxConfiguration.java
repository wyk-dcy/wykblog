package com.wykblog.blog.config;

import com.wykblog.blog.filter.CrosFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AjaxConfiguration {

    @Bean
    public FilterRegistrationBean registerFilter(){

        FilterRegistrationBean bean = new FilterRegistrationBean();

        bean.addUrlPatterns("/*");
        bean.setFilter(new CrosFilter());

        return bean;
    }

}