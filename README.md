# Watermark Generator

A Spring Boot REST API to add watermark text to images, supporting both single and tiled (repeated) watermark modes.

## Features
- Upload an image and a string to receive a watermarked image in response
- Choose between a single watermark or a tiled watermark (repeated across the image)
- Supports PNG images
- Simple API, ready for integration or extension

## API Usage

### Endpoint
```
POST /api/watermark
```

### Request (multipart/form-data)
- `image` (file): The image to watermark (PNG recommended)
- `text` (string): The watermark text
- `tiled` (boolean, optional): If true, tiles the watermark text all over the image. Default: false

#### Example using `curl`:
```
curl -X POST \
  -F "image=@your-image.png" \
  -F "text=Confidential" \
  -F "tiled=true" \
  http://localhost:8080/api/watermark --output watermarked.png
```

## Running the Application

### Prerequisites
- Java 17+
- Maven

### Build and Run
```
./mvnw clean package
java -jar target/watermark-generator-0.0.1-SNAPSHOT.jar
```

Or use the provided script:
```
./run-with-java17.sh
```

## Testing
Run all tests:
```
./mvnw test
```

## Code Coverage
To generate a code coverage report (after configuring JaCoCo):
```
./mvnw clean test jacoco:report
```

## Project Structure
- `src/main/java/com/hardian/watermark_generator/WatermarkController.java` - REST API controller
- `src/main/java/com/hardian/watermark_generator/WatermarkService.java` - Watermarking logic
- `src/test/java/com/hardian/watermark_generator/` - Unit and integration tests
- `test-watermark-endpoint.sh` - Example script to test the API

## License
MIT (add your license here)

---

Created by Hardian Adi
