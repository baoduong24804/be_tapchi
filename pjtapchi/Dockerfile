# Sử dụng OpenJDK 17 làm base image
FROM openjdk:17-jdk-slim

# Tạo thư mục để chứa ứng dụng
WORKDIR /app

# Sao chép tệp jar từ máy cục bộ vào container
COPY target/api_anime404-0.0.1-SNAPSHOT.jar api_anime404-0.0.1-SNAPSHOT.jar

# Chạy ứng dụng Spring Boot
ENTRYPOINT ["java", "-jar", "api_anime404-0.0.1-SNAPSHOT.jar"]
