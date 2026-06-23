package com.imageprocessor.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

@Service
public class ZoomOutService {

    public byte[] zoomOutImage(
            MultipartFile image,
            double zoomFactor)
            throws Exception {

        if (zoomFactor <= 1.0) {
            throw new IllegalArgumentException(
                    "Zoom factor must be greater than 1");
        }

        BufferedImage originalImage =
                ImageIO.read(image.getInputStream());

        int oldWidth = originalImage.getWidth();
        int oldHeight = originalImage.getHeight();

        int newWidth =
                (int) (oldWidth / zoomFactor);

        int newHeight =
                (int) (oldHeight / zoomFactor);

        int[] sourcePixels =
                originalImage.getRGB(
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
                        (int) (x * zoomFactor);

                int sourceY =
                        (int) (y * zoomFactor);

                if (sourceX >= oldWidth) {
                    sourceX = oldWidth - 1;
                }

                if (sourceY >= oldHeight) {
                    sourceY = oldHeight - 1;
                }

                int sourceIndex =
                        sourceY * oldWidth + sourceX;

                int targetIndex =
                        y * newWidth + x;

                targetPixels[targetIndex] =
                        sourcePixels[sourceIndex];
            }
        }

        BufferedImage outputImage =
                new BufferedImage(
                        newWidth,
                        newHeight,
                        BufferedImage.TYPE_INT_ARGB);

        outputImage.setRGB(
                0,
                0,
                newWidth,
                newHeight,
                targetPixels,
                0,
                newWidth);

        ByteArrayOutputStream outputStream =
                new ByteArrayOutputStream();

        ImageIO.write(
                outputImage,
                "png",
                outputStream);

        return outputStream.toByteArray();
    }
}