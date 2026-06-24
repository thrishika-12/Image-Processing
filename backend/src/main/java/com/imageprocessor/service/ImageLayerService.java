package com.imageprocessor.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

@Service
public class ImageLayerService {

    public byte[] layerImages(
            MultipartFile backgroundFile,
            MultipartFile overlayFile,
            int positionX,
            int positionY)
            throws Exception {

        BufferedImage background =
                ImageIO.read(backgroundFile.getInputStream());

        BufferedImage overlay =
                ImageIO.read(overlayFile.getInputStream());

        int bgWidth = background.getWidth();
        int bgHeight = background.getHeight();

        int overlayWidth = overlay.getWidth();
        int overlayHeight = overlay.getHeight();

        int[] backgroundPixels =
                background.getRGB(
                        0,
                        0,
                        bgWidth,
                        bgHeight,
                        null,
                        0,
                        bgWidth);

        int[] overlayPixels =
                overlay.getRGB(
                        0,
                        0,
                        overlayWidth,
                        overlayHeight,
                        null,
                        0,
                        overlayWidth);

        for (int y = 0; y < overlayHeight; y++) {

            for (int x = 0; x < overlayWidth; x++) {

                int bgX = positionX + x;
                int bgY = positionY + y;

                if (bgX < 0 ||
                        bgY < 0 ||
                        bgX >= bgWidth ||
                        bgY >= bgHeight) {

                    continue;
                }

                int overlayIndex =
                        y * overlayWidth + x;

                int bgIndex =
                        bgY * bgWidth + bgX;

                int overlayPixel =
                        overlayPixels[overlayIndex];

                backgroundPixels[bgIndex] =
                        overlayPixel;
            }
        }

        BufferedImage result =
                new BufferedImage(
                        bgWidth,
                        bgHeight,
                        BufferedImage.TYPE_INT_ARGB);

        result.setRGB(
                0,
                0,
                bgWidth,
                bgHeight,
                backgroundPixels,
                0,
                bgWidth);

        ByteArrayOutputStream output =
                new ByteArrayOutputStream();

        ImageIO.write(
                result,
                "png",
                output);

        return output.toByteArray();
    }
}