/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MD;

import MD.exceptions.NonexistentEntityException;
import MD.exceptions.RollbackFailureException;
import MD.Documento;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import MD.TipoDocumento;
import MD.Proyecto;
import MD.Empleado;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author Kenny
 */
public class DocumentoJpaController implements Serializable {

    public DocumentoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Documento documento) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TipoDocumento tdocid = documento.getTdocid();
            if (tdocid != null) {
                tdocid = em.getReference(tdocid.getClass(), tdocid.getTdocid());
                documento.setTdocid(tdocid);
            }
            Proyecto proid = documento.getProid();
            if (proid != null) {
                proid = em.getReference(proid.getClass(), proid.getProid());
                documento.setProid(proid);
            }
            Empleado empid = documento.getEmpid();
            if (empid != null) {
                empid = em.getReference(empid.getClass(), empid.getEmpid());
                documento.setEmpid(empid);
            }
            em.persist(documento);
            if (tdocid != null) {
                tdocid.getDocumentoCollection().add(documento);
                tdocid = em.merge(tdocid);
            }
            if (proid != null) {
                proid.getDocumentoCollection().add(documento);
                proid = em.merge(proid);
            }
            if (empid != null) {
                empid.getDocumentoCollection().add(documento);
                empid = em.merge(empid);
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

    public void edit(Documento documento) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Documento persistentDocumento = em.find(Documento.class, documento.getDocid());
            TipoDocumento tdocidOld = persistentDocumento.getTdocid();
            TipoDocumento tdocidNew = documento.getTdocid();
            Proyecto proidOld = persistentDocumento.getProid();
            Proyecto proidNew = documento.getProid();
            Empleado empidOld = persistentDocumento.getEmpid();
            Empleado empidNew = documento.getEmpid();
            if (tdocidNew != null) {
                tdocidNew = em.getReference(tdocidNew.getClass(), tdocidNew.getTdocid());
                documento.setTdocid(tdocidNew);
            }
            if (proidNew != null) {
                proidNew = em.getReference(proidNew.getClass(), proidNew.getProid());
                documento.setProid(proidNew);
            }
            if (empidNew != null) {
                empidNew = em.getReference(empidNew.getClass(), empidNew.getEmpid());
                documento.setEmpid(empidNew);
            }
            documento = em.merge(documento);
            if (tdocidOld != null && !tdocidOld.equals(tdocidNew)) {
                tdocidOld.getDocumentoCollection().remove(documento);
                tdocidOld = em.merge(tdocidOld);
            }
            if (tdocidNew != null && !tdocidNew.equals(tdocidOld)) {
                tdocidNew.getDocumentoCollection().add(documento);
                tdocidNew = em.merge(tdocidNew);
            }
            if (proidOld != null && !proidOld.equals(proidNew)) {
                proidOld.getDocumentoCollection().remove(documento);
                proidOld = em.merge(proidOld);
            }
            if (proidNew != null && !proidNew.equals(proidOld)) {
                proidNew.getDocumentoCollection().add(documento);
                proidNew = em.merge(proidNew);
            }
            if (empidOld != null && !empidOld.equals(empidNew)) {
                empidOld.getDocumentoCollection().remove(documento);
                empidOld = em.merge(empidOld);
            }
            if (empidNew != null && !empidNew.equals(empidOld)) {
                empidNew.getDocumentoCollection().add(documento);
                empidNew = em.merge(empidNew);
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
                Integer id = documento.getDocid();
                if (findDocumento(id) == null) {
                    throw new NonexistentEntityException("The documento with id " + id + " no longer exists.");
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
            Documento documento;
            try {
                documento = em.getReference(Documento.class, id);
                documento.getDocid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The documento with id " + id + " no longer exists.", enfe);
            }
            TipoDocumento tdocid = documento.getTdocid();
            if (tdocid != null) {
                tdocid.getDocumentoCollection().remove(documento);
                tdocid = em.merge(tdocid);
            }
            Proyecto proid = documento.getProid();
            if (proid != null) {
                proid.getDocumentoCollection().remove(documento);
                proid = em.merge(proid);
            }
            Empleado empid = documento.getEmpid();
            if (empid != null) {
                empid.getDocumentoCollection().remove(documento);
                empid = em.merge(empid);
            }
            em.remove(documento);
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

    public List<Documento> findDocumentoEntities() {
        return findDocumentoEntities(true, -1, -1);
    }

    public List<Documento> findDocumentoEntities(int maxResults, int firstResult) {
        return findDocumentoEntities(false, maxResults, firstResult);
    }

    private List<Documento> findDocumentoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Documento.class));
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

    public Documento findDocumento(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Documento.class, id);
        } finally {
            em.close();
        }
    }

    public int getDocumentoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Documento> rt = cq.from(Documento.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
