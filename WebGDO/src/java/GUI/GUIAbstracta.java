/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import DP.AbstractFacade;
import java.io.Serializable;
import java.util.Collection;
import javax.faces.component.html.HtmlPanelGroup;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.component.html.HtmlPanelGrid;
import javax.faces.component.html.HtmlCommandLink;
import javax.faces.component.html.HtmlCommandButton;
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
    
    public GUIAbstracta() {
    }
    
    public void generarMenu(final HtmlPanelGroup menu, Collection<AbstractFacade> facades, final PaginaPrincipalGUI gui) {
        guardarFacades(facades);
        guiPrincipal = gui;
        
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
        
        CommandButton editar = new CommandButton();
        editar.setValue("Editar");
        editar.addActionListener(new ActionListener() {

            @Override
            public void processAction(ActionEvent event) throws AbortProcessingException {
                generarMenuDeEdicion(menu, event);
                guiPrincipal.validate();
            }
        });
        
        cuadricula.getChildren().add(ver);
        cuadricula.getChildren().add(crear);
        cuadricula.getChildren().add(editar);
        menu.getChildren().add(cuadricula);
    }
    
    protected abstract void generarMenuDeVista(HtmlPanelGroup menu, ActionEvent evento);
    protected abstract void generarMenuDeCreacion(HtmlPanelGroup menu, ActionEvent evento);
    protected abstract void generarMenuDeEdicion(HtmlPanelGroup menu, ActionEvent evento);
    protected abstract void guardarFacades(Collection<AbstractFacade> facades);
}
