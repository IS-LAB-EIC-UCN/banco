package cl.ucn.service;

import cl.ucn.bean.Cliente;
import java.util.List;
import java.util.Optional;

public interface ClienteService {
    Cliente agregarCliente(Cliente cliente);
    Optional<Cliente> obtenerPorId(Long id);
    Optional<Cliente> obtenerPorRut(String rut);
    List<Cliente> listar();
    Cliente actualizar(Long id, Cliente data);
    void eliminar(Long id);
}