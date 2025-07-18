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

## 🎯 OOP Design Patterns & Technical Implementation

### **Solid Java Fundamentals with Proper OOP Design**

#### **1. Inheritance & Polymorphism**
- **Abstract Base Class**: `Event` serves as the base class for all event types
- **Concrete Implementations**: `EmailEvent`, `SmsEvent`, `PushEvent` extend `Event`
- **Polymorphic Processing**: `EventProcessor` handles different event types through common `Event` interface
- **Method Overriding**: Each event type can override base methods if needed

#### **2. Encapsulation**
- **Private Fields**: All model classes use private fields with public getters/setters
- **Data Hiding**: Internal queue management in `EventService` is encapsulated
- **Controlled Access**: Thread-safe operations through synchronized methods and volatile variables

#### **3. Abstraction**
- **Abstract Classes**: `Event` and `EventPayload` provide abstraction layers
- **Interface Segregation**: Clean separation between different event types
- **Implementation Hiding**: Processing logic is abstracted behind service interfaces

#### **4. Composition & Aggregation**
- **Service Composition**: `EventProcessor` composes `EventService` and `CallbackService`
- **Queue Aggregation**: `EventService` aggregates multiple `BlockingQueue` instances
- **Dependency Injection**: Spring's IoC container manages object relationships

### **Robust REST API with Validation and Error Handling**

#### **1. Input Validation**
- **Bean Validation**: Uses `@NotNull`, `@NotBlank`, `@Email` annotations
- **Custom Validation**: Event-specific validation in `EventController.validateAndConvertEvent()`
- **Error Responses**: Proper HTTP status codes (400 Bad Request) for validation failures

#### **2. Error Handling**
- **Exception Handling**: `@ExceptionHandler` for global exception management
- **Graceful Degradation**: System continues processing even if callbacks fail
- **Detailed Error Messages**: Specific validation error messages for debugging

#### **3. RESTful Design**
- **Resource-Oriented**: `/api/events` follows REST conventions
- **HTTP Methods**: Proper use of POST for event creation
- **Content Negotiation**: JSON request/response handling

### **Efficient Concurrency with Separate Queues and Threads**

#### **1. Thread Pool Management**
- **Fixed Thread Pool**: `ExecutorService.newFixedThreadPool(3)` for controlled concurrency
- **Dedicated Threads**: One thread per event type ensures isolation
- **Thread Safety**: `BlockingQueue` provides thread-safe operations

#### **2. Queue Implementation**
- **BlockingQueue**: `LinkedBlockingQueue` for thread-safe FIFO operations
- **Non-blocking Operations**: `offer()` method prevents blocking on queue full
- **Fair Scheduling**: Events processed in order of arrival

#### **3. Synchronization Mechanisms**
- **Volatile Variables**: `running` and `acceptingEvents` flags for thread communication
- **Atomic Operations**: Queue operations are inherently thread-safe
- **Proper Shutdown**: `@PreDestroy` ensures clean thread termination

### **Reliable Async Processing with FIFO Ordering**

#### **1. Asynchronous Architecture**
- **Non-blocking API**: Event submission returns immediately
- **Background Processing**: Events processed in separate threads
- **Callback Pattern**: Asynchronous status updates via HTTP callbacks

#### **2. FIFO Implementation**
- **Queue-based Processing**: `BlockingQueue.take()` ensures FIFO order
- **Event Ordering**: Events processed in exact order of submission
- **Type-specific Queues**: Each event type maintains its own FIFO order

#### **3. Processing Guarantees**
- **At-least-once Processing**: Events are guaranteed to be processed
- **Failure Isolation**: One event failure doesn't affect others
- **Status Tracking**: Every event gets a callback with final status

### **Comprehensive Testing Covering All Major Scenarios**

#### **1. Unit Testing Framework**
- **JUnit 5**: Modern testing framework with `@Test` annotations
- **Spring Boot Test**: Integration testing with `@SpringBootTest`
- **MockMvc**: Web layer testing for REST endpoints

#### **2. Test Coverage**
- **Service Layer**: `EventServiceTest` covers business logic
- **Controller Layer**: `EventControllerTest` covers API endpoints
- **Integration Testing**: `EventProcessorTest` covers end-to-end processing

#### **3. Mocking & Stubbing**
- **MockServer**: HTTP callback testing with mock server
- **Reflection**: Testing private fields for state verification
- **Test Isolation**: Each test runs in isolated context

### **Production-Ready Dockerization for Easy Deployment**

#### **1. Multi-stage Build**
- **Base Image**: `openjdk:17-jdk-slim` for optimized container size
- **JAR Packaging**: Spring Boot fat JAR with all dependencies
- **Port Exposure**: Proper port mapping for external access

#### **2. Container Orchestration**
- **Docker Compose**: Multi-service orchestration ready
- **Health Checks**: Container health monitoring
- **Resource Management**: Memory and CPU limits configurable

#### **3. Deployment Ready**
- **Environment Variables**: Configurable via environment
- **Logging**: Structured logging for production monitoring
- **Graceful Shutdown**: Proper signal handling for container termination

### **Design Patterns Used**

#### **1. Factory Pattern**
- **Event Creation**: `EventController` acts as factory for creating specific event types
- **Payload Conversion**: Converts generic payloads to specific event types

#### **2. Observer Pattern**
- **Callback System**: Event processing notifies observers via HTTP callbacks
- **Status Updates**: Real-time status updates to external systems

#### **3. Strategy Pattern**
- **Event Processing**: Different processing strategies for each event type
- **Queue Management**: Different queues for different event types

#### **4. Template Method Pattern**
- **Event Processing**: Common processing template with type-specific delays
- **Callback Handling**: Common callback structure with type-specific data

### **Technical Stack Details**

#### **1. Spring Framework Features**
- **Dependency Injection**: `@Autowired` for loose coupling
- **AOP**: `@PostConstruct` and `@PreDestroy` for lifecycle management
- **Validation**: Bean Validation API integration
- **Web MVC**: RESTful web services with Spring Web

#### **2. Java Concurrency**
- **ExecutorService**: Thread pool management
- **BlockingQueue**: Thread-safe queue operations
- **Volatile Variables**: Thread communication
- **InterruptedException**: Proper thread interruption handling

#### **3. JSON Processing**
- **Jackson**: Polymorphic deserialization with `@JsonTypeInfo`
- **Type Information**: Runtime type resolution for payloads
- **Custom Serialization**: Structured JSON output

#### **4. HTTP Client**
- **HttpClient**: Modern HTTP client for callbacks
- **Async Operations**: Non-blocking HTTP requests
- **Error Handling**: Robust error handling for network failures

## �� API Endpoints

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
