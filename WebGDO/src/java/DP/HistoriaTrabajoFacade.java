/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DP;

import static DP.EntityType.HISTORIA_TRABAJO;
import MD.HistoriaTrabajo;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Kenny
 */
@Stateless
public class HistoriaTrabajoFacade extends AbstractFacade<HistoriaTrabajo> {
    @PersistenceContext(unitName = "WebGDOPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HistoriaTrabajoFacade() {
        super(HistoriaTrabajo.class);
    }

    @Override
    public EntityType getType() {
        return HISTORIA_TRABAJO;
    }
    
}
