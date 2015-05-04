/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import DP.AbstractFacade;
import java.io.Serializable;
import java.util.Collection;
import javax.annotation.PostConstruct;
import javax.faces.component.html.HtmlPanelGroup;
import javax.faces.event.ActionEvent;

/**
 *
 * @author Kenny
 */
public class InventarioGUI extends GUIAbstracta implements Serializable {
    private HtmlPanelGroup menu;
    
    /**
     * Creates a new instance of InventarioGUI
     */
    public InventarioGUI() {
    }
    
    @PostConstruct
    public void init() {
        
    }

    @Override
    protected void guardarFacades(Collection<AbstractFacade> facades) {
        
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
