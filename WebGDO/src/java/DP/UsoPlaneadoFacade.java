/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DP;

import static DP.EntityType.USO_PLANEADO;
import MD.UsoPlaneado;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Kenny
 */
@Stateless
public class UsoPlaneadoFacade extends AbstractFacade<UsoPlaneado> {
    @PersistenceContext(unitName = "WebGDOPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsoPlaneadoFacade() {
        super(UsoPlaneado.class);
    }

    @Override
    public EntityType getType() {
        return USO_PLANEADO;
    }
    
}
