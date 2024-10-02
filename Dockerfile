# Sử dụng image OpenJDK 17 làm nền tảng
FROM openjdk:17-jdk-slim

# Tạo thư mục chứa ứng dụng trong container
WORKDIR /app

# Sao chép toàn bộ mã nguồn dự án vào container
COPY . .

# Thiết lập quyền thực thi cho tệp mvnw
RUN chmod +x ./mvnw

# Cài đặt các dependencies và build ứng dụng, bỏ qua các bài test để tăng tốc độ build
RUN ./mvnw clean package -DskipTests

# Expose cổng 8080 (dùng trong ứng dụng Spring Boot)
EXPOSE 8080

# Lệnh để chạy ứng dụng với Spring Boot
ENTRYPOINT ["./mvnw", "spring-boot:run"]