/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DP;

import static DP.EntityType.EMPLEADO;
import MD.Empleado;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Kenny
 */
@Stateless
public class EmpleadoFacade extends AbstractFacade<Empleado> {
    @PersistenceContext(unitName = "WebGDOPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EmpleadoFacade() {
        super(Empleado.class);
    }

    @Override
    public EntityType getType() {
        return EMPLEADO;
    }
    
}
