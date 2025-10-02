# 1. Base image -> Java 17 JDK
FROM eclipse-temurin:17-jdk-alpine

# 2. Çalışma dizini container içinde
WORKDIR /app

# 3. Build ettiğimiz jar dosyasını kopyala
# (mvn clean package sonrası target klasöründe oluşacak)
COPY target/*.jar app.jar

# 4. Container 8080 portunu aç
EXPOSE 8080

# 5. Spring Boot jar’ını çalıştır
ENTRYPOINT ["java", "-jar", "app.jar"]
