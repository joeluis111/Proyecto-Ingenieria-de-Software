/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DP;

import DP.exceptions.NonexistentEntityException;
import DP.exceptions.PreexistingEntityException;
import DP.exceptions.RollbackFailureException;
import MD.HistoriaInventaria;
import MD.HistoriaInventaria;
import MD.HistoriaInventariaPK;
import MD.HistoriaInventariaPK;
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
public class HistoriaInventariaJpaController implements Serializable {

    public HistoriaInventariaJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(HistoriaInventaria historiaInventaria) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (historiaInventaria.getHistoriaInventariaPK() == null) {
            historiaInventaria.setHistoriaInventariaPK(new HistoriaInventariaPK());
        }
        historiaInventaria.getHistoriaInventariaPK().setProid(historiaInventaria.getProyecto().getProid());
        historiaInventaria.getHistoriaInventariaPK().setMatid(historiaInventaria.getMaterial().getMatid());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Proyecto proyecto = historiaInventaria.getProyecto();
            if (proyecto != null) {
                proyecto = em.getReference(proyecto.getClass(), proyecto.getProid());
                historiaInventaria.setProyecto(proyecto);
            }
            Material material = historiaInventaria.getMaterial();
            if (material != null) {
                material = em.getReference(material.getClass(), material.getMatid());
                historiaInventaria.setMaterial(material);
            }
            em.persist(historiaInventaria);
            if (proyecto != null) {
                proyecto.getHistoriaInventariaCollection().add(historiaInventaria);
                proyecto = em.merge(proyecto);
            }
            if (material != null) {
                material.getHistoriaInventariaCollection().add(historiaInventaria);
                material = em.merge(material);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findHistoriaInventaria(historiaInventaria.getHistoriaInventariaPK()) != null) {
                throw new PreexistingEntityException("HistoriaInventaria " + historiaInventaria + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(HistoriaInventaria historiaInventaria) throws NonexistentEntityException, RollbackFailureException, Exception {
        historiaInventaria.getHistoriaInventariaPK().setProid(historiaInventaria.getProyecto().getProid());
        historiaInventaria.getHistoriaInventariaPK().setMatid(historiaInventaria.getMaterial().getMatid());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            HistoriaInventaria persistentHistoriaInventaria = em.find(HistoriaInventaria.class, historiaInventaria.getHistoriaInventariaPK());
            Proyecto proyectoOld = persistentHistoriaInventaria.getProyecto();
            Proyecto proyectoNew = historiaInventaria.getProyecto();
            Material materialOld = persistentHistoriaInventaria.getMaterial();
            Material materialNew = historiaInventaria.getMaterial();
            if (proyectoNew != null) {
                proyectoNew = em.getReference(proyectoNew.getClass(), proyectoNew.getProid());
                historiaInventaria.setProyecto(proyectoNew);
            }
            if (materialNew != null) {
                materialNew = em.getReference(materialNew.getClass(), materialNew.getMatid());
                historiaInventaria.setMaterial(materialNew);
            }
            historiaInventaria = em.merge(historiaInventaria);
            if (proyectoOld != null && !proyectoOld.equals(proyectoNew)) {
                proyectoOld.getHistoriaInventariaCollection().remove(historiaInventaria);
                proyectoOld = em.merge(proyectoOld);
            }
            if (proyectoNew != null && !proyectoNew.equals(proyectoOld)) {
                proyectoNew.getHistoriaInventariaCollection().add(historiaInventaria);
                proyectoNew = em.merge(proyectoNew);
            }
            if (materialOld != null && !materialOld.equals(materialNew)) {
                materialOld.getHistoriaInventariaCollection().remove(historiaInventaria);
                materialOld = em.merge(materialOld);
            }
            if (materialNew != null && !materialNew.equals(materialOld)) {
                materialNew.getHistoriaInventariaCollection().add(historiaInventaria);
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
                HistoriaInventariaPK id = historiaInventaria.getHistoriaInventariaPK();
                if (findHistoriaInventaria(id) == null) {
                    throw new NonexistentEntityException("The historiaInventaria with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(HistoriaInventariaPK id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            HistoriaInventaria historiaInventaria;
            try {
                historiaInventaria = em.getReference(HistoriaInventaria.class, id);
                historiaInventaria.getHistoriaInventariaPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The historiaInventaria with id " + id + " no longer exists.", enfe);
            }
            Proyecto proyecto = historiaInventaria.getProyecto();
            if (proyecto != null) {
                proyecto.getHistoriaInventariaCollection().remove(historiaInventaria);
                proyecto = em.merge(proyecto);
            }
            Material material = historiaInventaria.getMaterial();
            if (material != null) {
                material.getHistoriaInventariaCollection().remove(historiaInventaria);
                material = em.merge(material);
            }
            em.remove(historiaInventaria);
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

    public List<HistoriaInventaria> findHistoriaInventariaEntities() {
        return findHistoriaInventariaEntities(true, -1, -1);
    }

    public List<HistoriaInventaria> findHistoriaInventariaEntities(int maxResults, int firstResult) {
        return findHistoriaInventariaEntities(false, maxResults, firstResult);
    }

    private List<HistoriaInventaria> findHistoriaInventariaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(HistoriaInventaria.class));
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

    public HistoriaInventaria findHistoriaInventaria(HistoriaInventariaPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(HistoriaInventaria.class, id);
        } finally {
            em.close();
        }
    }

    public int getHistoriaInventariaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<HistoriaInventaria> rt = cq.from(HistoriaInventaria.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
