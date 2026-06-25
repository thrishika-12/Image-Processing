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
            String direction
    ) throws Exception {

        if ("anticlockwise".equalsIgnoreCase(direction)) {
            angle = -angle;
        }

        BufferedImage input = ImageIO.read(image.getInputStream());

        int width = input.getWidth();
        int height = input.getHeight();

        double radians = Math.toRadians(angle);
        double cos = Math.cos(radians);
        double sin = Math.sin(radians);

        // -----------------------------
        // 1. Compute new image bounds
        // -----------------------------
        int newWidth = (int) Math.ceil(
                Math.abs(width * cos) + Math.abs(height * sin)
        );
        int newHeight = (int) Math.ceil(
                Math.abs(height * cos) + Math.abs(width * sin)
        );

        BufferedImage output = new BufferedImage(
                newWidth,
                newHeight,
                BufferedImage.TYPE_INT_ARGB
        );

        int[] inputPixels = new int[width * height];
        input.getRGB(0, 0, width, height, inputPixels, 0, width);

        int[] outputPixels = new int[newWidth * newHeight];

        double cxIn = width / 2.0;
        double cyIn = height / 2.0;

        double cxOut = newWidth / 2.0;
        double cyOut = newHeight / 2.0;

        // -----------------------------------
        // 2. Inverse mapping with interpolation
        // -----------------------------------
        for (int y = 0; y < newHeight; y++) {
            for (int x = 0; x < newWidth; x++) {

                // translate to center
                double dx = x - cxOut;
                double dy = y - cyOut;

                // inverse rotation
                double srcX = cos * dx + sin * dy + cxIn;
                double srcY = -sin * dx + cos * dy + cyIn;

                int x0 = (int) Math.floor(srcX);
                int y0 = (int) Math.floor(srcY);
                int x1 = x0 + 1;
                int y1 = y0 + 1;

                int index = y * newWidth + x;

                if (x0 >= 0 && x1 < width && y0 >= 0 && y1 < height) {

                    // -------------------------
                    // bilinear interpolation
                    // -------------------------
                    double dx1 = srcX - x0;
                    double dy1 = srcY - y0;

                    int p00 = inputPixels[y0 * width + x0];
                    int p10 = inputPixels[y0 * width + x1];
                    int p01 = inputPixels[y1 * width + x0];
                    int p11 = inputPixels[y1 * width + x1];

                    int a = bilerp(getA(p00), getA(p10), getA(p01), getA(p11), dx1, dy1);
                    int r = bilerp(getR(p00), getR(p10), getR(p01), getR(p11), dx1, dy1);
                    int g = bilerp(getG(p00), getG(p10), getG(p01), getG(p11), dx1, dy1);
                    int b = bilerp(getB(p00), getB(p10), getB(p01), getB(p11), dx1, dy1);

                    outputPixels[index] =
                            (a << 24) | (r << 16) | (g << 8) | b;

                } else {
                    // transparent background
                    outputPixels[index] = 0x00000000;
                }
            }
        }

        output.setRGB(0, 0, newWidth, newHeight, outputPixels, 0, newWidth);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(output, "png", baos);

        return baos.toByteArray();
    }

    // -------------------------
    // helpers
    // -------------------------

    private int getA(int c) { return (c >> 24) & 0xff; }
    private int getR(int c) { return (c >> 16) & 0xff; }
    private int getG(int c) { return (c >> 8) & 0xff; }
    private int getB(int c) { return c & 0xff; }

    private int bilerp(int c00, int c10, int c01, int c11,
                       double tx, double ty) {

        double i1 = c00 * (1 - tx) + c10 * tx;
        double i2 = c01 * (1 - tx) + c11 * tx;
        return (int) Math.round(i1 * (1 - ty) + i2 * ty);
    }
}