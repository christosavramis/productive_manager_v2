package com.example.application.backend.service;

import com.example.application.backend.data.AuditType;
import com.example.application.backend.data.entities.Audit;
import com.vaadin.flow.component.upload.receivers.MultiFileMemoryBuffer;
import org.apache.commons.codec.binary.Base64;
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
    public final static String IMAGE_FOLDER_PATH_SAVE = "./static/images/";
    public final static String IMAGE_FOLDER_PATH_DISPLAY = "./images/";

    public final static String IMAGE_FOLDER_PATH_DISPLAY_GEN = "./images/product/";

    public String saveImage(String name, String saveName, MultiFileMemoryBuffer buffer) {
        String location = "";
        try {
//            location = IMAGE_FOLDER_PATH_DISPLAY + saveName;
//            File outputFile = new File(IMAGE_FOLDER_PATH_SAVE + saveName);
//            BufferedImage inputImage = ImageIO.read(buffer.getInputStream(name));
//            ImageIO.write(inputImage, "png", outputFile);

            location = "data:image/png;base64," + Base64.encodeBase64String(buffer.getInputStream(name).readAllBytes());
            auditService.save(new Audit(LocalDateTime.now(),"created new image " + name, AuditType.INFO), this.getClass());
        } catch (IOException e) {
            auditService.save(new Audit(LocalDateTime.now(), e.getMessage(), AuditType.ERROR), this.getClass());
        }
        return location;
    }
}
