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
        if (tipoTrabajador.getEmpleadosCollection() == null) {
            tipoTrabajador.setEmpleadosCollection(new ArrayList<Empleado>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<Empleado> attachedEmpleadosCollection = new ArrayList<Empleado>();
            for (Empleado empleadosCollectionEmpleadoToAttach : tipoTrabajador.getEmpleadosCollection()) {
                empleadosCollectionEmpleadoToAttach = em.getReference(empleadosCollectionEmpleadoToAttach.getClass(), empleadosCollectionEmpleadoToAttach.getPersonalID());
                attachedEmpleadosCollection.add(empleadosCollectionEmpleadoToAttach);
            }
            tipoTrabajador.setEmpleadosCollection(attachedEmpleadosCollection);
            em.persist(tipoTrabajador);
            for (Empleado empleadosCollectionEmpleado : tipoTrabajador.getEmpleadosCollection()) {
                TipoTrabajador oldTipoTrabajadorIDOfEmpleadosCollectionEmpleado = empleadosCollectionEmpleado.getTipoTrabajadorID();
                empleadosCollectionEmpleado.setTipoTrabajadorID(tipoTrabajador);
                empleadosCollectionEmpleado = em.merge(empleadosCollectionEmpleado);
                if (oldTipoTrabajadorIDOfEmpleadosCollectionEmpleado != null) {
                    oldTipoTrabajadorIDOfEmpleadosCollectionEmpleado.getEmpleadosCollection().remove(empleadosCollectionEmpleado);
                    oldTipoTrabajadorIDOfEmpleadosCollectionEmpleado = em.merge(oldTipoTrabajadorIDOfEmpleadosCollectionEmpleado);
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

    public void edit(TipoTrabajador tipoTrabajador) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TipoTrabajador persistentTipoTrabajador = em.find(TipoTrabajador.class, tipoTrabajador.getTipoTrabajadorID());
            Collection<Empleado> empleadosCollectionOld = persistentTipoTrabajador.getEmpleadosCollection();
            Collection<Empleado> empleadosCollectionNew = tipoTrabajador.getEmpleadosCollection();
            List<String> illegalOrphanMessages = null;
            for (Empleado empleadosCollectionOldEmpleado : empleadosCollectionOld) {
                if (!empleadosCollectionNew.contains(empleadosCollectionOldEmpleado)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Empleado " + empleadosCollectionOldEmpleado + " since its tipoTrabajadorID field is not nullable.");
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
            tipoTrabajador.setEmpleadosCollection(empleadosCollectionNew);
            tipoTrabajador = em.merge(tipoTrabajador);
            for (Empleado empleadosCollectionNewEmpleado : empleadosCollectionNew) {
                if (!empleadosCollectionOld.contains(empleadosCollectionNewEmpleado)) {
                    TipoTrabajador oldTipoTrabajadorIDOfEmpleadosCollectionNewEmpleado = empleadosCollectionNewEmpleado.getTipoTrabajadorID();
                    empleadosCollectionNewEmpleado.setTipoTrabajadorID(tipoTrabajador);
                    empleadosCollectionNewEmpleado = em.merge(empleadosCollectionNewEmpleado);
                    if (oldTipoTrabajadorIDOfEmpleadosCollectionNewEmpleado != null && !oldTipoTrabajadorIDOfEmpleadosCollectionNewEmpleado.equals(tipoTrabajador)) {
                        oldTipoTrabajadorIDOfEmpleadosCollectionNewEmpleado.getEmpleadosCollection().remove(empleadosCollectionNewEmpleado);
                        oldTipoTrabajadorIDOfEmpleadosCollectionNewEmpleado = em.merge(oldTipoTrabajadorIDOfEmpleadosCollectionNewEmpleado);
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
                Integer id = tipoTrabajador.getTipoTrabajadorID();
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

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TipoTrabajador tipoTrabajador;
            try {
                tipoTrabajador = em.getReference(TipoTrabajador.class, id);
                tipoTrabajador.getTipoTrabajadorID();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoTrabajador with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Empleado> empleadosCollectionOrphanCheck = tipoTrabajador.getEmpleadosCollection();
            for (Empleado empleadosCollectionOrphanCheckEmpleado : empleadosCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TipoTrabajador (" + tipoTrabajador + ") cannot be destroyed since the Empleado " + empleadosCollectionOrphanCheckEmpleado + " in its empleadosCollection field has a non-nullable tipoTrabajadorID field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
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
