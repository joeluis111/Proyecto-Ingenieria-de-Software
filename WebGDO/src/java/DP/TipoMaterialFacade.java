/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DP;

import MD.EntityType;
import static MD.EntityType.TIPO_MATERIAL;
import MD.TipoMaterial;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Kenny
 */
@Stateless
public class TipoMaterialFacade extends AbstractFacade<TipoMaterial> {
    @PersistenceContext(unitName = "WebGDOPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TipoMaterialFacade() {
        super(TipoMaterial.class);
    }

    @Override
    public EntityType getType() {
        return TIPO_MATERIAL;
    }
    
}
