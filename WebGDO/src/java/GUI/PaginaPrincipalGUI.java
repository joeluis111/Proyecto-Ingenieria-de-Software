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
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.html.HtmlPanelGroup;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Kenny
 */
@ManagedBean
@SessionScoped
public class PaginaPrincipalGUI {
    private HtmlPanelGroup menu = new HtmlPanelGroup();
    
    ClienteGUI clienteGUI;
    EmpleadoGUI empleadoGUI;
    InventarioGUI inventarioGUI;
    MaterialGUI materialGUI;
    ProyectoGUI proyectoGUI;
    
    @EJB
    ProyectoFacade proyectoFacade;
    
    @EJB
    ClienteFacade clienteFacade;
    
    @EJB
    EmpleadoFacade empleadoFacade;
    
    @EJB
    InventarioFacade inventarioFacade;
    
    @EJB
    MaterialFacade materialFacade;
    
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
        clienteGUI.generarMenu(menu, facades, this);
        validate();
    }
    
    public void manejarEmpleados() {
        ArrayList<AbstractFacade> facades = new ArrayList();
        facades.add(empleadoFacade);
        empleadoGUI.generarMenu(menu, facades, this);
        validate();
    }
    
    public void manejarInventario() {
        ArrayList<AbstractFacade> facades = new ArrayList();
        facades.add(proyectoFacade);
        inventarioGUI.generarMenu(menu, facades, this);
        validate();
    }
    
    public void manejarMateriales() {
        ArrayList<AbstractFacade> facades = new ArrayList();
        facades.add(proyectoFacade);
        materialGUI.generarMenu(menu, facades, this);
        validate();
    }
    
    public void manejarProyectos() {
        ArrayList<AbstractFacade> facades = new ArrayList();
        facades.add(proyectoFacade);
        proyectoGUI.generarMenu(menu, facades, this);
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
