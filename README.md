# 🧱 Proyecto Banco — Arquitectura y Validación con ArchUnit

Este proyecto es una aplicación Java moderna basada en **Javalin** y **JPA (Hibernate)** que sigue una arquitectura **en capas**.  
Además, incorpora ejercicios con **ArchUnit** para que el estudiante valide las dependencias y convenciones de la arquitectura.

---

## 📦 Estructura del Proyecto

```
banco/
 ├── src/
 │   ├── main/
 │   │   ├── java/
 │   │   │   ├── cl/ucn/app/          # Punto de entrada (App.java)
 │   │   │   ├── cl/ucn/web/          # Rutas HTTP y controladores
 │   │   │   ├── cl/ucn/service/      # Lógica de negocio
 │   │   │   ├── cl/ucn/persistence/  # Acceso a datos (JPA)
 │   │   │   └── cl/ucn/bean/         # Entidades JPA
 │   │   └── resources/
 │   │       ├── META-INF/persistence.xml
 │   │       ├── public/              # Archivos HTML (UI Javalin)
 │   │       └── data.sql
 │   └── test/
 │       └── java/cl/ucn/arch/        # Tests de arquitectura (ArchUnit)
 ├── pom.xml
 └── README.md
```

---

## ⚙️ Requisitos

- **JDK** 17 o superior
- **Apache Maven** 3.8+
- **SQLite** (usado por defecto en `persistence.xml`)

---

## 🚀 Ejecución del Proyecto

### Compilación y ejecución con Maven

```bash
mvn -DskipTests=true clean compile exec:java
```

El proyecto levanta un servidor **Javalin** en `http://localhost:7070/`  
y sirve los HTML desde `src/main/resources/public/`.

---

## 🧩 Ejercicios de Arquitectura (ArchUnit)

El proyecto incluye una carpeta `src/test/java/cl/ucn/arch/` con ejercicios que validan las dependencias entre capas.

### 1️⃣ Ejercicio: Reglas de capas
Archivo: `LayeringRulesTest.java`  
Define las dependencias permitidas entre paquetes (`app`, `web`, `service`, `persistence`, `bean`).

### 2️⃣ Ejercicio: Evitar dependencias prohibidas
Archivo: `NoDirectWebToPersistenceTest.java`  
Verifica que `web` no use clases de `persistence` directamente.

### 3️⃣ Ejercicio: Convenciones de nombres
Archivo: `NamingConventionsTest.java`  
Asegura que las clases terminen en `Controller`, `Service`, `Repository`, etc.

### 4️⃣ Ejercicio: Prohibir uso directo de entidades
Archivo: `WebMustNotUseBeanDirectlyTest.java`  
Impide que `web` importe entidades JPA (`cl.ucn.bean.*`), promoviendo el uso de DTOs.

### 5️⃣ Ejercicio: Estructura mínima requerida
Archivo: `PresenceTest.java`  
Falla hasta que el estudiante cree las clases mínimas requeridas (Controller, Service, Repository, etc.).

---

## 📘 Cómo ejecutar los tests

```bash
mvn test
```

Los resultados mostrarán violaciones de arquitectura hasta que se cumplan las reglas definidas.

---

## 👩‍💻 Autor

**Daniel San Martín** — Universidad Católica del Norte

---

## 📝 Licencia

Proyecto académico bajo licencia MIT.  
Puedes usarlo, modificarlo y redistribuirlo citando la fuente original.
