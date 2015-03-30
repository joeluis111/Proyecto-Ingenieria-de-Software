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

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Statement;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name="PruebasUnitarias")
@SessionScoped
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
        PrintWriter bitacora = null;
        try {
            bitacora = new PrintWriter("C:\\Users\\Kenny\\Documents\\GitHub\\Proyecto-Ingenieria-de-Software\\Proyecto_Gestion_de_Obra\\pruebasUnitarias.txt");
        }
        catch (Exception e) {}
        try {
            bitacora.append("Empezando...\n");
            
            MysqlDataSource dataSource = new MysqlDataSource();
            dataSource.setURL("jdbc:mysql://localhost:3306/conexiongdo?user=root&password=password");
            
            Connection conn = dataSource.getConnection();
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("use conexiongdo;");
            stmt.executeUpdate("INSERT INTO clientes VALUES (1, 'Juarez', 'Direccion', '12345');");
            
            bitacora.append("EXITO\n");
        }
        catch (Exception e) {
            bitacora.append(e.getMessage()+"\n");
            e.printStackTrace(bitacora);
        }
        finally {
            if (bitacora != null)
                bitacora.close();
        }
    }
    
    private static void probarProyecto() {
        
    }
    
    private static void probarContrato() {
        
    }
}
