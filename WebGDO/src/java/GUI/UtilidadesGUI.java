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
import javax.faces.component.UISelectItem;
import javax.faces.component.UISelectItems;
import javax.faces.component.html.HtmlOutputText;
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
            items.add(new SelectItem(e.getID(), e.getCadenaDesplegable()));
        }
        UISelectItems itemsSeleccionables = new UISelectItems();
        itemsSeleccionables.setValue(items);
        combobox.getChildren().add(itemsSeleccionables);
        return combobox;
    }
}
