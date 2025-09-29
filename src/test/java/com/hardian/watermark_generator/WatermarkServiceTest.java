package com.hardian.watermark_generator;

import org.junit.jupiter.api.Test;

import org.springframework.mock.web.MockMultipartFile;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.Color;
import java.io.ByteArrayOutputStream;
import javax.imageio.ImageIO;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class WatermarkServiceTest {

    @Test
    void addTextWatermark_shouldReturnImageBytes() throws IOException {
        WatermarkService service = new WatermarkService();

        // Create a simple in-memory PNG image
        BufferedImage img = new BufferedImage(100, 50, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = img.createGraphics();
        g2d.setColor(Color.BLUE);
        g2d.fillRect(0, 0, 100, 50);
        g2d.dispose();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(img, "png", baos);
        byte[] imageBytes = baos.toByteArray();

        MockMultipartFile file = new MockMultipartFile("image", "test.png", "image/png", imageBytes);
        String watermark = "Test Watermark";
        // Should not throw and should return a non-null byte array
        assertDoesNotThrow(() -> {
            byte[] result = service.addTextWatermark(file, watermark);
            assertNotNull(result);
            assertTrue(result.length > 0);
        });
    }
}
