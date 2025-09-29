package com.hardian.watermark_generator;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@RestController
@RequestMapping("/api/watermark")
public class WatermarkController {

    private final WatermarkService watermarkService;

    public WatermarkController(WatermarkService watermarkService) {
        this.watermarkService = watermarkService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<byte[]> addWatermark(
            @RequestPart("image") MultipartFile image,
            @RequestPart("text") String text,
            @RequestParam(value = "tiled", required = false, defaultValue = "false") boolean tiled) {
        try {
            byte[] watermarkedImage;
            if (tiled) {
                watermarkedImage = watermarkService.addTiledWatermark(image, text);
            } else {
                watermarkedImage = watermarkService.addTextWatermark(image, text);
            }
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=watermarked.png")
                    .contentType(MediaType.IMAGE_PNG)
                    .body(watermarkedImage);
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
