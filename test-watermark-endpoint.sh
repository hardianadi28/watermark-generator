#!/bin/zsh
# Test the /api/watermark endpoint with a sample image and watermark text

# Path to a sample PNG image (replace with your own if needed)
SAMPLE_IMAGE="/Users/hardiansyah/Downloads/ktp.jpeg"

# Create a sample image if it doesn't exist
echo "Creating a sample image..."
if [ ! -f "$SAMPLE_IMAGE" ]; then
  convert -size 100x50 xc:blue "$SAMPLE_IMAGE"
fi

curl -v -X POST \
  -F "image=@$SAMPLE_IMAGE;type=image/png" \
  -F "text=© Hardiansyah" \
  -F "tiled=false" \
  http://localhost:8080/api/watermark \
  --output watermarked.png

echo "Response saved to watermarked.png"
