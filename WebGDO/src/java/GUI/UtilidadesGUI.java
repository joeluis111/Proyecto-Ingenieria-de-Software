/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import javax.faces.component.html.HtmlOutputText;
import org.primefaces.component.commandbutton.CommandButton;

/**
 *
 * @author Kenny
 */
public class UtilidadesGUI {
    public static CommandButton crearBotonDeVer() {
        CommandButton ver = new CommandButton();
        ver.setValue("Ver");
        
        return ver;
    }
    
    public static HtmlOutputText crearTexto(Object s) {
        HtmlOutputText t = new HtmlOutputText();
        t.setValue(s);
        return t;
    }
}
