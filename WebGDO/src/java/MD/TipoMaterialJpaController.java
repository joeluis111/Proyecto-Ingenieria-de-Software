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
import MD.Material;
import MD.TipoMaterial;
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
public class TipoMaterialJpaController implements Serializable {

    public TipoMaterialJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoMaterial tipoMaterial) throws RollbackFailureException, Exception {
        if (tipoMaterial.getMaterialCollection() == null) {
            tipoMaterial.setMaterialCollection(new ArrayList<Material>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<Material> attachedMaterialCollection = new ArrayList<Material>();
            for (Material materialCollectionMaterialToAttach : tipoMaterial.getMaterialCollection()) {
                materialCollectionMaterialToAttach = em.getReference(materialCollectionMaterialToAttach.getClass(), materialCollectionMaterialToAttach.getMatid());
                attachedMaterialCollection.add(materialCollectionMaterialToAttach);
            }
            tipoMaterial.setMaterialCollection(attachedMaterialCollection);
            em.persist(tipoMaterial);
            for (Material materialCollectionMaterial : tipoMaterial.getMaterialCollection()) {
                TipoMaterial oldTmidOfMaterialCollectionMaterial = materialCollectionMaterial.getTmid();
                materialCollectionMaterial.setTmid(tipoMaterial);
                materialCollectionMaterial = em.merge(materialCollectionMaterial);
                if (oldTmidOfMaterialCollectionMaterial != null) {
                    oldTmidOfMaterialCollectionMaterial.getMaterialCollection().remove(materialCollectionMaterial);
                    oldTmidOfMaterialCollectionMaterial = em.merge(oldTmidOfMaterialCollectionMaterial);
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

    public void edit(TipoMaterial tipoMaterial) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TipoMaterial persistentTipoMaterial = em.find(TipoMaterial.class, tipoMaterial.getTmid());
            Collection<Material> materialCollectionOld = persistentTipoMaterial.getMaterialCollection();
            Collection<Material> materialCollectionNew = tipoMaterial.getMaterialCollection();
            List<String> illegalOrphanMessages = null;
            for (Material materialCollectionOldMaterial : materialCollectionOld) {
                if (!materialCollectionNew.contains(materialCollectionOldMaterial)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Material " + materialCollectionOldMaterial + " since its tmid field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Material> attachedMaterialCollectionNew = new ArrayList<Material>();
            for (Material materialCollectionNewMaterialToAttach : materialCollectionNew) {
                materialCollectionNewMaterialToAttach = em.getReference(materialCollectionNewMaterialToAttach.getClass(), materialCollectionNewMaterialToAttach.getMatid());
                attachedMaterialCollectionNew.add(materialCollectionNewMaterialToAttach);
            }
            materialCollectionNew = attachedMaterialCollectionNew;
            tipoMaterial.setMaterialCollection(materialCollectionNew);
            tipoMaterial = em.merge(tipoMaterial);
            for (Material materialCollectionNewMaterial : materialCollectionNew) {
                if (!materialCollectionOld.contains(materialCollectionNewMaterial)) {
                    TipoMaterial oldTmidOfMaterialCollectionNewMaterial = materialCollectionNewMaterial.getTmid();
                    materialCollectionNewMaterial.setTmid(tipoMaterial);
                    materialCollectionNewMaterial = em.merge(materialCollectionNewMaterial);
                    if (oldTmidOfMaterialCollectionNewMaterial != null && !oldTmidOfMaterialCollectionNewMaterial.equals(tipoMaterial)) {
                        oldTmidOfMaterialCollectionNewMaterial.getMaterialCollection().remove(materialCollectionNewMaterial);
                        oldTmidOfMaterialCollectionNewMaterial = em.merge(oldTmidOfMaterialCollectionNewMaterial);
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
                Integer id = tipoMaterial.getTmid();
                if (findTipoMaterial(id) == null) {
                    throw new NonexistentEntityException("The tipoMaterial with id " + id + " no longer exists.");
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
            TipoMaterial tipoMaterial;
            try {
                tipoMaterial = em.getReference(TipoMaterial.class, id);
                tipoMaterial.getTmid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoMaterial with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Material> materialCollectionOrphanCheck = tipoMaterial.getMaterialCollection();
            for (Material materialCollectionOrphanCheckMaterial : materialCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TipoMaterial (" + tipoMaterial + ") cannot be destroyed since the Material " + materialCollectionOrphanCheckMaterial + " in its materialCollection field has a non-nullable tmid field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(tipoMaterial);
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

    public List<TipoMaterial> findTipoMaterialEntities() {
        return findTipoMaterialEntities(true, -1, -1);
    }

    public List<TipoMaterial> findTipoMaterialEntities(int maxResults, int firstResult) {
        return findTipoMaterialEntities(false, maxResults, firstResult);
    }

    private List<TipoMaterial> findTipoMaterialEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoMaterial.class));
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

    public TipoMaterial findTipoMaterial(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoMaterial.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoMaterialCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoMaterial> rt = cq.from(TipoMaterial.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
