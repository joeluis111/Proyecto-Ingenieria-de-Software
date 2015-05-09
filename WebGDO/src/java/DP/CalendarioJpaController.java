/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DP;

import DP.exceptions.IllegalOrphanException;
import DP.exceptions.NonexistentEntityException;
import DP.exceptions.RollbackFailureException;
import MD.Calendario;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import MD.Proyecto;
import MD.Evento;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author Kenny
 */
public class CalendarioJpaController implements Serializable {

    public CalendarioJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Calendario calendario) throws RollbackFailureException, Exception {
        if (calendario.getEventoCollection() == null) {
            calendario.setEventoCollection(new ArrayList<Evento>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Proyecto proid = calendario.getProid();
            if (proid != null) {
                proid = em.getReference(proid.getClass(), proid.getProid());
                calendario.setProid(proid);
            }
            Collection<Evento> attachedEventoCollection = new ArrayList<Evento>();
            for (Evento eventoCollectionEventoToAttach : calendario.getEventoCollection()) {
                eventoCollectionEventoToAttach = em.getReference(eventoCollectionEventoToAttach.getClass(), eventoCollectionEventoToAttach.getEvid());
                attachedEventoCollection.add(eventoCollectionEventoToAttach);
            }
            calendario.setEventoCollection(attachedEventoCollection);
            em.persist(calendario);
            if (proid != null) {
                proid.getCalendarioCollection().add(calendario);
                proid = em.merge(proid);
            }
            for (Evento eventoCollectionEvento : calendario.getEventoCollection()) {
                Calendario oldCalidOfEventoCollectionEvento = eventoCollectionEvento.getCalid();
                eventoCollectionEvento.setCalid(calendario);
                eventoCollectionEvento = em.merge(eventoCollectionEvento);
                if (oldCalidOfEventoCollectionEvento != null) {
                    oldCalidOfEventoCollectionEvento.getEventoCollection().remove(eventoCollectionEvento);
                    oldCalidOfEventoCollectionEvento = em.merge(oldCalidOfEventoCollectionEvento);
                }
            }
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

    public void edit(Calendario calendario) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Calendario persistentCalendario = em.find(Calendario.class, calendario.getCalid());
            Proyecto proidOld = persistentCalendario.getProid();
            Proyecto proidNew = calendario.getProid();
            Collection<Evento> eventoCollectionOld = persistentCalendario.getEventoCollection();
            Collection<Evento> eventoCollectionNew = calendario.getEventoCollection();
            List<String> illegalOrphanMessages = null;
            for (Evento eventoCollectionOldEvento : eventoCollectionOld) {
                if (!eventoCollectionNew.contains(eventoCollectionOldEvento)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Evento " + eventoCollectionOldEvento + " since its calid field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (proidNew != null) {
                proidNew = em.getReference(proidNew.getClass(), proidNew.getProid());
                calendario.setProid(proidNew);
            }
            Collection<Evento> attachedEventoCollectionNew = new ArrayList<Evento>();
            for (Evento eventoCollectionNewEventoToAttach : eventoCollectionNew) {
                eventoCollectionNewEventoToAttach = em.getReference(eventoCollectionNewEventoToAttach.getClass(), eventoCollectionNewEventoToAttach.getEvid());
                attachedEventoCollectionNew.add(eventoCollectionNewEventoToAttach);
            }
            eventoCollectionNew = attachedEventoCollectionNew;
            calendario.setEventoCollection(eventoCollectionNew);
            calendario = em.merge(calendario);
            if (proidOld != null && !proidOld.equals(proidNew)) {
                proidOld.getCalendarioCollection().remove(calendario);
                proidOld = em.merge(proidOld);
            }
            if (proidNew != null && !proidNew.equals(proidOld)) {
                proidNew.getCalendarioCollection().add(calendario);
                proidNew = em.merge(proidNew);
            }
            for (Evento eventoCollectionNewEvento : eventoCollectionNew) {
                if (!eventoCollectionOld.contains(eventoCollectionNewEvento)) {
                    Calendario oldCalidOfEventoCollectionNewEvento = eventoCollectionNewEvento.getCalid();
                    eventoCollectionNewEvento.setCalid(calendario);
                    eventoCollectionNewEvento = em.merge(eventoCollectionNewEvento);
                    if (oldCalidOfEventoCollectionNewEvento != null && !oldCalidOfEventoCollectionNewEvento.equals(calendario)) {
                        oldCalidOfEventoCollectionNewEvento.getEventoCollection().remove(eventoCollectionNewEvento);
                        oldCalidOfEventoCollectionNewEvento = em.merge(oldCalidOfEventoCollectionNewEvento);
                    }
                }
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
                Integer id = calendario.getCalid();
                if (findCalendario(id) == null) {
                    throw new NonexistentEntityException("The calendario with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Calendario calendario;
            try {
                calendario = em.getReference(Calendario.class, id);
                calendario.getCalid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The calendario with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Evento> eventoCollectionOrphanCheck = calendario.getEventoCollection();
            for (Evento eventoCollectionOrphanCheckEvento : eventoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Calendario (" + calendario + ") cannot be destroyed since the Evento " + eventoCollectionOrphanCheckEvento + " in its eventoCollection field has a non-nullable calid field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Proyecto proid = calendario.getProid();
            if (proid != null) {
                proid.getCalendarioCollection().remove(calendario);
                proid = em.merge(proid);
            }
            em.remove(calendario);
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

    public List<Calendario> findCalendarioEntities() {
        return findCalendarioEntities(true, -1, -1);
    }

    public List<Calendario> findCalendarioEntities(int maxResults, int firstResult) {
        return findCalendarioEntities(false, maxResults, firstResult);
    }

    private List<Calendario> findCalendarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Calendario.class));
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

    public Calendario findCalendario(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Calendario.class, id);
        } finally {
            em.close();
        }
    }

    public int getCalendarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Calendario> rt = cq.from(Calendario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
