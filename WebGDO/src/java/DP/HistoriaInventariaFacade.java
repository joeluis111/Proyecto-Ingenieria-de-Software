/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DP;

import MD.EntityType;
import static MD.EntityType.HISTORIA_INVENTARIA;
import MD.HistoriaInventaria;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Kenny
 */
@Stateless
public class HistoriaInventariaFacade extends AbstractFacade<HistoriaInventaria> {
    @PersistenceContext(unitName = "WebGDOPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HistoriaInventariaFacade() {
        super(HistoriaInventaria.class);
    }

    @Override
    public EntityType getType() {
        return HISTORIA_INVENTARIA;
    }
    
}
