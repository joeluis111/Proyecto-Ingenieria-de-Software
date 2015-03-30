/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DP;

import DP.exceptions.IllegalOrphanException;
import DP.exceptions.NonexistentEntityException;
import DP.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import MD.Empleado;
import MD.TituloProfesional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author Kenny
 */
public class TituloProfesionalJpaController implements Serializable {

    public TituloProfesionalJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TituloProfesional tituloProfesional) throws RollbackFailureException, Exception {
        if (tituloProfesional.getEmpleadosCollection() == null) {
            tituloProfesional.setEmpleadosCollection(new ArrayList<Empleado>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<Empleado> attachedEmpleadosCollection = new ArrayList<Empleado>();
            for (Empleado empleadosCollectionEmpleadoToAttach : tituloProfesional.getEmpleadosCollection()) {
                empleadosCollectionEmpleadoToAttach = em.getReference(empleadosCollectionEmpleadoToAttach.getClass(), empleadosCollectionEmpleadoToAttach.getPersonalID());
                attachedEmpleadosCollection.add(empleadosCollectionEmpleadoToAttach);
            }
            tituloProfesional.setEmpleadosCollection(attachedEmpleadosCollection);
            em.persist(tituloProfesional);
            for (Empleado empleadosCollectionEmpleado : tituloProfesional.getEmpleadosCollection()) {
                TituloProfesional oldTituloProfesionalIDOfEmpleadosCollectionEmpleado = empleadosCollectionEmpleado.getTituloProfesionalID();
                empleadosCollectionEmpleado.setTituloProfesionalID(tituloProfesional);
                empleadosCollectionEmpleado = em.merge(empleadosCollectionEmpleado);
                if (oldTituloProfesionalIDOfEmpleadosCollectionEmpleado != null) {
                    oldTituloProfesionalIDOfEmpleadosCollectionEmpleado.getEmpleadosCollection().remove(empleadosCollectionEmpleado);
                    oldTituloProfesionalIDOfEmpleadosCollectionEmpleado = em.merge(oldTituloProfesionalIDOfEmpleadosCollectionEmpleado);
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

    public void edit(TituloProfesional tituloProfesional) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TituloProfesional persistentTituloProfesional = em.find(TituloProfesional.class, tituloProfesional.getTituloProfesionalID());
            Collection<Empleado> empleadosCollectionOld = persistentTituloProfesional.getEmpleadosCollection();
            Collection<Empleado> empleadosCollectionNew = tituloProfesional.getEmpleadosCollection();
            List<String> illegalOrphanMessages = null;
            for (Empleado empleadosCollectionOldEmpleado : empleadosCollectionOld) {
                if (!empleadosCollectionNew.contains(empleadosCollectionOldEmpleado)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Empleado " + empleadosCollectionOldEmpleado + " since its tituloProfesionalID field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Empleado> attachedEmpleadosCollectionNew = new ArrayList<Empleado>();
            for (Empleado empleadosCollectionNewEmpleadoToAttach : empleadosCollectionNew) {
                empleadosCollectionNewEmpleadoToAttach = em.getReference(empleadosCollectionNewEmpleadoToAttach.getClass(), empleadosCollectionNewEmpleadoToAttach.getPersonalID());
                attachedEmpleadosCollectionNew.add(empleadosCollectionNewEmpleadoToAttach);
            }
            empleadosCollectionNew = attachedEmpleadosCollectionNew;
            tituloProfesional.setEmpleadosCollection(empleadosCollectionNew);
            tituloProfesional = em.merge(tituloProfesional);
            for (Empleado empleadosCollectionNewEmpleado : empleadosCollectionNew) {
                if (!empleadosCollectionOld.contains(empleadosCollectionNewEmpleado)) {
                    TituloProfesional oldTituloProfesionalIDOfEmpleadosCollectionNewEmpleado = empleadosCollectionNewEmpleado.getTituloProfesionalID();
                    empleadosCollectionNewEmpleado.setTituloProfesionalID(tituloProfesional);
                    empleadosCollectionNewEmpleado = em.merge(empleadosCollectionNewEmpleado);
                    if (oldTituloProfesionalIDOfEmpleadosCollectionNewEmpleado != null && !oldTituloProfesionalIDOfEmpleadosCollectionNewEmpleado.equals(tituloProfesional)) {
                        oldTituloProfesionalIDOfEmpleadosCollectionNewEmpleado.getEmpleadosCollection().remove(empleadosCollectionNewEmpleado);
                        oldTituloProfesionalIDOfEmpleadosCollectionNewEmpleado = em.merge(oldTituloProfesionalIDOfEmpleadosCollectionNewEmpleado);
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
                Integer id = tituloProfesional.getTituloProfesionalID();
                if (findTituloProfesional(id) == null) {
                    throw new NonexistentEntityException("The tituloProfesional with id " + id + " no longer exists.");
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
            TituloProfesional tituloProfesional;
            try {
                tituloProfesional = em.getReference(TituloProfesional.class, id);
                tituloProfesional.getTituloProfesionalID();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tituloProfesional with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Empleado> empleadosCollectionOrphanCheck = tituloProfesional.getEmpleadosCollection();
            for (Empleado empleadosCollectionOrphanCheckEmpleado : empleadosCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TituloProfesional (" + tituloProfesional + ") cannot be destroyed since the Empleado " + empleadosCollectionOrphanCheckEmpleado + " in its empleadosCollection field has a non-nullable tituloProfesionalID field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(tituloProfesional);
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

    public List<TituloProfesional> findTituloProfesionalEntities() {
        return findTituloProfesionalEntities(true, -1, -1);
    }

    public List<TituloProfesional> findTituloProfesionalEntities(int maxResults, int firstResult) {
        return findTituloProfesionalEntities(false, maxResults, firstResult);
    }

    private List<TituloProfesional> findTituloProfesionalEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TituloProfesional.class));
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

    public TituloProfesional findTituloProfesional(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TituloProfesional.class, id);
        } finally {
            em.close();
        }
    }

    public int getTituloProfesionalCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TituloProfesional> rt = cq.from(TituloProfesional.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
