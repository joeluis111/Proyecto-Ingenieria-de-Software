/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import DP.AbstractFacade;
import static DP.EntityType.PROYECTO;
import javax.faces.component.html.HtmlPanelGroup;
import javax.faces.component.html.HtmlPanelGrid;
import javax.faces.component.html.HtmlOutputLabel;

import DP.ProyectoFacade;
import MD.Calendario;
import MD.Evento;
import MD.Proyecto;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import javax.faces.component.html.HtmlCommandLink;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import org.primefaces.component.commandbutton.CommandButton;

/**
 *
 * @author Kenny
 */
public class ProyectoGUI extends GUIAbstracta implements Serializable {
    ProyectoFacade proyectoFacade;
    
    /**
     * Creates a new instance of ProyectoGUI
     */
    public ProyectoGUI() {
    }
    
    private HtmlPanelGrid generarGantt(int id, ProyectoFacade facade) {
        HtmlPanelGrid tabla = new HtmlPanelGrid();
        
        HtmlOutputLabel signoNumero = new HtmlOutputLabel();
        signoNumero.setValue("#");
        
        HtmlOutputLabel nombre = new HtmlOutputLabel();
        nombre.setValue("Nombre");
        
        HtmlOutputLabel duracion = new HtmlOutputLabel();
        duracion.setValue("Duracion");
        
        HtmlOutputLabel inicio = new HtmlOutputLabel();
        inicio.setValue("Inicio");
        
        HtmlOutputLabel fin = new HtmlOutputLabel();
        fin.setValue("Fin");
        
        tabla.getChildren().add(signoNumero);
        tabla.getChildren().add(nombre);
        tabla.getChildren().add(duracion);
        tabla.getChildren().add(inicio);
        tabla.getChildren().add(fin);
        
        Calendario c = facade.find(id).getCalendarioCollection().iterator().next();
        facade.find(id);
        Iterator<Evento> iterador = c.getEventoCollection().iterator();
        int j = 0;
        while (iterador.hasNext()) {
            ++j;
            HtmlOutputLabel numeroEvento = new HtmlOutputLabel();
            numeroEvento.setValue(j);
            tabla.getChildren().add(numeroEvento);
            
            Evento e = iterador.next();
            
            HtmlOutputLabel nombreEvento = new HtmlOutputLabel();
            nombreEvento.setValue(e.getEvnombre());
            
            HtmlOutputLabel fechaInicio = new HtmlOutputLabel();
            Date fechaInicioPlaneada = e.getEvfechainicioplaneada();
            Date fechaFinPlaneada = e.getEvfechafinplaneada();
            fechaInicio.setValue(fechaInicioPlaneada);
            
            HtmlOutputLabel fechaFin = new HtmlOutputLabel();
            fechaFin.setValue(fechaFinPlaneada);
            
            HtmlOutputLabel l = new HtmlOutputLabel();
            l.setValue((fechaFinPlaneada.getTime() - fechaInicioPlaneada.getTime())/ (24 * 60 * 60 * 1000));
            
            tabla.getChildren().add(nombreEvento);
            tabla.getChildren().add(l);
            tabla.getChildren().add(fechaInicio);
            tabla.getChildren().add(fechaFin);
        }
        
        tabla.setColumns(5);
        
        return tabla;
    }

    @Override
    protected void guardarFacades(Collection<AbstractFacade> facades) {
        Iterator<AbstractFacade> iterador = facades.iterator();
        while (iterador.hasNext()) {
            AbstractFacade facade = iterador.next();
            if (facade.getType() == PROYECTO) {
                proyectoFacade = (ProyectoFacade)facade;
            }
            return;
        }
    }

    @Override
    protected void generarMenuDeVista(final HtmlPanelGroup menu, ActionEvent e) {
        menu.getChildren().clear();
        
        Iterator<Proyecto> iterador = proyectoFacade.findAll().iterator();
        HtmlPanelGrid cuadricula = new HtmlPanelGrid();
        cuadricula.setColumns(3);
        
        HtmlOutputText columnaID = new HtmlOutputText();
        columnaID.setValue("ID");
        
        HtmlOutputText columnaNombre = new HtmlOutputText();
        columnaNombre.setValue("Nombre");
        
        cuadricula.getChildren().add(columnaID);
        cuadricula.getChildren().add(columnaNombre);
        cuadricula.getChildren().add(new HtmlOutputText());
        
        while (iterador.hasNext()) {
            final Proyecto p = iterador.next();
            
            HtmlOutputText id = new HtmlOutputText();
            id.setValue(p.getProid());
            
            HtmlOutputText nombre = new HtmlOutputText();
            nombre.setValue(p.getPronombre());
            
            CommandButton editar = UtilidadesGUI.crearBoton("Ver");
            editar.addActionListener(new ActionListener() {

                @Override
                public void processAction(ActionEvent event) throws AbortProcessingException {
                    menu.getChildren().clear();
                    menu.getChildren().add(generarGantt(p.getProid(), proyectoFacade));
                    guiPrincipal.validate();
                }
            });
            
            cuadricula.getChildren().add(id);
            cuadricula.getChildren().add(nombre);
            cuadricula.getChildren().add(editar);
        }
        menu.getChildren().add(cuadricula);
    }

    @Override
    protected void generarMenuDeCreacion(HtmlPanelGroup menu, ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
