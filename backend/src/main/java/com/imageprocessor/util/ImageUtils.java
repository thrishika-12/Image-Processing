package com.imageprocessor.util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

public class ImageUtils {

    public static byte[] toByteArray(
            BufferedImage image) throws Exception {

        ByteArrayOutputStream baos =
                new ByteArrayOutputStream();

        ImageIO.write(image, "png", baos);

        return baos.toByteArray();
    }
}