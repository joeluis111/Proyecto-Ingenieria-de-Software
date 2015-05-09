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
import MD.UsoPlaneado;
import java.util.ArrayList;
import java.util.Collection;
import MD.Documento;
import MD.Calendario;
import MD.Inventario;
import MD.Proyecto;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author Kenny
 */
public class ProyectoJpaController implements Serializable {

    public ProyectoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Proyecto proyecto) throws RollbackFailureException, Exception {
        if (proyecto.getUsoPlaneadoCollection() == null) {
            proyecto.setUsoPlaneadoCollection(new ArrayList<UsoPlaneado>());
        }
        if (proyecto.getDocumentoCollection() == null) {
            proyecto.setDocumentoCollection(new ArrayList<Documento>());
        }
        if (proyecto.getCalendarioCollection() == null) {
            proyecto.setCalendarioCollection(new ArrayList<Calendario>());
        }
        if (proyecto.getInventarioCollection() == null) {
            proyecto.setInventarioCollection(new ArrayList<Inventario>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<UsoPlaneado> attachedUsoPlaneadoCollection = new ArrayList<UsoPlaneado>();
            for (UsoPlaneado usoPlaneadoCollectionUsoPlaneadoToAttach : proyecto.getUsoPlaneadoCollection()) {
                usoPlaneadoCollectionUsoPlaneadoToAttach = em.getReference(usoPlaneadoCollectionUsoPlaneadoToAttach.getClass(), usoPlaneadoCollectionUsoPlaneadoToAttach.getUsoPlaneadoPK());
                attachedUsoPlaneadoCollection.add(usoPlaneadoCollectionUsoPlaneadoToAttach);
            }
            proyecto.setUsoPlaneadoCollection(attachedUsoPlaneadoCollection);
            Collection<Documento> attachedDocumentoCollection = new ArrayList<Documento>();
            for (Documento documentoCollectionDocumentoToAttach : proyecto.getDocumentoCollection()) {
                documentoCollectionDocumentoToAttach = em.getReference(documentoCollectionDocumentoToAttach.getClass(), documentoCollectionDocumentoToAttach.getDocid());
                attachedDocumentoCollection.add(documentoCollectionDocumentoToAttach);
            }
            proyecto.setDocumentoCollection(attachedDocumentoCollection);
            Collection<Calendario> attachedCalendarioCollection = new ArrayList<Calendario>();
            for (Calendario calendarioCollectionCalendarioToAttach : proyecto.getCalendarioCollection()) {
                calendarioCollectionCalendarioToAttach = em.getReference(calendarioCollectionCalendarioToAttach.getClass(), calendarioCollectionCalendarioToAttach.getCalid());
                attachedCalendarioCollection.add(calendarioCollectionCalendarioToAttach);
            }
            proyecto.setCalendarioCollection(attachedCalendarioCollection);
            Collection<Inventario> attachedInventarioCollection = new ArrayList<Inventario>();
            for (Inventario inventarioCollectionInventarioToAttach : proyecto.getInventarioCollection()) {
                inventarioCollectionInventarioToAttach = em.getReference(inventarioCollectionInventarioToAttach.getClass(), inventarioCollectionInventarioToAttach.getInventarioPK());
                attachedInventarioCollection.add(inventarioCollectionInventarioToAttach);
            }
            proyecto.setInventarioCollection(attachedInventarioCollection);
            em.persist(proyecto);
            for (UsoPlaneado usoPlaneadoCollectionUsoPlaneado : proyecto.getUsoPlaneadoCollection()) {
                Proyecto oldProyectoOfUsoPlaneadoCollectionUsoPlaneado = usoPlaneadoCollectionUsoPlaneado.getProyecto();
                usoPlaneadoCollectionUsoPlaneado.setProyecto(proyecto);
                usoPlaneadoCollectionUsoPlaneado = em.merge(usoPlaneadoCollectionUsoPlaneado);
                if (oldProyectoOfUsoPlaneadoCollectionUsoPlaneado != null) {
                    oldProyectoOfUsoPlaneadoCollectionUsoPlaneado.getUsoPlaneadoCollection().remove(usoPlaneadoCollectionUsoPlaneado);
                    oldProyectoOfUsoPlaneadoCollectionUsoPlaneado = em.merge(oldProyectoOfUsoPlaneadoCollectionUsoPlaneado);
                }
            }
            for (Documento documentoCollectionDocumento : proyecto.getDocumentoCollection()) {
                Proyecto oldProidOfDocumentoCollectionDocumento = documentoCollectionDocumento.getProid();
                documentoCollectionDocumento.setProid(proyecto);
                documentoCollectionDocumento = em.merge(documentoCollectionDocumento);
                if (oldProidOfDocumentoCollectionDocumento != null) {
                    oldProidOfDocumentoCollectionDocumento.getDocumentoCollection().remove(documentoCollectionDocumento);
                    oldProidOfDocumentoCollectionDocumento = em.merge(oldProidOfDocumentoCollectionDocumento);
                }
            }
            for (Calendario calendarioCollectionCalendario : proyecto.getCalendarioCollection()) {
                Proyecto oldProidOfCalendarioCollectionCalendario = calendarioCollectionCalendario.getProid();
                calendarioCollectionCalendario.setProid(proyecto);
                calendarioCollectionCalendario = em.merge(calendarioCollectionCalendario);
                if (oldProidOfCalendarioCollectionCalendario != null) {
                    oldProidOfCalendarioCollectionCalendario.getCalendarioCollection().remove(calendarioCollectionCalendario);
                    oldProidOfCalendarioCollectionCalendario = em.merge(oldProidOfCalendarioCollectionCalendario);
                }
            }
            for (Inventario inventarioCollectionInventario : proyecto.getInventarioCollection()) {
                Proyecto oldProyectoOfInventarioCollectionInventario = inventarioCollectionInventario.getProyecto();
                inventarioCollectionInventario.setProyecto(proyecto);
                inventarioCollectionInventario = em.merge(inventarioCollectionInventario);
                if (oldProyectoOfInventarioCollectionInventario != null) {
                    oldProyectoOfInventarioCollectionInventario.getInventarioCollection().remove(inventarioCollectionInventario);
                    oldProyectoOfInventarioCollectionInventario = em.merge(oldProyectoOfInventarioCollectionInventario);
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

    public void edit(Proyecto proyecto) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Proyecto persistentProyecto = em.find(Proyecto.class, proyecto.getProid());
            Collection<UsoPlaneado> usoPlaneadoCollectionOld = persistentProyecto.getUsoPlaneadoCollection();
            Collection<UsoPlaneado> usoPlaneadoCollectionNew = proyecto.getUsoPlaneadoCollection();
            Collection<Documento> documentoCollectionOld = persistentProyecto.getDocumentoCollection();
            Collection<Documento> documentoCollectionNew = proyecto.getDocumentoCollection();
            Collection<Calendario> calendarioCollectionOld = persistentProyecto.getCalendarioCollection();
            Collection<Calendario> calendarioCollectionNew = proyecto.getCalendarioCollection();
            Collection<Inventario> inventarioCollectionOld = persistentProyecto.getInventarioCollection();
            Collection<Inventario> inventarioCollectionNew = proyecto.getInventarioCollection();
            List<String> illegalOrphanMessages = null;
            for (UsoPlaneado usoPlaneadoCollectionOldUsoPlaneado : usoPlaneadoCollectionOld) {
                if (!usoPlaneadoCollectionNew.contains(usoPlaneadoCollectionOldUsoPlaneado)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain UsoPlaneado " + usoPlaneadoCollectionOldUsoPlaneado + " since its proyecto field is not nullable.");
                }
            }
            for (Calendario calendarioCollectionOldCalendario : calendarioCollectionOld) {
                if (!calendarioCollectionNew.contains(calendarioCollectionOldCalendario)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Calendario " + calendarioCollectionOldCalendario + " since its proid field is not nullable.");
                }
            }
            for (Inventario inventarioCollectionOldInventario : inventarioCollectionOld) {
                if (!inventarioCollectionNew.contains(inventarioCollectionOldInventario)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Inventario " + inventarioCollectionOldInventario + " since its proyecto field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<UsoPlaneado> attachedUsoPlaneadoCollectionNew = new ArrayList<UsoPlaneado>();
            for (UsoPlaneado usoPlaneadoCollectionNewUsoPlaneadoToAttach : usoPlaneadoCollectionNew) {
                usoPlaneadoCollectionNewUsoPlaneadoToAttach = em.getReference(usoPlaneadoCollectionNewUsoPlaneadoToAttach.getClass(), usoPlaneadoCollectionNewUsoPlaneadoToAttach.getUsoPlaneadoPK());
                attachedUsoPlaneadoCollectionNew.add(usoPlaneadoCollectionNewUsoPlaneadoToAttach);
            }
            usoPlaneadoCollectionNew = attachedUsoPlaneadoCollectionNew;
            proyecto.setUsoPlaneadoCollection(usoPlaneadoCollectionNew);
            Collection<Documento> attachedDocumentoCollectionNew = new ArrayList<Documento>();
            for (Documento documentoCollectionNewDocumentoToAttach : documentoCollectionNew) {
                documentoCollectionNewDocumentoToAttach = em.getReference(documentoCollectionNewDocumentoToAttach.getClass(), documentoCollectionNewDocumentoToAttach.getDocid());
                attachedDocumentoCollectionNew.add(documentoCollectionNewDocumentoToAttach);
            }
            documentoCollectionNew = attachedDocumentoCollectionNew;
            proyecto.setDocumentoCollection(documentoCollectionNew);
            Collection<Calendario> attachedCalendarioCollectionNew = new ArrayList<Calendario>();
            for (Calendario calendarioCollectionNewCalendarioToAttach : calendarioCollectionNew) {
                calendarioCollectionNewCalendarioToAttach = em.getReference(calendarioCollectionNewCalendarioToAttach.getClass(), calendarioCollectionNewCalendarioToAttach.getCalid());
                attachedCalendarioCollectionNew.add(calendarioCollectionNewCalendarioToAttach);
            }
            calendarioCollectionNew = attachedCalendarioCollectionNew;
            proyecto.setCalendarioCollection(calendarioCollectionNew);
            Collection<Inventario> attachedInventarioCollectionNew = new ArrayList<Inventario>();
            for (Inventario inventarioCollectionNewInventarioToAttach : inventarioCollectionNew) {
                inventarioCollectionNewInventarioToAttach = em.getReference(inventarioCollectionNewInventarioToAttach.getClass(), inventarioCollectionNewInventarioToAttach.getInventarioPK());
                attachedInventarioCollectionNew.add(inventarioCollectionNewInventarioToAttach);
            }
            inventarioCollectionNew = attachedInventarioCollectionNew;
            proyecto.setInventarioCollection(inventarioCollectionNew);
            proyecto = em.merge(proyecto);
            for (UsoPlaneado usoPlaneadoCollectionNewUsoPlaneado : usoPlaneadoCollectionNew) {
                if (!usoPlaneadoCollectionOld.contains(usoPlaneadoCollectionNewUsoPlaneado)) {
                    Proyecto oldProyectoOfUsoPlaneadoCollectionNewUsoPlaneado = usoPlaneadoCollectionNewUsoPlaneado.getProyecto();
                    usoPlaneadoCollectionNewUsoPlaneado.setProyecto(proyecto);
                    usoPlaneadoCollectionNewUsoPlaneado = em.merge(usoPlaneadoCollectionNewUsoPlaneado);
                    if (oldProyectoOfUsoPlaneadoCollectionNewUsoPlaneado != null && !oldProyectoOfUsoPlaneadoCollectionNewUsoPlaneado.equals(proyecto)) {
                        oldProyectoOfUsoPlaneadoCollectionNewUsoPlaneado.getUsoPlaneadoCollection().remove(usoPlaneadoCollectionNewUsoPlaneado);
                        oldProyectoOfUsoPlaneadoCollectionNewUsoPlaneado = em.merge(oldProyectoOfUsoPlaneadoCollectionNewUsoPlaneado);
                    }
                }
            }
            for (Documento documentoCollectionOldDocumento : documentoCollectionOld) {
                if (!documentoCollectionNew.contains(documentoCollectionOldDocumento)) {
                    documentoCollectionOldDocumento.setProid(null);
                    documentoCollectionOldDocumento = em.merge(documentoCollectionOldDocumento);
                }
            }
            for (Documento documentoCollectionNewDocumento : documentoCollectionNew) {
                if (!documentoCollectionOld.contains(documentoCollectionNewDocumento)) {
                    Proyecto oldProidOfDocumentoCollectionNewDocumento = documentoCollectionNewDocumento.getProid();
                    documentoCollectionNewDocumento.setProid(proyecto);
                    documentoCollectionNewDocumento = em.merge(documentoCollectionNewDocumento);
                    if (oldProidOfDocumentoCollectionNewDocumento != null && !oldProidOfDocumentoCollectionNewDocumento.equals(proyecto)) {
                        oldProidOfDocumentoCollectionNewDocumento.getDocumentoCollection().remove(documentoCollectionNewDocumento);
                        oldProidOfDocumentoCollectionNewDocumento = em.merge(oldProidOfDocumentoCollectionNewDocumento);
                    }
                }
            }
            for (Calendario calendarioCollectionNewCalendario : calendarioCollectionNew) {
                if (!calendarioCollectionOld.contains(calendarioCollectionNewCalendario)) {
                    Proyecto oldProidOfCalendarioCollectionNewCalendario = calendarioCollectionNewCalendario.getProid();
                    calendarioCollectionNewCalendario.setProid(proyecto);
                    calendarioCollectionNewCalendario = em.merge(calendarioCollectionNewCalendario);
                    if (oldProidOfCalendarioCollectionNewCalendario != null && !oldProidOfCalendarioCollectionNewCalendario.equals(proyecto)) {
                        oldProidOfCalendarioCollectionNewCalendario.getCalendarioCollection().remove(calendarioCollectionNewCalendario);
                        oldProidOfCalendarioCollectionNewCalendario = em.merge(oldProidOfCalendarioCollectionNewCalendario);
                    }
                }
            }
            for (Inventario inventarioCollectionNewInventario : inventarioCollectionNew) {
                if (!inventarioCollectionOld.contains(inventarioCollectionNewInventario)) {
                    Proyecto oldProyectoOfInventarioCollectionNewInventario = inventarioCollectionNewInventario.getProyecto();
                    inventarioCollectionNewInventario.setProyecto(proyecto);
                    inventarioCollectionNewInventario = em.merge(inventarioCollectionNewInventario);
                    if (oldProyectoOfInventarioCollectionNewInventario != null && !oldProyectoOfInventarioCollectionNewInventario.equals(proyecto)) {
                        oldProyectoOfInventarioCollectionNewInventario.getInventarioCollection().remove(inventarioCollectionNewInventario);
                        oldProyectoOfInventarioCollectionNewInventario = em.merge(oldProyectoOfInventarioCollectionNewInventario);
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
                Integer id = proyecto.getProid();
                if (findProyecto(id) == null) {
                    throw new NonexistentEntityException("The proyecto with id " + id + " no longer exists.");
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
            Proyecto proyecto;
            try {
                proyecto = em.getReference(Proyecto.class, id);
                proyecto.getProid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The proyecto with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<UsoPlaneado> usoPlaneadoCollectionOrphanCheck = proyecto.getUsoPlaneadoCollection();
            for (UsoPlaneado usoPlaneadoCollectionOrphanCheckUsoPlaneado : usoPlaneadoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Proyecto (" + proyecto + ") cannot be destroyed since the UsoPlaneado " + usoPlaneadoCollectionOrphanCheckUsoPlaneado + " in its usoPlaneadoCollection field has a non-nullable proyecto field.");
            }
            Collection<Calendario> calendarioCollectionOrphanCheck = proyecto.getCalendarioCollection();
            for (Calendario calendarioCollectionOrphanCheckCalendario : calendarioCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Proyecto (" + proyecto + ") cannot be destroyed since the Calendario " + calendarioCollectionOrphanCheckCalendario + " in its calendarioCollection field has a non-nullable proid field.");
            }
            Collection<Inventario> inventarioCollectionOrphanCheck = proyecto.getInventarioCollection();
            for (Inventario inventarioCollectionOrphanCheckInventario : inventarioCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Proyecto (" + proyecto + ") cannot be destroyed since the Inventario " + inventarioCollectionOrphanCheckInventario + " in its inventarioCollection field has a non-nullable proyecto field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Documento> documentoCollection = proyecto.getDocumentoCollection();
            for (Documento documentoCollectionDocumento : documentoCollection) {
                documentoCollectionDocumento.setProid(null);
                documentoCollectionDocumento = em.merge(documentoCollectionDocumento);
            }
            em.remove(proyecto);
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

    public List<Proyecto> findProyectoEntities() {
        return findProyectoEntities(true, -1, -1);
    }

    public List<Proyecto> findProyectoEntities(int maxResults, int firstResult) {
        return findProyectoEntities(false, maxResults, firstResult);
    }

    private List<Proyecto> findProyectoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Proyecto.class));
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

    public Proyecto findProyecto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Proyecto.class, id);
        } finally {
            em.close();
        }
    }

    public int getProyectoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Proyecto> rt = cq.from(Proyecto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
