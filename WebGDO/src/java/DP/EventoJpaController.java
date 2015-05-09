/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DP;

import DP.exceptions.NonexistentEntityException;
import DP.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import MD.Calendario;
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
public class EventoJpaController implements Serializable {

    public EventoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Evento evento) throws RollbackFailureException, Exception {
        if (evento.getEventoCollection() == null) {
            evento.setEventoCollection(new ArrayList<Evento>());
        }
        if (evento.getEventoCollection1() == null) {
            evento.setEventoCollection1(new ArrayList<Evento>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Calendario calid = evento.getCalid();
            if (calid != null) {
                calid = em.getReference(calid.getClass(), calid.getCalid());
                evento.setCalid(calid);
            }
            Collection<Evento> attachedEventoCollection = new ArrayList<Evento>();
            for (Evento eventoCollectionEventoToAttach : evento.getEventoCollection()) {
                eventoCollectionEventoToAttach = em.getReference(eventoCollectionEventoToAttach.getClass(), eventoCollectionEventoToAttach.getEvid());
                attachedEventoCollection.add(eventoCollectionEventoToAttach);
            }
            evento.setEventoCollection(attachedEventoCollection);
            Collection<Evento> attachedEventoCollection1 = new ArrayList<Evento>();
            for (Evento eventoCollection1EventoToAttach : evento.getEventoCollection1()) {
                eventoCollection1EventoToAttach = em.getReference(eventoCollection1EventoToAttach.getClass(), eventoCollection1EventoToAttach.getEvid());
                attachedEventoCollection1.add(eventoCollection1EventoToAttach);
            }
            evento.setEventoCollection1(attachedEventoCollection1);
            em.persist(evento);
            if (calid != null) {
                calid.getEventoCollection().add(evento);
                calid = em.merge(calid);
            }
            for (Evento eventoCollectionEvento : evento.getEventoCollection()) {
                eventoCollectionEvento.getEventoCollection().add(evento);
                eventoCollectionEvento = em.merge(eventoCollectionEvento);
            }
            for (Evento eventoCollection1Evento : evento.getEventoCollection1()) {
                eventoCollection1Evento.getEventoCollection().add(evento);
                eventoCollection1Evento = em.merge(eventoCollection1Evento);
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

    public void edit(Evento evento) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Evento persistentEvento = em.find(Evento.class, evento.getEvid());
            Calendario calidOld = persistentEvento.getCalid();
            Calendario calidNew = evento.getCalid();
            Collection<Evento> eventoCollectionOld = persistentEvento.getEventoCollection();
            Collection<Evento> eventoCollectionNew = evento.getEventoCollection();
            Collection<Evento> eventoCollection1Old = persistentEvento.getEventoCollection1();
            Collection<Evento> eventoCollection1New = evento.getEventoCollection1();
            if (calidNew != null) {
                calidNew = em.getReference(calidNew.getClass(), calidNew.getCalid());
                evento.setCalid(calidNew);
            }
            Collection<Evento> attachedEventoCollectionNew = new ArrayList<Evento>();
            for (Evento eventoCollectionNewEventoToAttach : eventoCollectionNew) {
                eventoCollectionNewEventoToAttach = em.getReference(eventoCollectionNewEventoToAttach.getClass(), eventoCollectionNewEventoToAttach.getEvid());
                attachedEventoCollectionNew.add(eventoCollectionNewEventoToAttach);
            }
            eventoCollectionNew = attachedEventoCollectionNew;
            evento.setEventoCollection(eventoCollectionNew);
            Collection<Evento> attachedEventoCollection1New = new ArrayList<Evento>();
            for (Evento eventoCollection1NewEventoToAttach : eventoCollection1New) {
                eventoCollection1NewEventoToAttach = em.getReference(eventoCollection1NewEventoToAttach.getClass(), eventoCollection1NewEventoToAttach.getEvid());
                attachedEventoCollection1New.add(eventoCollection1NewEventoToAttach);
            }
            eventoCollection1New = attachedEventoCollection1New;
            evento.setEventoCollection1(eventoCollection1New);
            evento = em.merge(evento);
            if (calidOld != null && !calidOld.equals(calidNew)) {
                calidOld.getEventoCollection().remove(evento);
                calidOld = em.merge(calidOld);
            }
            if (calidNew != null && !calidNew.equals(calidOld)) {
                calidNew.getEventoCollection().add(evento);
                calidNew = em.merge(calidNew);
            }
            for (Evento eventoCollectionOldEvento : eventoCollectionOld) {
                if (!eventoCollectionNew.contains(eventoCollectionOldEvento)) {
                    eventoCollectionOldEvento.getEventoCollection().remove(evento);
                    eventoCollectionOldEvento = em.merge(eventoCollectionOldEvento);
                }
            }
            for (Evento eventoCollectionNewEvento : eventoCollectionNew) {
                if (!eventoCollectionOld.contains(eventoCollectionNewEvento)) {
                    eventoCollectionNewEvento.getEventoCollection().add(evento);
                    eventoCollectionNewEvento = em.merge(eventoCollectionNewEvento);
                }
            }
            for (Evento eventoCollection1OldEvento : eventoCollection1Old) {
                if (!eventoCollection1New.contains(eventoCollection1OldEvento)) {
                    eventoCollection1OldEvento.getEventoCollection().remove(evento);
                    eventoCollection1OldEvento = em.merge(eventoCollection1OldEvento);
                }
            }
            for (Evento eventoCollection1NewEvento : eventoCollection1New) {
                if (!eventoCollection1Old.contains(eventoCollection1NewEvento)) {
                    eventoCollection1NewEvento.getEventoCollection().add(evento);
                    eventoCollection1NewEvento = em.merge(eventoCollection1NewEvento);
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
                Integer id = evento.getEvid();
                if (findEvento(id) == null) {
                    throw new NonexistentEntityException("The evento with id " + id + " no longer exists.");
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
            Evento evento;
            try {
                evento = em.getReference(Evento.class, id);
                evento.getEvid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The evento with id " + id + " no longer exists.", enfe);
            }
            Calendario calid = evento.getCalid();
            if (calid != null) {
                calid.getEventoCollection().remove(evento);
                calid = em.merge(calid);
            }
            Collection<Evento> eventoCollection = evento.getEventoCollection();
            for (Evento eventoCollectionEvento : eventoCollection) {
                eventoCollectionEvento.getEventoCollection().remove(evento);
                eventoCollectionEvento = em.merge(eventoCollectionEvento);
            }
            Collection<Evento> eventoCollection1 = evento.getEventoCollection1();
            for (Evento eventoCollection1Evento : eventoCollection1) {
                eventoCollection1Evento.getEventoCollection().remove(evento);
                eventoCollection1Evento = em.merge(eventoCollection1Evento);
            }
            em.remove(evento);
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

    public List<Evento> findEventoEntities() {
        return findEventoEntities(true, -1, -1);
    }

    public List<Evento> findEventoEntities(int maxResults, int firstResult) {
        return findEventoEntities(false, maxResults, firstResult);
    }

    private List<Evento> findEventoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Evento.class));
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

    public Evento findEvento(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Evento.class, id);
        } finally {
            em.close();
        }
    }

    public int getEventoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Evento> rt = cq.from(Evento.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
