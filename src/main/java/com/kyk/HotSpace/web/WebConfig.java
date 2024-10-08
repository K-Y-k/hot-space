package com.kyk.HotSpace.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@PropertySource("classpath:fileDir.properties")
public class WebConfig implements WebMvcConfigurer {

    /**
     * 파일 업로드한 것을 view에서 접근할 경로 설정
     */
    // @Value를 활용하는 이유는 협업 과정에서 각자 컴퓨터에서 저장하려면 각기 다른 상대경로로 주어야하기에 이를 활용
    @Value("${profileFile.dir}")  // 프로퍼티에 설정한 file.dir 값을 읽어온다.
    String profileFileUploadPath;

    @Value("${storeFile.dir}")
    String storeFileUploadPath;


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/profileImageUpload/**")
                // 웹 브라우저에 입력하는 url에 /profileImageUpload로 시작하는 경우 uploadPath에 설정한 폴더 기준으로 파일을 읽어오도록 설정
                .addResourceLocations(profileFileUploadPath);
                // 로컬 컴퓨터에 저장된 파일을 읽어올 root경로

        registry.addResourceHandler("/storeFileImageUpload/**")
                .addResourceLocations(storeFileUploadPath);
    }
}
