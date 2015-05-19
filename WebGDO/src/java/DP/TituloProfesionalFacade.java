/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DP;

import MD.EntityType;
import static MD.EntityType.TITULO_PROFESIONAL;
import MD.TituloProfesional;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Kenny
 */
@Stateless
public class TituloProfesionalFacade extends AbstractFacade<TituloProfesional> {
    @PersistenceContext(unitName = "WebGDOPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TituloProfesionalFacade() {
        super(TituloProfesional.class);
    }

    @Override
    public EntityType getType() {
        return TITULO_PROFESIONAL;
    }
    
}
