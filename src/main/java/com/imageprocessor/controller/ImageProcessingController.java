package com.imageprocessor.controller;

import com.imageprocessor.service.*;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/process")
public class ImageProcessingController {

    private final BrightnessService brightnessService;

    private final ContrastService contrastService;

    private final BlurService blurService;

    private final SharpenService sharpenService;

    private final RotationService rotationService;

    private final FlipService flipService;

    public ImageProcessingController(
            BrightnessService brightnessService,
            ContrastService contrastService,
            BlurService blurService,
            SharpenService sharpenService,
            RotationService rotationService, FlipService flipService) {

        this.brightnessService = brightnessService;
        this.contrastService = contrastService;
        this.blurService = blurService;
        this.sharpenService = sharpenService;
        this.rotationService = rotationService;
        this.flipService = flipService;
    }

    @PostMapping("/brightness")
    public ResponseEntity<byte[]> adjustBrightness(
            @RequestParam MultipartFile image,
            @RequestParam int brightness) throws Exception {

        byte[] processedImage =
                brightnessService.adjustBrightness(
                        image,
                        brightness);

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(processedImage);
    }

    @PostMapping("/contrast")
    public ResponseEntity<byte[]> adjustContrast(
            @RequestParam MultipartFile image,
            @RequestParam double contrastFactor)
            throws Exception {

        byte[] processedImage =
                contrastService.adjustContrast(
                        image,
                        contrastFactor);

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(processedImage);
    }

    @PostMapping("/blur")
    public ResponseEntity<byte[]> blurImage(
            @RequestParam MultipartFile image,
            @RequestParam int intensity)
            throws Exception {

        byte[] processedImage =
                blurService.blurImage(
                        image,
                        intensity);

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(processedImage);
    }

    @PostMapping("/sharpen")
    public ResponseEntity<byte[]> sharpenImage(
            @RequestParam MultipartFile image,
            @RequestParam int intensity)
            throws Exception {

        byte[] processedImage =
                sharpenService.sharpenImage(
                        image,
                        intensity);

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(processedImage);
    }

    @PostMapping("/rotate")
    public ResponseEntity<byte[]> rotateImage(
            @RequestParam MultipartFile image,
            @RequestParam double angle,
            @RequestParam String direction)
            throws Exception {

        byte[] processedImage =
                rotationService.rotateImage(
                        image,
                        angle,
                        direction);

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(processedImage);
    }

    @PostMapping("/flip")
    public ResponseEntity<byte[]> flipImage(
            @RequestParam("image") MultipartFile image,
            @RequestParam("flipType") String flipType) throws Exception {

        byte[] result = flipService.flipImage(image, flipType);

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(result);
    }
}