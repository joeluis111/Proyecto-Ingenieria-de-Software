/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MD;

import MD.exceptions.NonexistentEntityException;
import MD.exceptions.PreexistingEntityException;
import MD.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import MD.Empleado;
import MD.HistoriaTrabajo;
import MD.HistoriaTrabajoPK;
import MD.Proyecto;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
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

    public void create(HistoriaTrabajo historiaTrabajo) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (historiaTrabajo.getHistoriaTrabajoPK() == null) {
            historiaTrabajo.setHistoriaTrabajoPK(new HistoriaTrabajoPK());
        }
        historiaTrabajo.getHistoriaTrabajoPK().setEmpid(historiaTrabajo.getEmpleado().getEmpid());
        historiaTrabajo.getHistoriaTrabajoPK().setProid(historiaTrabajo.getProyecto().getProid());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Empleado empleado = historiaTrabajo.getEmpleado();
            if (empleado != null) {
                empleado = em.getReference(empleado.getClass(), empleado.getEmpid());
                historiaTrabajo.setEmpleado(empleado);
            }
            Proyecto proyecto = historiaTrabajo.getProyecto();
            if (proyecto != null) {
                proyecto = em.getReference(proyecto.getClass(), proyecto.getProid());
                historiaTrabajo.setProyecto(proyecto);
            }
            em.persist(historiaTrabajo);
            if (empleado != null) {
                empleado.getHistoriaTrabajoCollection().add(historiaTrabajo);
                empleado = em.merge(empleado);
            }
            if (proyecto != null) {
                proyecto.getHistoriaTrabajoCollection().add(historiaTrabajo);
                proyecto = em.merge(proyecto);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findHistoriaTrabajo(historiaTrabajo.getHistoriaTrabajoPK()) != null) {
                throw new PreexistingEntityException("HistoriaTrabajo " + historiaTrabajo + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(HistoriaTrabajo historiaTrabajo) throws NonexistentEntityException, RollbackFailureException, Exception {
        historiaTrabajo.getHistoriaTrabajoPK().setEmpid(historiaTrabajo.getEmpleado().getEmpid());
        historiaTrabajo.getHistoriaTrabajoPK().setProid(historiaTrabajo.getProyecto().getProid());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            HistoriaTrabajo persistentHistoriaTrabajo = em.find(HistoriaTrabajo.class, historiaTrabajo.getHistoriaTrabajoPK());
            Empleado empleadoOld = persistentHistoriaTrabajo.getEmpleado();
            Empleado empleadoNew = historiaTrabajo.getEmpleado();
            Proyecto proyectoOld = persistentHistoriaTrabajo.getProyecto();
            Proyecto proyectoNew = historiaTrabajo.getProyecto();
            if (empleadoNew != null) {
                empleadoNew = em.getReference(empleadoNew.getClass(), empleadoNew.getEmpid());
                historiaTrabajo.setEmpleado(empleadoNew);
            }
            if (proyectoNew != null) {
                proyectoNew = em.getReference(proyectoNew.getClass(), proyectoNew.getProid());
                historiaTrabajo.setProyecto(proyectoNew);
            }
            historiaTrabajo = em.merge(historiaTrabajo);
            if (empleadoOld != null && !empleadoOld.equals(empleadoNew)) {
                empleadoOld.getHistoriaTrabajoCollection().remove(historiaTrabajo);
                empleadoOld = em.merge(empleadoOld);
            }
            if (empleadoNew != null && !empleadoNew.equals(empleadoOld)) {
                empleadoNew.getHistoriaTrabajoCollection().add(historiaTrabajo);
                empleadoNew = em.merge(empleadoNew);
            }
            if (proyectoOld != null && !proyectoOld.equals(proyectoNew)) {
                proyectoOld.getHistoriaTrabajoCollection().remove(historiaTrabajo);
                proyectoOld = em.merge(proyectoOld);
            }
            if (proyectoNew != null && !proyectoNew.equals(proyectoOld)) {
                proyectoNew.getHistoriaTrabajoCollection().add(historiaTrabajo);
                proyectoNew = em.merge(proyectoNew);
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
                HistoriaTrabajoPK id = historiaTrabajo.getHistoriaTrabajoPK();
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

    public void destroy(HistoriaTrabajoPK id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            HistoriaTrabajo historiaTrabajo;
            try {
                historiaTrabajo = em.getReference(HistoriaTrabajo.class, id);
                historiaTrabajo.getHistoriaTrabajoPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The historiaTrabajo with id " + id + " no longer exists.", enfe);
            }
            Empleado empleado = historiaTrabajo.getEmpleado();
            if (empleado != null) {
                empleado.getHistoriaTrabajoCollection().remove(historiaTrabajo);
                empleado = em.merge(empleado);
            }
            Proyecto proyecto = historiaTrabajo.getProyecto();
            if (proyecto != null) {
                proyecto.getHistoriaTrabajoCollection().remove(historiaTrabajo);
                proyecto = em.merge(proyecto);
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

    public HistoriaTrabajo findHistoriaTrabajo(HistoriaTrabajoPK id) {
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
