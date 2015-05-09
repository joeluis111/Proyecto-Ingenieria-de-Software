/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import DP.AbstractFacade;
import DP.ClienteFacade;
import DP.EmpleadoFacade;
import DP.InventarioFacade;
import DP.MaterialFacade;
import DP.ProyectoFacade;
import DP.TipoMaterialFacade;
import DP.TipoTrabajadorFacade;
import DP.TituloProfesionalFacade;
import DP.UnidadFacade;
import java.io.Serializable;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.html.HtmlPanelGroup;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Kenny
 */
@ManagedBean
@ViewScoped
public class PaginaPrincipalGUI implements Serializable {
    private HtmlPanelGroup menu = new HtmlPanelGroup();
    
    private ClienteGUI clienteGUI;
    private EmpleadoGUI empleadoGUI;
    private InventarioGUI inventarioGUI;
    private MaterialGUI materialGUI;
    private ProyectoGUI proyectoGUI;
    
    @EJB
    private ProyectoFacade proyectoFacade;
    
    @EJB
    private ClienteFacade clienteFacade;
    
    @EJB
    private EmpleadoFacade empleadoFacade;
    
    @EJB
    private InventarioFacade inventarioFacade;
    
    @EJB
    private MaterialFacade materialFacade;
    
    @EJB
    private TipoTrabajadorFacade tipoTrabajadorFacade;
    
    @EJB
    private TituloProfesionalFacade tituloProfesionalFacade;
    
    @EJB
    private TipoMaterialFacade tipoMaterialFacade;
    
    @EJB
    private UnidadFacade unidadFacade;
    
    /**
     * Creates a new instance of PaginaPrincipalGUI
     */
    public PaginaPrincipalGUI() {
    }
    
    @PostConstruct
    public void init() {
        try {
            clienteGUI = new ClienteGUI();
            empleadoGUI = new EmpleadoGUI();
            inventarioGUI  = new InventarioGUI();
            materialGUI = new MaterialGUI();
            proyectoGUI = new ProyectoGUI();
        }
        catch (Exception e) {
            System.err.println("Yes");
            System.err.println(e);
        }
    }
    
    public void manejarClientes() {
        ArrayList<AbstractFacade> facades = new ArrayList();
        facades.add(proyectoFacade);
        clienteGUI.generarMenu(menu, facades, this, FacesContext.getCurrentInstance().getApplication());
        validate();
    }
    
    public void manejarEmpleados() {
        ArrayList<AbstractFacade> facades = new ArrayList();
        facades.add(empleadoFacade);
        facades.add(tipoTrabajadorFacade);
        facades.add(tituloProfesionalFacade);
        empleadoGUI.generarMenu(menu, facades, this, FacesContext.getCurrentInstance().getApplication());
        validate();
    }
    
    public void manejarInventario() {
        ArrayList<AbstractFacade> facades = new ArrayList();
        facades.add(proyectoFacade);
        inventarioGUI.generarMenu(menu, facades, this, FacesContext.getCurrentInstance().getApplication());
        validate();
    }
    
    public void manejarMateriales() {
        ArrayList<AbstractFacade> facades = new ArrayList();
        facades.add(materialFacade);
        facades.add(tipoMaterialFacade);
        facades.add(unidadFacade);
        materialGUI.generarMenu(menu, facades, this, FacesContext.getCurrentInstance().getApplication());
        validate();
    }
    
    public void manejarProyectos() {
        ArrayList<AbstractFacade> facades = new ArrayList();
        facades.add(proyectoFacade);
        proyectoGUI.generarMenu(menu, facades, this, FacesContext.getCurrentInstance().getApplication());
        validate();
    }
    
    public HtmlPanelGroup getMenu() {
        return menu;
    }

    public void setMenu(HtmlPanelGroup menu) {
        this.menu = menu;
    }
    
    public void validate() {
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("forma:panelCentro");
     }
}
