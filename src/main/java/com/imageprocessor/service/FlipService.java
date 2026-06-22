package com.imageprocessor.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

@Service
public class FlipService {

    public byte[] flipImage(
            MultipartFile imageFile,
            String flipType) throws Exception {

        BufferedImage inputImage =
                ImageIO.read(imageFile.getInputStream());

        int width = inputImage.getWidth();
        int height = inputImage.getHeight();

        BufferedImage outputImage =
                new BufferedImage(
                        width,
                        height,
                        inputImage.getType());

        if ("HORIZONTAL".equalsIgnoreCase(flipType)) {
            horizontalFlip(inputImage, outputImage);
        } else if ("VERTICAL".equalsIgnoreCase(flipType)) {
            verticalFlip(inputImage, outputImage);
        } else {
            throw new IllegalArgumentException(
                    "flipType must be HORIZONTAL or VERTICAL");
        }

        ByteArrayOutputStream baos =
                new ByteArrayOutputStream();

        ImageIO.write(outputImage, "jpg", baos);

        return baos.toByteArray();
    }

    /**
     * Horizontal Flip
     * Left ↔ Right
     */
    private void horizontalFlip(
            BufferedImage input,
            BufferedImage output) {

        int width = input.getWidth();
        int height = input.getHeight();

        for (int y = 0; y < height; y++) {

            for (int x = 0; x < width; x++) {

                int sourceIndex =
                        y * width + x;

                int flippedX =
                        width - 1 - x;

                int destinationIndex =
                        y * width + flippedX;

                int sourceX = sourceIndex % width;
                int sourceY = sourceIndex / width;

                int destX = destinationIndex % width;
                int destY = destinationIndex / width;

                int rgb =
                        input.getRGB(sourceX, sourceY);

                output.setRGB(destX, destY, rgb);
            }
        }
    }

    /**
     * Vertical Flip
     * Top ↔ Bottom
     */
    private void verticalFlip(
            BufferedImage input,
            BufferedImage output) {

        int width = input.getWidth();
        int height = input.getHeight();

        for (int y = 0; y < height; y++) {

            for (int x = 0; x < width; x++) {

                int sourceIndex =
                        y * width + x;

                int flippedY =
                        height - 1 - y;

                int destinationIndex =
                        flippedY * width + x;

                int sourceX = sourceIndex % width;
                int sourceY = sourceIndex / width;

                int destX = destinationIndex % width;
                int destY = destinationIndex / width;

                int rgb =
                        input.getRGB(sourceX, sourceY);

                output.setRGB(destX, destY, rgb);
            }
        }
    }
}