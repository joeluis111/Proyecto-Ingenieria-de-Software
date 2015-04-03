/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package paquete2.jcontroller;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import paquete1.jpa.Titulosprofesionales;

/**
 *
 * @author Bryan
 */
@Stateless
public class TitulosprofesionalesFacade extends AbstractFacade<Titulosprofesionales> {
    @PersistenceContext(unitName = "AplicacionWebGboPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TitulosprofesionalesFacade() {
        super(Titulosprofesionales.class);
    }
    
}
