
package com.hardian.watermark_generator;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;


@Service
public class WatermarkService {
	/**
	 * Adds a repeated watermark text all over the provided image.
	 * @param imageFile the image file
	 * @param watermarkText the text to use as watermark
	 * @return byte[] of the watermarked image (PNG format)
	 * @throws IOException if image processing fails
	 */
	public byte[] addTiledWatermark(MultipartFile imageFile, String watermarkText) throws IOException {
		BufferedImage originalImage = readImage(imageFile);
		int width = originalImage.getWidth();
		int height = originalImage.getHeight();

		Graphics2D g2d = null;
		BufferedImage watermarked = createWatermarkedImage(originalImage);
		g2d = watermarked.createGraphics();
		setWatermarkGraphics(g2d, width, 0.3f, new Color(120, 120, 120), width / 20);

		FontMetrics fontMetrics = g2d.getFontMetrics();
		int stringWidth = fontMetrics.stringWidth(watermarkText);
		int stringHeight = fontMetrics.getHeight();

		// Tile the watermark text across the image
		for (int y = 0; y < height; y += stringHeight + 20) {
			for (int x = 0; x < width; x += stringWidth + 40) {
				g2d.drawString(watermarkText, x, y + fontMetrics.getAscent());
			}
		}
		g2d.dispose();
		return bufferedImageToPngBytes(watermarked);
	}

	/**
	 * Adds a watermark text to the provided image.
	 * @param imageFile the image file
	 * @param watermarkText the text to use as watermark
	 * @return byte[] of the watermarked image (PNG format)
	 * @throws IOException if image processing fails
	 */
	public byte[] addTextWatermark(MultipartFile imageFile, String watermarkText) throws IOException {
		BufferedImage originalImage = readImage(imageFile);
		int width = originalImage.getWidth();
		int height = originalImage.getHeight();

		Graphics2D g2d = null;
		BufferedImage watermarked = createWatermarkedImage(originalImage);
		g2d = watermarked.createGraphics();
		setWatermarkGraphics(g2d, width, 0.4f, new Color(120, 120, 120), width / 15);

		FontMetrics fontMetrics = g2d.getFontMetrics();
		int stringWidth = fontMetrics.stringWidth(watermarkText);
		int stringHeight = fontMetrics.getHeight();
		int x = width - stringWidth - 20;
		int y = height - stringHeight + fontMetrics.getAscent() - 10;

		g2d.drawString(watermarkText, x, y);
		g2d.dispose();
		return bufferedImageToPngBytes(watermarked);
	}
	// --- Private helpers to reduce code duplication ---
	private BufferedImage readImage(MultipartFile imageFile) throws IOException {
		return ImageIO.read(imageFile.getInputStream());
	}

	private BufferedImage createWatermarkedImage(BufferedImage originalImage) {
		int width = originalImage.getWidth();
		int height = originalImage.getHeight();
		BufferedImage watermarked = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = watermarked.createGraphics();
		g2d.drawImage(originalImage, 0, 0, null);
		g2d.dispose();
		return watermarked;
	}

	private void setWatermarkGraphics(Graphics2D g2d, int width, float alpha, Color color, int fontSize) {
		AlphaComposite alphaChannel = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
		g2d.setComposite(alphaChannel);
		g2d.setColor(color);
		g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, Math.max(18, fontSize)));
	}

	private byte[] bufferedImageToPngBytes(BufferedImage image) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(image, "png", baos);
		return baos.toByteArray();
	}
}
