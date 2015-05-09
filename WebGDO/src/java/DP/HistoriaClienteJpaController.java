/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DP;

import DP.exceptions.NonexistentEntityException;
import DP.exceptions.PreexistingEntityException;
import DP.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import MD.Proyecto;
import MD.Cliente;
import MD.Cliente;
import MD.HistoriaCliente;
import MD.HistoriaCliente;
import MD.HistoriaClientePK;
import MD.HistoriaClientePK;
import MD.Proyecto;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author Kenny
 */
public class HistoriaClienteJpaController implements Serializable {

    public HistoriaClienteJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(HistoriaCliente historiaCliente) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (historiaCliente.getHistoriaClientePK() == null) {
            historiaCliente.setHistoriaClientePK(new HistoriaClientePK());
        }
        historiaCliente.getHistoriaClientePK().setProid(historiaCliente.getProyecto().getProid());
        historiaCliente.getHistoriaClientePK().setCliid(historiaCliente.getCliente().getCliid());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Proyecto proyecto = historiaCliente.getProyecto();
            if (proyecto != null) {
                proyecto = em.getReference(proyecto.getClass(), proyecto.getProid());
                historiaCliente.setProyecto(proyecto);
            }
            Cliente cliente = historiaCliente.getCliente();
            if (cliente != null) {
                cliente = em.getReference(cliente.getClass(), cliente.getCliid());
                historiaCliente.setCliente(cliente);
            }
            em.persist(historiaCliente);
            if (proyecto != null) {
                proyecto.getHistoriaClienteCollection().add(historiaCliente);
                proyecto = em.merge(proyecto);
            }
            if (cliente != null) {
                cliente.getHistoriaClienteCollection().add(historiaCliente);
                cliente = em.merge(cliente);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findHistoriaCliente(historiaCliente.getHistoriaClientePK()) != null) {
                throw new PreexistingEntityException("HistoriaCliente " + historiaCliente + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(HistoriaCliente historiaCliente) throws NonexistentEntityException, RollbackFailureException, Exception {
        historiaCliente.getHistoriaClientePK().setProid(historiaCliente.getProyecto().getProid());
        historiaCliente.getHistoriaClientePK().setCliid(historiaCliente.getCliente().getCliid());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            HistoriaCliente persistentHistoriaCliente = em.find(HistoriaCliente.class, historiaCliente.getHistoriaClientePK());
            Proyecto proyectoOld = persistentHistoriaCliente.getProyecto();
            Proyecto proyectoNew = historiaCliente.getProyecto();
            Cliente clienteOld = persistentHistoriaCliente.getCliente();
            Cliente clienteNew = historiaCliente.getCliente();
            if (proyectoNew != null) {
                proyectoNew = em.getReference(proyectoNew.getClass(), proyectoNew.getProid());
                historiaCliente.setProyecto(proyectoNew);
            }
            if (clienteNew != null) {
                clienteNew = em.getReference(clienteNew.getClass(), clienteNew.getCliid());
                historiaCliente.setCliente(clienteNew);
            }
            historiaCliente = em.merge(historiaCliente);
            if (proyectoOld != null && !proyectoOld.equals(proyectoNew)) {
                proyectoOld.getHistoriaClienteCollection().remove(historiaCliente);
                proyectoOld = em.merge(proyectoOld);
            }
            if (proyectoNew != null && !proyectoNew.equals(proyectoOld)) {
                proyectoNew.getHistoriaClienteCollection().add(historiaCliente);
                proyectoNew = em.merge(proyectoNew);
            }
            if (clienteOld != null && !clienteOld.equals(clienteNew)) {
                clienteOld.getHistoriaClienteCollection().remove(historiaCliente);
                clienteOld = em.merge(clienteOld);
            }
            if (clienteNew != null && !clienteNew.equals(clienteOld)) {
                clienteNew.getHistoriaClienteCollection().add(historiaCliente);
                clienteNew = em.merge(clienteNew);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                HistoriaClientePK id = historiaCliente.getHistoriaClientePK();
                if (findHistoriaCliente(id) == null) {
                    throw new NonexistentEntityException("The historiaCliente with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(HistoriaClientePK id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            HistoriaCliente historiaCliente;
            try {
                historiaCliente = em.getReference(HistoriaCliente.class, id);
                historiaCliente.getHistoriaClientePK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The historiaCliente with id " + id + " no longer exists.", enfe);
            }
            Proyecto proyecto = historiaCliente.getProyecto();
            if (proyecto != null) {
                proyecto.getHistoriaClienteCollection().remove(historiaCliente);
                proyecto = em.merge(proyecto);
            }
            Cliente cliente = historiaCliente.getCliente();
            if (cliente != null) {
                cliente.getHistoriaClienteCollection().remove(historiaCliente);
                cliente = em.merge(cliente);
            }
            em.remove(historiaCliente);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<HistoriaCliente> findHistoriaClienteEntities() {
        return findHistoriaClienteEntities(true, -1, -1);
    }

    public List<HistoriaCliente> findHistoriaClienteEntities(int maxResults, int firstResult) {
        return findHistoriaClienteEntities(false, maxResults, firstResult);
    }

    private List<HistoriaCliente> findHistoriaClienteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(HistoriaCliente.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public HistoriaCliente findHistoriaCliente(HistoriaClientePK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(HistoriaCliente.class, id);
        } finally {
            em.close();
        }
    }

    public int getHistoriaClienteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<HistoriaCliente> rt = cq.from(HistoriaCliente.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
