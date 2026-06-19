package com.imageprocessor.service;

import com.imageprocessor.util.ImageUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

@Service
public class BrightnessService {

    public byte[] adjustBrightness(
            MultipartFile image,
            int brightness) throws Exception {

        BufferedImage input = ImageIO.read(image.getInputStream());

        int width = input.getWidth();
        int height = input.getHeight();

        for (int i = 0; i < width * height; i++) {

            int x = i % width;
            int y = i / width;

            int rgb = input.getRGB(x, y);

            int alpha = (rgb >> 24) & 0xff;
            int red   = (rgb >> 16) & 0xff;
            int green = (rgb >> 8) & 0xff;
            int blue  = rgb & 0xff;

            red = clamp(red + brightness);
            green = clamp(green + brightness);
            blue = clamp(blue + brightness);

            int newRgb =
                    (alpha << 24) |
                            (red << 16) |
                            (green << 8) |
                            blue;

            input.setRGB(x, y, newRgb);
        }

        return ImageUtils.toByteArray(input);
    }

    private int clamp(int value) {
        return Math.max(0, Math.min(255, value));
    }
}