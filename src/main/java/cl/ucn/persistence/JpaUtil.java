// src/main/java/cl/ucn/infra/JpaUtil.java
package cl.ucn.persistence;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public final class JpaUtil {

    private static final String PU_NAME = "bancoapp";

    private static final EntityManagerFactory EMF = build();

    private JpaUtil() {}

    private static EntityManagerFactory build() {
        // Crea la EMF una sola vez para toda la app
        return Persistence.createEntityManagerFactory(PU_NAME);
    }

    /** Devuelve la EMF singleton (thread-safe). */
    public static EntityManagerFactory emf() {
        return EMF;
    }

    /** Crea un EntityManager nuevo (NO es thread-safe; úsalo por request y ciérralo). */
    public static EntityManager em() {
        return EMF.createEntityManager();
    }

    /** Cierra la fábrica (llamar al apagar la app). */
    public static void close() {
        if (EMF.isOpen()) {
            EMF.close();
        }
    }
}
