# 📚 Book App with Spring Boot, Security, Spring Data JPA, Maven, H2DB, and Docker

A secure book management RESTful application using Spring Boot, H2 in-memory database, Spring Security, and containerized with Docker.

---

## 🧰 Development Environment

- **OS**: Windows 10  
- **IDE**: Visual Studio Code  
  - Extensions:
    - Extension Pack for Java (Microsoft)
    - Project Manager for Java
    - Debugger for Java
    - Maven for Java
    - Remote - Containers
    - Test Runner for Java
    - Spring Initializr Java Support
    - Spring Boot Dashboard
    - Spring Boot Extension Pack (Pivotal)
    - Spring Boot Tools (Pivotal)
    - Dependency Analytics (Red Hat)
    - Language Support for Java(TM) (Red Hat)
    - YAML (Red Hat)
    - Lombok Annotations Support for VS Code
- **Java**: JDK 17  
- **Build Tool**: Maven 3.6.3  
- **API Client**: Postman v9.1.5  
- **Containerization**: Docker (v20.10.11) + Docker Desktop (v4.3.0)

---

## ⚙️ Prerequisites & Setup

### 1. Install JDK 17  
👉 [https://www.oracle.com/java/technologies/downloads/](https://www.oracle.com/java/technologies/downloads/)

Set `JAVA_HOME` and update your `PATH`:  
**Windows:**
```bash
JAVA_HOME=C:\Path\To\jdk-17
set PATH=%JAVA_HOME%\bin;%PATH%
```
**Linux:**
```bash
export JAVA_HOME=/path/to/jdk-17
export PATH=$JAVA_HOME/bin:$PATH
```

Verify:
```bash
java -version
```

### 2. Install Maven  
👉 [https://maven.apache.org/download.cgi](https://maven.apache.org/download.cgi)

Set `M2_HOME` and update your `PATH`:  
**Windows:**
```bash
M2_HOME=C:\Path\To\apache-maven-3.6.3
set PATH=%M2_HOME%\bin;%PATH%
```
**Linux:**
```bash
export M2_HOME=/path/to/apache-maven-3.6.3
export PATH=$M2_HOME/bin:$PATH
```

Verify:
```bash
mvn --version
```

### 3. Install Project Lombok  
👉 [https://projectlombok.org/setup/overview](https://projectlombok.org/setup/overview)

---

## 🚀 Build & Run

### Build the Application
```bash
mvn clean install package -U
```

### Run Locally via Command Line
```bash
mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=*:8000"
```
**Or**
```bash
java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:8000 -jar target/book.jar
```

Configure **Spring Boot Remote DevTools**:  
- URL: `http://localhost:8080/`  
- Secret: `test`

### Run with Docker
```bash
docker-compose build
docker-compose up -d
```

Check logs:
```bash
docker logs -f book
```

Remote debugging:
- JVM Port: `8000`

---

## 📚 API Documentation

- Swagger UI: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)  
- Home Page: [http://localhost:8080/](http://localhost:8080/)

---

## 🧪 Testing with Postman

Import the API definition into Postman from the `postman` folder located at the project root.

You can use it to test all available endpoints interactively.

