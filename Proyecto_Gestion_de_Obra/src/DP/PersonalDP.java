/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DP;

import java.sql.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.imageio.IIOImage;

/**
 *
 * @author Kenny
 */
@ManagedBean
@RequestScoped
public class PersonalDP {
    
    @ManagedProperty(value="#{nombre}")
    private String nombre;
    
    @ManagedProperty(value="#{direccion}")
    private String direccion;
    
    @ManagedProperty(value="#{fechaNacimiento}")
    private Date fechaNacimiento;
    
    @ManagedProperty(value="#{tituloTrabajo}")
    private String tituloTrabajo;
    
    @ManagedProperty(value="#{tipoTrabajador}")
    private String tipoTrabajador;
    
    @ManagedProperty(value="#{fechaCapacitacion}")
    private Date fechaCapacitacion;
    
    @ManagedProperty(value="#{fechaTerminacion}")
    private Date fechaTerminacion;
    
    @ManagedProperty(value="#{vacaciones}")
    private Double vacaciones;
    
    @ManagedProperty(value="#{genero}")
    private char genero;
    
    @ManagedProperty(value="#{numeroTelefono}")
    private String numeroTelefono;
    
    @ManagedProperty(value="#{cedula}")
    private String cedula;
    
    @ManagedProperty(value="#{codigoPostal}")
    private String codigoPostal;
    
    @ManagedProperty(value="#{contrato}")
    private IIOImage contrato;
    
    @ManagedProperty(value="#{IESS}")
    private IIOImage IESS;
    
    @ManagedProperty(value="#{permisos}")
    private IIOImage permisos;
    
    @ManagedProperty(value="#{certificados}")
    private IIOImage certificados;
    
    /**
     * Creates a new instance of PersonalDP
     */
    public PersonalDP() {
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String valor) {
        nombre = valor;
    }
    
    public String getDireccion() {
        return direccion;
    }
    
    public void setDireccion(String valor) {
        direccion = valor;
    }
    
    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }
    
    public void setFechaNacimiento(Date valor) {
        fechaNacimiento = valor;
    }
    
    public String getTituloTrabajo() {
        return tituloTrabajo;
    }
    
    public void setTituloTrabajo(String valor) {
        tituloTrabajo = valor;
    }
    
    public String getTipoTrabajador() {
        return tipoTrabajador;
    }
    
    public void setTipoTrabajador(String valor) {
        tipoTrabajador = valor;
    }
    
    public Date getFechaCapacitacion() {
        return fechaCapacitacion;
    }
    
    public void setFechaCapacitacion(Date valor) {
        fechaCapacitacion = valor;
    }
    
    public Date getFechaTerminacion() {
        return fechaTerminacion;
    }
    
    public void setFechaTerminacion(Date valor) {
        fechaTerminacion = valor;
    }
    
    public Double getVacaciones() {
        return vacaciones;
    }
    
    public void setVacaciones(Double valor) {
        vacaciones = valor;
    }
    
    public char getGenero() {
        return genero;
    }
    
    public void setGenero(char valor) {
        genero = valor;
    }
    
    public String getNumeroTelefono() {
        return numeroTelefono;
    }
    
    public void setNumeroTelefono(String valor) {
        numeroTelefono = valor;
    }
    
    public String getCedula() {
        return cedula;
    }
    
    public void setCedula(String valor) {
        cedula = valor;
    }
    
    public String getCodigoPostal() {
        return codigoPostal;
    }
    
    public void setCodigoPostal(String valor) {
        codigoPostal = valor;
    }
    
    public IIOImage getContrato() {
        return contrato;
    }
    
    public void setContrato(IIOImage valor) {
        contrato = valor;
    }
    
    public IIOImage getIESS() {
        return IESS;
    }
    
    public void setIESS(IIOImage valor) {
        IESS = valor;
    }
    
    public IIOImage getPermisos() {
        return permisos;
    }
    
    public void setPermisos(IIOImage valor) {
        permisos = valor;
    }
    
    public IIOImage getCertificados() {
        return certificados;
    }
    
    public void setCertificados(IIOImage valor) {
        certificados = valor;
    }
}
