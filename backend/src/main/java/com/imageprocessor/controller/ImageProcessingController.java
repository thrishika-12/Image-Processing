package com.imageprocessor.controller;

import com.imageprocessor.dtos.ImageShapeResponse;
import com.imageprocessor.service.*;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@RequestMapping("/api/process")
@CrossOrigin(origins = "http://localhost:5173")
public class ImageProcessingController {

    private final BrightnessService brightnessService;

    private final ContrastService contrastService;

    private final BlurService blurService;

    private final SharpenService sharpenService;

    private final RotationService rotationService;

    private final FlipService flipService;

    private final GrayscaleService grayscaleService;

    private final BackgroundRemovalService backgroundRemovalService;

    private final ShapeDetectionService shapeDetectionService;

    private final ZoomService zoomService;

    private final ZoomOutService zoomOutService;

    private final ImageLayerService imageLayerService;

    public ImageProcessingController(
            BrightnessService brightnessService,
            ContrastService contrastService,
            BlurService blurService,
            SharpenService sharpenService,
            RotationService rotationService, FlipService flipService, GrayscaleService grayscaleService, BackgroundRemovalService backgroundRemovalService, ShapeDetectionService shapeDetectionService, ZoomService zoomService, ZoomOutService zoomOutService, ImageLayerService imageLayerService) {

        this.brightnessService = brightnessService;
        this.contrastService = contrastService;
        this.blurService = blurService;
        this.sharpenService = sharpenService;
        this.rotationService = rotationService;
        this.flipService = flipService;
        this.grayscaleService = grayscaleService;
        this.backgroundRemovalService = backgroundRemovalService;
        this.shapeDetectionService = shapeDetectionService;
        this.zoomService = zoomService;
        this.zoomOutService = zoomOutService;
        this.imageLayerService = imageLayerService;
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

    @PostMapping(
            value = "/remove-background",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<byte[]> removeBackground(
            @RequestParam("image") MultipartFile image,
            @RequestParam(value = "threshold", defaultValue = "80") int threshold)
            throws Exception {

        byte[] processedImage =
                backgroundRemovalService.removeBackground(
                        image,
                        threshold);

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(processedImage);
    }

    @PostMapping(
            value = "/grayscale",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<byte[]> grayscale(
            @RequestParam("image") MultipartFile image)
            throws Exception {

        byte[] result = grayscaleService.convertToGrayscale(image);

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(result);
    }

    @PostMapping(
            value = "/detect-shape",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ImageShapeResponse> detectShape(
            @RequestParam("image") MultipartFile image)
            throws Exception {

        return ResponseEntity.ok(
                shapeDetectionService.detectShape(image));
    }

    @PostMapping("/zoom")
    public ResponseEntity<byte[]> zoomImage(
            @RequestParam MultipartFile image,
            @RequestParam double zoomFactor)
            throws Exception {

        byte[] processedImage =
                zoomService.zoomImage(
                        image,
                        zoomFactor);

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(processedImage);
    }

    @PostMapping("/zoom-out")
    public ResponseEntity<byte[]> zoomOutImage(
            @RequestParam MultipartFile image,
            @RequestParam double zoomFactor)
            throws Exception {

        byte[] processedImage =
                zoomOutService.zoomOutImage(
                        image,
                        zoomFactor);

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(processedImage);
    }

    @PostMapping("/layer")
    public ResponseEntity<byte[]> layerImages(
            @RequestParam MultipartFile backgroundImage,
            @RequestParam MultipartFile overlayImage,
            @RequestParam int positionX,
            @RequestParam int positionY)
            throws Exception {

        byte[] result =
                imageLayerService.layerImages(
                        backgroundImage,
                        overlayImage,
                        positionX,
                        positionY);

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(result);
    }
}