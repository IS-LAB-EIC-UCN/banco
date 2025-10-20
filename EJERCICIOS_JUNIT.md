# Banco — Ejercicios con ArchUnit (paquetes `cl.ucn.*`)

Este README propone **4 ejercicios** para verificar la arquitectura del proyecto *Banco* usando **ArchUnit** con la siguiente organización por capas/paquetes:

- `cl.ucn.controller` — controladores (REST/Servlet/JSF)
- `cl.ucn.service` — lógica de negocio
- `cl.ucn.persistence` — acceso a datos (JPA/EJB/Repos)
- `cl.ucn.bean` — entidades de dominio/JPA

> **Objetivo:** que el estudiante escriba reglas ArchUnit que describan la arquitectura deseada y **corrija** el código del proyecto hasta que las reglas pasen.

---

## Requisitos

- JDK 17+
- Maven 3.8+
- Dependencias (añadir en `pom.xml`, alcance `test`):
  ```xml
  <dependency>
    <groupId>com.tngtech.archunit</groupId>
    <artifactId>archunit-junit5-api</artifactId>
    <version>1.3.0</version>
    <scope>test</scope>
  </dependency>
  <dependency>
    <groupId>com.tngtech.archunit</groupId>
    <artifactId>archunit-junit5-engine</artifactId>
    <version>1.3.0</version>
    <scope>test</scope>
  </dependency>
  <dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter</artifactId>
    <version>5.10.2</version>
    <scope>test</scope>
  </dependency>
  ```

Ejecutar:
```bash
mvn test
```

---

## Ejercicio 1 — Reglas de capas por paquete

**Descripción:** Define una arquitectura en capas donde:
- `controller` **solo** puede depender de `service`
- `service` **solo** puede depender de `persistence`
- `persistence` **no** puede depender de otras capas
- Ninguna capa puede depender de `controller` (hacia “arriba”)

**Test sugerido (`LayeringRulesTest.java`):**

**Tareas:**
1. Crear el test.
2. Ejecutar `mvn test` y revisar violaciones (p. ej., llamadas directas `controller -> persistence`).
3. **Refactorizar** para cumplir (delegar en `service`).

**Criterio de logro:** el test pasa sin violaciones.

---

## Ejercicio 2 — Prohibir dependencias directas `controller -> persistence`

**Descripción:** Además de la regla de capas general, escribe una regla **explícita** que impida dependencias directas desde `cl.ucn.controller` hacia `cl.ucn.persistence` (imports, invocaciones, referencias a tipos).

**Test sugerido (`NoDirectControllerToPersistenceTest.java`):**

**Clase para forzar fallo (crearla en el proyecto):**
- `cl.ucn.controller.BadAccountController` que **importe** e **invoque** `cl.ucn.persistence.AccountRepository`.

**Criterio de logro:** el test falla con la clase “mala”; tras refactorizar, pasa en verde.

---

## Ejercicio 3 — Convenciones de nombres por capa

**Descripción:** Asegura sufijos coherentes según la capa:
- `..controller..` → termina en `Resource` o `Controller`
- `..service..` → termina en `Service`
- `..persistence..` → termina en `Repository` o `DAO`

**Test sugerido (`NamingConventionsTest.java`):**

**Clase para forzar fallo (crearla en el proyecto):**
- `cl.ucn.persistence.UserServiceHelper` (sufijo `Service` en paquete `persistence`).

**Criterio de logro:** tras corregir nombres/paquetes, los tres checks pasan.

---

## Ejercicio 4 — Evitar que `controller` use entidades `bean` directamente (Investigar que es un DTO)

**Descripción:** Obliga a que la capa `controller` **no** refiera tipos en `cl.ucn.bean` (entidades JPA). Se espera que el intercambio sea vía DTOs (si existen) y servicios.

**Test sugerido (`ControllerMustNotUseBeanDirectlyTest.java`):**

**Clase para forzar fallo (crearla en el proyecto):**
- `cl.ucn.controller.AccountResource` que **importe** `cl.ucn.bean.Account` directamente.

**Criterio de logro:** refactorizar para usar DTOs y delegar conversión en `cl.ucn.service`.

---

## Entregables
1. Código de tests ArchUnit (4 archivos).
2. Clases de ejemplo que **fallen inicialmente** (al menos una por Ej. 2, 3 y 4).
3. Refactorizaciones que corrigen las violaciones.
4. Evidencia de ejecución: salida de `mvn test` antes y después de la corrección.

---

## Consejos

- Usa `@AnalyzeClasses(packages = "cl.ucn")` para analizar el paquete base.
- Evita dependencias “hacia arriba” (nadie depende de `controller`).
- Si aparecen ciclos entre paquetes, revisa responsabilidad y mueve clases.

¡Éxito con los ejercicios! 💪
