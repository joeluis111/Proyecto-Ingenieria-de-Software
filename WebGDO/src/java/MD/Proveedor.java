/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MD;

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
 * @author Kenny
 */
@Entity
@Table(name = "proveedores")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Proveedor.findAll", query = "SELECT p FROM Proveedor p"),
    @NamedQuery(name = "Proveedor.findByProvid", query = "SELECT p FROM Proveedor p WHERE p.provid = :provid"),
    @NamedQuery(name = "Proveedor.findByProvnombre", query = "SELECT p FROM Proveedor p WHERE p.provnombre = :provnombre")})
public class Proveedor implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "PROVID")
    private Integer provid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "PROVNOMBRE")
    private String provnombre;
    @JoinTable(name = "prov_mat", joinColumns = {
        @JoinColumn(name = "PROVID", referencedColumnName = "PROVID")}, inverseJoinColumns = {
        @JoinColumn(name = "MATID", referencedColumnName = "MATID")})
    @ManyToMany
    private Collection<Material> materialCollection;
    @JoinColumn(name = "TPROID", referencedColumnName = "TPROID")
    @ManyToOne(optional = false)
    private TipoProveedor tproid;

    public Proveedor() {
    }

    public Proveedor(Integer provid) {
        this.provid = provid;
    }

    public Proveedor(Integer provid, String provnombre) {
        this.provid = provid;
        this.provnombre = provnombre;
    }

    public Integer getProvid() {
        return provid;
    }

    public void setProvid(Integer provid) {
        this.provid = provid;
    }

    public String getProvnombre() {
        return provnombre;
    }

    public void setProvnombre(String provnombre) {
        this.provnombre = provnombre;
    }

    @XmlTransient
    public Collection<Material> getMaterialCollection() {
        return materialCollection;
    }

    public void setMaterialCollection(Collection<Material> materialCollection) {
        this.materialCollection = materialCollection;
    }

    public TipoProveedor getTproid() {
        return tproid;
    }

    public void setTproid(TipoProveedor tproid) {
        this.tproid = tproid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (provid != null ? provid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Proveedor)) {
            return false;
        }
        Proveedor other = (Proveedor) object;
        if ((this.provid == null && other.provid != null) || (this.provid != null && !this.provid.equals(other.provid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "MD.Proveedor[ provid=" + provid + " ]";
    }
    
}
