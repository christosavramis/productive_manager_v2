package com.example.application.backend.configuration;

import com.example.application.backend.service.ImageService;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
	    registry.addResourceHandler(ImageService.IMAGE_FOLDER_PATH_DISPLAY + "**")
			    .addResourceLocations("file:resources/", "file:" + ImageService.IMAGE_FOLDER_PATH_SAVE);
    }
}