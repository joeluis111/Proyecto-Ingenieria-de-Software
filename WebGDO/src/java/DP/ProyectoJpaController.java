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
import MD.HistoriaTrabajo;
import MD.Documento;
import MD.HistoriaCliente;
import MD.HistoriaInventaria;
import MD.Calendario;
import MD.Calendario;
import MD.Documento;
import MD.HistoriaCliente;
import MD.HistoriaInventaria;
import MD.HistoriaTrabajo;
import MD.Inventario;
import MD.Inventario;
import MD.Proyecto;
import MD.Proyecto;
import MD.UsoPlaneado;
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
        if (proyecto.getHistoriaTrabajoCollection() == null) {
            proyecto.setHistoriaTrabajoCollection(new ArrayList<HistoriaTrabajo>());
        }
        if (proyecto.getDocumentoCollection() == null) {
            proyecto.setDocumentoCollection(new ArrayList<Documento>());
        }
        if (proyecto.getHistoriaClienteCollection() == null) {
            proyecto.setHistoriaClienteCollection(new ArrayList<HistoriaCliente>());
        }
        if (proyecto.getHistoriaInventariaCollection() == null) {
            proyecto.setHistoriaInventariaCollection(new ArrayList<HistoriaInventaria>());
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
            Collection<HistoriaTrabajo> attachedHistoriaTrabajoCollection = new ArrayList<HistoriaTrabajo>();
            for (HistoriaTrabajo historiaTrabajoCollectionHistoriaTrabajoToAttach : proyecto.getHistoriaTrabajoCollection()) {
                historiaTrabajoCollectionHistoriaTrabajoToAttach = em.getReference(historiaTrabajoCollectionHistoriaTrabajoToAttach.getClass(), historiaTrabajoCollectionHistoriaTrabajoToAttach.getHistoriaTrabajoPK());
                attachedHistoriaTrabajoCollection.add(historiaTrabajoCollectionHistoriaTrabajoToAttach);
            }
            proyecto.setHistoriaTrabajoCollection(attachedHistoriaTrabajoCollection);
            Collection<Documento> attachedDocumentoCollection = new ArrayList<Documento>();
            for (Documento documentoCollectionDocumentoToAttach : proyecto.getDocumentoCollection()) {
                documentoCollectionDocumentoToAttach = em.getReference(documentoCollectionDocumentoToAttach.getClass(), documentoCollectionDocumentoToAttach.getDocid());
                attachedDocumentoCollection.add(documentoCollectionDocumentoToAttach);
            }
            proyecto.setDocumentoCollection(attachedDocumentoCollection);
            Collection<HistoriaCliente> attachedHistoriaClienteCollection = new ArrayList<HistoriaCliente>();
            for (HistoriaCliente historiaClienteCollectionHistoriaClienteToAttach : proyecto.getHistoriaClienteCollection()) {
                historiaClienteCollectionHistoriaClienteToAttach = em.getReference(historiaClienteCollectionHistoriaClienteToAttach.getClass(), historiaClienteCollectionHistoriaClienteToAttach.getHistoriaClientePK());
                attachedHistoriaClienteCollection.add(historiaClienteCollectionHistoriaClienteToAttach);
            }
            proyecto.setHistoriaClienteCollection(attachedHistoriaClienteCollection);
            Collection<HistoriaInventaria> attachedHistoriaInventariaCollection = new ArrayList<HistoriaInventaria>();
            for (HistoriaInventaria historiaInventariaCollectionHistoriaInventariaToAttach : proyecto.getHistoriaInventariaCollection()) {
                historiaInventariaCollectionHistoriaInventariaToAttach = em.getReference(historiaInventariaCollectionHistoriaInventariaToAttach.getClass(), historiaInventariaCollectionHistoriaInventariaToAttach.getHistoriaInventariaPK());
                attachedHistoriaInventariaCollection.add(historiaInventariaCollectionHistoriaInventariaToAttach);
            }
            proyecto.setHistoriaInventariaCollection(attachedHistoriaInventariaCollection);
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
            for (HistoriaTrabajo historiaTrabajoCollectionHistoriaTrabajo : proyecto.getHistoriaTrabajoCollection()) {
                Proyecto oldProyectoOfHistoriaTrabajoCollectionHistoriaTrabajo = historiaTrabajoCollectionHistoriaTrabajo.getProyecto();
                historiaTrabajoCollectionHistoriaTrabajo.setProyecto(proyecto);
                historiaTrabajoCollectionHistoriaTrabajo = em.merge(historiaTrabajoCollectionHistoriaTrabajo);
                if (oldProyectoOfHistoriaTrabajoCollectionHistoriaTrabajo != null) {
                    oldProyectoOfHistoriaTrabajoCollectionHistoriaTrabajo.getHistoriaTrabajoCollection().remove(historiaTrabajoCollectionHistoriaTrabajo);
                    oldProyectoOfHistoriaTrabajoCollectionHistoriaTrabajo = em.merge(oldProyectoOfHistoriaTrabajoCollectionHistoriaTrabajo);
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
            for (HistoriaCliente historiaClienteCollectionHistoriaCliente : proyecto.getHistoriaClienteCollection()) {
                Proyecto oldProyectoOfHistoriaClienteCollectionHistoriaCliente = historiaClienteCollectionHistoriaCliente.getProyecto();
                historiaClienteCollectionHistoriaCliente.setProyecto(proyecto);
                historiaClienteCollectionHistoriaCliente = em.merge(historiaClienteCollectionHistoriaCliente);
                if (oldProyectoOfHistoriaClienteCollectionHistoriaCliente != null) {
                    oldProyectoOfHistoriaClienteCollectionHistoriaCliente.getHistoriaClienteCollection().remove(historiaClienteCollectionHistoriaCliente);
                    oldProyectoOfHistoriaClienteCollectionHistoriaCliente = em.merge(oldProyectoOfHistoriaClienteCollectionHistoriaCliente);
                }
            }
            for (HistoriaInventaria historiaInventariaCollectionHistoriaInventaria : proyecto.getHistoriaInventariaCollection()) {
                Proyecto oldProyectoOfHistoriaInventariaCollectionHistoriaInventaria = historiaInventariaCollectionHistoriaInventaria.getProyecto();
                historiaInventariaCollectionHistoriaInventaria.setProyecto(proyecto);
                historiaInventariaCollectionHistoriaInventaria = em.merge(historiaInventariaCollectionHistoriaInventaria);
                if (oldProyectoOfHistoriaInventariaCollectionHistoriaInventaria != null) {
                    oldProyectoOfHistoriaInventariaCollectionHistoriaInventaria.getHistoriaInventariaCollection().remove(historiaInventariaCollectionHistoriaInventaria);
                    oldProyectoOfHistoriaInventariaCollectionHistoriaInventaria = em.merge(oldProyectoOfHistoriaInventariaCollectionHistoriaInventaria);
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
            Collection<HistoriaTrabajo> historiaTrabajoCollectionOld = persistentProyecto.getHistoriaTrabajoCollection();
            Collection<HistoriaTrabajo> historiaTrabajoCollectionNew = proyecto.getHistoriaTrabajoCollection();
            Collection<Documento> documentoCollectionOld = persistentProyecto.getDocumentoCollection();
            Collection<Documento> documentoCollectionNew = proyecto.getDocumentoCollection();
            Collection<HistoriaCliente> historiaClienteCollectionOld = persistentProyecto.getHistoriaClienteCollection();
            Collection<HistoriaCliente> historiaClienteCollectionNew = proyecto.getHistoriaClienteCollection();
            Collection<HistoriaInventaria> historiaInventariaCollectionOld = persistentProyecto.getHistoriaInventariaCollection();
            Collection<HistoriaInventaria> historiaInventariaCollectionNew = proyecto.getHistoriaInventariaCollection();
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
            for (HistoriaTrabajo historiaTrabajoCollectionOldHistoriaTrabajo : historiaTrabajoCollectionOld) {
                if (!historiaTrabajoCollectionNew.contains(historiaTrabajoCollectionOldHistoriaTrabajo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain HistoriaTrabajo " + historiaTrabajoCollectionOldHistoriaTrabajo + " since its proyecto field is not nullable.");
                }
            }
            for (HistoriaCliente historiaClienteCollectionOldHistoriaCliente : historiaClienteCollectionOld) {
                if (!historiaClienteCollectionNew.contains(historiaClienteCollectionOldHistoriaCliente)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain HistoriaCliente " + historiaClienteCollectionOldHistoriaCliente + " since its proyecto field is not nullable.");
                }
            }
            for (HistoriaInventaria historiaInventariaCollectionOldHistoriaInventaria : historiaInventariaCollectionOld) {
                if (!historiaInventariaCollectionNew.contains(historiaInventariaCollectionOldHistoriaInventaria)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain HistoriaInventaria " + historiaInventariaCollectionOldHistoriaInventaria + " since its proyecto field is not nullable.");
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
            Collection<HistoriaTrabajo> attachedHistoriaTrabajoCollectionNew = new ArrayList<HistoriaTrabajo>();
            for (HistoriaTrabajo historiaTrabajoCollectionNewHistoriaTrabajoToAttach : historiaTrabajoCollectionNew) {
                historiaTrabajoCollectionNewHistoriaTrabajoToAttach = em.getReference(historiaTrabajoCollectionNewHistoriaTrabajoToAttach.getClass(), historiaTrabajoCollectionNewHistoriaTrabajoToAttach.getHistoriaTrabajoPK());
                attachedHistoriaTrabajoCollectionNew.add(historiaTrabajoCollectionNewHistoriaTrabajoToAttach);
            }
            historiaTrabajoCollectionNew = attachedHistoriaTrabajoCollectionNew;
            proyecto.setHistoriaTrabajoCollection(historiaTrabajoCollectionNew);
            Collection<Documento> attachedDocumentoCollectionNew = new ArrayList<Documento>();
            for (Documento documentoCollectionNewDocumentoToAttach : documentoCollectionNew) {
                documentoCollectionNewDocumentoToAttach = em.getReference(documentoCollectionNewDocumentoToAttach.getClass(), documentoCollectionNewDocumentoToAttach.getDocid());
                attachedDocumentoCollectionNew.add(documentoCollectionNewDocumentoToAttach);
            }
            documentoCollectionNew = attachedDocumentoCollectionNew;
            proyecto.setDocumentoCollection(documentoCollectionNew);
            Collection<HistoriaCliente> attachedHistoriaClienteCollectionNew = new ArrayList<HistoriaCliente>();
            for (HistoriaCliente historiaClienteCollectionNewHistoriaClienteToAttach : historiaClienteCollectionNew) {
                historiaClienteCollectionNewHistoriaClienteToAttach = em.getReference(historiaClienteCollectionNewHistoriaClienteToAttach.getClass(), historiaClienteCollectionNewHistoriaClienteToAttach.getHistoriaClientePK());
                attachedHistoriaClienteCollectionNew.add(historiaClienteCollectionNewHistoriaClienteToAttach);
            }
            historiaClienteCollectionNew = attachedHistoriaClienteCollectionNew;
            proyecto.setHistoriaClienteCollection(historiaClienteCollectionNew);
            Collection<HistoriaInventaria> attachedHistoriaInventariaCollectionNew = new ArrayList<HistoriaInventaria>();
            for (HistoriaInventaria historiaInventariaCollectionNewHistoriaInventariaToAttach : historiaInventariaCollectionNew) {
                historiaInventariaCollectionNewHistoriaInventariaToAttach = em.getReference(historiaInventariaCollectionNewHistoriaInventariaToAttach.getClass(), historiaInventariaCollectionNewHistoriaInventariaToAttach.getHistoriaInventariaPK());
                attachedHistoriaInventariaCollectionNew.add(historiaInventariaCollectionNewHistoriaInventariaToAttach);
            }
            historiaInventariaCollectionNew = attachedHistoriaInventariaCollectionNew;
            proyecto.setHistoriaInventariaCollection(historiaInventariaCollectionNew);
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
            for (HistoriaTrabajo historiaTrabajoCollectionNewHistoriaTrabajo : historiaTrabajoCollectionNew) {
                if (!historiaTrabajoCollectionOld.contains(historiaTrabajoCollectionNewHistoriaTrabajo)) {
                    Proyecto oldProyectoOfHistoriaTrabajoCollectionNewHistoriaTrabajo = historiaTrabajoCollectionNewHistoriaTrabajo.getProyecto();
                    historiaTrabajoCollectionNewHistoriaTrabajo.setProyecto(proyecto);
                    historiaTrabajoCollectionNewHistoriaTrabajo = em.merge(historiaTrabajoCollectionNewHistoriaTrabajo);
                    if (oldProyectoOfHistoriaTrabajoCollectionNewHistoriaTrabajo != null && !oldProyectoOfHistoriaTrabajoCollectionNewHistoriaTrabajo.equals(proyecto)) {
                        oldProyectoOfHistoriaTrabajoCollectionNewHistoriaTrabajo.getHistoriaTrabajoCollection().remove(historiaTrabajoCollectionNewHistoriaTrabajo);
                        oldProyectoOfHistoriaTrabajoCollectionNewHistoriaTrabajo = em.merge(oldProyectoOfHistoriaTrabajoCollectionNewHistoriaTrabajo);
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
            for (HistoriaCliente historiaClienteCollectionNewHistoriaCliente : historiaClienteCollectionNew) {
                if (!historiaClienteCollectionOld.contains(historiaClienteCollectionNewHistoriaCliente)) {
                    Proyecto oldProyectoOfHistoriaClienteCollectionNewHistoriaCliente = historiaClienteCollectionNewHistoriaCliente.getProyecto();
                    historiaClienteCollectionNewHistoriaCliente.setProyecto(proyecto);
                    historiaClienteCollectionNewHistoriaCliente = em.merge(historiaClienteCollectionNewHistoriaCliente);
                    if (oldProyectoOfHistoriaClienteCollectionNewHistoriaCliente != null && !oldProyectoOfHistoriaClienteCollectionNewHistoriaCliente.equals(proyecto)) {
                        oldProyectoOfHistoriaClienteCollectionNewHistoriaCliente.getHistoriaClienteCollection().remove(historiaClienteCollectionNewHistoriaCliente);
                        oldProyectoOfHistoriaClienteCollectionNewHistoriaCliente = em.merge(oldProyectoOfHistoriaClienteCollectionNewHistoriaCliente);
                    }
                }
            }
            for (HistoriaInventaria historiaInventariaCollectionNewHistoriaInventaria : historiaInventariaCollectionNew) {
                if (!historiaInventariaCollectionOld.contains(historiaInventariaCollectionNewHistoriaInventaria)) {
                    Proyecto oldProyectoOfHistoriaInventariaCollectionNewHistoriaInventaria = historiaInventariaCollectionNewHistoriaInventaria.getProyecto();
                    historiaInventariaCollectionNewHistoriaInventaria.setProyecto(proyecto);
                    historiaInventariaCollectionNewHistoriaInventaria = em.merge(historiaInventariaCollectionNewHistoriaInventaria);
                    if (oldProyectoOfHistoriaInventariaCollectionNewHistoriaInventaria != null && !oldProyectoOfHistoriaInventariaCollectionNewHistoriaInventaria.equals(proyecto)) {
                        oldProyectoOfHistoriaInventariaCollectionNewHistoriaInventaria.getHistoriaInventariaCollection().remove(historiaInventariaCollectionNewHistoriaInventaria);
                        oldProyectoOfHistoriaInventariaCollectionNewHistoriaInventaria = em.merge(oldProyectoOfHistoriaInventariaCollectionNewHistoriaInventaria);
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
            Collection<HistoriaTrabajo> historiaTrabajoCollectionOrphanCheck = proyecto.getHistoriaTrabajoCollection();
            for (HistoriaTrabajo historiaTrabajoCollectionOrphanCheckHistoriaTrabajo : historiaTrabajoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Proyecto (" + proyecto + ") cannot be destroyed since the HistoriaTrabajo " + historiaTrabajoCollectionOrphanCheckHistoriaTrabajo + " in its historiaTrabajoCollection field has a non-nullable proyecto field.");
            }
            Collection<HistoriaCliente> historiaClienteCollectionOrphanCheck = proyecto.getHistoriaClienteCollection();
            for (HistoriaCliente historiaClienteCollectionOrphanCheckHistoriaCliente : historiaClienteCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Proyecto (" + proyecto + ") cannot be destroyed since the HistoriaCliente " + historiaClienteCollectionOrphanCheckHistoriaCliente + " in its historiaClienteCollection field has a non-nullable proyecto field.");
            }
            Collection<HistoriaInventaria> historiaInventariaCollectionOrphanCheck = proyecto.getHistoriaInventariaCollection();
            for (HistoriaInventaria historiaInventariaCollectionOrphanCheckHistoriaInventaria : historiaInventariaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Proyecto (" + proyecto + ") cannot be destroyed since the HistoriaInventaria " + historiaInventariaCollectionOrphanCheckHistoriaInventaria + " in its historiaInventariaCollection field has a non-nullable proyecto field.");
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
