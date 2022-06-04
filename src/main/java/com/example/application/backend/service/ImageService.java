package com.example.application.backend.service;

import com.example.application.backend.data.AuditType;
import com.example.application.backend.data.entity.Audit;
import com.vaadin.flow.component.upload.receivers.MultiFileMemoryBuffer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

@Service
public class ImageService {
    private @Autowired AuditService auditService;
    public static final String IMAGE_FOLDER_PATH_SAVE = "./src/main/resources/META-INF/resources/images/product/";
    public static final String IMAGE_FOLDER_PATH_DISPLAY = "./images/product/";

    public String saveImage(String name, String saveName, MultiFileMemoryBuffer buffer) {
        String location = "";
        try {
            location = IMAGE_FOLDER_PATH_DISPLAY + saveName;
            File outputFile = new File(IMAGE_FOLDER_PATH_SAVE + saveName);
            BufferedImage inputImage = ImageIO.read(buffer.getInputStream(name));
            ImageIO.write(inputImage, "png", outputFile);
            auditService.save(new Audit("Guest", LocalDateTime.now(),"created new image " + name, AuditType.INFO), this.getClass());
        } catch (IOException e) {
            auditService.save(new Audit("Guest", LocalDateTime.now(), e.getMessage(), AuditType.ERROR), this.getClass());
        }
        return location;
    }
}
