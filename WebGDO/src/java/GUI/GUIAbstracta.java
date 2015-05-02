/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import DP.AbstractFacade;
import java.util.Collection;
import javax.faces.component.html.HtmlPanelGroup;
import javax.faces.component.html.HtmlOutputLink;
import javax.faces.component.ActionSource;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.component.html.HtmlPanelGrid;
import javax.faces.component.html.HtmlCommandLink;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

/**
 *
 * @author Kenny
 */
public abstract class GUIAbstracta {
    
    public void generarMenu(final HtmlPanelGroup menu, Collection<AbstractFacade> facades) {
        guardarFacades(facades);
        
        menu.getChildren().clear();
        HtmlPanelGrid cuadricula = new HtmlPanelGrid();
        cuadricula.setColumns(1);
        
        HtmlCommandLink ver = new HtmlCommandLink();
        HtmlOutputText textoVer = new HtmlOutputText();
        textoVer.setValue("Ver");
        ver.addActionListener(new ActionListener() {

            @Override
            public void processAction(ActionEvent event) throws AbortProcessingException {
                generarMenuDeVista(menu, event);
            }
        });
        ver.getChildren().add(textoVer);
        
        HtmlCommandLink crear = new HtmlCommandLink();
        HtmlOutputText textoCrear = new HtmlOutputText();
        textoCrear.setValue("Crear");
        crear.addActionListener(new ActionListener() {

            @Override
            public void processAction(ActionEvent event) throws AbortProcessingException {
                generarMenuDeCreacion(menu, event);
            }
        });
        crear.getChildren().add(textoCrear);
        
        HtmlCommandLink editar = new HtmlCommandLink();
        HtmlOutputText textoEditar = new HtmlOutputText();
        textoEditar.setValue("Editar");
        editar.addActionListener(new ActionListener() {

            @Override
            public void processAction(ActionEvent event) throws AbortProcessingException {
                generarMenuDeEdicion(menu, event);
            }
        });
        editar.getChildren().add(textoEditar);
        
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
