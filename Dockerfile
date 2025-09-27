#
# Etapa 1: Development
#
FROM maven:3.9.6-eclipse-temurin-17-alpine AS dev

WORKDIR /app

# Copio pom
COPY pom.xml .
RUN mvn dependency:go-offline

# Copio código fuente
COPY src ./src

# Setear un entorno de desarrollo
ENV SPRING_PROFILES_ACTIVE=dev

# Usuario no root (buena práctica)
RUN addgroup -S spring && adduser -S spring -G spring
USER spring


#
# Etapa 2: Build
#
FROM maven:3.9.6-eclipse-temurin-17-alpine AS build

WORKDIR /app

# Copio todo desde la etapa dev (ya tiene dependencias descargadas)
COPY --from=dev /app /app

# Compilo y empaqueto (puedes quitar -DskipTests si querés ejecutar tests)
RUN mvn clean package -DskipTests

RUN gzip -9 target/*.jar && mv target/*.jar.gz target/app.jar.gz


#
# Etapa 3: Production
#
FROM eclipse-temurin:17-jre-alpine AS prod

WORKDIR /app

# Copio solo el jar generado en la etapa de build
COPY --from=build /app/target/app.jar.gz app.jar.gz
RUN gunzip app.jar.gz

# === New Relic Agent ===
# Instalar unzip
RUN apk add --no-cache unzip curl

# Descargar la última versión del agente
RUN curl -L https://download.newrelic.com/newrelic/java-agent/newrelic-agent/current/newrelic-java.zip \
    -o /tmp/newrelic-java.zip \
 && unzip /tmp/newrelic-java.zip -d /app/ \
 && rm /tmp/newrelic-java.zip


# Exponer puerto de la app
EXPOSE 8082

# Perfil de prod
ENV SPRING_PROFILES_ACTIVE=prod

# Usuario no root
RUN addgroup -S spring && adduser -S spring -G spring
USER spring

#ENTRYPOINT ["java", "-jar", "app.jar"]
ENTRYPOINT ["java", "-javaagent:/app/newrelic/newrelic.jar", "-jar", "app.jar"]


#TODO: ver si hay una forma de comprimir el jar para que sea lo màs chiquito posible.