/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MD;

import MD.exceptions.IllegalOrphanException;
import MD.exceptions.NonexistentEntityException;
import MD.exceptions.RollbackFailureException;
import MD.Cliente;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import MD.HistoriaCliente;
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
public class ClienteJpaController implements Serializable {

    public ClienteJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cliente cliente) throws RollbackFailureException, Exception {
        if (cliente.getHistoriaClienteCollection() == null) {
            cliente.setHistoriaClienteCollection(new ArrayList<HistoriaCliente>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<HistoriaCliente> attachedHistoriaClienteCollection = new ArrayList<HistoriaCliente>();
            for (HistoriaCliente historiaClienteCollectionHistoriaClienteToAttach : cliente.getHistoriaClienteCollection()) {
                historiaClienteCollectionHistoriaClienteToAttach = em.getReference(historiaClienteCollectionHistoriaClienteToAttach.getClass(), historiaClienteCollectionHistoriaClienteToAttach.getHistoriaClientePK());
                attachedHistoriaClienteCollection.add(historiaClienteCollectionHistoriaClienteToAttach);
            }
            cliente.setHistoriaClienteCollection(attachedHistoriaClienteCollection);
            em.persist(cliente);
            for (HistoriaCliente historiaClienteCollectionHistoriaCliente : cliente.getHistoriaClienteCollection()) {
                Cliente oldClienteOfHistoriaClienteCollectionHistoriaCliente = historiaClienteCollectionHistoriaCliente.getCliente();
                historiaClienteCollectionHistoriaCliente.setCliente(cliente);
                historiaClienteCollectionHistoriaCliente = em.merge(historiaClienteCollectionHistoriaCliente);
                if (oldClienteOfHistoriaClienteCollectionHistoriaCliente != null) {
                    oldClienteOfHistoriaClienteCollectionHistoriaCliente.getHistoriaClienteCollection().remove(historiaClienteCollectionHistoriaCliente);
                    oldClienteOfHistoriaClienteCollectionHistoriaCliente = em.merge(oldClienteOfHistoriaClienteCollectionHistoriaCliente);
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

    public void edit(Cliente cliente) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Cliente persistentCliente = em.find(Cliente.class, cliente.getCliid());
            Collection<HistoriaCliente> historiaClienteCollectionOld = persistentCliente.getHistoriaClienteCollection();
            Collection<HistoriaCliente> historiaClienteCollectionNew = cliente.getHistoriaClienteCollection();
            List<String> illegalOrphanMessages = null;
            for (HistoriaCliente historiaClienteCollectionOldHistoriaCliente : historiaClienteCollectionOld) {
                if (!historiaClienteCollectionNew.contains(historiaClienteCollectionOldHistoriaCliente)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain HistoriaCliente " + historiaClienteCollectionOldHistoriaCliente + " since its cliente field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<HistoriaCliente> attachedHistoriaClienteCollectionNew = new ArrayList<HistoriaCliente>();
            for (HistoriaCliente historiaClienteCollectionNewHistoriaClienteToAttach : historiaClienteCollectionNew) {
                historiaClienteCollectionNewHistoriaClienteToAttach = em.getReference(historiaClienteCollectionNewHistoriaClienteToAttach.getClass(), historiaClienteCollectionNewHistoriaClienteToAttach.getHistoriaClientePK());
                attachedHistoriaClienteCollectionNew.add(historiaClienteCollectionNewHistoriaClienteToAttach);
            }
            historiaClienteCollectionNew = attachedHistoriaClienteCollectionNew;
            cliente.setHistoriaClienteCollection(historiaClienteCollectionNew);
            cliente = em.merge(cliente);
            for (HistoriaCliente historiaClienteCollectionNewHistoriaCliente : historiaClienteCollectionNew) {
                if (!historiaClienteCollectionOld.contains(historiaClienteCollectionNewHistoriaCliente)) {
                    Cliente oldClienteOfHistoriaClienteCollectionNewHistoriaCliente = historiaClienteCollectionNewHistoriaCliente.getCliente();
                    historiaClienteCollectionNewHistoriaCliente.setCliente(cliente);
                    historiaClienteCollectionNewHistoriaCliente = em.merge(historiaClienteCollectionNewHistoriaCliente);
                    if (oldClienteOfHistoriaClienteCollectionNewHistoriaCliente != null && !oldClienteOfHistoriaClienteCollectionNewHistoriaCliente.equals(cliente)) {
                        oldClienteOfHistoriaClienteCollectionNewHistoriaCliente.getHistoriaClienteCollection().remove(historiaClienteCollectionNewHistoriaCliente);
                        oldClienteOfHistoriaClienteCollectionNewHistoriaCliente = em.merge(oldClienteOfHistoriaClienteCollectionNewHistoriaCliente);
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
                Integer id = cliente.getCliid();
                if (findCliente(id) == null) {
                    throw new NonexistentEntityException("The cliente with id " + id + " no longer exists.");
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
            Cliente cliente;
            try {
                cliente = em.getReference(Cliente.class, id);
                cliente.getCliid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cliente with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<HistoriaCliente> historiaClienteCollectionOrphanCheck = cliente.getHistoriaClienteCollection();
            for (HistoriaCliente historiaClienteCollectionOrphanCheckHistoriaCliente : historiaClienteCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Cliente (" + cliente + ") cannot be destroyed since the HistoriaCliente " + historiaClienteCollectionOrphanCheckHistoriaCliente + " in its historiaClienteCollection field has a non-nullable cliente field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(cliente);
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

    public List<Cliente> findClienteEntities() {
        return findClienteEntities(true, -1, -1);
    }

    public List<Cliente> findClienteEntities(int maxResults, int firstResult) {
        return findClienteEntities(false, maxResults, firstResult);
    }

    private List<Cliente> findClienteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cliente.class));
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

    public Cliente findCliente(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cliente.class, id);
        } finally {
            em.close();
        }
    }

    public int getClienteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cliente> rt = cq.from(Cliente.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
