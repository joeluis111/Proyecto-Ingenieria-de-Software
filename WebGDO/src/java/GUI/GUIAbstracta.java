/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import DP.AbstractFacade;
import java.io.Serializable;
import java.util.Collection;
import javax.faces.application.Application;
import javax.faces.component.html.HtmlPanelGroup;
import javax.faces.component.html.HtmlPanelGrid;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import org.primefaces.component.commandbutton.CommandButton;

/**
 *
 * @author Kenny
 */
public abstract class GUIAbstracta implements Serializable {
    protected PaginaPrincipalGUI guiPrincipal;
    protected Application application;
    
    public GUIAbstracta() {
    }
    
    /**
     * Genera el menú principal del manejo de la entidad.
     * @param menu
     * @param facades
     * @param gui
     * @param app
     */
    public void generarMenu(final HtmlPanelGroup menu, Collection<AbstractFacade> facades, final PaginaPrincipalGUI gui, Application app) {
        guardarFacades(facades);
        guiPrincipal = gui;
        application = app;
        
        menu.getChildren().clear();
        HtmlPanelGrid cuadricula = new HtmlPanelGrid();
        cuadricula.setColumns(1);
        
        CommandButton ver = new CommandButton();
        ver.setValue("Ver");
        ver.addActionListener(new ActionListener() {

            @Override
            public void processAction(ActionEvent event) throws AbortProcessingException {
                generarMenuDeVista(menu, event);
                guiPrincipal.validate();
            }
        });
        
        CommandButton crear = new CommandButton();
        crear.setValue("Crear");
        crear.addActionListener(new ActionListener() {

            @Override
            public void processAction(ActionEvent event) throws AbortProcessingException {
                generarMenuDeCreacion(menu, event);
                guiPrincipal.validate();
            }
        });
        
        cuadricula.getChildren().add(ver);
        cuadricula.getChildren().add(crear);
        menu.getChildren().add(cuadricula);
    }
    
    /**
     * Genera el menú de vista.
     * @param menu
     * @param evento
     */
    protected abstract void generarMenuDeVista(HtmlPanelGroup menu, ActionEvent evento);
    
    /**
     * Genera el menú de creación.
     * @param menu
     * @param evento
     */
    protected abstract void generarMenuDeCreacion(HtmlPanelGroup menu, ActionEvent evento);
    
    /**
     * Guarda los facades para acceder a la base de datos.
     * @param facades
     */
    protected abstract void guardarFacades(Collection<AbstractFacade> facades);
}
