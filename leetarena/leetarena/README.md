# LeetArena

A Spring Boot application for LeetCode problem management and practice.

## Description

LeetArena is a web application built with Spring Boot that helps users manage and practice LeetCode problems. It provides a platform for tracking progress, managing problem sets, and enhancing the coding practice experience.

## Features

- **Problem Management**: Track and organize LeetCode problems
- **Progress Tracking**: Monitor your solving progress and statistics
- **Web Interface**: User-friendly web interface for problem management
- **Database Integration**: Persistent storage using JPA and JDBC

## Technology Stack

- **Backend**: Spring Boot 3.5.5
- **Java Version**: 17
- **Database**: JPA with JDBC support
- **Build Tool**: Maven
- **Additional Libraries**: Lombok for code generation

## Prerequisites

- Java 17 or higher
- Maven 3.6 or higher
- Your preferred IDE (IntelliJ IDEA, Eclipse, VS Code)

## Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/yourusername/leetarena.git
cd leetarena
```

### 2. Build the Project

```bash
./mvnw clean install
```

### 3. Run the Application

```bash
./mvnw spring-boot:run
```

The application will start on `http://localhost:8080`

### 4. Development

For development, you can run the application with hot reload:

```bash
./mvnw spring-boot:run -Dspring-boot.run.jvmArguments="-Dspring.devtools.restart.enabled=true"
```

## Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── com/example/leetarena/
│   │       └── LeetarenaApplication.java
│   └── resources/
│       ├── application.properties
│       ├── static/
│       └── templates/
└── test/
    └── java/
        └── com/example/leetarena/
            └── LeetarenaApplicationTests.java
```

## Configuration

The application configuration is in `src/main/resources/application.properties`. You can customize database settings, server port, and other properties as needed.

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Contact

Your Name - [@yourtwitter](https://twitter.com/yourtwitter) - email@example.com

Project Link: [https://github.com/yourusername/leetarena](https://github.com/yourusername/leetarena)
