package com.imageprocessor.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

@Service
public class RotationService {

    public byte[] rotateImage(
            MultipartFile image,
            double angle,
            String direction)
            throws Exception {

        if ("anticlockwise".equalsIgnoreCase(direction)) {
            angle = -angle;
        }

        BufferedImage input =
                ImageIO.read(image.getInputStream());

        int width = input.getWidth();
        int height = input.getHeight();

        int[] inputPixels =
                new int[width * height];

        int[] outputPixels =
                new int[width * height];

        input.getRGB(
                0,
                0,
                width,
                height,
                inputPixels,
                0,
                width);

        double radians =
                Math.toRadians(angle);

        double cos =
                Math.cos(radians);

        double sin =
                Math.sin(radians);

        double centerX =
                width / 2.0;

        double centerY =
                height / 2.0;

        for (int y = 0; y < height; y++) {

            for (int x = 0; x < width; x++) {

                double dx = x - centerX;
                double dy = y - centerY;

                double sourceX =
                        cos * dx
                                + sin * dy
                                + centerX;

                double sourceY =
                        -sin * dx
                                + cos * dy
                                + centerY;

                int sx =
                        (int)Math.round(sourceX);

                int sy =
                        (int)Math.round(sourceY);

                int outputIndex =
                        y * width + x;

                if (sx >= 0 &&
                        sx < width &&
                        sy >= 0 &&
                        sy < height) {

                    int sourceIndex =
                            sy * width + sx;

                    outputPixels[outputIndex] =
                            inputPixels[sourceIndex];
                }
                else {

                    outputPixels[outputIndex] =
                            0xFFFFFF;
                }
            }
        }

        BufferedImage output =
                new BufferedImage(
                        width,
                        height,
                        BufferedImage.TYPE_INT_RGB);

        output.setRGB(
                0,
                0,
                width,
                height,
                outputPixels,
                0,
                width);

        ByteArrayOutputStream baos =
                new ByteArrayOutputStream();

        ImageIO.write(
                output,
                "png",
                baos);

        return baos.toByteArray();
    }
}