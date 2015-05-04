/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import DP.AbstractFacade;
import DP.EmpleadoFacade;
import static DP.EntityType.EMPLEADO;
import DP.TipoTrabajadorFacade;
import DP.TituloProfesionalFacade;
import MD.Empleado;
import MD.TipoTrabajador;
import MD.TituloProfesional;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItem;
import org.primefaces.component.inputtext.InputText;
import org.primefaces.component.outputlabel.OutputLabel;
import org.primefaces.component.calendar.Calendar;
import org.primefaces.component.selectonemenu.SelectOneMenu;
import javax.faces.component.UISelectItems;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.component.html.HtmlPanelGrid;
import javax.faces.component.html.HtmlPanelGroup;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import javax.faces.model.SelectItem;
import org.primefaces.component.commandbutton.CommandButton;

/**
 *
 * @author Kenny
 */
public class EmpleadoGUI extends GUIAbstracta implements Serializable {
    private HtmlPanelGroup menu;
    private EmpleadoFacade empleadoFacade;
    private TipoTrabajadorFacade tipoTrabajadorFacade;
    private TituloProfesionalFacade tituloProfesionalFacade;
    
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
            switch(facade.getType()) {
                case EMPLEADO:
                    empleadoFacade = (EmpleadoFacade)facade;
                    break;
                case TIPO_TRABAJADOR:
                    tipoTrabajadorFacade = (TipoTrabajadorFacade)facade;
                    break;
                case TITULO_PROFESIONAL:
                    tituloProfesionalFacade = (TituloProfesionalFacade)facade;
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
            CommandButton ver = UtilidadesGUI.crearBoton("Ver");
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
        menu.getChildren().clear();
        
        HtmlPanelGrid principal = new HtmlPanelGrid();
        principal.setColumns(1);
        HtmlPanelGrid ingreso = new HtmlPanelGrid();
        ingreso.setColumns(2);
        
        OutputLabel etiquetaCedula = new OutputLabel();
        etiquetaCedula.setValue("Cédula");
        OutputLabel etiquetaNombres = new OutputLabel();
        etiquetaNombres.setValue("Nombres");
        OutputLabel etiquetaApellidos = new OutputLabel();
        etiquetaApellidos.setValue("Apellidos");
        OutputLabel etiquetaCodigoPostal = new OutputLabel();
        etiquetaCodigoPostal.setValue("Código Postal");
        OutputLabel etiquetaDireccion = new OutputLabel();
        etiquetaDireccion.setValue("Dirección");
        OutputLabel etiquetaNumeroTelefono = new OutputLabel();
        etiquetaNumeroTelefono.setValue("Número Teléfono");
        OutputLabel etiquetaTipoTrabajador = new OutputLabel();
        etiquetaTipoTrabajador.setValue("Tipo de Trabajador");
        OutputLabel etiquetaTituloProfesional = new OutputLabel();
        etiquetaTituloProfesional.setValue("Título Profesional");
        OutputLabel etiquetaGenero = new OutputLabel();
        etiquetaGenero.setValue("Género");
        OutputLabel etiquetaFechaNacimiento = new OutputLabel();
        etiquetaFechaNacimiento.setValue("Fecha de Nacimiento");
        
        final InputText ingresoCedula = new InputText();
        final InputText ingresoNombres = new InputText();
        final InputText ingresoApellidos = new InputText();
        final InputText ingresoCodigoPostal = new InputText();
        final InputText ingresoDireccion = new InputText();
        final InputText ingresoNumeroTelefono = new InputText();
        final SelectOneMenu ingresoTipoTrabajador = new SelectOneMenu();
        final SelectOneMenu ingresoTituloProfesional = new SelectOneMenu();
        final SelectOneMenu ingresoGenero = new SelectOneMenu();
        final Calendar ingresoFechaNacimiento = new Calendar();
        
        Iterator<TipoTrabajador> tiposTrabajadores = tipoTrabajadorFacade.findAll().iterator();
        ArrayList<String> itemsTipoTrabajador = new ArrayList();
        while (tiposTrabajadores.hasNext()) {
            itemsTipoTrabajador.add(tiposTrabajadores.next().getTtnombre());
        }
        UISelectItems opcionesTipoTrabajador = new UISelectItems();
        opcionesTipoTrabajador.setValue(itemsTipoTrabajador);
        ingresoTipoTrabajador.getChildren().add(opcionesTipoTrabajador);
        
        Iterator<TituloProfesional> titulosProfesionales = tituloProfesionalFacade.findAll().iterator();
        ArrayList<String> itemsTituloProfesional = new ArrayList();
        while (titulosProfesionales.hasNext()) {
            itemsTituloProfesional.add(titulosProfesionales.next().getTpnombre());
        }
        UISelectItems opcionesTituloProfesional = new UISelectItems();
        opcionesTituloProfesional.setValue(itemsTituloProfesional);
        ingresoTituloProfesional.getChildren().add(opcionesTituloProfesional);
        
        ArrayList<String> itemsGenero = new ArrayList();
        itemsGenero.add("Hombre");
        itemsGenero.add("Mujer");
        UISelectItems opcionesGenero = new UISelectItems();
        opcionesGenero.setValue(itemsGenero);
        ingresoGenero.getChildren().add(opcionesGenero);
        
        ingreso.getChildren().add(etiquetaCedula);
        ingreso.getChildren().add(ingresoCedula);
        ingreso.getChildren().add(etiquetaNombres);
        ingreso.getChildren().add(ingresoNombres);
        ingreso.getChildren().add(etiquetaApellidos);
        ingreso.getChildren().add(ingresoApellidos);
        ingreso.getChildren().add(etiquetaCodigoPostal);
        ingreso.getChildren().add(ingresoCodigoPostal);
        ingreso.getChildren().add(etiquetaDireccion);
        ingreso.getChildren().add(ingresoDireccion);
        ingreso.getChildren().add(etiquetaNumeroTelefono);
        ingreso.getChildren().add(ingresoNumeroTelefono);
        ingreso.getChildren().add(etiquetaTipoTrabajador);
        ingreso.getChildren().add(ingresoTipoTrabajador);
        ingreso.getChildren().add(etiquetaTituloProfesional);
        ingreso.getChildren().add(ingresoTituloProfesional);
        ingreso.getChildren().add(etiquetaGenero);
        ingreso.getChildren().add(ingresoGenero);
        ingreso.getChildren().add(etiquetaFechaNacimiento);
        ingreso.getChildren().add(ingresoFechaNacimiento);
        
        CommandButton submit = new CommandButton();
        submit.setValue("Ingresar");
        submit.addActionListener(new ActionListener() {

            @Override
            public void processAction(ActionEvent event) throws AbortProcessingException {
                Empleado e = new Empleado();
                e.setEmpapellidos(ingresoApellidos.getValue().toString());
                e.setEmpcedula(ingresoCedula.getValue().toString());
                e.setEmpcodigopostal(ingresoCodigoPostal.getValue().toString());
                e.setEmpdireccion(ingresoDireccion.getValue().toString());
                e.setEmpfechanacimiento((Date)ingresoFechaNacimiento.getValue());
                
                if (ingresoGenero.getValue().equals("Hombre")) {
                    e.setEmpgenero('h');
                }
                else {
                    e.setEmpgenero('m');
                }
                
                e.setEmpnombres(ingresoNombres.getValue().toString());
                e.setEmpnumerotelefono(ingresoNumeroTelefono.getValue().toString());
                
                Iterator<TipoTrabajador> iteradorTiposTrabajadores = tipoTrabajadorFacade.findAll().iterator();
                while (iteradorTiposTrabajadores.hasNext()) {
                    TipoTrabajador tt = iteradorTiposTrabajadores.next();
                    if (tt.getTtnombre().equals(ingresoTipoTrabajador.getValue())) {
                        e.setTtid(tt);
                        break;
                    } 
                }
                
                Iterator<TituloProfesional> iteradorTitulosProfesionales = tituloProfesionalFacade.findAll().iterator();
                while (iteradorTitulosProfesionales.hasNext()) {
                    TituloProfesional tp = iteradorTitulosProfesionales.next();
                    if (tp.getTpnombre().equals(ingresoTituloProfesional.getValue())) {
                        e.setTpid(tp);
                        break;
                    }
                }
                
                empleadoFacade.create(e);
            }
        });
        
        principal.getChildren().add(UtilidadesGUI.crearTexto("Ingresar Empleado"));
        principal.getChildren().add(ingreso);
        principal.getChildren().add(submit);
        menu.getChildren().add(principal);
        
        guiPrincipal.validate();
    }
}
