# RTT Messenger

A Java-based networking application for measuring Round-Trip Time (RTT) with reliable data transfer.

## Overview

RTT Messenger implements a UDP-based messaging system with HTTP API support, featuring:
- Real-time RTT measurement
- RDT 3.0 protocol implementation
- UDP over HTTP tunneling
- Performance monitoring

## Features

### Core Functionality
- UDP message transmission
- Automatic retransmission
- Sequence number tracking
- RTT statistics collection
- REST API interface

### Performance Features
- Configurable timeout settings
- Error simulation
- Thread pool for server operations
- Connection pooling
- Performance metrics

## Project Structure

```
rtt-messenger/
├── main/
│   └── java/
│       ├── Client.java            # UDP client implementation
│       ├── Server.java            # UDP server implementation
│       ├── Message.java           # Message handling
│       ├── RTTStatistics.java     # Statistics tracking
│       ├── UDPOverHTTP.java       # HTTP tunneling
│       └── MessageController.java # REST API
└── test/
    └── java/
        ├── RTTTest.java           # RTT functionality tests
        └── PerformanceTest.java   # Performance tests
```

## Getting Started

### Prerequisites
- Java JDK 11 or higher
- Network connectivity
- cURL (for testing REST API)

### Running the Application

1. Start the UDP Server:
```bash
java Server
```

2. Start the REST API:
```bash
java MessageController
```

3. Run tests:
```bash
./test.bat
```

## Testing

### Unit Tests
Run RTT and basic functionality tests:
```bash
java -cp .;junit-platform-console-standalone-1.8.2.jar org.junit.platform.console.ConsoleLauncher --scan-classpath
```

### Performance Testing
Test throughput and reliability:
```bash
java PerformanceTest
```

### API Testing
Test REST endpoints:
```bash
curl -X POST -d "Test message" http://localhost:8000/message
curl -X GET http://localhost:8000/message
```

## Configuration

Key settings in source files:
- UDP Port: 8080
- HTTP Port: 8000
- Socket Timeout: 1000ms
- Error Rate: 10%
- Max Retries: 3

## Features in Detail

### RTT Measurement
- Timestamp tracking
- Statistical analysis
- Min/Max/Average calculations

### RDT 3.0 Implementation
- Sequence numbers
- Acknowledgments
- Retransmission
- Error handling

### REST API
- POST /message - Send messages
- GET /message - View statistics

## Performance Optimization
- Server thread pooling
- Client connection pooling
- Optimized buffer sizes
- Resource cleanup


