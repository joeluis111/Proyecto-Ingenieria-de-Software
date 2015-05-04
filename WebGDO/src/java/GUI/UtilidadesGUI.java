/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import javax.faces.component.html.HtmlOutputText;
import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.component.outputlabel.OutputLabel;

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
}
