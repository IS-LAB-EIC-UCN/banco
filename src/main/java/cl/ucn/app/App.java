package cl.ucn.app;

import cl.ucn.persistence.JpaUtil;
import cl.ucn.service.ClienteService;
import cl.ucn.service.ClienteServiceImpl;
import cl.ucn.web.Rutas;
import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;

public class App {
    public static void main(String[] args) {

        // Inicializa JPA (dispara schema-generation del PU "bancoapp")
        JpaUtil.emf();
        Runtime.getRuntime().addShutdownHook(new Thread(JpaUtil::close));

        ClienteService service = new ClienteServiceImpl();

        Javalin app = Javalin.create(cfg -> {
            cfg.staticFiles.add(s -> {
                s.directory  = "public";
                s.location   = Location.CLASSPATH;
                s.hostedPath = "/";
            });
        }).start(7070);

        Rutas.registrar(app, service);
    }
}
