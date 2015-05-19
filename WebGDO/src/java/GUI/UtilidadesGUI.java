/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import DP.AbstractFacade;
import MD.Entidad;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import javax.faces.component.UISelectItems;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.component.html.HtmlPanelGrid;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import javax.faces.model.SelectItem;
import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.component.outputlabel.OutputLabel;
import org.primefaces.component.selectonemenu.SelectOneMenu;

/**
 *
 * @author Kenny
 */
public class UtilidadesGUI {
    public static CommandButton crearBoton(String s) {
        CommandButton b = new CommandButton();
        b.setValue(s);
        
        return b;
    }
    
    public static HtmlOutputText crearTexto(Object s) {
        HtmlOutputText t = new HtmlOutputText();
        t.setValue(s);
        return t;
    }
    
    public static OutputLabel crearEtiqueta(Object s) {
        OutputLabel o = new OutputLabel();
        o.setValue(s);
        return o;
    }
    
    public static SelectOneMenu crearCombobox(AbstractFacade facade) {
        SelectOneMenu combobox = new SelectOneMenu();
        Iterator<Entidad> entidades = facade.findAll().iterator();
        Collection<SelectItem> items = new ArrayList();
        while (entidades.hasNext()) {
            Entidad e = entidades.next();
            items.add(new SelectItem(e.getIdentidad(), e.getCadenaDesplegable()));
        }
        UISelectItems itemsSeleccionables = new UISelectItems();
        itemsSeleccionables.setValue(items);
        combobox.getChildren().add(itemsSeleccionables);
        return combobox;
    }
    
    public static HtmlPanelGrid crearBorrarEditar(final AbstractFacade facade, final Entidad e, ActionListener accionEditar) {
        HtmlPanelGrid cuadricula = new HtmlPanelGrid();
        
        CommandButton borrar = crearBoton("Borrar");
        CommandButton editar = crearBoton("Editar");
        
        borrar.addActionListener(new ActionListener() {

            @Override
            public void processAction(ActionEvent event) throws AbortProcessingException {
                facade.remove(e);
            }
        });
        
        editar.addActionListener(accionEditar);
        
        cuadricula.setColumns(2);
        cuadricula.getChildren().add(borrar);
        cuadricula.getChildren().add(editar);
        
        return cuadricula;
    }
}
