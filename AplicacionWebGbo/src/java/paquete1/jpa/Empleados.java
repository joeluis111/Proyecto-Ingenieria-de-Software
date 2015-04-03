/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package paquete1.jpa;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Bryan
 */
@Entity
@Table(name = "empleados")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Empleados.findAll", query = "SELECT e FROM Empleados e"),
    @NamedQuery(name = "Empleados.findByPersonalID", query = "SELECT e FROM Empleados e WHERE e.personalID = :personalID"),
    @NamedQuery(name = "Empleados.findByCedula", query = "SELECT e FROM Empleados e WHERE e.cedula = :cedula"),
    @NamedQuery(name = "Empleados.findByNombre", query = "SELECT e FROM Empleados e WHERE e.nombre = :nombre"),
    @NamedQuery(name = "Empleados.findByDireccion", query = "SELECT e FROM Empleados e WHERE e.direccion = :direccion"),
    @NamedQuery(name = "Empleados.findByFechaNacimiento", query = "SELECT e FROM Empleados e WHERE e.fechaNacimiento = :fechaNacimiento"),
    @NamedQuery(name = "Empleados.findByGenero", query = "SELECT e FROM Empleados e WHERE e.genero = :genero"),
    @NamedQuery(name = "Empleados.findByNumeroTelefono", query = "SELECT e FROM Empleados e WHERE e.numeroTelefono = :numeroTelefono"),
    @NamedQuery(name = "Empleados.findByCodigoPostal", query = "SELECT e FROM Empleados e WHERE e.codigoPostal = :codigoPostal")})
public class Empleados implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "personalID")
    private Integer personalID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "cedula")
    private String cedula;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "direccion")
    private String direccion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechaNacimiento")
    @Temporal(TemporalType.DATE)
    private Date fechaNacimiento;
    @Basic(optional = false)
    @NotNull
    @Column(name = "genero")
    private char genero;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "numeroTelefono")
    private String numeroTelefono;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "codigoPostal")
    private String codigoPostal;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Column(name = "contrato")
    private byte[] contrato;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Column(name = "IESS")
    private byte[] iess;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Column(name = "permisos")
    private byte[] permisos;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Column(name = "certificados")
    private byte[] certificados;
    @JoinTable(name = "trabaja", joinColumns = {
        @JoinColumn(name = "personalID", referencedColumnName = "personalID")}, inverseJoinColumns = {
        @JoinColumn(name = "proyectoID", referencedColumnName = "proyectoID")})
    @ManyToMany
    private Collection<Proyectos> proyectosCollection;
    @JoinColumn(name = "tituloProfesionalID", referencedColumnName = "tituloProfesionalID")
    @ManyToOne(optional = false)
    private Titulosprofesionales tituloProfesionalID;
    @JoinColumn(name = "tipoTrabajadorID", referencedColumnName = "tipoTrabajadorID")
    @ManyToOne(optional = false)
    private Tipostrabajador tipoTrabajadorID;

    public Empleados() {
    }

    public Empleados(Integer personalID) {
        this.personalID = personalID;
    }

    public Empleados(Integer personalID, String cedula, String nombre, String direccion, Date fechaNacimiento, char genero, String numeroTelefono, String codigoPostal, byte[] contrato, byte[] iess, byte[] permisos, byte[] certificados) {
        this.personalID = personalID;
        this.cedula = cedula;
        this.nombre = nombre;
        this.direccion = direccion;
        this.fechaNacimiento = fechaNacimiento;
        this.genero = genero;
        this.numeroTelefono = numeroTelefono;
        this.codigoPostal = codigoPostal;
        this.contrato = contrato;
        this.iess = iess;
        this.permisos = permisos;
        this.certificados = certificados;
    }

    public Integer getPersonalID() {
        return personalID;
    }

    public void setPersonalID(Integer personalID) {
        this.personalID = personalID;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public char getGenero() {
        return genero;
    }

    public void setGenero(char genero) {
        this.genero = genero;
    }

    public String getNumeroTelefono() {
        return numeroTelefono;
    }

    public void setNumeroTelefono(String numeroTelefono) {
        this.numeroTelefono = numeroTelefono;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public byte[] getContrato() {
        return contrato;
    }

    public void setContrato(byte[] contrato) {
        this.contrato = contrato;
    }

    public byte[] getIess() {
        return iess;
    }

    public void setIess(byte[] iess) {
        this.iess = iess;
    }

    public byte[] getPermisos() {
        return permisos;
    }

    public void setPermisos(byte[] permisos) {
        this.permisos = permisos;
    }

    public byte[] getCertificados() {
        return certificados;
    }

    public void setCertificados(byte[] certificados) {
        this.certificados = certificados;
    }

    @XmlTransient
    public Collection<Proyectos> getProyectosCollection() {
        return proyectosCollection;
    }

    public void setProyectosCollection(Collection<Proyectos> proyectosCollection) {
        this.proyectosCollection = proyectosCollection;
    }

    public Titulosprofesionales getTituloProfesionalID() {
        return tituloProfesionalID;
    }

    public void setTituloProfesionalID(Titulosprofesionales tituloProfesionalID) {
        this.tituloProfesionalID = tituloProfesionalID;
    }

    public Tipostrabajador getTipoTrabajadorID() {
        return tipoTrabajadorID;
    }

    public void setTipoTrabajadorID(Tipostrabajador tipoTrabajadorID) {
        this.tipoTrabajadorID = tipoTrabajadorID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (personalID != null ? personalID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Empleados)) {
            return false;
        }
        Empleados other = (Empleados) object;
        if ((this.personalID == null && other.personalID != null) || (this.personalID != null && !this.personalID.equals(other.personalID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "paquete1.jpa.Empleados[ personalID=" + personalID + " ]";
    }
    
}
