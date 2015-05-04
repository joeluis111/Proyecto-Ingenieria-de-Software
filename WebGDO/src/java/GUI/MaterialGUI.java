/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import DP.AbstractFacade;
import DP.MaterialFacade;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import javax.annotation.PostConstruct;
import javax.faces.component.html.HtmlPanelGroup;
import javax.faces.event.ActionEvent;

/**
 *
 * @author Kenny
 */
public class MaterialGUI extends GUIAbstracta implements Serializable {
    private HtmlPanelGroup menu;
    private MaterialFacade materialFacade;
    
    /**
     * Creates a new instance of MaterialGUI
     */
    public MaterialGUI() {
    }
    
    @PostConstruct
    public void init() {
        
    }

    @Override
    protected void guardarFacades(Collection<AbstractFacade> facades) {
        Iterator<AbstractFacade> iterador = facades.iterator();
        while (iterador.hasNext()) {
            AbstractFacade facade = iterador.next();
            switch(facade.getType()) {
                case MATERIAL:
                    materialFacade = (MaterialFacade)facade;
                    break;
            }
        }
    }

    @Override
    protected void generarMenuDeVista(HtmlPanelGroup menu, ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void generarMenuDeCreacion(HtmlPanelGroup menu, ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
