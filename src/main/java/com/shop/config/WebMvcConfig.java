package com.shop.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${uploadPath}") //application.properties의 uploadPath를 읽어온다.
    String uploadPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**") //웹브라우저에 입력하는 url에 /images로 시작하는 경우 uploadPath에 설정한 폴더를 기준으로 파일을 읽어오도록 설정한다.
                .addResourceLocations(uploadPath); //컴퓨터에 저장된 파일을 읽어올 root경로를 설정한다.
    }
}
