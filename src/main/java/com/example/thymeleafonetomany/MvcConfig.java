package com.example.thymeleafonetomany;

import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

public class MvcConfig  implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        Path brandUploadDir = Paths.get("./brand-images");
        String brandUploadPath = brandUploadDir.toFile().getAbsolutePath();
        registry.addResourceHandler("/brand-images/**").addResourceLocations("file:/" + brandUploadPath + "/");
    }
}
