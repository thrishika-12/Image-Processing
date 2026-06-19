package com.imageprocessor.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

@Service
public class SharpenService {

    public byte[] sharpenImage(
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

        int centerWeight;

        switch (intensity) {
            case 1:
                centerWeight = 5;
                break;

            case 2:
                centerWeight = 7;
                break;

            case 3:
                centerWeight = 9;
                break;

            default:
                centerWeight = 5;
        }

        for (int y = 1; y < height - 1; y++) {

            for (int x = 1; x < width - 1; x++) {

                int centerIndex = y * width + x;
                int topIndex = (y - 1) * width + x;
                int bottomIndex = (y + 1) * width + x;
                int leftIndex = y * width + (x - 1);
                int rightIndex = y * width + (x + 1);

                int centerRGB = pixels[centerIndex];
                int topRGB = pixels[topIndex];
                int bottomRGB = pixels[bottomIndex];
                int leftRGB = pixels[leftIndex];
                int rightRGB = pixels[rightIndex];

                int r =
                        centerWeight * ((centerRGB >> 16) & 0xff)
                                - ((topRGB >> 16) & 0xff)
                                - ((bottomRGB >> 16) & 0xff)
                                - ((leftRGB >> 16) & 0xff)
                                - ((rightRGB >> 16) & 0xff);

                int g =
                        centerWeight * ((centerRGB >> 8) & 0xff)
                                - ((topRGB >> 8) & 0xff)
                                - ((bottomRGB >> 8) & 0xff)
                                - ((leftRGB >> 8) & 0xff)
                                - ((rightRGB >> 8) & 0xff);

                int b =
                        centerWeight * (centerRGB & 0xff)
                                - (topRGB & 0xff)
                                - (bottomRGB & 0xff)
                                - (leftRGB & 0xff)
                                - (rightRGB & 0xff);

                r = clamp(r);
                g = clamp(g);
                b = clamp(b);

                outputPixels[centerIndex] =
                        (r << 16)
                                | (g << 8)
                                | b;
            }
        }

        // Copy border pixels unchanged
        for (int x = 0; x < width; x++) {
            outputPixels[x] = pixels[x];
            outputPixels[(height - 1) * width + x] =
                    pixels[(height - 1) * width + x];
        }

        for (int y = 0; y < height; y++) {
            outputPixels[y * width] =
                    pixels[y * width];

            outputPixels[y * width + width - 1] =
                    pixels[y * width + width - 1];
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

        ImageIO.write(output, "png", baos);

        return baos.toByteArray();
    }

    private int clamp(int value) {

        if (value < 0) {
            return 0;
        }

        if (value > 255) {
            return 255;
        }

        return value;
    }
}