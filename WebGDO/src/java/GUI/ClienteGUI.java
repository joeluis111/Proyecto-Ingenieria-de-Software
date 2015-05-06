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
public class ClienteGUI extends GUIAbstracta implements Serializable {
    /**
     * Creates a new instance of ClienteGUI
     */
    public ClienteGUI() {
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected void guardarFacades(Collection<AbstractFacade> facades) {
        System.out.println("Guardar");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void generarMenuDeVista(HtmlPanelGroup menu, ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void generarMenuDeCreacion(HtmlPanelGroup menu, ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
