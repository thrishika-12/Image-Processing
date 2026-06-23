package com.imageprocessor.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

@Service
public class ZoomService {

    public byte[] zoomImage(
            MultipartFile image,
            double zoomFactor)
            throws Exception {

        BufferedImage original =
                ImageIO.read(image.getInputStream());

        int oldWidth = original.getWidth();
        int oldHeight = original.getHeight();

        int newWidth =
                (int)(oldWidth * zoomFactor);

        int newHeight =
                (int)(oldHeight * zoomFactor);

        int[] sourcePixels =
                original.getRGB(
                        0,
                        0,
                        oldWidth,
                        oldHeight,
                        null,
                        0,
                        oldWidth);

        int[] targetPixels =
                new int[newWidth * newHeight];

        for (int y = 0; y < newHeight; y++) {

            for (int x = 0; x < newWidth; x++) {

                int sourceX =
                        (int)(x / zoomFactor);

                int sourceY =
                        (int)(y / zoomFactor);

                int sourceIndex =
                        sourceY * oldWidth + sourceX;

                int targetIndex =
                        y * newWidth + x;

                targetPixels[targetIndex] =
                        sourcePixels[sourceIndex];
            }
        }

        BufferedImage result =
                new BufferedImage(
                        newWidth,
                        newHeight,
                        BufferedImage.TYPE_INT_ARGB);

        result.setRGB(
                0,
                0,
                newWidth,
                newHeight,
                targetPixels,
                0,
                newWidth);

        ByteArrayOutputStream output =
                new ByteArrayOutputStream();

        ImageIO.write(result, "png", output);

        return output.toByteArray();
    }
}