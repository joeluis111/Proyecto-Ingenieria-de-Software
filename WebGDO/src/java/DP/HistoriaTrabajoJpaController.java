/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DP;

import DP.exceptions.NonexistentEntityException;
import DP.exceptions.RollbackFailureException;
import MD.HistoriaTrabajo;
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
public class HistoriaTrabajoJpaController implements Serializable {

    public HistoriaTrabajoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(HistoriaTrabajo historiaTrabajo) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            em.persist(historiaTrabajo);
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

    public void edit(HistoriaTrabajo historiaTrabajo) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            historiaTrabajo = em.merge(historiaTrabajo);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = historiaTrabajo.getHtid();
                if (findHistoriaTrabajo(id) == null) {
                    throw new NonexistentEntityException("The historiaTrabajo with id " + id + " no longer exists.");
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
            HistoriaTrabajo historiaTrabajo;
            try {
                historiaTrabajo = em.getReference(HistoriaTrabajo.class, id);
                historiaTrabajo.getHtid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The historiaTrabajo with id " + id + " no longer exists.", enfe);
            }
            em.remove(historiaTrabajo);
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

    public List<HistoriaTrabajo> findHistoriaTrabajoEntities() {
        return findHistoriaTrabajoEntities(true, -1, -1);
    }

    public List<HistoriaTrabajo> findHistoriaTrabajoEntities(int maxResults, int firstResult) {
        return findHistoriaTrabajoEntities(false, maxResults, firstResult);
    }

    private List<HistoriaTrabajo> findHistoriaTrabajoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(HistoriaTrabajo.class));
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

    public HistoriaTrabajo findHistoriaTrabajo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(HistoriaTrabajo.class, id);
        } finally {
            em.close();
        }
    }

    public int getHistoriaTrabajoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<HistoriaTrabajo> rt = cq.from(HistoriaTrabajo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
