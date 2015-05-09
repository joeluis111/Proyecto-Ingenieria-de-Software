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
import MD.Proveedor;
import MD.TipoProveedor;
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
public class TipoProveedorJpaController implements Serializable {

    public TipoProveedorJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoProveedor tipoProveedor) throws RollbackFailureException, Exception {
        if (tipoProveedor.getProveedorCollection() == null) {
            tipoProveedor.setProveedorCollection(new ArrayList<Proveedor>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<Proveedor> attachedProveedorCollection = new ArrayList<Proveedor>();
            for (Proveedor proveedorCollectionProveedorToAttach : tipoProveedor.getProveedorCollection()) {
                proveedorCollectionProveedorToAttach = em.getReference(proveedorCollectionProveedorToAttach.getClass(), proveedorCollectionProveedorToAttach.getProvid());
                attachedProveedorCollection.add(proveedorCollectionProveedorToAttach);
            }
            tipoProveedor.setProveedorCollection(attachedProveedorCollection);
            em.persist(tipoProveedor);
            for (Proveedor proveedorCollectionProveedor : tipoProveedor.getProveedorCollection()) {
                TipoProveedor oldTproidOfProveedorCollectionProveedor = proveedorCollectionProveedor.getTproid();
                proveedorCollectionProveedor.setTproid(tipoProveedor);
                proveedorCollectionProveedor = em.merge(proveedorCollectionProveedor);
                if (oldTproidOfProveedorCollectionProveedor != null) {
                    oldTproidOfProveedorCollectionProveedor.getProveedorCollection().remove(proveedorCollectionProveedor);
                    oldTproidOfProveedorCollectionProveedor = em.merge(oldTproidOfProveedorCollectionProveedor);
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

    public void edit(TipoProveedor tipoProveedor) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TipoProveedor persistentTipoProveedor = em.find(TipoProveedor.class, tipoProveedor.getTproid());
            Collection<Proveedor> proveedorCollectionOld = persistentTipoProveedor.getProveedorCollection();
            Collection<Proveedor> proveedorCollectionNew = tipoProveedor.getProveedorCollection();
            List<String> illegalOrphanMessages = null;
            for (Proveedor proveedorCollectionOldProveedor : proveedorCollectionOld) {
                if (!proveedorCollectionNew.contains(proveedorCollectionOldProveedor)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Proveedor " + proveedorCollectionOldProveedor + " since its tproid field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Proveedor> attachedProveedorCollectionNew = new ArrayList<Proveedor>();
            for (Proveedor proveedorCollectionNewProveedorToAttach : proveedorCollectionNew) {
                proveedorCollectionNewProveedorToAttach = em.getReference(proveedorCollectionNewProveedorToAttach.getClass(), proveedorCollectionNewProveedorToAttach.getProvid());
                attachedProveedorCollectionNew.add(proveedorCollectionNewProveedorToAttach);
            }
            proveedorCollectionNew = attachedProveedorCollectionNew;
            tipoProveedor.setProveedorCollection(proveedorCollectionNew);
            tipoProveedor = em.merge(tipoProveedor);
            for (Proveedor proveedorCollectionNewProveedor : proveedorCollectionNew) {
                if (!proveedorCollectionOld.contains(proveedorCollectionNewProveedor)) {
                    TipoProveedor oldTproidOfProveedorCollectionNewProveedor = proveedorCollectionNewProveedor.getTproid();
                    proveedorCollectionNewProveedor.setTproid(tipoProveedor);
                    proveedorCollectionNewProveedor = em.merge(proveedorCollectionNewProveedor);
                    if (oldTproidOfProveedorCollectionNewProveedor != null && !oldTproidOfProveedorCollectionNewProveedor.equals(tipoProveedor)) {
                        oldTproidOfProveedorCollectionNewProveedor.getProveedorCollection().remove(proveedorCollectionNewProveedor);
                        oldTproidOfProveedorCollectionNewProveedor = em.merge(oldTproidOfProveedorCollectionNewProveedor);
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
                Integer id = tipoProveedor.getTproid();
                if (findTipoProveedor(id) == null) {
                    throw new NonexistentEntityException("The tipoProveedor with id " + id + " no longer exists.");
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
            TipoProveedor tipoProveedor;
            try {
                tipoProveedor = em.getReference(TipoProveedor.class, id);
                tipoProveedor.getTproid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoProveedor with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Proveedor> proveedorCollectionOrphanCheck = tipoProveedor.getProveedorCollection();
            for (Proveedor proveedorCollectionOrphanCheckProveedor : proveedorCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TipoProveedor (" + tipoProveedor + ") cannot be destroyed since the Proveedor " + proveedorCollectionOrphanCheckProveedor + " in its proveedorCollection field has a non-nullable tproid field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(tipoProveedor);
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

    public List<TipoProveedor> findTipoProveedorEntities() {
        return findTipoProveedorEntities(true, -1, -1);
    }

    public List<TipoProveedor> findTipoProveedorEntities(int maxResults, int firstResult) {
        return findTipoProveedorEntities(false, maxResults, firstResult);
    }

    private List<TipoProveedor> findTipoProveedorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoProveedor.class));
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

    public TipoProveedor findTipoProveedor(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoProveedor.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoProveedorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoProveedor> rt = cq.from(TipoProveedor.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
