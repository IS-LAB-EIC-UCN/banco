package cl.ucn.service;

import cl.ucn.persistence.JpaUtil;
import cl.ucn.bean.Cliente;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import java.util.List;
import java.util.Optional;

public class ClienteServiceImpl implements ClienteService {

    private EntityManager em() {
        return JpaUtil.emf().createEntityManager();
    }

    @Override
    public Cliente agregarCliente(Cliente cliente) {
        var em = em();
        try {
            em.getTransaction().begin();
            em.persist(cliente);
            em.getTransaction().commit();
            return cliente;
        } finally {
            em.close();
        }
    }

    @Override
    public Optional<Cliente> obtenerPorId(Long id) {
        var em = em();
        try {
            return Optional.ofNullable(em.find(Cliente.class, id));
        } finally {
            em.close();
        }
    }

    @Override
    public Optional<Cliente> obtenerPorRut(String rut) {
        var em = em();
        try {
            var q = em.createQuery("select c from Cliente c where c.rut = :rut", Cliente.class);
            q.setParameter("rut", rut);
            return Optional.of(q.getSingleResult());
        } catch (NoResultException nre) {
            return Optional.empty();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Cliente> listar() {
        var em = em();
        try {
            return em.createQuery("select c from Cliente c order by c.id", Cliente.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Cliente actualizar(Long id, Cliente data) {
        var em = em();
        try {
            em.getTransaction().begin();
            var c = em.find(Cliente.class, id);
            if (c == null) {
                em.getTransaction().rollback();
                throw new IllegalArgumentException("No existe cliente id=" + id);
            }
            c.setRut(data.getRut());
            c.setNombre(data.getNombre());
            c.setSueldo(data.getSueldo());
            em.merge(c);
            em.getTransaction().commit();
            return c;
        } finally {
            em.close();
        }
    }

    @Override
    public void eliminar(Long id) {
        var em = em();
        try {
            em.getTransaction().begin();
            var c = em.find(Cliente.class, id);
            if (c != null) em.remove(c);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}
