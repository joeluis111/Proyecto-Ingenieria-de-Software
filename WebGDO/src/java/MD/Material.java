/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MD;

import static MD.EntityType.MATERIAL;
import java.io.Serializable;
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
 * @author Kenny
 */
@Entity
@Table(name = "materiales")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Material.findAll", query = "SELECT m FROM Material m"),
    @NamedQuery(name = "Material.findByMatid", query = "SELECT m FROM Material m WHERE m.matid = :matid"),
    @NamedQuery(name = "Material.findByMatnombre", query = "SELECT m FROM Material m WHERE m.matnombre = :matnombre")})
public class Material implements Serializable, Entidad {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "MATID")
    private Integer matid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "MATNOMBRE")
    private String matnombre;
    @ManyToMany(mappedBy = "materialCollection")
    private Collection<Proveedor> proveedorCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "material")
    private Collection<UsoPlaneado> usoPlaneadoCollection;
    @JoinColumn(name = "UNID", referencedColumnName = "UNID")
    @ManyToOne(optional = false)
    private Unidad unid;
    @JoinColumn(name = "TMID", referencedColumnName = "TMID")
    @ManyToOne(optional = false)
    private TipoMaterial tmid;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "material")
    private Collection<Inventario> inventarioCollection;

    public Material() {
    }

    public Material(Integer matid) {
        this.matid = matid;
    }

    public Material(Integer matid, String matnombre) {
        this.matid = matid;
        this.matnombre = matnombre;
    }

    public Integer getMatid() {
        return matid;
    }

    public void setMatid(Integer matid) {
        this.matid = matid;
    }

    public String getMatnombre() {
        return matnombre;
    }

    public void setMatnombre(String matnombre) {
        this.matnombre = matnombre;
    }

    @XmlTransient
    public Collection<Proveedor> getProveedorCollection() {
        return proveedorCollection;
    }

    public void setProveedorCollection(Collection<Proveedor> proveedorCollection) {
        this.proveedorCollection = proveedorCollection;
    }

    @XmlTransient
    public Collection<UsoPlaneado> getUsoPlaneadoCollection() {
        return usoPlaneadoCollection;
    }

    public void setUsoPlaneadoCollection(Collection<UsoPlaneado> usoPlaneadoCollection) {
        this.usoPlaneadoCollection = usoPlaneadoCollection;
    }

    public Unidad getUnid() {
        return unid;
    }

    public void setUnid(Unidad unid) {
        this.unid = unid;
    }

    public TipoMaterial getTmid() {
        return tmid;
    }

    public void setTmid(TipoMaterial tmid) {
        this.tmid = tmid;
    }

    @XmlTransient
    public Collection<Inventario> getInventarioCollection() {
        return inventarioCollection;
    }

    public void setInventarioCollection(Collection<Inventario> inventarioCollection) {
        this.inventarioCollection = inventarioCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (matid != null ? matid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Material)) {
            return false;
        }
        Material other = (Material) object;
        if ((this.matid == null && other.matid != null) || (this.matid != null && !this.matid.equals(other.matid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "MD.Material[ matid=" + matid + " ]";
    }

    @Override
    public Object getIdentidad() {
        return this.getMatid();
    }

    @Override
    public String getCadenaDesplegable() {
        return this.getMatnombre();
    }

    @Override
    public EntityType getType() {
        return MATERIAL;
    }
    
}
