/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DP;

import DP.exceptions.NonexistentEntityException;
import DP.exceptions.RollbackFailureException;
import MD.HistoriaCliente;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
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

    public void create(HistoriaCliente historiaCliente) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            em.persist(historiaCliente);
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

    public void edit(HistoriaCliente historiaCliente) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            historiaCliente = em.merge(historiaCliente);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = historiaCliente.getHcid();
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

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            HistoriaCliente historiaCliente;
            try {
                historiaCliente = em.getReference(HistoriaCliente.class, id);
                historiaCliente.getHcid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The historiaCliente with id " + id + " no longer exists.", enfe);
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

    public HistoriaCliente findHistoriaCliente(Integer id) {
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
