package com.imageprocessor.service;

import com.imageprocessor.dtos.ImageShapeResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

@Service
public class ShapeDetectionService {

    public ImageShapeResponse detectShape(
            MultipartFile imageFile)
            throws Exception {

        BufferedImage image =
                ImageIO.read(
                        imageFile.getInputStream());

        int width = image.getWidth();
        int height = image.getHeight();

        String shape;

        if (width == height) {
            shape = "Square";
        } else {
            shape = "Rectangle";
        }

        return new ImageShapeResponse(
                width,
                height,
                shape);
    }
}