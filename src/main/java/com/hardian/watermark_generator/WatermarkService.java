
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
		BufferedImage originalImage = ImageIO.read(imageFile.getInputStream());
		int width = originalImage.getWidth();
		int height = originalImage.getHeight();

		BufferedImage watermarked = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = watermarked.createGraphics();
		g2d.drawImage(originalImage, 0, 0, null);

	// Make watermark slightly more transparent for a balanced look
	AlphaComposite alphaChannel = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f);
	g2d.setComposite(alphaChannel);
	g2d.setColor(new Color(120, 120, 120)); // Use a lighter gray for less dark watermark
		int fontSize = Math.max(18, width / 20);
		g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, fontSize));

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

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(watermarked, "png", baos);
		return baos.toByteArray();
	}

	/**
	 * Adds a watermark text to the provided image.
	 * @param imageFile the image file
	 * @param watermarkText the text to use as watermark
	 * @return byte[] of the watermarked image (PNG format)
	 * @throws IOException if image processing fails
	 */
	public byte[] addTextWatermark(MultipartFile imageFile, String watermarkText) throws IOException {
		BufferedImage originalImage = ImageIO.read(imageFile.getInputStream());
		int width = originalImage.getWidth();
		int height = originalImage.getHeight();

		BufferedImage watermarked = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = watermarked.createGraphics();
		g2d.drawImage(originalImage, 0, 0, null);

		// Set watermark properties
		AlphaComposite alphaChannel = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f);
		g2d.setComposite(alphaChannel);
		g2d.setColor(new Color(120, 120, 120)); // Use a lighter gray for less dark watermark
		int fontSize = Math.max(18, width / 15);
		g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, fontSize));

		// Calculate position
		FontMetrics fontMetrics = g2d.getFontMetrics();
		int stringWidth = fontMetrics.stringWidth(watermarkText);
		int stringHeight = fontMetrics.getHeight();
		int x = width - stringWidth - 20;
		int y = height - stringHeight + fontMetrics.getAscent() - 10;

		// Draw watermark
		g2d.drawString(watermarkText, x, y);
		g2d.dispose();

		// Convert to byte array
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(watermarked, "png", baos);
		return baos.toByteArray();
	}
}
