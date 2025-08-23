# Event Service Image Upload Functionality

This document describes the image upload functionality implemented in the Event Service.

## Overview

The Event Service now supports image uploads when creating events. Images are stored locally on the server and can be accessed via HTTP endpoints.

## Features

- **Image Upload**: Support for JPG, JPEG, PNG, and GIF files
- **File Validation**: Automatic validation of file type and size
- **Unique Naming**: Generated unique filenames to prevent conflicts
- **Image Serving**: HTTP endpoint to serve uploaded images
- **Error Handling**: Comprehensive error handling for upload failures

## Configuration

### Application Properties

```properties
# File upload configuration
spring.servlet.multipart.max-file-size=10MB
spring.servlet.multipart.max-request-size=10MB
spring.servlet.multipart.enabled=true

# Image upload configuration
app.upload.dir=${user.home}/event-images
app.upload.max-size=10485760
app.upload.allowed-types=jpg,jpeg,png,gif
```

### Configuration Details

- **Max File Size**: 10MB per file
- **Upload Directory**: Defaults to `{user.home}/event-images`
- **Allowed Formats**: JPG, JPEG, PNG, GIF
- **File Validation**: Magic number validation to ensure files are actual images

## API Endpoints

### 1. Create Event with Image

**Endpoint**: `POST /api/events/with-image`

**Content-Type**: `multipart/form-data`

**Request Body**:
```
name: "Event Name"
description: "Event Description"
location: "Event Location"
date: "2024-01-01T10:00:00"
category: "MUSIC"
seatCategories: [{"categoryName": "VIP", "totalSeats": 100, "pricePerSeat": 50.0}]
image: [binary file]
```

**Response**: `EventResponseDTO` with the created event including the image URL

### 2. Create Event without Image (Existing)

**Endpoint**: `POST /api/events`

**Content-Type**: `application/json`

**Request Body**: `EventRequestDTO` (existing functionality)

### 3. Serve Images

**Endpoint**: `GET /api/events/images/{filename}`

**Response**: The actual image file

## Database Changes

The `Event` entity now includes an `imageUrl` field:

```java
@Column(name = "image_url")
private String imageUrl;
```

## File Storage

- Images are stored in the configured upload directory
- Filenames are generated using UUID to prevent conflicts
- Original file extensions are preserved
- Images are served directly by the application

## Error Handling

The service includes comprehensive error handling for:

- **File Size Exceeded**: Returns HTTP 413 (Payload Too Large)
- **Invalid File Type**: Returns HTTP 400 (Bad Request)
- **Empty Files**: Returns HTTP 400 (Bad Request)
- **Invalid Image Format**: Returns HTTP 400 (Bad Request)

## Security Considerations

- File type validation using magic numbers
- File size limits
- Unique filename generation
- No direct file path exposure

## Usage Examples

### Frontend Integration (Flutter)

```dart
// Example of how to use the image upload endpoint
Future<void> createEventWithImage({
  required String name,
  required String description,
  required String location,
  required DateTime date,
  required String category,
  required List<SeatCategory> seatCategories,
  required File imageFile,
}) async {
  var request = http.MultipartRequest(
    'POST',
    Uri.parse('http://localhost:8084/api/events/with-image'),
  );

  // Add text fields
  request.fields['name'] = name;
  request.fields['description'] = description;
  request.fields['location'] = location;
  request.fields['date'] = date.toIso8601String();
  request.fields['category'] = category;
  request.fields['seatCategories'] = jsonEncode(seatCategories);

  // Add image file
  request.files.add(
    await http.MultipartFile.fromPath(
      'image',
      imageFile.path,
    ),
  );

  var response = await request.send();
  if (response.statusCode == 200) {
    var responseData = await response.stream.bytesToString();
    var event = jsonDecode(responseData);
    print('Event created with image URL: ${event['imageUrl']}');
  }
}
```

### cURL Example

```bash
curl -X POST \
  http://localhost:8084/api/events/with-image \
  -H 'Content-Type: multipart/form-data' \
  -F 'name=Concert Event' \
  -F 'description=A great concert' \
  -F 'location=Music Hall' \
  -F 'date=2024-01-01T19:00:00' \
  -F 'category=MUSIC' \
  -F 'seatCategories=[{"categoryName":"VIP","totalSeats":100,"pricePerSeat":50.0}]' \
  -F 'image=@/path/to/image.jpg'
```

## Testing

To test the image upload functionality:

1. Start the Event Service
2. Use the `/api/events/with-image` endpoint with a multipart form
3. Verify the image is stored in the upload directory
4. Access the image via the returned URL
5. Verify the image is served correctly

## Future Enhancements

- Cloud storage integration (AWS S3, Google Cloud Storage)
- Image resizing and optimization
- Multiple image support per event
- Image metadata storage
- CDN integration for better performance
