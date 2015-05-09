/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DP;

import DP.exceptions.NonexistentEntityException;
import DP.exceptions.RollbackFailureException;
import MD.HistoriaInventaria;
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
public class HistoriaInventariaJpaController implements Serializable {

    public HistoriaInventariaJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(HistoriaInventaria historiaInventaria) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            em.persist(historiaInventaria);
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

    public void edit(HistoriaInventaria historiaInventaria) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            historiaInventaria = em.merge(historiaInventaria);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = historiaInventaria.getHistid();
                if (findHistoriaInventaria(id) == null) {
                    throw new NonexistentEntityException("The historiaInventaria with id " + id + " no longer exists.");
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
            HistoriaInventaria historiaInventaria;
            try {
                historiaInventaria = em.getReference(HistoriaInventaria.class, id);
                historiaInventaria.getHistid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The historiaInventaria with id " + id + " no longer exists.", enfe);
            }
            em.remove(historiaInventaria);
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

    public List<HistoriaInventaria> findHistoriaInventariaEntities() {
        return findHistoriaInventariaEntities(true, -1, -1);
    }

    public List<HistoriaInventaria> findHistoriaInventariaEntities(int maxResults, int firstResult) {
        return findHistoriaInventariaEntities(false, maxResults, firstResult);
    }

    private List<HistoriaInventaria> findHistoriaInventariaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(HistoriaInventaria.class));
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

    public HistoriaInventaria findHistoriaInventaria(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(HistoriaInventaria.class, id);
        } finally {
            em.close();
        }
    }

    public int getHistoriaInventariaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<HistoriaInventaria> rt = cq.from(HistoriaInventaria.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
