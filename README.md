# UFC-FantasyIA

> **Fight camp en construcción** · Fantasy league de UFC con backend en Spring Boot y PostgreSQL.

UFC-FantasyIA es una plataforma en desarrollo donde los usuarios podrán construir equipos de peleadores respetando un presupuesto tipo *salary cap* y seguir su rendimiento. En esta etapa inicial, el proyecto cuenta con la base del backend, la conexión a PostgreSQL y un endpoint de salud para verificar que la API está disponible.

<div align="center">

**SPRING BOOT** · **JAVA 21** · **POSTGRESQL** · **MAVEN**

</div>

## Estado actual

| Área | Estado |
| --- | --- |
| Aplicación Spring Boot | En desarrollo |
| PostgreSQL | Configurado con Compose |
| Persistencia | Spring Data JPA |
| Migraciones | Flyway habilitado |
| Endpoint de salud | Disponible |
| Funcionalidad fantasy | En construcción |

## Stack

- Java 21
- Spring Boot 4.1.1
- Spring Web MVC
- Spring Data JPA
- PostgreSQL 16
- Flyway
- Maven
- Docker Compose o Podman Compose

## Requisitos

Antes de iniciar el proyecto necesitas tener instalado:

- Java 21 JDK
- Maven 3.9 o superior
- Docker Compose o Podman con `podman-compose`
- `direnv` si quieres activar Java 21 únicamente dentro de este proyecto

Comprueba las versiones:

```bash
java -version
mvn -version
docker compose version
```

## Configuración de Java 21 por proyecto

El proyecto utiliza Java 21. Si no quieres cambiar la versión global de Java, puedes usar `direnv` para activarla solo dentro de esta carpeta.

Crea un archivo `.envrc` en la raíz del proyecto:

```bash
export JAVA_HOME=/usr/lib/jvm/java-21-temurin-jdk
export PATH="$JAVA_HOME/bin:$PATH"
```

Autoriza la configuración:

```bash
direnv allow
```

Al entrar al proyecto, verifica que la versión activa sea Java 21:

```bash
java -version
```

## PostgreSQL

El archivo `docker-compose.yml` levanta un contenedor PostgreSQL con los siguientes datos de desarrollo:

| Parámetro | Valor |
| --- | --- |
| Base de datos | `ufc_fantasy` |
| Usuario | Definido en `.env` |
| Contraseña | Definida en `.env` |
| Puerto | `5432` |
| Contenedor | `ufc-postgres` |

Primero crea el archivo local de variables a partir de la plantilla:

```bash
cp .env.example .env
```

Edita `.env` y define una contraseña local segura. Este archivo está ignorado por Git y no debe subirse al repositorio.

Inicia la base de datos desde la raíz del proyecto:

```bash
docker compose up -d
```

Comprueba el estado del contenedor:

```bash
docker compose ps
```

Para detener PostgreSQL:

```bash
docker compose down
```

Los datos se conservan en el volumen `postgres_data`. Para eliminar también ese volumen:

```bash
docker compose down -v
```

> En Fedora, `docker compose` puede ejecutarse mediante `podman-compose`. El mensaje que indica el uso de un proveedor externo es informativo.

## Ejecutar la aplicación

Con PostgreSQL en ejecución, inicia Spring Boot con Maven:

```bash
mvn spring-boot:run
```

Para compilar el proyecto:

```bash
mvn clean compile
```

La aplicación utiliza esta conexión local:

```text
jdbc:postgresql://localhost:5432/ufc_fantasy
```

## Endpoint de salud

Cuando la aplicación esté ejecutándose, consulta:

```bash
curl http://localhost:8080/api/v1/health
```

Respuesta esperada:

```json
{
  "status": "healthy",
  "service": "API"
}
```

## Estructura actual

```text
UFC-FantasyIA/
├── src/
│   ├── main/
│   │   ├── java/com/ufc/UFC_FantasyIA/
│   │   │   ├── controller/
│   │   │   │   └── HealthController.java
│   │   │   └── UfcFantasyIaApplication.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
├── docker-compose.yml
├── pom.xml
└── README.md
```

## Desarrollo

El proyecto se encuentra en construcción. Por ahora, el objetivo es consolidar la base del backend y del entorno local antes de incorporar el resto de la experiencia fantasy.
