/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DP;

import DP.exceptions.NonexistentEntityException;
import DP.exceptions.PreexistingEntityException;
import DP.exceptions.RollbackFailureException;
import MD.Inventario;
import MD.Inventario;
import MD.InventarioPK;
import MD.InventarioPK;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import MD.Proyecto;
import MD.Material;
import MD.Material;
import MD.Proyecto;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author Kenny
 */
public class InventarioJpaController implements Serializable {

    public InventarioJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Inventario inventario) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (inventario.getInventarioPK() == null) {
            inventario.setInventarioPK(new InventarioPK());
        }
        inventario.getInventarioPK().setMatid(inventario.getMaterial().getMatid());
        inventario.getInventarioPK().setProid(inventario.getProyecto().getProid());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Proyecto proyecto = inventario.getProyecto();
            if (proyecto != null) {
                proyecto = em.getReference(proyecto.getClass(), proyecto.getProid());
                inventario.setProyecto(proyecto);
            }
            Material material = inventario.getMaterial();
            if (material != null) {
                material = em.getReference(material.getClass(), material.getMatid());
                inventario.setMaterial(material);
            }
            em.persist(inventario);
            if (proyecto != null) {
                proyecto.getInventarioCollection().add(inventario);
                proyecto = em.merge(proyecto);
            }
            if (material != null) {
                material.getInventarioCollection().add(inventario);
                material = em.merge(material);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findInventario(inventario.getInventarioPK()) != null) {
                throw new PreexistingEntityException("Inventario " + inventario + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Inventario inventario) throws NonexistentEntityException, RollbackFailureException, Exception {
        inventario.getInventarioPK().setMatid(inventario.getMaterial().getMatid());
        inventario.getInventarioPK().setProid(inventario.getProyecto().getProid());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Inventario persistentInventario = em.find(Inventario.class, inventario.getInventarioPK());
            Proyecto proyectoOld = persistentInventario.getProyecto();
            Proyecto proyectoNew = inventario.getProyecto();
            Material materialOld = persistentInventario.getMaterial();
            Material materialNew = inventario.getMaterial();
            if (proyectoNew != null) {
                proyectoNew = em.getReference(proyectoNew.getClass(), proyectoNew.getProid());
                inventario.setProyecto(proyectoNew);
            }
            if (materialNew != null) {
                materialNew = em.getReference(materialNew.getClass(), materialNew.getMatid());
                inventario.setMaterial(materialNew);
            }
            inventario = em.merge(inventario);
            if (proyectoOld != null && !proyectoOld.equals(proyectoNew)) {
                proyectoOld.getInventarioCollection().remove(inventario);
                proyectoOld = em.merge(proyectoOld);
            }
            if (proyectoNew != null && !proyectoNew.equals(proyectoOld)) {
                proyectoNew.getInventarioCollection().add(inventario);
                proyectoNew = em.merge(proyectoNew);
            }
            if (materialOld != null && !materialOld.equals(materialNew)) {
                materialOld.getInventarioCollection().remove(inventario);
                materialOld = em.merge(materialOld);
            }
            if (materialNew != null && !materialNew.equals(materialOld)) {
                materialNew.getInventarioCollection().add(inventario);
                materialNew = em.merge(materialNew);
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
                InventarioPK id = inventario.getInventarioPK();
                if (findInventario(id) == null) {
                    throw new NonexistentEntityException("The inventario with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(InventarioPK id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Inventario inventario;
            try {
                inventario = em.getReference(Inventario.class, id);
                inventario.getInventarioPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The inventario with id " + id + " no longer exists.", enfe);
            }
            Proyecto proyecto = inventario.getProyecto();
            if (proyecto != null) {
                proyecto.getInventarioCollection().remove(inventario);
                proyecto = em.merge(proyecto);
            }
            Material material = inventario.getMaterial();
            if (material != null) {
                material.getInventarioCollection().remove(inventario);
                material = em.merge(material);
            }
            em.remove(inventario);
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

    public List<Inventario> findInventarioEntities() {
        return findInventarioEntities(true, -1, -1);
    }

    public List<Inventario> findInventarioEntities(int maxResults, int firstResult) {
        return findInventarioEntities(false, maxResults, firstResult);
    }

    private List<Inventario> findInventarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Inventario.class));
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

    public Inventario findInventario(InventarioPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Inventario.class, id);
        } finally {
            em.close();
        }
    }

    public int getInventarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Inventario> rt = cq.from(Inventario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
