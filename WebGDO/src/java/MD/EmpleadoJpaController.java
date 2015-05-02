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
import MD.TipoTrabajador;
import MD.TituloProfesional;
import MD.HistoriaTrabajo;
import java.util.ArrayList;
import java.util.Collection;
import MD.Documento;
import MD.Empleado;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author Kenny
 */
public class EmpleadoJpaController implements Serializable {

    public EmpleadoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Empleado empleado) throws RollbackFailureException, Exception {
        if (empleado.getHistoriaTrabajoCollection() == null) {
            empleado.setHistoriaTrabajoCollection(new ArrayList<HistoriaTrabajo>());
        }
        if (empleado.getDocumentoCollection() == null) {
            empleado.setDocumentoCollection(new ArrayList<Documento>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TipoTrabajador ttid = empleado.getTtid();
            if (ttid != null) {
                ttid = em.getReference(ttid.getClass(), ttid.getTtid());
                empleado.setTtid(ttid);
            }
            TituloProfesional tpid = empleado.getTpid();
            if (tpid != null) {
                tpid = em.getReference(tpid.getClass(), tpid.getTpid());
                empleado.setTpid(tpid);
            }
            Collection<HistoriaTrabajo> attachedHistoriaTrabajoCollection = new ArrayList<HistoriaTrabajo>();
            for (HistoriaTrabajo historiaTrabajoCollectionHistoriaTrabajoToAttach : empleado.getHistoriaTrabajoCollection()) {
                historiaTrabajoCollectionHistoriaTrabajoToAttach = em.getReference(historiaTrabajoCollectionHistoriaTrabajoToAttach.getClass(), historiaTrabajoCollectionHistoriaTrabajoToAttach.getHistoriaTrabajoPK());
                attachedHistoriaTrabajoCollection.add(historiaTrabajoCollectionHistoriaTrabajoToAttach);
            }
            empleado.setHistoriaTrabajoCollection(attachedHistoriaTrabajoCollection);
            Collection<Documento> attachedDocumentoCollection = new ArrayList<Documento>();
            for (Documento documentoCollectionDocumentoToAttach : empleado.getDocumentoCollection()) {
                documentoCollectionDocumentoToAttach = em.getReference(documentoCollectionDocumentoToAttach.getClass(), documentoCollectionDocumentoToAttach.getDocid());
                attachedDocumentoCollection.add(documentoCollectionDocumentoToAttach);
            }
            empleado.setDocumentoCollection(attachedDocumentoCollection);
            em.persist(empleado);
            if (ttid != null) {
                ttid.getEmpleadoCollection().add(empleado);
                ttid = em.merge(ttid);
            }
            if (tpid != null) {
                tpid.getEmpleadoCollection().add(empleado);
                tpid = em.merge(tpid);
            }
            for (HistoriaTrabajo historiaTrabajoCollectionHistoriaTrabajo : empleado.getHistoriaTrabajoCollection()) {
                Empleado oldEmpleadoOfHistoriaTrabajoCollectionHistoriaTrabajo = historiaTrabajoCollectionHistoriaTrabajo.getEmpleado();
                historiaTrabajoCollectionHistoriaTrabajo.setEmpleado(empleado);
                historiaTrabajoCollectionHistoriaTrabajo = em.merge(historiaTrabajoCollectionHistoriaTrabajo);
                if (oldEmpleadoOfHistoriaTrabajoCollectionHistoriaTrabajo != null) {
                    oldEmpleadoOfHistoriaTrabajoCollectionHistoriaTrabajo.getHistoriaTrabajoCollection().remove(historiaTrabajoCollectionHistoriaTrabajo);
                    oldEmpleadoOfHistoriaTrabajoCollectionHistoriaTrabajo = em.merge(oldEmpleadoOfHistoriaTrabajoCollectionHistoriaTrabajo);
                }
            }
            for (Documento documentoCollectionDocumento : empleado.getDocumentoCollection()) {
                Empleado oldEmpidOfDocumentoCollectionDocumento = documentoCollectionDocumento.getEmpid();
                documentoCollectionDocumento.setEmpid(empleado);
                documentoCollectionDocumento = em.merge(documentoCollectionDocumento);
                if (oldEmpidOfDocumentoCollectionDocumento != null) {
                    oldEmpidOfDocumentoCollectionDocumento.getDocumentoCollection().remove(documentoCollectionDocumento);
                    oldEmpidOfDocumentoCollectionDocumento = em.merge(oldEmpidOfDocumentoCollectionDocumento);
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

    public void edit(Empleado empleado) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Empleado persistentEmpleado = em.find(Empleado.class, empleado.getEmpid());
            TipoTrabajador ttidOld = persistentEmpleado.getTtid();
            TipoTrabajador ttidNew = empleado.getTtid();
            TituloProfesional tpidOld = persistentEmpleado.getTpid();
            TituloProfesional tpidNew = empleado.getTpid();
            Collection<HistoriaTrabajo> historiaTrabajoCollectionOld = persistentEmpleado.getHistoriaTrabajoCollection();
            Collection<HistoriaTrabajo> historiaTrabajoCollectionNew = empleado.getHistoriaTrabajoCollection();
            Collection<Documento> documentoCollectionOld = persistentEmpleado.getDocumentoCollection();
            Collection<Documento> documentoCollectionNew = empleado.getDocumentoCollection();
            List<String> illegalOrphanMessages = null;
            for (HistoriaTrabajo historiaTrabajoCollectionOldHistoriaTrabajo : historiaTrabajoCollectionOld) {
                if (!historiaTrabajoCollectionNew.contains(historiaTrabajoCollectionOldHistoriaTrabajo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain HistoriaTrabajo " + historiaTrabajoCollectionOldHistoriaTrabajo + " since its empleado field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (ttidNew != null) {
                ttidNew = em.getReference(ttidNew.getClass(), ttidNew.getTtid());
                empleado.setTtid(ttidNew);
            }
            if (tpidNew != null) {
                tpidNew = em.getReference(tpidNew.getClass(), tpidNew.getTpid());
                empleado.setTpid(tpidNew);
            }
            Collection<HistoriaTrabajo> attachedHistoriaTrabajoCollectionNew = new ArrayList<HistoriaTrabajo>();
            for (HistoriaTrabajo historiaTrabajoCollectionNewHistoriaTrabajoToAttach : historiaTrabajoCollectionNew) {
                historiaTrabajoCollectionNewHistoriaTrabajoToAttach = em.getReference(historiaTrabajoCollectionNewHistoriaTrabajoToAttach.getClass(), historiaTrabajoCollectionNewHistoriaTrabajoToAttach.getHistoriaTrabajoPK());
                attachedHistoriaTrabajoCollectionNew.add(historiaTrabajoCollectionNewHistoriaTrabajoToAttach);
            }
            historiaTrabajoCollectionNew = attachedHistoriaTrabajoCollectionNew;
            empleado.setHistoriaTrabajoCollection(historiaTrabajoCollectionNew);
            Collection<Documento> attachedDocumentoCollectionNew = new ArrayList<Documento>();
            for (Documento documentoCollectionNewDocumentoToAttach : documentoCollectionNew) {
                documentoCollectionNewDocumentoToAttach = em.getReference(documentoCollectionNewDocumentoToAttach.getClass(), documentoCollectionNewDocumentoToAttach.getDocid());
                attachedDocumentoCollectionNew.add(documentoCollectionNewDocumentoToAttach);
            }
            documentoCollectionNew = attachedDocumentoCollectionNew;
            empleado.setDocumentoCollection(documentoCollectionNew);
            empleado = em.merge(empleado);
            if (ttidOld != null && !ttidOld.equals(ttidNew)) {
                ttidOld.getEmpleadoCollection().remove(empleado);
                ttidOld = em.merge(ttidOld);
            }
            if (ttidNew != null && !ttidNew.equals(ttidOld)) {
                ttidNew.getEmpleadoCollection().add(empleado);
                ttidNew = em.merge(ttidNew);
            }
            if (tpidOld != null && !tpidOld.equals(tpidNew)) {
                tpidOld.getEmpleadoCollection().remove(empleado);
                tpidOld = em.merge(tpidOld);
            }
            if (tpidNew != null && !tpidNew.equals(tpidOld)) {
                tpidNew.getEmpleadoCollection().add(empleado);
                tpidNew = em.merge(tpidNew);
            }
            for (HistoriaTrabajo historiaTrabajoCollectionNewHistoriaTrabajo : historiaTrabajoCollectionNew) {
                if (!historiaTrabajoCollectionOld.contains(historiaTrabajoCollectionNewHistoriaTrabajo)) {
                    Empleado oldEmpleadoOfHistoriaTrabajoCollectionNewHistoriaTrabajo = historiaTrabajoCollectionNewHistoriaTrabajo.getEmpleado();
                    historiaTrabajoCollectionNewHistoriaTrabajo.setEmpleado(empleado);
                    historiaTrabajoCollectionNewHistoriaTrabajo = em.merge(historiaTrabajoCollectionNewHistoriaTrabajo);
                    if (oldEmpleadoOfHistoriaTrabajoCollectionNewHistoriaTrabajo != null && !oldEmpleadoOfHistoriaTrabajoCollectionNewHistoriaTrabajo.equals(empleado)) {
                        oldEmpleadoOfHistoriaTrabajoCollectionNewHistoriaTrabajo.getHistoriaTrabajoCollection().remove(historiaTrabajoCollectionNewHistoriaTrabajo);
                        oldEmpleadoOfHistoriaTrabajoCollectionNewHistoriaTrabajo = em.merge(oldEmpleadoOfHistoriaTrabajoCollectionNewHistoriaTrabajo);
                    }
                }
            }
            for (Documento documentoCollectionOldDocumento : documentoCollectionOld) {
                if (!documentoCollectionNew.contains(documentoCollectionOldDocumento)) {
                    documentoCollectionOldDocumento.setEmpid(null);
                    documentoCollectionOldDocumento = em.merge(documentoCollectionOldDocumento);
                }
            }
            for (Documento documentoCollectionNewDocumento : documentoCollectionNew) {
                if (!documentoCollectionOld.contains(documentoCollectionNewDocumento)) {
                    Empleado oldEmpidOfDocumentoCollectionNewDocumento = documentoCollectionNewDocumento.getEmpid();
                    documentoCollectionNewDocumento.setEmpid(empleado);
                    documentoCollectionNewDocumento = em.merge(documentoCollectionNewDocumento);
                    if (oldEmpidOfDocumentoCollectionNewDocumento != null && !oldEmpidOfDocumentoCollectionNewDocumento.equals(empleado)) {
                        oldEmpidOfDocumentoCollectionNewDocumento.getDocumentoCollection().remove(documentoCollectionNewDocumento);
                        oldEmpidOfDocumentoCollectionNewDocumento = em.merge(oldEmpidOfDocumentoCollectionNewDocumento);
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
                Integer id = empleado.getEmpid();
                if (findEmpleado(id) == null) {
                    throw new NonexistentEntityException("The empleado with id " + id + " no longer exists.");
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
            Empleado empleado;
            try {
                empleado = em.getReference(Empleado.class, id);
                empleado.getEmpid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The empleado with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<HistoriaTrabajo> historiaTrabajoCollectionOrphanCheck = empleado.getHistoriaTrabajoCollection();
            for (HistoriaTrabajo historiaTrabajoCollectionOrphanCheckHistoriaTrabajo : historiaTrabajoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Empleado (" + empleado + ") cannot be destroyed since the HistoriaTrabajo " + historiaTrabajoCollectionOrphanCheckHistoriaTrabajo + " in its historiaTrabajoCollection field has a non-nullable empleado field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            TipoTrabajador ttid = empleado.getTtid();
            if (ttid != null) {
                ttid.getEmpleadoCollection().remove(empleado);
                ttid = em.merge(ttid);
            }
            TituloProfesional tpid = empleado.getTpid();
            if (tpid != null) {
                tpid.getEmpleadoCollection().remove(empleado);
                tpid = em.merge(tpid);
            }
            Collection<Documento> documentoCollection = empleado.getDocumentoCollection();
            for (Documento documentoCollectionDocumento : documentoCollection) {
                documentoCollectionDocumento.setEmpid(null);
                documentoCollectionDocumento = em.merge(documentoCollectionDocumento);
            }
            em.remove(empleado);
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

    public List<Empleado> findEmpleadoEntities() {
        return findEmpleadoEntities(true, -1, -1);
    }

    public List<Empleado> findEmpleadoEntities(int maxResults, int firstResult) {
        return findEmpleadoEntities(false, maxResults, firstResult);
    }

    private List<Empleado> findEmpleadoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Empleado.class));
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

    public Empleado findEmpleado(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Empleado.class, id);
        } finally {
            em.close();
        }
    }

    public int getEmpleadoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Empleado> rt = cq.from(Empleado.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
