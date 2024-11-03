# RTT Messenger Project

## Project Overview
RTT Messenger is a networking application designed to measure and analyze Round-Trip Time (RTT) in client-server communications. The project implements reliable data transfer mechanisms using RDT 3.0 principles and provides both UDP and HTTP-based communication options.

## Key Features
- Real-time RTT measurement and analysis
- Reliable data transfer using RDT 3.0 protocols
- Support for both UDP and HTTP communication
- Message acknowledgment and error handling
- Unique message ID tracking and timestamp management
- Performance monitoring and optimization

## Technical Architecture
### Communication Protocols
- Primary: UDP for initial implementation
- Secondary: HTTP REST API
- Future: TCP support planned

### Core Components
1. **Server**
   - UDP packet handling
   - Message acknowledgment system
   - RTT calculation logic
   - REST API endpoints

2. **Client**
   - Message sending capabilities
   - Acknowledgment handling
   - RTT measurement integration
   - API interaction layer

3. **Message Handling**
   - Unique message ID generation
   - Timestamp tracking
   - Serialization/deserialization
   - Error detection and correction

## Implementation Details
### RDT 3.0 Features
- Acknowledgments (ACK) and negative acknowledgments (NAK)
- Automatic retransmission of lost messages
- Corruption detection and handling
- Sequence number tracking

### Performance Monitoring
- JMX integration for JVM metrics
- OS-level performance tracking
- RTT statistics collection and analysis

## Development Phases
1. Basic Infrastructure Setup
2. Core Communication Implementation
3. RTT Measurement System
4. RDT 3.0 Integration
5. REST API Development
6. Protocol Expansion (UDP over HTTP)
7. Testing and Optimization
8. Documentation and Maintenance

## Future Enhancements
- TCP protocol integration
- Graphical user interface
- Enhanced error handling
- Performance optimization
- Extended monitoring capabilities

## Technical Requirements
- Java Development Kit (JDK)
- Network connectivity
- Support for UDP and HTTP protocols
- Sufficient system resources for performance monitoring

## Documentation
- API documentation
- Setup instructions
- Development guidelines
- Performance monitoring guide
