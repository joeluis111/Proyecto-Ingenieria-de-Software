/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package paquete1.jpa;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Bryan
 */
@Entity
@Table(name = "proveedores")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Proveedores.findAll", query = "SELECT p FROM Proveedores p"),
    @NamedQuery(name = "Proveedores.findByProveedorID", query = "SELECT p FROM Proveedores p WHERE p.proveedorID = :proveedorID"),
    @NamedQuery(name = "Proveedores.findByNombre", query = "SELECT p FROM Proveedores p WHERE p.nombre = :nombre")})
public class Proveedores implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "proveedorID")
    private Integer proveedorID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "nombre")
    private String nombre;
    @JoinTable(name = "provee", joinColumns = {
        @JoinColumn(name = "proveedorID", referencedColumnName = "proveedorID")}, inverseJoinColumns = {
        @JoinColumn(name = "materialID", referencedColumnName = "materialID")})
    @ManyToMany
    private Collection<Materiales> materialesCollection;
    @JoinColumn(name = "tipoProveedorID", referencedColumnName = "tipoProveedorID")
    @ManyToOne(optional = false)
    private Tiposproveedor tipoProveedorID;

    public Proveedores() {
    }

    public Proveedores(Integer proveedorID) {
        this.proveedorID = proveedorID;
    }

    public Proveedores(Integer proveedorID, String nombre) {
        this.proveedorID = proveedorID;
        this.nombre = nombre;
    }

    public Integer getProveedorID() {
        return proveedorID;
    }

    public void setProveedorID(Integer proveedorID) {
        this.proveedorID = proveedorID;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @XmlTransient
    public Collection<Materiales> getMaterialesCollection() {
        return materialesCollection;
    }

    public void setMaterialesCollection(Collection<Materiales> materialesCollection) {
        this.materialesCollection = materialesCollection;
    }

    public Tiposproveedor getTipoProveedorID() {
        return tipoProveedorID;
    }

    public void setTipoProveedorID(Tiposproveedor tipoProveedorID) {
        this.tipoProveedorID = tipoProveedorID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (proveedorID != null ? proveedorID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Proveedores)) {
            return false;
        }
        Proveedores other = (Proveedores) object;
        if ((this.proveedorID == null && other.proveedorID != null) || (this.proveedorID != null && !this.proveedorID.equals(other.proveedorID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "paquete1.jpa.Proveedores[ proveedorID=" + proveedorID + " ]";
    }
    
}
