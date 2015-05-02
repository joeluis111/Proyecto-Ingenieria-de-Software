/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MD;

import MD.exceptions.IllegalOrphanException;
import MD.exceptions.NonexistentEntityException;
import MD.exceptions.RollbackFailureException;
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
        if (tituloProfesional.getEmpleadoCollection() == null) {
            tituloProfesional.setEmpleadoCollection(new ArrayList<Empleado>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<Empleado> attachedEmpleadoCollection = new ArrayList<Empleado>();
            for (Empleado empleadoCollectionEmpleadoToAttach : tituloProfesional.getEmpleadoCollection()) {
                empleadoCollectionEmpleadoToAttach = em.getReference(empleadoCollectionEmpleadoToAttach.getClass(), empleadoCollectionEmpleadoToAttach.getEmpid());
                attachedEmpleadoCollection.add(empleadoCollectionEmpleadoToAttach);
            }
            tituloProfesional.setEmpleadoCollection(attachedEmpleadoCollection);
            em.persist(tituloProfesional);
            for (Empleado empleadoCollectionEmpleado : tituloProfesional.getEmpleadoCollection()) {
                TituloProfesional oldTpidOfEmpleadoCollectionEmpleado = empleadoCollectionEmpleado.getTpid();
                empleadoCollectionEmpleado.setTpid(tituloProfesional);
                empleadoCollectionEmpleado = em.merge(empleadoCollectionEmpleado);
                if (oldTpidOfEmpleadoCollectionEmpleado != null) {
                    oldTpidOfEmpleadoCollectionEmpleado.getEmpleadoCollection().remove(empleadoCollectionEmpleado);
                    oldTpidOfEmpleadoCollectionEmpleado = em.merge(oldTpidOfEmpleadoCollectionEmpleado);
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
            TituloProfesional persistentTituloProfesional = em.find(TituloProfesional.class, tituloProfesional.getTpid());
            Collection<Empleado> empleadoCollectionOld = persistentTituloProfesional.getEmpleadoCollection();
            Collection<Empleado> empleadoCollectionNew = tituloProfesional.getEmpleadoCollection();
            List<String> illegalOrphanMessages = null;
            for (Empleado empleadoCollectionOldEmpleado : empleadoCollectionOld) {
                if (!empleadoCollectionNew.contains(empleadoCollectionOldEmpleado)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Empleado " + empleadoCollectionOldEmpleado + " since its tpid field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Empleado> attachedEmpleadoCollectionNew = new ArrayList<Empleado>();
            for (Empleado empleadoCollectionNewEmpleadoToAttach : empleadoCollectionNew) {
                empleadoCollectionNewEmpleadoToAttach = em.getReference(empleadoCollectionNewEmpleadoToAttach.getClass(), empleadoCollectionNewEmpleadoToAttach.getEmpid());
                attachedEmpleadoCollectionNew.add(empleadoCollectionNewEmpleadoToAttach);
            }
            empleadoCollectionNew = attachedEmpleadoCollectionNew;
            tituloProfesional.setEmpleadoCollection(empleadoCollectionNew);
            tituloProfesional = em.merge(tituloProfesional);
            for (Empleado empleadoCollectionNewEmpleado : empleadoCollectionNew) {
                if (!empleadoCollectionOld.contains(empleadoCollectionNewEmpleado)) {
                    TituloProfesional oldTpidOfEmpleadoCollectionNewEmpleado = empleadoCollectionNewEmpleado.getTpid();
                    empleadoCollectionNewEmpleado.setTpid(tituloProfesional);
                    empleadoCollectionNewEmpleado = em.merge(empleadoCollectionNewEmpleado);
                    if (oldTpidOfEmpleadoCollectionNewEmpleado != null && !oldTpidOfEmpleadoCollectionNewEmpleado.equals(tituloProfesional)) {
                        oldTpidOfEmpleadoCollectionNewEmpleado.getEmpleadoCollection().remove(empleadoCollectionNewEmpleado);
                        oldTpidOfEmpleadoCollectionNewEmpleado = em.merge(oldTpidOfEmpleadoCollectionNewEmpleado);
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
                Integer id = tituloProfesional.getTpid();
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
                tituloProfesional.getTpid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tituloProfesional with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Empleado> empleadoCollectionOrphanCheck = tituloProfesional.getEmpleadoCollection();
            for (Empleado empleadoCollectionOrphanCheckEmpleado : empleadoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TituloProfesional (" + tituloProfesional + ") cannot be destroyed since the Empleado " + empleadoCollectionOrphanCheckEmpleado + " in its empleadoCollection field has a non-nullable tpid field.");
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
