package com.imageprocessor.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

@Service
public class BackgroundRemovalService {

    public byte[] removeBackground(
            MultipartFile imageFile,
            int threshold) throws Exception {

        BufferedImage inputImage =
                ImageIO.read(imageFile.getInputStream());

        int width = inputImage.getWidth();
        int height = inputImage.getHeight();

        BufferedImage outputImage =
                new BufferedImage(
                        width,
                        height,
                        BufferedImage.TYPE_INT_ARGB);

        // Assume top-left corner belongs to background
        int backgroundRGB = inputImage.getRGB(0, 0);

        int bgRed = (backgroundRGB >> 16) & 0xFF;
        int bgGreen = (backgroundRGB >> 8) & 0xFF;
        int bgBlue = backgroundRGB & 0xFF;

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

                int colorDifference =
                        Math.abs(red - bgRed) +
                                Math.abs(green - bgGreen) +
                                Math.abs(blue - bgBlue);

                if (colorDifference <= threshold) {

                    // Transparent pixel
                    outputImage.setRGB(
                            sourceX,
                            sourceY,
                            0x00000000);

                } else {

                    // Keep foreground pixel
                    outputImage.setRGB(
                            sourceX,
                            sourceY,
                            rgb);
                }
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