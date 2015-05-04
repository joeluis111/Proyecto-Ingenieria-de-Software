/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import DP.AbstractFacade;
import DP.EmpleadoFacade;
import static DP.EntityType.EMPLEADO;
import MD.Empleado;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import javax.annotation.PostConstruct;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlCommandLink;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.component.html.HtmlPanelGrid;
import javax.faces.component.html.HtmlPanelGroup;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import org.primefaces.component.commandbutton.CommandButton;

/**
 *
 * @author Kenny
 */
public class EmpleadoGUI extends GUIAbstracta implements Serializable {
    private HtmlPanelGroup menu;
    EmpleadoFacade empleadoFacade;
    
    /**
     * Creates a new instance of EmpleadoGUI
     */
    public EmpleadoGUI() {
    }
    
    @PostConstruct
    public void init() {
        
    }

    @Override
    protected void guardarFacades(Collection<AbstractFacade> facades) {
        Iterator<AbstractFacade> iterador = facades.iterator();
        while (iterador.hasNext()) {
            AbstractFacade facade = iterador.next();
            if (facade.getType() == EMPLEADO) {
                empleadoFacade = (EmpleadoFacade)facade;
                break;
            }
        }
    }

    @Override
    protected void generarMenuDeVista(final HtmlPanelGroup menu, ActionEvent e) {
        menu.getChildren().clear();
        HtmlPanelGrid cuadricula = new HtmlPanelGrid();
        cuadricula.setColumns(5);
        Iterator<Empleado> iterator = empleadoFacade.findAll().iterator();
        
        HtmlOutputText columnaNombres = new HtmlOutputText();
        HtmlOutputText columnaApellidos = new HtmlOutputText();
        HtmlOutputText columnaTitulo = new HtmlOutputText();
        HtmlOutputText columnaTipo = new HtmlOutputText();
        
        columnaNombres.setValue("Nombres");
        columnaApellidos.setValue("Apellidos");
        columnaTitulo.setValue("Título");
        columnaTipo.setValue("Tipo de Trabajador");
        
        cuadricula.getChildren().add(columnaNombres);
        cuadricula.getChildren().add(columnaApellidos);
        cuadricula.getChildren().add(columnaTitulo);
        cuadricula.getChildren().add(columnaTipo);
        cuadricula.getChildren().add(new HtmlOutputText());
                
        while (iterator.hasNext()) {
            final Empleado empleado = iterator.next();
            desplegarRegistro(empleado, cuadricula);
            CommandButton ver = UtilidadesGUI.crearBotonDeVer();
            ver.addActionListener(new ActionListener() {

                @Override
                public void processAction(ActionEvent event) throws AbortProcessingException {
                    menu.getChildren().clear();
                    menu.getChildren().add(desplegarADetalle(empleado));
                    guiPrincipal.validate();
                }
            });
            cuadricula.getChildren().add(ver);
        }
        menu.getChildren().add(cuadricula);
    }
    
    private void desplegarRegistro(Empleado e, HtmlPanelGrid cuadricula) {
        HtmlOutputText nombres = new HtmlOutputText();
        HtmlOutputText apellidos = new HtmlOutputText();
        HtmlOutputText titulo = new HtmlOutputText();
        HtmlOutputText tipo = new HtmlOutputText();
        nombres.setValue(e.getEmpnombres());
        apellidos.setValue(e.getEmpapellidos());
        titulo.setValue(e.getTpid().getTpnombre());
        tipo.setValue(e.getTtid().getTtnombre());
        
        cuadricula.getChildren().add(nombres);
        cuadricula.getChildren().add(apellidos);
        cuadricula.getChildren().add(titulo);
        cuadricula.getChildren().add(tipo);
    }
    
    private UIComponent desplegarADetalle(Empleado e) {
        HtmlPanelGrid cuadricula = new HtmlPanelGrid();
        
        cuadricula.setColumns(2);
        cuadricula.getChildren().add(UtilidadesGUI.crearTexto("Cédula"));
        cuadricula.getChildren().add(UtilidadesGUI.crearTexto(e.getEmpcedula()));
        
        cuadricula.getChildren().add(UtilidadesGUI.crearTexto("Nombres"));
        cuadricula.getChildren().add(UtilidadesGUI.crearTexto(e.getEmpnombres()));
        
        cuadricula.getChildren().add(UtilidadesGUI.crearTexto("Apellidos"));
        cuadricula.getChildren().add(UtilidadesGUI.crearTexto(e.getEmpapellidos()));
        
        cuadricula.getChildren().add(UtilidadesGUI.crearTexto("Código Postal"));
        cuadricula.getChildren().add(UtilidadesGUI.crearTexto(e.getEmpcodigopostal()));
        
        cuadricula.getChildren().add(UtilidadesGUI.crearTexto("Dirección"));
        cuadricula.getChildren().add(UtilidadesGUI.crearTexto(e.getEmpdireccion()));
        
        cuadricula.getChildren().add(UtilidadesGUI.crearTexto("Fecha de Nacimiento"));
        cuadricula.getChildren().add(UtilidadesGUI.crearTexto(e.getEmpfechanacimiento()));
        
        cuadricula.getChildren().add(UtilidadesGUI.crearTexto("Género"));
        cuadricula.getChildren().add(UtilidadesGUI.crearTexto(e.getEmpgenero()));
        
        cuadricula.getChildren().add(UtilidadesGUI.crearTexto("Número de Teléfono"));
        cuadricula.getChildren().add(UtilidadesGUI.crearTexto(e.getEmpnumerotelefono()));
        
        return cuadricula;
    }

    @Override
    protected void generarMenuDeCreacion(HtmlPanelGroup menu, ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void generarMenuDeEdicion(HtmlPanelGroup menu, ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
