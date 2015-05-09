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
import MD.Material;
import MD.Unidad;
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
public class UnidadJpaController implements Serializable {

    public UnidadJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Unidad unidad) throws RollbackFailureException, Exception {
        if (unidad.getMaterialCollection() == null) {
            unidad.setMaterialCollection(new ArrayList<Material>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<Material> attachedMaterialCollection = new ArrayList<Material>();
            for (Material materialCollectionMaterialToAttach : unidad.getMaterialCollection()) {
                materialCollectionMaterialToAttach = em.getReference(materialCollectionMaterialToAttach.getClass(), materialCollectionMaterialToAttach.getMatid());
                attachedMaterialCollection.add(materialCollectionMaterialToAttach);
            }
            unidad.setMaterialCollection(attachedMaterialCollection);
            em.persist(unidad);
            for (Material materialCollectionMaterial : unidad.getMaterialCollection()) {
                Unidad oldUnidOfMaterialCollectionMaterial = materialCollectionMaterial.getUnid();
                materialCollectionMaterial.setUnid(unidad);
                materialCollectionMaterial = em.merge(materialCollectionMaterial);
                if (oldUnidOfMaterialCollectionMaterial != null) {
                    oldUnidOfMaterialCollectionMaterial.getMaterialCollection().remove(materialCollectionMaterial);
                    oldUnidOfMaterialCollectionMaterial = em.merge(oldUnidOfMaterialCollectionMaterial);
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

    public void edit(Unidad unidad) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Unidad persistentUnidad = em.find(Unidad.class, unidad.getUnid());
            Collection<Material> materialCollectionOld = persistentUnidad.getMaterialCollection();
            Collection<Material> materialCollectionNew = unidad.getMaterialCollection();
            List<String> illegalOrphanMessages = null;
            for (Material materialCollectionOldMaterial : materialCollectionOld) {
                if (!materialCollectionNew.contains(materialCollectionOldMaterial)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Material " + materialCollectionOldMaterial + " since its unid field is not nullable.");
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
            unidad.setMaterialCollection(materialCollectionNew);
            unidad = em.merge(unidad);
            for (Material materialCollectionNewMaterial : materialCollectionNew) {
                if (!materialCollectionOld.contains(materialCollectionNewMaterial)) {
                    Unidad oldUnidOfMaterialCollectionNewMaterial = materialCollectionNewMaterial.getUnid();
                    materialCollectionNewMaterial.setUnid(unidad);
                    materialCollectionNewMaterial = em.merge(materialCollectionNewMaterial);
                    if (oldUnidOfMaterialCollectionNewMaterial != null && !oldUnidOfMaterialCollectionNewMaterial.equals(unidad)) {
                        oldUnidOfMaterialCollectionNewMaterial.getMaterialCollection().remove(materialCollectionNewMaterial);
                        oldUnidOfMaterialCollectionNewMaterial = em.merge(oldUnidOfMaterialCollectionNewMaterial);
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
                Integer id = unidad.getUnid();
                if (findUnidad(id) == null) {
                    throw new NonexistentEntityException("The unidad with id " + id + " no longer exists.");
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
            Unidad unidad;
            try {
                unidad = em.getReference(Unidad.class, id);
                unidad.getUnid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The unidad with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Material> materialCollectionOrphanCheck = unidad.getMaterialCollection();
            for (Material materialCollectionOrphanCheckMaterial : materialCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Unidad (" + unidad + ") cannot be destroyed since the Material " + materialCollectionOrphanCheckMaterial + " in its materialCollection field has a non-nullable unid field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(unidad);
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

    public List<Unidad> findUnidadEntities() {
        return findUnidadEntities(true, -1, -1);
    }

    public List<Unidad> findUnidadEntities(int maxResults, int firstResult) {
        return findUnidadEntities(false, maxResults, firstResult);
    }

    private List<Unidad> findUnidadEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Unidad.class));
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

    public Unidad findUnidad(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Unidad.class, id);
        } finally {
            em.close();
        }
    }

    public int getUnidadCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Unidad> rt = cq.from(Unidad.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
