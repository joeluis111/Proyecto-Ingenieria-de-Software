/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Kenny
 */
package pruebas;

import DP.PersonalDP;

public class PruebasUnitarias {
    public static void correrPruebasUnitarias() {
        probarMaterial();
        probarPersonal();
        probarProyecto();
        probarContrato();
    }
    
    private static void probarMaterial() {
        
    }
    
    private static void probarPersonal() {
        String clase = "DP.PersonalDP, ";
        PersonalDP personal = new PersonalDP();
        personal.cargarPorCedula("0");
        // TODO: completar
    }
    
    private static void probarProyecto() {
        
    }
    
    private static void probarContrato() {
        
    }
}
