/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import DP.AbstractFacade;
import DP.MaterialFacade;
import DP.TipoMaterialFacade;
import DP.UnidadFacade;
import MD.Material;
import MD.TipoMaterial;
import MD.Unidad;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import javax.annotation.PostConstruct;
import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItems;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.component.html.HtmlPanelGrid;
import javax.faces.component.html.HtmlPanelGroup;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.component.inputtext.InputText;
import org.primefaces.component.selectonemenu.SelectOneMenu;

/**
 *
 * @author Kenny
 */
public class MaterialGUI extends GUIAbstracta implements Serializable {
    private HtmlPanelGroup menu;
    private MaterialFacade materialFacade;
    private TipoMaterialFacade tipoMaterialFacade;
    private UnidadFacade unidadFacade;
    
    /**
     * Creates a new instance of MaterialGUI
     */
    public MaterialGUI() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void guardarFacades(Collection<AbstractFacade> facades) {
        Iterator<AbstractFacade> iterador = facades.iterator();
        while (iterador.hasNext()) {
            AbstractFacade facade = iterador.next();
            switch(facade.getType()) {
                case MATERIAL:
                    materialFacade = (MaterialFacade)facade;
                    break;
                case TIPO_MATERIAL:
                    tipoMaterialFacade = (TipoMaterialFacade)facade;
                    break;
                case UNIDAD:
                    unidadFacade = (UnidadFacade)facade;
                    break;
            }
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected void generarMenuDeVista(final HtmlPanelGroup menu, ActionEvent e) {
        menu.getChildren().clear();
        HtmlPanelGrid cuadricula = new HtmlPanelGrid();
        cuadricula.setColumns(2);
        
        cuadricula.getChildren().add(UtilidadesGUI.crearTexto("Nombre de Material"));
        cuadricula.getChildren().add(new HtmlOutputText());
        
        Iterator<Material> materiales = materialFacade.findAll().iterator();
        while (materiales.hasNext()) {
            final Material actual = materiales.next();
            cuadricula.getChildren().add(UtilidadesGUI.crearTexto(actual.getMatnombre()));
            CommandButton ver = UtilidadesGUI.crearBoton("Ver");
            ver.addActionListener(new ActionListener() {

                @Override
                public void processAction(ActionEvent event) throws AbortProcessingException {
                    desplegarADetalle(actual, menu);
                }
            });
            cuadricula.getChildren().add(ver);
        }
        menu.getChildren().add(cuadricula);
        guiPrincipal.validate();
    }
    
    private void desplegarADetalle(Material m, HtmlPanelGroup menu) {
        menu.getChildren().clear();
        HtmlPanelGrid valores = new HtmlPanelGrid();
        valores.setColumns(2);
        
        valores.getChildren().add(UtilidadesGUI.crearTexto("Nombre de Material"));
        valores.getChildren().add(UtilidadesGUI.crearTexto(m.getMatnombre()));
        valores.getChildren().add(UtilidadesGUI.crearTexto("Tipo de Material"));
        valores.getChildren().add(UtilidadesGUI.crearTexto(m.getTmid().getTmnombre()));
        valores.getChildren().add(UtilidadesGUI.crearTexto("Unidad"));
        valores.getChildren().add(UtilidadesGUI.crearTexto(m.getUnid().getUnnombre()));
        menu.getChildren().add(valores);
        menu.getChildren().add(crearBorrarEditar(m));
        guiPrincipal.validate();
    }
    
    private UIComponent crearBorrarEditar(Material m) {
        HtmlPanelGrid cuadricula = new HtmlPanelGrid();
        cuadricula.setColumns(2);
        
        cuadricula.getChildren().add(UtilidadesGUI.crearBoton("Borrar"));
        cuadricula.getChildren().add(UtilidadesGUI.crearBoton("Editar"));
        
        return cuadricula;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected void generarMenuDeCreacion(HtmlPanelGroup menu, ActionEvent e) {
        menu.getChildren().clear();
        
        HtmlPanelGrid cuadricula = new HtmlPanelGrid();
        cuadricula.setColumns(2);
        final InputText ingresoNombre = new InputText();
        final SelectOneMenu ingresoTipoMaterial = new SelectOneMenu();
        final SelectOneMenu ingresoUnidad = new SelectOneMenu();
        
        ArrayList<String> tiposMaterialLista = new ArrayList();
        Iterator<TipoMaterial> tiposMaterial = tipoMaterialFacade.findAll().iterator();
        while (tiposMaterial.hasNext()) {
            tiposMaterialLista.add(tiposMaterial.next().getTmnombre());
        }
        UISelectItems itemsTiposMaterial = new UISelectItems();
        itemsTiposMaterial.setValue(tiposMaterialLista);
        ingresoTipoMaterial.getChildren().add(itemsTiposMaterial);
        
        ArrayList<String> unidadLista = new ArrayList();
        Iterator<Unidad> unidades = unidadFacade.findAll().iterator();
        while (unidades.hasNext()) {
            unidadLista.add(unidades.next().getUnnombre());
        }
        UISelectItems itemsUnidad = new UISelectItems();
        itemsUnidad.setValue(unidadLista);
        ingresoUnidad.getChildren().add(itemsUnidad);
        
        cuadricula.getChildren().add(UtilidadesGUI.crearEtiqueta("Nombre de Material"));
        cuadricula.getChildren().add(ingresoNombre);
        cuadricula.getChildren().add(UtilidadesGUI.crearEtiqueta("Tipo de Material"));
        cuadricula.getChildren().add(ingresoTipoMaterial);
        cuadricula.getChildren().add(UtilidadesGUI.crearEtiqueta("Unidad de Medida"));
        cuadricula.getChildren().add(ingresoUnidad);
        
        CommandButton submit = UtilidadesGUI.crearBoton("Ingresar");
        submit.addActionListener(new ActionListener() {

            @Override
            public void processAction(ActionEvent event) throws AbortProcessingException {
                Material m = new Material();
                m.setMatnombre(ingresoNombre.getValue().toString());
                
                Iterator<TipoMaterial> iteradorTiposMaterial = tipoMaterialFacade.findAll().iterator();
                while (iteradorTiposMaterial.hasNext()) {
                    TipoMaterial tm = iteradorTiposMaterial.next();
                    if (tm.getTmnombre().equals(ingresoTipoMaterial.getValue())) {
                        m.setTmid(tm);
                        break;
                    } 
                }
                
                Iterator<Unidad> iteradorUnidades = unidadFacade.findAll().iterator();
                while (iteradorUnidades.hasNext()) {
                    Unidad unidad = iteradorUnidades.next();
                    if (unidad.getUnnombre().equals(ingresoUnidad.getValue())) {
                        m.setUnid(unidad);
                        break;
                    } 
                }
                
                materialFacade.create(m);
            }
        });
        
        menu.getChildren().add(cuadricula);
        menu.getChildren().add(submit);
        
        guiPrincipal.validate();
    }
}
