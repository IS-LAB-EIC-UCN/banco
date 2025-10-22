# 🧱 Proyecto Banco — Ejercicios con ArchUnit

Este proyecto aplica **verificación de arquitectura** usando [**ArchUnit**](https://www.archunit.org/).  
Los siguientes ejercicios ayudan a garantizar una **separación clara por capas** y buenas prácticas en proyectos Java.

---

## 🗂 Estructura esperada del proyecto

Tu proyecto debe seguir esta organización de paquetes:

```
cl.ucn.app          → punto de entrada (main, configuración Javalin)
cl.ucn.web          → endpoints HTTP, vistas o rutas
cl.ucn.service      → lógica de negocio
cl.ucn.persistence  → acceso a datos (JPA, repositorios, EJB)
cl.ucn.bean         → entidades JPA (modelo de dominio)
resources/
  ├── META-INF/persistence.xml
  ├── public/ (HTMLs servidos por Javalin)
  └── data.sql
```

---

Para ejecutar los tests:
```bash
mvn test
```

---

## 🧩 Ejercicio 1 — Reglas de capas

**Objetivo:**  
Definir una arquitectura limpia entre los paquetes:

| Capa | Puede depender de | No puede depender de |
|------|-------------------|----------------------|
| `app` | `web` | ninguna capa depende de `app` |
| `web` | `service` | `persistence`, `bean` |
| `service` | `persistence`, `bean` | `web`, `app` |
| `persistence` | `bean` | `web`, `service` |
| `bean` | (solo Java/Jakarta) | todas las demás |

**Archivo:** `src/test/java/cl/ucn/arch/LayeringRulesTest.java`

✅ **Pasa cuando:**  
Cada capa respeta sus dependencias permitidas.  
❌ **Falla si:**  
`web` llama directamente a `persistence`, o `bean` importa algo de `service`, etc.

---

## 🚫 Ejercicio 2 — Evitar `web → persistence`

**Objetivo:**  
Ninguna clase en `cl.ucn.web..` puede usar/importar tipos en `cl.ucn.persistence..`.

**Archivo:** `src/test/java/cl/ucn/arch/NoDirectWebToPersistenceTest.java`

**Clase para forzar fallo:**
```java
package cl.ucn.web;
import cl.ucn.persistence.StorageCliente;
public class BadController { StorageCliente s; }
```

✅ **Pasa cuando:** `web` solo accede a `service`.  
❌ **Falla si:** `web` usa clases DAO o repositorios directamente.

---

## 🧱 Ejercicio 3 — Convenciones de nombres por capa

**Objetivo:**  
Asegurar sufijos coherentes:

| Capa | Sufijo permitido |
|------|------------------|
| `web` | `Controller`, `Resource`, `Routes`, `Rutas` |
| `service` | `Service` |
| `persistence` | `Repository`, `DAO`, `Storage` |
| `bean` | `Entity` o sin sufijo |

**Archivo:** `src/test/java/cl/ucn/arch/NamingConventionsTest.java`

**Clase para forzar fallo:**
```java
package cl.ucn.persistence;
public class UserServiceHelper {} // nombre incorrecto
```

✅ **Pasa cuando:** todos los nombres siguen las convenciones.  
❌ **Falla si:** hay clases mal nombradas (como `UserServiceHelper`).

---

## 🔒 Ejercicio 4 — Evitar `web → bean` (usar DTOs)

**Objetivo:**  
Nada en `cl.ucn.web..` debe importar entidades JPA (`cl.ucn.bean..`).  
La capa web debe usar **DTOs** y comunicarse solo con `service`.

**Archivo:** `src/test/java/cl/ucn/arch/WebMustNotUseBeanDirectlyTest.java`

**Clase para forzar fallo:**
```java
package cl.ucn.web;
import cl.ucn.bean.Cliente;
public class AccountResource { Cliente c; }
```

✅ **Pasa cuando:** `web` usa DTOs.  
❌ **Falla si:** el controlador importa entidades directamente.

---

## 🧾 Ejercicio 5 — Comprobación de presencia mínima

**Archivo:** `src/test/java/cl/ucn/arch/PresenceTest.java`

**Objetivo:** verificar que el proyecto tenga clases básicas en cada capa (`Service`, `Repository`, `Controller`, etc.).  
Este test **falla hasta que los estudiantes creen** las clases mínimas necesarias.

---

## ✅ Entregables

1. 4 archivos de test ArchUnit (`LayeringRulesTest`, `NoDirectWebToPersistenceTest`, `NamingConventionsTest`, `WebMustNotUseBeanDirectlyTest`)
2. Clases de ejemplo que **fallen inicialmente**
3. `PresenceTest.java` para validar estructura mínima
4. Evidencia: salida de `mvn test` antes y después de las correcciones

---
