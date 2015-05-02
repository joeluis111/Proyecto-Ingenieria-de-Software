/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DP;

import static DP.EntityType.HISTORIA_CLIENTE;
import MD.HistoriaCliente;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Kenny
 */
@Stateless
public class HistoriaClienteFacade extends AbstractFacade<HistoriaCliente> {
    @PersistenceContext(unitName = "WebGDOPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HistoriaClienteFacade() {
        super(HistoriaCliente.class);
    }

    @Override
    public EntityType getType() {
        return HISTORIA_CLIENTE;
    }
    
}
