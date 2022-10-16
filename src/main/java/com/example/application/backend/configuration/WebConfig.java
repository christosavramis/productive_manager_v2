package com.example.application.backend.configuration;

import com.example.application.backend.service.ImageService;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        try {
            Files.createDirectories(Paths.get(ImageService.IMAGE_FOLDER_PATH_SAVE));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        registry.addResourceHandler(ImageService.IMAGE_FOLDER_PATH_DISPLAY + "**")
			    .addResourceLocations("file:resources/", "file:" + ImageService.IMAGE_FOLDER_PATH_SAVE);
    }
}