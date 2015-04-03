/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package paquete1.jpa;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
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
@Table(name = "tiposproveedor")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tiposproveedor.findAll", query = "SELECT t FROM Tiposproveedor t"),
    @NamedQuery(name = "Tiposproveedor.findByTipoProveedorID", query = "SELECT t FROM Tiposproveedor t WHERE t.tipoProveedorID = :tipoProveedorID"),
    @NamedQuery(name = "Tiposproveedor.findByTipo", query = "SELECT t FROM Tiposproveedor t WHERE t.tipo = :tipo")})
public class Tiposproveedor implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "tipoProveedorID")
    private Integer tipoProveedorID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "tipo")
    private String tipo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipoProveedorID")
    private Collection<Proveedores> proveedoresCollection;

    public Tiposproveedor() {
    }

    public Tiposproveedor(Integer tipoProveedorID) {
        this.tipoProveedorID = tipoProveedorID;
    }

    public Tiposproveedor(Integer tipoProveedorID, String tipo) {
        this.tipoProveedorID = tipoProveedorID;
        this.tipo = tipo;
    }

    public Integer getTipoProveedorID() {
        return tipoProveedorID;
    }

    public void setTipoProveedorID(Integer tipoProveedorID) {
        this.tipoProveedorID = tipoProveedorID;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @XmlTransient
    public Collection<Proveedores> getProveedoresCollection() {
        return proveedoresCollection;
    }

    public void setProveedoresCollection(Collection<Proveedores> proveedoresCollection) {
        this.proveedoresCollection = proveedoresCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tipoProveedorID != null ? tipoProveedorID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tiposproveedor)) {
            return false;
        }
        Tiposproveedor other = (Tiposproveedor) object;
        if ((this.tipoProveedorID == null && other.tipoProveedorID != null) || (this.tipoProveedorID != null && !this.tipoProveedorID.equals(other.tipoProveedorID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "paquete1.jpa.Tiposproveedor[ tipoProveedorID=" + tipoProveedorID + " ]";
    }
    
}
