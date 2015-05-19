/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DP;

import MD.EntityType;
import static MD.EntityType.TIPO_TRABAJADOR;
import MD.TipoTrabajador;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Kenny
 */
@Stateless
public class TipoTrabajadorFacade extends AbstractFacade<TipoTrabajador> {
    @PersistenceContext(unitName = "WebGDOPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TipoTrabajadorFacade() {
        super(TipoTrabajador.class);
    }

    @Override
    public EntityType getType() {
        return TIPO_TRABAJADOR;
    }
    
}
