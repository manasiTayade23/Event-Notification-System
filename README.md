# Event Notification System

A robust, asynchronous event notification system built with Spring Boot that processes and delivers notifications via Email, SMS, and Push notifications with callback support.

## 🚀 Features

- **Multi-channel Notifications**: Support for Email, SMS, and Push notifications
- **Asynchronous Processing**: Non-blocking event processing with dedicated queues
- **Callback System**: Real-time status updates via HTTP callbacks
- **Graceful Shutdown**: Proper cleanup and event handling during system shutdown
- **Docker Support**: Containerized deployment with Docker and Docker Compose
- **Comprehensive Testing**: Unit tests with MockServer for HTTP callback testing

## 🏗️ Architecture

### Core Components

1. **Event Controller** (`EventController.java`)
   - REST API endpoints for event submission and callback reception
   - Input validation and event type conversion
   - Callback endpoint for receiving processing status updates

2. **Event Service** (`EventService.java`)
   - Manages separate queues for each notification type (Email, SMS, Push)
   - Event submission and queue management
   - Graceful shutdown handling

3. **Event Processor** (`EventProcessor.java`)
   - Background processing of events from queues
   - Simulated processing delays (Email: 5s, SMS: 3s, Push: 2s)
   - 10% simulated failure rate for testing
   - Multi-threaded processing with dedicated thread pools

4. **Callback Service** (`CallbackService.java`)
   - HTTP client for sending callback notifications
   - JSON serialization of callback requests
   - Error handling for failed callbacks

### Data Models

- **Event Types**: `EMAIL`, `SMS`, `PUSH`
- **Event Classes**: `EmailEvent`, `SmsEvent`, `PushEvent`
- **Payload Classes**: `EmailPayload`, `SmsPayload`, `PushPayload`
- **Response Models**: `EventResponse`, `CallbackRequest`

## 📋 API Endpoints

### Submit Event
```
POST /api/events
Content-Type: application/json
```

**Request Body:**
```json
{
  "eventType": "EMAIL|SMS|PUSH",
  "payload": {
    // Email payload
    "recipient": "user@example.com",
    "message": "Hello from the notification system!"
    
    // OR SMS payload
    "phoneNumber": "+1234567890",
    "message": "SMS notification"
    
    // OR Push payload
    "deviceId": "device123",
    "message": "Push notification"
  },
  "callbackUrl": "https://your-callback-url.com/callback"
}
```

**Response:**
```json
{
  "eventId": "uuid-generated-event-id",
  "message": "Event accepted for processing."
}
```

### Callback Endpoint
```
POST /callback
Content-Type: application/json
```

**Callback Payload:**
```json
{
  "eventId": "uuid-generated-event-id",
  "status": "COMPLETED|FAILED",
  "eventType": "EMAIL|SMS|PUSH",
  "errorMessage": "Error details (if failed)",
  "processedAt": "2024-01-01T12:00:00Z"
}
```

## 🛠️ Technology Stack

- **Framework**: Spring Boot 3.2.0
- **Java Version**: 17
- **Build Tool**: Maven
- **Dependencies**:
  - Spring Boot Web Starter
  - Jackson (JSON processing)
  - MockServer (Testing)
  - Spring Boot Test

## 🚀 Getting Started

### Prerequisites
- Java 17 or higher
- Maven 3.6+
- Docker (optional)

### Local Development

1. **Clone the repository**
   ```bash
   git clone https://github.com/manasiTayade23/Event-Notification-System.git
   cd event-notification-system
   ```

2. **Build the project**
   ```bash
   mvn clean install
   ```

3. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

The application will start on `http://localhost:8080`

### Docker Deployment

1. **Build and run with Docker Compose**
   ```bash
   docker-compose up --build
   ```

2. **Or build and run manually**
   ```bash
   # Build the JAR
   mvn clean package
   
   # Build Docker image
   docker build -t event-notification-system .
   
   # Run container
   docker run -p 8080:8080 event-notification-system
   ```

## 📝 Usage Examples

### Email Notification
```bash
curl -X POST http://localhost:8080/api/events \
  -H "Content-Type: application/json" \
  -d '{
    "eventType": "EMAIL",
    "payload": {
      "recipient": "user@example.com",
      "message": "Welcome to our platform!"
    },
    "callbackUrl": "https://your-app.com/callback"
  }'
```

### SMS Notification
```bash
curl -X POST http://localhost:8080/api/events \
  -H "Content-Type: application/json" \
  -d '{
    "eventType": "SMS",
    "payload": {
      "phoneNumber": "+1234567890",
      "message": "Your verification code is 123456"
    },
    "callbackUrl": "https://your-app.com/callback"
  }'
```

### Push Notification
```bash
curl -X POST http://localhost:8080/api/events \
  -H "Content-Type: application/json" \
  -d '{
    "eventType": "PUSH",
    "payload": {
      "deviceId": "device123",
      "message": "New message received"
    },
    "callbackUrl": "https://your-app.com/callback"
  }'
```

## 🧪 Testing

Run the test suite:
```bash
mvn test
```

The test suite includes:
- Unit tests for `EventService`
- Integration tests for controllers
- MockServer tests for callback functionality

## 📊 Processing Details

### Queue Processing Times
- **Email Events**: 5 seconds processing time
- **SMS Events**: 3 seconds processing time  
- **Push Events**: 2 seconds processing time

### Failure Handling
- 10% simulated failure rate for testing purposes
- Failed events include error messages in callbacks
- System continues processing other events even if some fail

### Thread Pool Configuration
- Fixed thread pool with 3 threads
- One dedicated thread per event type (Email, SMS, Push)
- Graceful shutdown with 10-second timeout

## 🔧 Configuration

The system uses default Spring Boot configuration. Key settings:
- **Port**: 8080 (configurable via `server.port`)
- **Thread Pool**: 3 threads (configurable in `EventProcessor`)
- **Processing Delays**: Configurable in `EventProcessor.processQueue()`

## 🚨 Error Handling

- **Input Validation**: Comprehensive validation of request payloads
- **Callback Failures**: Logged but don't stop event processing
- **Graceful Shutdown**: Stops accepting new events and processes existing ones
- **Queue Management**: Non-blocking queue operations with proper error handling
