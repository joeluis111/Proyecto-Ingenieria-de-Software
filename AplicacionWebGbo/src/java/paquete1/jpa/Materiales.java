/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package paquete1.jpa;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
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
@Table(name = "materiales")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Materiales.findAll", query = "SELECT m FROM Materiales m"),
    @NamedQuery(name = "Materiales.findByMaterialID", query = "SELECT m FROM Materiales m WHERE m.materialID = :materialID"),
    @NamedQuery(name = "Materiales.findByNombre", query = "SELECT m FROM Materiales m WHERE m.nombre = :nombre"),
    @NamedQuery(name = "Materiales.findByTamano", query = "SELECT m FROM Materiales m WHERE m.tamano = :tamano")})
public class Materiales implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "materialID")
    private Integer materialID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "nombre")
    private String nombre;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "tamano")
    private BigDecimal tamano;
    @ManyToMany(mappedBy = "materialesCollection")
    private Collection<Proveedores> proveedoresCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "materiales")
    private Collection<Historiainventaria> historiainventariaCollection;
    @JoinColumn(name = "unidadID", referencedColumnName = "unidadID")
    @ManyToOne(optional = false)
    private Unidades unidadID;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "materiales")
    private Collection<Inventarios> inventariosCollection;

    public Materiales() {
    }

    public Materiales(Integer materialID) {
        this.materialID = materialID;
    }

    public Materiales(Integer materialID, String nombre, BigDecimal tamano) {
        this.materialID = materialID;
        this.nombre = nombre;
        this.tamano = tamano;
    }

    public Integer getMaterialID() {
        return materialID;
    }

    public void setMaterialID(Integer materialID) {
        this.materialID = materialID;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigDecimal getTamano() {
        return tamano;
    }

    public void setTamano(BigDecimal tamano) {
        this.tamano = tamano;
    }

    @XmlTransient
    public Collection<Proveedores> getProveedoresCollection() {
        return proveedoresCollection;
    }

    public void setProveedoresCollection(Collection<Proveedores> proveedoresCollection) {
        this.proveedoresCollection = proveedoresCollection;
    }

    @XmlTransient
    public Collection<Historiainventaria> getHistoriainventariaCollection() {
        return historiainventariaCollection;
    }

    public void setHistoriainventariaCollection(Collection<Historiainventaria> historiainventariaCollection) {
        this.historiainventariaCollection = historiainventariaCollection;
    }

    public Unidades getUnidadID() {
        return unidadID;
    }

    public void setUnidadID(Unidades unidadID) {
        this.unidadID = unidadID;
    }

    @XmlTransient
    public Collection<Inventarios> getInventariosCollection() {
        return inventariosCollection;
    }

    public void setInventariosCollection(Collection<Inventarios> inventariosCollection) {
        this.inventariosCollection = inventariosCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (materialID != null ? materialID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Materiales)) {
            return false;
        }
        Materiales other = (Materiales) object;
        if ((this.materialID == null && other.materialID != null) || (this.materialID != null && !this.materialID.equals(other.materialID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "paquete1.jpa.Materiales[ materialID=" + materialID + " ]";
    }
    
}
