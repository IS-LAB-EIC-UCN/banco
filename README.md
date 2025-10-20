# Proyecto Banco

Este proyecto es una aplicación Java EE/Jakarta EE desarrollada con **Maven** y desplegable en el servidor de aplicaciones **WildFly**.  
Implementa una capa de persistencia basada en **JPA (Hibernate)** y sigue una arquitectura modular con servicios, entidades y controladores.

---

## 📦 Estructura del Proyecto

```
banco/
 ├── src/
 │   ├── main/
 │   │   ├── java/           # Código fuente Java
 │   │   ├── resources/      # Archivos de configuración (persistence.xml, log4j, etc.)
 │   │   └── webapp/         # Archivos web (JSP, JSF, HTML, WEB-INF)
 │   └── test/               # Pruebas unitarias
 ├── pom.xml                 # Archivo de configuración Maven
 └── README.md
```

---

## ⚙️ Configuración del entorno

### Requisitos previos

- **JDK** 17 o superior  
- **Apache Maven** 3.8+  
- **WildFly** 26 o superior  
- **PostgreSQL** (o tu base de datos preferida)
- Driver JDBC instalado en WildFly

---

## 🧩 Configuración de la base de datos

1. Crear una base de datos (por ejemplo `banco`).
2. Configurar el *datasource* en WildFly:

   ```bash
   /subsystem=datasources/jdbc-driver=postgresql:add(
     driver-name=postgresql,
     driver-module-name=org.postgresql,
     driver-class-name=org.postgresql.Driver
   )

   /subsystem=datasources/data-source=BancoDS:add(
     jndi-name=java:/jdbc/BancoDS,
     driver-name=postgresql,
     connection-url=jdbc:postgresql://localhost:5432/banco,
     user-name=usuario,
     password=secreto,
     jta=true, use-ccm=true
   )
   ```

3. Verificar el JNDI en el log de WildFly:  
   ```
   Bound data source [java:/jdbc/BancoDS]
   ```

---

## 🧠 Configuración de `persistence.xml`

Ubicado en `src/main/resources/META-INF/persistence.xml`:

```xml
<persistence xmlns="https://jakarta.ee/xml/ns/persistence"
             xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
             xsi:schemaLocation="https://jakarta.ee/xml/ns/persistence
                                 https://jakarta.ee/xml/ns/persistence/persistence_3_1.xsd"
             version="3.1">
  <persistence-unit name="bancoappPersistenceUnit" transaction-type="JTA">
    <jta-data-source>java:/PostGreDS</jta-data-source>
    <properties>
      <property name="hibernate.dialect" value="org.hibernate.dialect.PostgreSQLDialect"/>
      <property name="hibernate.hbm2ddl.auto" value="update"/>
      <property name="hibernate.show_sql" value="true"/>
    </properties>
  </persistence-unit>
</persistence>
```

---

## 🚀 Compilación y despliegue con Maven

### 1️⃣ Limpiar y compilar sin desplegar

```bash
mvn clean install
```

Esto limpia el proyecto y genera el archivo WAR en `target/`, sin realizar el despliegue automático.

### 2️⃣ Desplegar manualmente en WildFly

Copia el archivo WAR generado a:
```
$WILDFLY_HOME/standalone/deployments/
```

### 3️⃣ Desplegar con Maven automáticamente

Puedes usar el plugin `wildfly-maven-plugin` en el `pom.xml`:

```xml
<build>
  <plugins>
    <plugin>
      <groupId>org.wildfly.plugins</groupId>
      <artifactId>wildfly-maven-plugin</artifactId>
      <version>4.0.0.Final</version>
      <configuration>
        <hostname>localhost</hostname>
        <port>9990</port>
        <username>admin</username>
        <password>admin</password>
      </configuration>
    </plugin>
  </plugins>
</build>
```

Entonces ejecuta:

```bash
mvn wildfly:deploy
```

---

## 🧪 Ejecución y pruebas

- Inicia WildFly:
  ```bash
  $WILDFLY_HOME/bin/standalone.sh
  ```
- Abre el navegador:
  ```
  http://localhost:8080/banco
  ```
- Para ejecutar las pruebas unitarias:
  ```bash
  mvn test
  ```

---

## 👩‍💻 Autores

- **Daniel San Martín** — Universidad Católica del Norte  

---

## 📝 Licencia

Proyecto académico bajo licencia MIT.  
Puedes usarlo, modificarlo y redistribuirlo citando la fuente original.
