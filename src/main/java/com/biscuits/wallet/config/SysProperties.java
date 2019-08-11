package com.biscuits.wallet.config;

import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

/**
 * @author biscuits
 * @date 2019-08-10
 */
@Configuration
public class SysProperties {
    @Bean("wechat")
    public PropertiesFactoryBean wechat(){
        PropertiesFactoryBean config = new PropertiesFactoryBean();
        ClassPathResource classPathResource = new ClassPathResource("/wechat.properties");
        config.setLocations(classPathResource);
        return config;
    }
}
