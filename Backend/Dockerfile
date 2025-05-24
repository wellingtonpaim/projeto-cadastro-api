# Etapa 1: Build com Maven 3.9.9 e Java 21
FROM maven:3.9.9-eclipse-temurin-21 AS builder

WORKDIR /app

# Copia o conteúdo do projeto
COPY . .

# Compila e gera o JAR, pulando os testes
RUN mvn clean package -DskipTests

# Etapa 2: Imagem final, somente com JAR
FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

# Copia o JAR final da etapa anterior
COPY --from=builder /app/target/Api_Cadastro-1.0.jar app.jar

# Expõe a porta padrão do Spring Boot
EXPOSE 8080

# Comando para rodar o JAR
ENTRYPOINT ["java", "-jar", "app.jar"]
