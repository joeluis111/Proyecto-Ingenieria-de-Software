/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DP;

import DP.exceptions.NonexistentEntityException;
import DP.exceptions.PreexistingEntityException;
import DP.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import MD.Material;
import MD.Proyecto;
import MD.UsoPlaneado;
import MD.UsoPlaneadoPK;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author Kenny
 */
public class UsoPlaneadoJpaController implements Serializable {

    public UsoPlaneadoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(UsoPlaneado usoPlaneado) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (usoPlaneado.getUsoPlaneadoPK() == null) {
            usoPlaneado.setUsoPlaneadoPK(new UsoPlaneadoPK());
        }
        usoPlaneado.getUsoPlaneadoPK().setMatid(usoPlaneado.getMaterial().getMatid());
        usoPlaneado.getUsoPlaneadoPK().setProid(usoPlaneado.getProyecto().getProid());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Material material = usoPlaneado.getMaterial();
            if (material != null) {
                material = em.getReference(material.getClass(), material.getMatid());
                usoPlaneado.setMaterial(material);
            }
            Proyecto proyecto = usoPlaneado.getProyecto();
            if (proyecto != null) {
                proyecto = em.getReference(proyecto.getClass(), proyecto.getProid());
                usoPlaneado.setProyecto(proyecto);
            }
            em.persist(usoPlaneado);
            if (material != null) {
                material.getUsoPlaneadoCollection().add(usoPlaneado);
                material = em.merge(material);
            }
            if (proyecto != null) {
                proyecto.getUsoPlaneadoCollection().add(usoPlaneado);
                proyecto = em.merge(proyecto);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findUsoPlaneado(usoPlaneado.getUsoPlaneadoPK()) != null) {
                throw new PreexistingEntityException("UsoPlaneado " + usoPlaneado + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(UsoPlaneado usoPlaneado) throws NonexistentEntityException, RollbackFailureException, Exception {
        usoPlaneado.getUsoPlaneadoPK().setMatid(usoPlaneado.getMaterial().getMatid());
        usoPlaneado.getUsoPlaneadoPK().setProid(usoPlaneado.getProyecto().getProid());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            UsoPlaneado persistentUsoPlaneado = em.find(UsoPlaneado.class, usoPlaneado.getUsoPlaneadoPK());
            Material materialOld = persistentUsoPlaneado.getMaterial();
            Material materialNew = usoPlaneado.getMaterial();
            Proyecto proyectoOld = persistentUsoPlaneado.getProyecto();
            Proyecto proyectoNew = usoPlaneado.getProyecto();
            if (materialNew != null) {
                materialNew = em.getReference(materialNew.getClass(), materialNew.getMatid());
                usoPlaneado.setMaterial(materialNew);
            }
            if (proyectoNew != null) {
                proyectoNew = em.getReference(proyectoNew.getClass(), proyectoNew.getProid());
                usoPlaneado.setProyecto(proyectoNew);
            }
            usoPlaneado = em.merge(usoPlaneado);
            if (materialOld != null && !materialOld.equals(materialNew)) {
                materialOld.getUsoPlaneadoCollection().remove(usoPlaneado);
                materialOld = em.merge(materialOld);
            }
            if (materialNew != null && !materialNew.equals(materialOld)) {
                materialNew.getUsoPlaneadoCollection().add(usoPlaneado);
                materialNew = em.merge(materialNew);
            }
            if (proyectoOld != null && !proyectoOld.equals(proyectoNew)) {
                proyectoOld.getUsoPlaneadoCollection().remove(usoPlaneado);
                proyectoOld = em.merge(proyectoOld);
            }
            if (proyectoNew != null && !proyectoNew.equals(proyectoOld)) {
                proyectoNew.getUsoPlaneadoCollection().add(usoPlaneado);
                proyectoNew = em.merge(proyectoNew);
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
                UsoPlaneadoPK id = usoPlaneado.getUsoPlaneadoPK();
                if (findUsoPlaneado(id) == null) {
                    throw new NonexistentEntityException("The usoPlaneado with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(UsoPlaneadoPK id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            UsoPlaneado usoPlaneado;
            try {
                usoPlaneado = em.getReference(UsoPlaneado.class, id);
                usoPlaneado.getUsoPlaneadoPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usoPlaneado with id " + id + " no longer exists.", enfe);
            }
            Material material = usoPlaneado.getMaterial();
            if (material != null) {
                material.getUsoPlaneadoCollection().remove(usoPlaneado);
                material = em.merge(material);
            }
            Proyecto proyecto = usoPlaneado.getProyecto();
            if (proyecto != null) {
                proyecto.getUsoPlaneadoCollection().remove(usoPlaneado);
                proyecto = em.merge(proyecto);
            }
            em.remove(usoPlaneado);
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

    public List<UsoPlaneado> findUsoPlaneadoEntities() {
        return findUsoPlaneadoEntities(true, -1, -1);
    }

    public List<UsoPlaneado> findUsoPlaneadoEntities(int maxResults, int firstResult) {
        return findUsoPlaneadoEntities(false, maxResults, firstResult);
    }

    private List<UsoPlaneado> findUsoPlaneadoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(UsoPlaneado.class));
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

    public UsoPlaneado findUsoPlaneado(UsoPlaneadoPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(UsoPlaneado.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsoPlaneadoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<UsoPlaneado> rt = cq.from(UsoPlaneado.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
