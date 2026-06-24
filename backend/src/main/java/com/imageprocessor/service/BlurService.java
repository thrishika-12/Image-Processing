package com.imageprocessor.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

@Service
public class BlurService {

    public byte[] blurImage(
            MultipartFile image,
            int intensity) throws Exception {

        BufferedImage input =
                ImageIO.read(image.getInputStream());

        int width = input.getWidth();
        int height = input.getHeight();

        int[] pixels = new int[width * height];

        input.getRGB(
                0,
                0,
                width,
                height,
                pixels,
                0,
                width);

        int[] outputPixels =
                new int[width * height];

        int radius;

        switch (intensity) {
            case 1:
                radius = 1; // 3x3
                break;
            case 2:
                radius = 2; // 5x5
                break;
            case 3:
                radius = 3; // 7x7
                break;
            default:
                radius = 1;
        }

        for (int y = 0; y < height; y++) {

            for (int x = 0; x < width; x++) {

                int redSum = 0;
                int greenSum = 0;
                int blueSum = 0;

                int count = 0;

                for (int dy = -radius;
                     dy <= radius;
                     dy++) {

                    for (int dx = -radius;
                         dx <= radius;
                         dx++) {

                        int nx = x + dx;
                        int ny = y + dy;

                        if (nx >= 0 &&
                                nx < width &&
                                ny >= 0 &&
                                ny < height) {

                            int neighborIndex =
                                    ny * width + nx;

                            int rgb =
                                    pixels[neighborIndex];

                            int r =
                                    (rgb >> 16) & 0xff;
                            int g =
                                    (rgb >> 8) & 0xff;
                            int b =
                                    rgb & 0xff;

                            redSum += r;
                            greenSum += g;
                            blueSum += b;

                            count++;
                        }
                    }
                }

                int avgR = redSum / count;
                int avgG = greenSum / count;
                int avgB = blueSum / count;

                int blurredRgb =
                        (avgR << 16)
                                | (avgG << 8)
                                | avgB;

                int currentIndex =
                        y * width + x;

                outputPixels[currentIndex] =
                        blurredRgb;
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

        ImageIO.write(output,
                "png",
                baos);

        return baos.toByteArray();
    }
}