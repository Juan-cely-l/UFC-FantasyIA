# UFC-FantasyIA

> **Fight camp en construcción** · Fantasy league de UFC con backend en Spring Boot y PostgreSQL.

UFC-FantasyIA es una plataforma en desarrollo donde los usuarios podrán construir equipos de peleadores respetando un presupuesto tipo *salary cap* y seguir su rendimiento. En esta etapa inicial, el proyecto cuenta con la base del backend, la conexión a PostgreSQL, el modelo de dominio, las migraciones de base de datos y un endpoint de salud para verificar que la API está disponible.

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
| Modelo de dominio | Entidades JPA implementadas |
| Repositorios | Repositorios Spring Data creados |
| Pruebas de persistencia | Test de `FighterRepository` en verde |
| Endpoint de salud | Disponible |
| Funcionalidad fantasy | En construcción |

## Stack

- Java 21
- Spring Boot 4.1.1
- Spring Web MVC
- Spring Data JPA
- Lombok
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

Al iniciar la aplicación, Flyway valida y ejecuta automáticamente las migraciones ubicadas en `src/main/resources/db/migration`.

La migración inicial `V1__init_schema.sql` crea las tablas:

- `events`
- `fighters`
- `weight_divisions`
- `fighter_weight_divisions`
- `fights`

Para comprobar el estado de las migraciones directamente en PostgreSQL:

```bash
docker compose exec postgres psql -U ufc_user -d ufc_fantasy -c "SELECT installed_rank, version, description, success FROM flyway_schema_history ORDER BY installed_rank;"
```

La salida debe mostrar la migración `1 - init schema` con `success` igual a `t`.

> Si la migración inicial ya se ejecutó antes de un cambio estructural durante el desarrollo, puedes recrear la base local con `docker compose down -v` y después `docker compose up -d`. Esto elimina únicamente los datos del entorno local.

## Modelo de dominio

El modelo utiliza nombres en inglés para mantener consistencia entre el código Java y el esquema SQL:

| Entidad | Responsabilidad |
| --- | --- |
| `Event` | Evento que contiene una o varias peleas |
| `Fight` | Pelea entre dos peleadores dentro de un evento y una división |
| `Fighter` | Peleador con sus datos físicos y país |
| `WeightDivision` | División de peso disponible |
| `FighterWeightDivision` | Relación entre un peleador y una división de peso |

`FighterWeightDivision` es una entidad intermedia explícita porque la relación utiliza una clave primaria compuesta por `fighter_id` y `weight_division_id`. Su identificador se representa con `@EmbeddedId` y las relaciones se enlazan mediante `@MapsId`.

## Repositorios

Se implementaron repositorios Spring Data JPA para las entidades principales y la relación intermedia:

- `EventRepository`
- `FightRepository`
- `FighterRepository`
- `WeightDivisionRepository`
- `FighterWeightDivisionRepository`

Todos utilizan `UUID` como tipo de identificador. El repositorio de `FighterWeightDivision` utiliza `FighterWeightDivisionId` como clave primaria.

## Pruebas

Para ejecutar el test de persistencia de `Fighter`, PostgreSQL debe estar iniciado y las variables de entorno deben estar disponibles:

```bash
docker compose up -d
mvn -Dtest=FighterRepositoryTest test
```

El test guarda un `Fighter`, confirma que se genera su `UUID`, lo recupera mediante `FighterRepository` y valida sus campos principales.

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
│   │   │   ├── entity/
│   │   │   │   ├── Event.java
│   │   │   │   ├── Fight.java
│   │   │   │   ├── Fighter.java
│   │   │   │   ├── FighterWeightDivision.java
│   │   │   │   ├── FighterWeightDivisionId.java
│   │   │   │   └── WeightDivision.java
│   │   │   ├── repository/
│   │   │   │   ├── EventRepository.java
│   │   │   │   ├── FightRepository.java
│   │   │   │   ├── FighterRepository.java
│   │   │   │   ├── FighterWeightDivisionRepository.java
│   │   │   │   └── WeightDivisionRepository.java
│   │   │   └── UfcFantasyIaApplication.java
│   │   └── resources/
│   │       ├── db/migration/
│   │       │   └── V1__init_schema.sql
│   │       └── application.properties
│   └── test/java/com/ufc/UFC_FantasyIA/
│       ├── UfcFantasyIaApplicationTests.java
│       └── repository/FighterRepositoryTest.java
├── docker-compose.yml
├── .env.example
├── pom.xml
└── README.md
```

## Desarrollo

El proyecto se encuentra en construcción. Por ahora, el objetivo es consolidar la base del backend y del entorno local antes de incorporar el resto de la experiencia fantasy.
![Peleas Boxing Event Data Model-2026-10-14-160441.png](Assets/Peleas%20Boxing%20Event%20Data%20Model-2026-10-14-160441.png)
