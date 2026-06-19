package com.imageprocessor.controller;

import com.imageprocessor.service.BrightnessService;
import com.imageprocessor.service.ContrastService;
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

    public ImageProcessingController(
            BrightnessService brightnessService,
            ContrastService contrastService) {

        this.brightnessService = brightnessService;
        this.contrastService = contrastService;
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
}