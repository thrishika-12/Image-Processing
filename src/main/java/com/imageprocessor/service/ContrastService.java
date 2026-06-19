package com.imageprocessor.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

@Service
public class ContrastService {

    public byte[] adjustContrast(
            MultipartFile image,
            double contrastFactor) throws Exception {

        BufferedImage inputImage =
                ImageIO.read(image.getInputStream());

        int width = inputImage.getWidth();
        int height = inputImage.getHeight();

        BufferedImage outputImage =
                new BufferedImage(
                        width,
                        height,
                        BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {

                int rgb = inputImage.getRGB(x, y);

                int r = (rgb >> 16) & 0xff;
                int g = (rgb >> 8) & 0xff;
                int b = rgb & 0xff;

                r = adjustChannel(r, contrastFactor);
                g = adjustChannel(g, contrastFactor);
                b = adjustChannel(b, contrastFactor);

                int newRgb =
                        (r << 16) |
                                (g << 8) |
                                b;

                outputImage.setRGB(x, y, newRgb);
            }
        }

        ByteArrayOutputStream baos =
                new ByteArrayOutputStream();

        ImageIO.write(outputImage, "png", baos);

        return baos.toByteArray();
    }

    private int adjustChannel(
            int value,
            double contrastFactor) {

        int newValue = (int)
                ((value - 128) * contrastFactor + 128);

        return Math.max(
                0,
                Math.min(255, newValue));
    }
}