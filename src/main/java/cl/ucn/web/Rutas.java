package cl.ucn.web;

import cl.ucn.bean.Cliente;
import cl.ucn.service.ClienteService;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.javalin.Javalin;
import io.javalin.http.HttpStatus;

import java.util.Map;

public final class Rutas {

    private Rutas() {}

    public static void registrar(Javalin app, ClienteService service) {

        // Errores básicos
        app.exception(Exception.class, (e, ctx) -> {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .json(Map.of("error", "Error interno", "message", e.getMessage()));
        });

        app.error(HttpStatus.NOT_FOUND.getCode(), ctx ->
                ctx.json(Map.of("error", "Recurso no encontrado", "path", ctx.path())));

        // Vistas
        app.get("/", ctx -> ctx.redirect("/index.html"));
        app.get("/ingresar-cliente", ctx -> ctx.redirect("/ingresarCliente.html"));
        app.get("/clientes", ctx -> ctx.redirect("/clientes.html"));

        // --- API v1 ---

        // Listar
        app.get("/api/v1/clientes", ctx -> ctx.json(service.listar()));

        // Crear
        app.post("/api/v1/clientes", ctx -> {
            var dto = ctx.bodyAsClass(ClienteDTO.class);
            var creado = service.agregarCliente(dto.toEntity());
            ctx.status(HttpStatus.CREATED).json(creado);
        });

        // Obtener por id
        app.get("/api/v1/clientes/{rut}", ctx -> {
            long id = Long.parseLong(ctx.pathParam("rut"));
            var opt = service.obtenerPorId(id);
            if (opt.isEmpty()) {
                ctx.status(HttpStatus.NOT_FOUND).json(Map.of("error", "No existe cliente id=" + id));
                return;
            }
            ctx.json(opt.get());
        });

        // Actualizar
        app.put("/api/v1/clientes/{rut}", ctx -> {
            long id = Long.parseLong(ctx.pathParam("rut"));
            var dto = ctx.bodyAsClass(ClienteDTO.class);
            var actualizado = service.actualizar(id, dto.toEntity());
            ctx.json(actualizado);
        });

        // Eliminar
        app.delete("/api/v1/clientes/{rut}", ctx -> {
            long id = Long.parseLong(ctx.pathParam("rut"));
            service.eliminar(id);
            ctx.status(HttpStatus.NO_CONTENT);
        });
    }

    // DTO simple para requests
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ClienteDTO {
        public String rut;
        public String nombre;
        public int sueldo;

        public Cliente toEntity() {

            rut = rut.replaceAll("[.-]", "").toUpperCase();
            int rutNumero = Integer.parseInt(rut);
            var c = new Cliente();
            c.setRut(rutNumero);
            c.setNombre(nombre);
            c.setSueldo(sueldo);
            return c;
        }
    }
}
