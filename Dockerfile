# Базовый образ с Java 17 (легковесный)
FROM openjdk:17-jdk-slim

# Рабочая папка внутри контейнера
WORKDIR /app

# Копируем jar-файл приложения в контейнер
COPY target/*.jar app.jar

# Открываем порт 8080 для доступа к приложению
EXPOSE 8080

# Команда запуска Spring Boot приложения
ENTRYPOINT ["java", "-jar", "app.jar"]
