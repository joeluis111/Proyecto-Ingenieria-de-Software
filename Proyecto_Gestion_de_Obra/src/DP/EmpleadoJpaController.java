/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DP;

import DP.exceptions.NonexistentEntityException;
import DP.exceptions.RollbackFailureException;
import MD.Empleado;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import MD.TipoTrabajador;
import MD.TituloProfesional;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author Kenny
 */
public class EmpleadoJpaController implements Serializable {

    public EmpleadoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Empleado empleado) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TipoTrabajador tipoTrabajadorID = empleado.getTipoTrabajadorID();
            if (tipoTrabajadorID != null) {
                tipoTrabajadorID = em.getReference(tipoTrabajadorID.getClass(), tipoTrabajadorID.getTipoTrabajadorID());
                empleado.setTipoTrabajadorID(tipoTrabajadorID);
            }
            TituloProfesional tituloProfesionalID = empleado.getTituloProfesionalID();
            if (tituloProfesionalID != null) {
                tituloProfesionalID = em.getReference(tituloProfesionalID.getClass(), tituloProfesionalID.getTituloProfesionalID());
                empleado.setTituloProfesionalID(tituloProfesionalID);
            }
            em.persist(empleado);
            if (tipoTrabajadorID != null) {
                tipoTrabajadorID.getEmpleadosCollection().add(empleado);
                tipoTrabajadorID = em.merge(tipoTrabajadorID);
            }
            if (tituloProfesionalID != null) {
                tituloProfesionalID.getEmpleadosCollection().add(empleado);
                tituloProfesionalID = em.merge(tituloProfesionalID);
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

    public void edit(Empleado empleado) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Empleado persistentEmpleado = em.find(Empleado.class, empleado.getPersonalID());
            TipoTrabajador tipoTrabajadorIDOld = persistentEmpleado.getTipoTrabajadorID();
            TipoTrabajador tipoTrabajadorIDNew = empleado.getTipoTrabajadorID();
            TituloProfesional tituloProfesionalIDOld = persistentEmpleado.getTituloProfesionalID();
            TituloProfesional tituloProfesionalIDNew = empleado.getTituloProfesionalID();
            if (tipoTrabajadorIDNew != null) {
                tipoTrabajadorIDNew = em.getReference(tipoTrabajadorIDNew.getClass(), tipoTrabajadorIDNew.getTipoTrabajadorID());
                empleado.setTipoTrabajadorID(tipoTrabajadorIDNew);
            }
            if (tituloProfesionalIDNew != null) {
                tituloProfesionalIDNew = em.getReference(tituloProfesionalIDNew.getClass(), tituloProfesionalIDNew.getTituloProfesionalID());
                empleado.setTituloProfesionalID(tituloProfesionalIDNew);
            }
            empleado = em.merge(empleado);
            if (tipoTrabajadorIDOld != null && !tipoTrabajadorIDOld.equals(tipoTrabajadorIDNew)) {
                tipoTrabajadorIDOld.getEmpleadosCollection().remove(empleado);
                tipoTrabajadorIDOld = em.merge(tipoTrabajadorIDOld);
            }
            if (tipoTrabajadorIDNew != null && !tipoTrabajadorIDNew.equals(tipoTrabajadorIDOld)) {
                tipoTrabajadorIDNew.getEmpleadosCollection().add(empleado);
                tipoTrabajadorIDNew = em.merge(tipoTrabajadorIDNew);
            }
            if (tituloProfesionalIDOld != null && !tituloProfesionalIDOld.equals(tituloProfesionalIDNew)) {
                tituloProfesionalIDOld.getEmpleadosCollection().remove(empleado);
                tituloProfesionalIDOld = em.merge(tituloProfesionalIDOld);
            }
            if (tituloProfesionalIDNew != null && !tituloProfesionalIDNew.equals(tituloProfesionalIDOld)) {
                tituloProfesionalIDNew.getEmpleadosCollection().add(empleado);
                tituloProfesionalIDNew = em.merge(tituloProfesionalIDNew);
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
                Integer id = empleado.getPersonalID();
                if (findEmpleado(id) == null) {
                    throw new NonexistentEntityException("The empleado with id " + id + " no longer exists.");
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
            Empleado empleado;
            try {
                empleado = em.getReference(Empleado.class, id);
                empleado.getPersonalID();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The empleado with id " + id + " no longer exists.", enfe);
            }
            TipoTrabajador tipoTrabajadorID = empleado.getTipoTrabajadorID();
            if (tipoTrabajadorID != null) {
                tipoTrabajadorID.getEmpleadosCollection().remove(empleado);
                tipoTrabajadorID = em.merge(tipoTrabajadorID);
            }
            TituloProfesional tituloProfesionalID = empleado.getTituloProfesionalID();
            if (tituloProfesionalID != null) {
                tituloProfesionalID.getEmpleadosCollection().remove(empleado);
                tituloProfesionalID = em.merge(tituloProfesionalID);
            }
            em.remove(empleado);
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

    public List<Empleado> findEmpleadoEntities() {
        return findEmpleadoEntities(true, -1, -1);
    }

    public List<Empleado> findEmpleadoEntities(int maxResults, int firstResult) {
        return findEmpleadoEntities(false, maxResults, firstResult);
    }

    private List<Empleado> findEmpleadoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Empleado.class));
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

    public Empleado findEmpleado(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Empleado.class, id);
        } finally {
            em.close();
        }
    }

    public int getEmpleadoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Empleado> rt = cq.from(Empleado.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
