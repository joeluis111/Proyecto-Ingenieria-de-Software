/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DP;

import static DP.EntityType.TIPO_DOCUMENTO;
import MD.TipoDocumento;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Kenny
 */
@Stateless
public class TipoDocumentoFacade extends AbstractFacade<TipoDocumento> {
    @PersistenceContext(unitName = "WebGDOPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TipoDocumentoFacade() {
        super(TipoDocumento.class);
    }

    @Override
    public EntityType getType() {
        return TIPO_DOCUMENTO;
    }
    
}
