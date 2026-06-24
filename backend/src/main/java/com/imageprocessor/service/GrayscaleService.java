package com.imageprocessor.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

@Service
public class GrayscaleService {

    public byte[] convertToGrayscale(
            MultipartFile imageFile)
            throws Exception {

        BufferedImage inputImage =
                ImageIO.read(imageFile.getInputStream());

        int width = inputImage.getWidth();
        int height = inputImage.getHeight();

        BufferedImage outputImage =
                new BufferedImage(
                        width,
                        height,
                        BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < height; y++) {

            for (int x = 0; x < width; x++) {

                // Linear Index
                int index = y * width + x;

                int sourceX = index % width;
                int sourceY = index / width;

                int rgb =
                        inputImage.getRGB(
                                sourceX,
                                sourceY);

                int red = (rgb >> 16) & 0xFF;
                int green = (rgb >> 8) & 0xFF;
                int blue = rgb & 0xFF;

                int gray =
                        (red + green + blue) / 3;

                int grayRGB =
                        (gray << 16) |
                                (gray << 8) |
                                gray;

                outputImage.setRGB(
                        sourceX,
                        sourceY,
                        grayRGB);
            }
        }

        ByteArrayOutputStream outputStream =
                new ByteArrayOutputStream();

        ImageIO.write(
                outputImage,
                "png",
                outputStream);

        return outputStream.toByteArray();
    }
}