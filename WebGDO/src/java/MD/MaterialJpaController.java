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
import MD.Unidad;
import MD.TipoMaterial;
import MD.Proveedor;
import java.util.ArrayList;
import java.util.Collection;
import MD.UsoPlaneado;
import MD.HistoriaInventaria;
import MD.Inventario;
import MD.Material;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author Kenny
 */
public class MaterialJpaController implements Serializable {

    public MaterialJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Material material) throws RollbackFailureException, Exception {
        if (material.getProveedorCollection() == null) {
            material.setProveedorCollection(new ArrayList<Proveedor>());
        }
        if (material.getUsoPlaneadoCollection() == null) {
            material.setUsoPlaneadoCollection(new ArrayList<UsoPlaneado>());
        }
        if (material.getHistoriaInventariaCollection() == null) {
            material.setHistoriaInventariaCollection(new ArrayList<HistoriaInventaria>());
        }
        if (material.getInventarioCollection() == null) {
            material.setInventarioCollection(new ArrayList<Inventario>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Unidad unid = material.getUnid();
            if (unid != null) {
                unid = em.getReference(unid.getClass(), unid.getUnid());
                material.setUnid(unid);
            }
            TipoMaterial tmid = material.getTmid();
            if (tmid != null) {
                tmid = em.getReference(tmid.getClass(), tmid.getTmid());
                material.setTmid(tmid);
            }
            Collection<Proveedor> attachedProveedorCollection = new ArrayList<Proveedor>();
            for (Proveedor proveedorCollectionProveedorToAttach : material.getProveedorCollection()) {
                proveedorCollectionProveedorToAttach = em.getReference(proveedorCollectionProveedorToAttach.getClass(), proveedorCollectionProveedorToAttach.getProvid());
                attachedProveedorCollection.add(proveedorCollectionProveedorToAttach);
            }
            material.setProveedorCollection(attachedProveedorCollection);
            Collection<UsoPlaneado> attachedUsoPlaneadoCollection = new ArrayList<UsoPlaneado>();
            for (UsoPlaneado usoPlaneadoCollectionUsoPlaneadoToAttach : material.getUsoPlaneadoCollection()) {
                usoPlaneadoCollectionUsoPlaneadoToAttach = em.getReference(usoPlaneadoCollectionUsoPlaneadoToAttach.getClass(), usoPlaneadoCollectionUsoPlaneadoToAttach.getUsoPlaneadoPK());
                attachedUsoPlaneadoCollection.add(usoPlaneadoCollectionUsoPlaneadoToAttach);
            }
            material.setUsoPlaneadoCollection(attachedUsoPlaneadoCollection);
            Collection<HistoriaInventaria> attachedHistoriaInventariaCollection = new ArrayList<HistoriaInventaria>();
            for (HistoriaInventaria historiaInventariaCollectionHistoriaInventariaToAttach : material.getHistoriaInventariaCollection()) {
                historiaInventariaCollectionHistoriaInventariaToAttach = em.getReference(historiaInventariaCollectionHistoriaInventariaToAttach.getClass(), historiaInventariaCollectionHistoriaInventariaToAttach.getHistoriaInventariaPK());
                attachedHistoriaInventariaCollection.add(historiaInventariaCollectionHistoriaInventariaToAttach);
            }
            material.setHistoriaInventariaCollection(attachedHistoriaInventariaCollection);
            Collection<Inventario> attachedInventarioCollection = new ArrayList<Inventario>();
            for (Inventario inventarioCollectionInventarioToAttach : material.getInventarioCollection()) {
                inventarioCollectionInventarioToAttach = em.getReference(inventarioCollectionInventarioToAttach.getClass(), inventarioCollectionInventarioToAttach.getInventarioPK());
                attachedInventarioCollection.add(inventarioCollectionInventarioToAttach);
            }
            material.setInventarioCollection(attachedInventarioCollection);
            em.persist(material);
            if (unid != null) {
                unid.getMaterialCollection().add(material);
                unid = em.merge(unid);
            }
            if (tmid != null) {
                tmid.getMaterialCollection().add(material);
                tmid = em.merge(tmid);
            }
            for (Proveedor proveedorCollectionProveedor : material.getProveedorCollection()) {
                proveedorCollectionProveedor.getMaterialCollection().add(material);
                proveedorCollectionProveedor = em.merge(proveedorCollectionProveedor);
            }
            for (UsoPlaneado usoPlaneadoCollectionUsoPlaneado : material.getUsoPlaneadoCollection()) {
                Material oldMaterialOfUsoPlaneadoCollectionUsoPlaneado = usoPlaneadoCollectionUsoPlaneado.getMaterial();
                usoPlaneadoCollectionUsoPlaneado.setMaterial(material);
                usoPlaneadoCollectionUsoPlaneado = em.merge(usoPlaneadoCollectionUsoPlaneado);
                if (oldMaterialOfUsoPlaneadoCollectionUsoPlaneado != null) {
                    oldMaterialOfUsoPlaneadoCollectionUsoPlaneado.getUsoPlaneadoCollection().remove(usoPlaneadoCollectionUsoPlaneado);
                    oldMaterialOfUsoPlaneadoCollectionUsoPlaneado = em.merge(oldMaterialOfUsoPlaneadoCollectionUsoPlaneado);
                }
            }
            for (HistoriaInventaria historiaInventariaCollectionHistoriaInventaria : material.getHistoriaInventariaCollection()) {
                Material oldMaterialOfHistoriaInventariaCollectionHistoriaInventaria = historiaInventariaCollectionHistoriaInventaria.getMaterial();
                historiaInventariaCollectionHistoriaInventaria.setMaterial(material);
                historiaInventariaCollectionHistoriaInventaria = em.merge(historiaInventariaCollectionHistoriaInventaria);
                if (oldMaterialOfHistoriaInventariaCollectionHistoriaInventaria != null) {
                    oldMaterialOfHistoriaInventariaCollectionHistoriaInventaria.getHistoriaInventariaCollection().remove(historiaInventariaCollectionHistoriaInventaria);
                    oldMaterialOfHistoriaInventariaCollectionHistoriaInventaria = em.merge(oldMaterialOfHistoriaInventariaCollectionHistoriaInventaria);
                }
            }
            for (Inventario inventarioCollectionInventario : material.getInventarioCollection()) {
                Material oldMaterialOfInventarioCollectionInventario = inventarioCollectionInventario.getMaterial();
                inventarioCollectionInventario.setMaterial(material);
                inventarioCollectionInventario = em.merge(inventarioCollectionInventario);
                if (oldMaterialOfInventarioCollectionInventario != null) {
                    oldMaterialOfInventarioCollectionInventario.getInventarioCollection().remove(inventarioCollectionInventario);
                    oldMaterialOfInventarioCollectionInventario = em.merge(oldMaterialOfInventarioCollectionInventario);
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

    public void edit(Material material) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Material persistentMaterial = em.find(Material.class, material.getMatid());
            Unidad unidOld = persistentMaterial.getUnid();
            Unidad unidNew = material.getUnid();
            TipoMaterial tmidOld = persistentMaterial.getTmid();
            TipoMaterial tmidNew = material.getTmid();
            Collection<Proveedor> proveedorCollectionOld = persistentMaterial.getProveedorCollection();
            Collection<Proveedor> proveedorCollectionNew = material.getProveedorCollection();
            Collection<UsoPlaneado> usoPlaneadoCollectionOld = persistentMaterial.getUsoPlaneadoCollection();
            Collection<UsoPlaneado> usoPlaneadoCollectionNew = material.getUsoPlaneadoCollection();
            Collection<HistoriaInventaria> historiaInventariaCollectionOld = persistentMaterial.getHistoriaInventariaCollection();
            Collection<HistoriaInventaria> historiaInventariaCollectionNew = material.getHistoriaInventariaCollection();
            Collection<Inventario> inventarioCollectionOld = persistentMaterial.getInventarioCollection();
            Collection<Inventario> inventarioCollectionNew = material.getInventarioCollection();
            List<String> illegalOrphanMessages = null;
            for (UsoPlaneado usoPlaneadoCollectionOldUsoPlaneado : usoPlaneadoCollectionOld) {
                if (!usoPlaneadoCollectionNew.contains(usoPlaneadoCollectionOldUsoPlaneado)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain UsoPlaneado " + usoPlaneadoCollectionOldUsoPlaneado + " since its material field is not nullable.");
                }
            }
            for (HistoriaInventaria historiaInventariaCollectionOldHistoriaInventaria : historiaInventariaCollectionOld) {
                if (!historiaInventariaCollectionNew.contains(historiaInventariaCollectionOldHistoriaInventaria)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain HistoriaInventaria " + historiaInventariaCollectionOldHistoriaInventaria + " since its material field is not nullable.");
                }
            }
            for (Inventario inventarioCollectionOldInventario : inventarioCollectionOld) {
                if (!inventarioCollectionNew.contains(inventarioCollectionOldInventario)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Inventario " + inventarioCollectionOldInventario + " since its material field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (unidNew != null) {
                unidNew = em.getReference(unidNew.getClass(), unidNew.getUnid());
                material.setUnid(unidNew);
            }
            if (tmidNew != null) {
                tmidNew = em.getReference(tmidNew.getClass(), tmidNew.getTmid());
                material.setTmid(tmidNew);
            }
            Collection<Proveedor> attachedProveedorCollectionNew = new ArrayList<Proveedor>();
            for (Proveedor proveedorCollectionNewProveedorToAttach : proveedorCollectionNew) {
                proveedorCollectionNewProveedorToAttach = em.getReference(proveedorCollectionNewProveedorToAttach.getClass(), proveedorCollectionNewProveedorToAttach.getProvid());
                attachedProveedorCollectionNew.add(proveedorCollectionNewProveedorToAttach);
            }
            proveedorCollectionNew = attachedProveedorCollectionNew;
            material.setProveedorCollection(proveedorCollectionNew);
            Collection<UsoPlaneado> attachedUsoPlaneadoCollectionNew = new ArrayList<UsoPlaneado>();
            for (UsoPlaneado usoPlaneadoCollectionNewUsoPlaneadoToAttach : usoPlaneadoCollectionNew) {
                usoPlaneadoCollectionNewUsoPlaneadoToAttach = em.getReference(usoPlaneadoCollectionNewUsoPlaneadoToAttach.getClass(), usoPlaneadoCollectionNewUsoPlaneadoToAttach.getUsoPlaneadoPK());
                attachedUsoPlaneadoCollectionNew.add(usoPlaneadoCollectionNewUsoPlaneadoToAttach);
            }
            usoPlaneadoCollectionNew = attachedUsoPlaneadoCollectionNew;
            material.setUsoPlaneadoCollection(usoPlaneadoCollectionNew);
            Collection<HistoriaInventaria> attachedHistoriaInventariaCollectionNew = new ArrayList<HistoriaInventaria>();
            for (HistoriaInventaria historiaInventariaCollectionNewHistoriaInventariaToAttach : historiaInventariaCollectionNew) {
                historiaInventariaCollectionNewHistoriaInventariaToAttach = em.getReference(historiaInventariaCollectionNewHistoriaInventariaToAttach.getClass(), historiaInventariaCollectionNewHistoriaInventariaToAttach.getHistoriaInventariaPK());
                attachedHistoriaInventariaCollectionNew.add(historiaInventariaCollectionNewHistoriaInventariaToAttach);
            }
            historiaInventariaCollectionNew = attachedHistoriaInventariaCollectionNew;
            material.setHistoriaInventariaCollection(historiaInventariaCollectionNew);
            Collection<Inventario> attachedInventarioCollectionNew = new ArrayList<Inventario>();
            for (Inventario inventarioCollectionNewInventarioToAttach : inventarioCollectionNew) {
                inventarioCollectionNewInventarioToAttach = em.getReference(inventarioCollectionNewInventarioToAttach.getClass(), inventarioCollectionNewInventarioToAttach.getInventarioPK());
                attachedInventarioCollectionNew.add(inventarioCollectionNewInventarioToAttach);
            }
            inventarioCollectionNew = attachedInventarioCollectionNew;
            material.setInventarioCollection(inventarioCollectionNew);
            material = em.merge(material);
            if (unidOld != null && !unidOld.equals(unidNew)) {
                unidOld.getMaterialCollection().remove(material);
                unidOld = em.merge(unidOld);
            }
            if (unidNew != null && !unidNew.equals(unidOld)) {
                unidNew.getMaterialCollection().add(material);
                unidNew = em.merge(unidNew);
            }
            if (tmidOld != null && !tmidOld.equals(tmidNew)) {
                tmidOld.getMaterialCollection().remove(material);
                tmidOld = em.merge(tmidOld);
            }
            if (tmidNew != null && !tmidNew.equals(tmidOld)) {
                tmidNew.getMaterialCollection().add(material);
                tmidNew = em.merge(tmidNew);
            }
            for (Proveedor proveedorCollectionOldProveedor : proveedorCollectionOld) {
                if (!proveedorCollectionNew.contains(proveedorCollectionOldProveedor)) {
                    proveedorCollectionOldProveedor.getMaterialCollection().remove(material);
                    proveedorCollectionOldProveedor = em.merge(proveedorCollectionOldProveedor);
                }
            }
            for (Proveedor proveedorCollectionNewProveedor : proveedorCollectionNew) {
                if (!proveedorCollectionOld.contains(proveedorCollectionNewProveedor)) {
                    proveedorCollectionNewProveedor.getMaterialCollection().add(material);
                    proveedorCollectionNewProveedor = em.merge(proveedorCollectionNewProveedor);
                }
            }
            for (UsoPlaneado usoPlaneadoCollectionNewUsoPlaneado : usoPlaneadoCollectionNew) {
                if (!usoPlaneadoCollectionOld.contains(usoPlaneadoCollectionNewUsoPlaneado)) {
                    Material oldMaterialOfUsoPlaneadoCollectionNewUsoPlaneado = usoPlaneadoCollectionNewUsoPlaneado.getMaterial();
                    usoPlaneadoCollectionNewUsoPlaneado.setMaterial(material);
                    usoPlaneadoCollectionNewUsoPlaneado = em.merge(usoPlaneadoCollectionNewUsoPlaneado);
                    if (oldMaterialOfUsoPlaneadoCollectionNewUsoPlaneado != null && !oldMaterialOfUsoPlaneadoCollectionNewUsoPlaneado.equals(material)) {
                        oldMaterialOfUsoPlaneadoCollectionNewUsoPlaneado.getUsoPlaneadoCollection().remove(usoPlaneadoCollectionNewUsoPlaneado);
                        oldMaterialOfUsoPlaneadoCollectionNewUsoPlaneado = em.merge(oldMaterialOfUsoPlaneadoCollectionNewUsoPlaneado);
                    }
                }
            }
            for (HistoriaInventaria historiaInventariaCollectionNewHistoriaInventaria : historiaInventariaCollectionNew) {
                if (!historiaInventariaCollectionOld.contains(historiaInventariaCollectionNewHistoriaInventaria)) {
                    Material oldMaterialOfHistoriaInventariaCollectionNewHistoriaInventaria = historiaInventariaCollectionNewHistoriaInventaria.getMaterial();
                    historiaInventariaCollectionNewHistoriaInventaria.setMaterial(material);
                    historiaInventariaCollectionNewHistoriaInventaria = em.merge(historiaInventariaCollectionNewHistoriaInventaria);
                    if (oldMaterialOfHistoriaInventariaCollectionNewHistoriaInventaria != null && !oldMaterialOfHistoriaInventariaCollectionNewHistoriaInventaria.equals(material)) {
                        oldMaterialOfHistoriaInventariaCollectionNewHistoriaInventaria.getHistoriaInventariaCollection().remove(historiaInventariaCollectionNewHistoriaInventaria);
                        oldMaterialOfHistoriaInventariaCollectionNewHistoriaInventaria = em.merge(oldMaterialOfHistoriaInventariaCollectionNewHistoriaInventaria);
                    }
                }
            }
            for (Inventario inventarioCollectionNewInventario : inventarioCollectionNew) {
                if (!inventarioCollectionOld.contains(inventarioCollectionNewInventario)) {
                    Material oldMaterialOfInventarioCollectionNewInventario = inventarioCollectionNewInventario.getMaterial();
                    inventarioCollectionNewInventario.setMaterial(material);
                    inventarioCollectionNewInventario = em.merge(inventarioCollectionNewInventario);
                    if (oldMaterialOfInventarioCollectionNewInventario != null && !oldMaterialOfInventarioCollectionNewInventario.equals(material)) {
                        oldMaterialOfInventarioCollectionNewInventario.getInventarioCollection().remove(inventarioCollectionNewInventario);
                        oldMaterialOfInventarioCollectionNewInventario = em.merge(oldMaterialOfInventarioCollectionNewInventario);
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
                Integer id = material.getMatid();
                if (findMaterial(id) == null) {
                    throw new NonexistentEntityException("The material with id " + id + " no longer exists.");
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
            Material material;
            try {
                material = em.getReference(Material.class, id);
                material.getMatid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The material with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<UsoPlaneado> usoPlaneadoCollectionOrphanCheck = material.getUsoPlaneadoCollection();
            for (UsoPlaneado usoPlaneadoCollectionOrphanCheckUsoPlaneado : usoPlaneadoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Material (" + material + ") cannot be destroyed since the UsoPlaneado " + usoPlaneadoCollectionOrphanCheckUsoPlaneado + " in its usoPlaneadoCollection field has a non-nullable material field.");
            }
            Collection<HistoriaInventaria> historiaInventariaCollectionOrphanCheck = material.getHistoriaInventariaCollection();
            for (HistoriaInventaria historiaInventariaCollectionOrphanCheckHistoriaInventaria : historiaInventariaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Material (" + material + ") cannot be destroyed since the HistoriaInventaria " + historiaInventariaCollectionOrphanCheckHistoriaInventaria + " in its historiaInventariaCollection field has a non-nullable material field.");
            }
            Collection<Inventario> inventarioCollectionOrphanCheck = material.getInventarioCollection();
            for (Inventario inventarioCollectionOrphanCheckInventario : inventarioCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Material (" + material + ") cannot be destroyed since the Inventario " + inventarioCollectionOrphanCheckInventario + " in its inventarioCollection field has a non-nullable material field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Unidad unid = material.getUnid();
            if (unid != null) {
                unid.getMaterialCollection().remove(material);
                unid = em.merge(unid);
            }
            TipoMaterial tmid = material.getTmid();
            if (tmid != null) {
                tmid.getMaterialCollection().remove(material);
                tmid = em.merge(tmid);
            }
            Collection<Proveedor> proveedorCollection = material.getProveedorCollection();
            for (Proveedor proveedorCollectionProveedor : proveedorCollection) {
                proveedorCollectionProveedor.getMaterialCollection().remove(material);
                proveedorCollectionProveedor = em.merge(proveedorCollectionProveedor);
            }
            em.remove(material);
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

    public List<Material> findMaterialEntities() {
        return findMaterialEntities(true, -1, -1);
    }

    public List<Material> findMaterialEntities(int maxResults, int firstResult) {
        return findMaterialEntities(false, maxResults, firstResult);
    }

    private List<Material> findMaterialEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Material.class));
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

    public Material findMaterial(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Material.class, id);
        } finally {
            em.close();
        }
    }

    public int getMaterialCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Material> rt = cq.from(Material.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
