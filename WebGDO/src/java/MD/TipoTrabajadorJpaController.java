/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MD;

import MD.exceptions.NonexistentEntityException;
import MD.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import MD.Empleado;
import MD.TipoTrabajador;
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
public class TipoTrabajadorJpaController implements Serializable {

    public TipoTrabajadorJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoTrabajador tipoTrabajador) throws RollbackFailureException, Exception {
        if (tipoTrabajador.getEmpleadoCollection() == null) {
            tipoTrabajador.setEmpleadoCollection(new ArrayList<Empleado>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<Empleado> attachedEmpleadoCollection = new ArrayList<Empleado>();
            for (Empleado empleadoCollectionEmpleadoToAttach : tipoTrabajador.getEmpleadoCollection()) {
                empleadoCollectionEmpleadoToAttach = em.getReference(empleadoCollectionEmpleadoToAttach.getClass(), empleadoCollectionEmpleadoToAttach.getEmpid());
                attachedEmpleadoCollection.add(empleadoCollectionEmpleadoToAttach);
            }
            tipoTrabajador.setEmpleadoCollection(attachedEmpleadoCollection);
            em.persist(tipoTrabajador);
            for (Empleado empleadoCollectionEmpleado : tipoTrabajador.getEmpleadoCollection()) {
                TipoTrabajador oldTtidOfEmpleadoCollectionEmpleado = empleadoCollectionEmpleado.getTtid();
                empleadoCollectionEmpleado.setTtid(tipoTrabajador);
                empleadoCollectionEmpleado = em.merge(empleadoCollectionEmpleado);
                if (oldTtidOfEmpleadoCollectionEmpleado != null) {
                    oldTtidOfEmpleadoCollectionEmpleado.getEmpleadoCollection().remove(empleadoCollectionEmpleado);
                    oldTtidOfEmpleadoCollectionEmpleado = em.merge(oldTtidOfEmpleadoCollectionEmpleado);
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

    public void edit(TipoTrabajador tipoTrabajador) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TipoTrabajador persistentTipoTrabajador = em.find(TipoTrabajador.class, tipoTrabajador.getTtid());
            Collection<Empleado> empleadoCollectionOld = persistentTipoTrabajador.getEmpleadoCollection();
            Collection<Empleado> empleadoCollectionNew = tipoTrabajador.getEmpleadoCollection();
            Collection<Empleado> attachedEmpleadoCollectionNew = new ArrayList<Empleado>();
            for (Empleado empleadoCollectionNewEmpleadoToAttach : empleadoCollectionNew) {
                empleadoCollectionNewEmpleadoToAttach = em.getReference(empleadoCollectionNewEmpleadoToAttach.getClass(), empleadoCollectionNewEmpleadoToAttach.getEmpid());
                attachedEmpleadoCollectionNew.add(empleadoCollectionNewEmpleadoToAttach);
            }
            empleadoCollectionNew = attachedEmpleadoCollectionNew;
            tipoTrabajador.setEmpleadoCollection(empleadoCollectionNew);
            tipoTrabajador = em.merge(tipoTrabajador);
            for (Empleado empleadoCollectionOldEmpleado : empleadoCollectionOld) {
                if (!empleadoCollectionNew.contains(empleadoCollectionOldEmpleado)) {
                    empleadoCollectionOldEmpleado.setTtid(null);
                    empleadoCollectionOldEmpleado = em.merge(empleadoCollectionOldEmpleado);
                }
            }
            for (Empleado empleadoCollectionNewEmpleado : empleadoCollectionNew) {
                if (!empleadoCollectionOld.contains(empleadoCollectionNewEmpleado)) {
                    TipoTrabajador oldTtidOfEmpleadoCollectionNewEmpleado = empleadoCollectionNewEmpleado.getTtid();
                    empleadoCollectionNewEmpleado.setTtid(tipoTrabajador);
                    empleadoCollectionNewEmpleado = em.merge(empleadoCollectionNewEmpleado);
                    if (oldTtidOfEmpleadoCollectionNewEmpleado != null && !oldTtidOfEmpleadoCollectionNewEmpleado.equals(tipoTrabajador)) {
                        oldTtidOfEmpleadoCollectionNewEmpleado.getEmpleadoCollection().remove(empleadoCollectionNewEmpleado);
                        oldTtidOfEmpleadoCollectionNewEmpleado = em.merge(oldTtidOfEmpleadoCollectionNewEmpleado);
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
                Integer id = tipoTrabajador.getTtid();
                if (findTipoTrabajador(id) == null) {
                    throw new NonexistentEntityException("The tipoTrabajador with id " + id + " no longer exists.");
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
            TipoTrabajador tipoTrabajador;
            try {
                tipoTrabajador = em.getReference(TipoTrabajador.class, id);
                tipoTrabajador.getTtid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoTrabajador with id " + id + " no longer exists.", enfe);
            }
            Collection<Empleado> empleadoCollection = tipoTrabajador.getEmpleadoCollection();
            for (Empleado empleadoCollectionEmpleado : empleadoCollection) {
                empleadoCollectionEmpleado.setTtid(null);
                empleadoCollectionEmpleado = em.merge(empleadoCollectionEmpleado);
            }
            em.remove(tipoTrabajador);
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

    public List<TipoTrabajador> findTipoTrabajadorEntities() {
        return findTipoTrabajadorEntities(true, -1, -1);
    }

    public List<TipoTrabajador> findTipoTrabajadorEntities(int maxResults, int firstResult) {
        return findTipoTrabajadorEntities(false, maxResults, firstResult);
    }

    private List<TipoTrabajador> findTipoTrabajadorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoTrabajador.class));
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

    public TipoTrabajador findTipoTrabajador(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoTrabajador.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoTrabajadorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoTrabajador> rt = cq.from(TipoTrabajador.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
