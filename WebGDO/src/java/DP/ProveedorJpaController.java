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
import MD.TipoProveedor;
import MD.Material;
import MD.Proveedor;
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
public class ProveedorJpaController implements Serializable {

    public ProveedorJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Proveedor proveedor) throws RollbackFailureException, Exception {
        if (proveedor.getMaterialCollection() == null) {
            proveedor.setMaterialCollection(new ArrayList<Material>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TipoProveedor tproid = proveedor.getTproid();
            if (tproid != null) {
                tproid = em.getReference(tproid.getClass(), tproid.getTproid());
                proveedor.setTproid(tproid);
            }
            Collection<Material> attachedMaterialCollection = new ArrayList<Material>();
            for (Material materialCollectionMaterialToAttach : proveedor.getMaterialCollection()) {
                materialCollectionMaterialToAttach = em.getReference(materialCollectionMaterialToAttach.getClass(), materialCollectionMaterialToAttach.getMatid());
                attachedMaterialCollection.add(materialCollectionMaterialToAttach);
            }
            proveedor.setMaterialCollection(attachedMaterialCollection);
            em.persist(proveedor);
            if (tproid != null) {
                tproid.getProveedorCollection().add(proveedor);
                tproid = em.merge(tproid);
            }
            for (Material materialCollectionMaterial : proveedor.getMaterialCollection()) {
                materialCollectionMaterial.getProveedorCollection().add(proveedor);
                materialCollectionMaterial = em.merge(materialCollectionMaterial);
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

    public void edit(Proveedor proveedor) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Proveedor persistentProveedor = em.find(Proveedor.class, proveedor.getProvid());
            TipoProveedor tproidOld = persistentProveedor.getTproid();
            TipoProveedor tproidNew = proveedor.getTproid();
            Collection<Material> materialCollectionOld = persistentProveedor.getMaterialCollection();
            Collection<Material> materialCollectionNew = proveedor.getMaterialCollection();
            if (tproidNew != null) {
                tproidNew = em.getReference(tproidNew.getClass(), tproidNew.getTproid());
                proveedor.setTproid(tproidNew);
            }
            Collection<Material> attachedMaterialCollectionNew = new ArrayList<Material>();
            for (Material materialCollectionNewMaterialToAttach : materialCollectionNew) {
                materialCollectionNewMaterialToAttach = em.getReference(materialCollectionNewMaterialToAttach.getClass(), materialCollectionNewMaterialToAttach.getMatid());
                attachedMaterialCollectionNew.add(materialCollectionNewMaterialToAttach);
            }
            materialCollectionNew = attachedMaterialCollectionNew;
            proveedor.setMaterialCollection(materialCollectionNew);
            proveedor = em.merge(proveedor);
            if (tproidOld != null && !tproidOld.equals(tproidNew)) {
                tproidOld.getProveedorCollection().remove(proveedor);
                tproidOld = em.merge(tproidOld);
            }
            if (tproidNew != null && !tproidNew.equals(tproidOld)) {
                tproidNew.getProveedorCollection().add(proveedor);
                tproidNew = em.merge(tproidNew);
            }
            for (Material materialCollectionOldMaterial : materialCollectionOld) {
                if (!materialCollectionNew.contains(materialCollectionOldMaterial)) {
                    materialCollectionOldMaterial.getProveedorCollection().remove(proveedor);
                    materialCollectionOldMaterial = em.merge(materialCollectionOldMaterial);
                }
            }
            for (Material materialCollectionNewMaterial : materialCollectionNew) {
                if (!materialCollectionOld.contains(materialCollectionNewMaterial)) {
                    materialCollectionNewMaterial.getProveedorCollection().add(proveedor);
                    materialCollectionNewMaterial = em.merge(materialCollectionNewMaterial);
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
                Integer id = proveedor.getProvid();
                if (findProveedor(id) == null) {
                    throw new NonexistentEntityException("The proveedor with id " + id + " no longer exists.");
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
            Proveedor proveedor;
            try {
                proveedor = em.getReference(Proveedor.class, id);
                proveedor.getProvid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The proveedor with id " + id + " no longer exists.", enfe);
            }
            TipoProveedor tproid = proveedor.getTproid();
            if (tproid != null) {
                tproid.getProveedorCollection().remove(proveedor);
                tproid = em.merge(tproid);
            }
            Collection<Material> materialCollection = proveedor.getMaterialCollection();
            for (Material materialCollectionMaterial : materialCollection) {
                materialCollectionMaterial.getProveedorCollection().remove(proveedor);
                materialCollectionMaterial = em.merge(materialCollectionMaterial);
            }
            em.remove(proveedor);
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

    public List<Proveedor> findProveedorEntities() {
        return findProveedorEntities(true, -1, -1);
    }

    public List<Proveedor> findProveedorEntities(int maxResults, int firstResult) {
        return findProveedorEntities(false, maxResults, firstResult);
    }

    private List<Proveedor> findProveedorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Proveedor.class));
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

    public Proveedor findProveedor(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Proveedor.class, id);
        } finally {
            em.close();
        }
    }

    public int getProveedorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Proveedor> rt = cq.from(Proveedor.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
