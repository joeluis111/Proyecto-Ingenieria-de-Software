/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MD;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
 * @author Kenny
 */
@Entity
@Table(name = "tiposmaterial")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoMaterial.findAll", query = "SELECT t FROM TipoMaterial t"),
    @NamedQuery(name = "TipoMaterial.findByTmid", query = "SELECT t FROM TipoMaterial t WHERE t.tmid = :tmid"),
    @NamedQuery(name = "TipoMaterial.findByTmnombre", query = "SELECT t FROM TipoMaterial t WHERE t.tmnombre = :tmnombre"),
    @NamedQuery(name = "TipoMaterial.findByTmdescripcion", query = "SELECT t FROM TipoMaterial t WHERE t.tmdescripcion = :tmdescripcion")})
public class TipoMaterial implements Serializable, Entidad {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "TMID")
    private Integer tmid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "TMNOMBRE")
    private String tmnombre;
    @Size(max = 255)
    @Column(name = "TMDESCRIPCION")
    private String tmdescripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tmid")
    private Collection<Material> materialCollection;

    public TipoMaterial() {
    }

    public TipoMaterial(Integer tmid) {
        this.tmid = tmid;
    }

    public TipoMaterial(Integer tmid, String tmnombre) {
        this.tmid = tmid;
        this.tmnombre = tmnombre;
    }

    public Integer getTmid() {
        return tmid;
    }

    public void setTmid(Integer tmid) {
        this.tmid = tmid;
    }

    public String getTmnombre() {
        return tmnombre;
    }

    public void setTmnombre(String tmnombre) {
        this.tmnombre = tmnombre;
    }

    public String getTmdescripcion() {
        return tmdescripcion;
    }

    public void setTmdescripcion(String tmdescripcion) {
        this.tmdescripcion = tmdescripcion;
    }

    @XmlTransient
    public Collection<Material> getMaterialCollection() {
        return materialCollection;
    }

    public void setMaterialCollection(Collection<Material> materialCollection) {
        this.materialCollection = materialCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tmid != null ? tmid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoMaterial)) {
            return false;
        }
        TipoMaterial other = (TipoMaterial) object;
        if ((this.tmid == null && other.tmid != null) || (this.tmid != null && !this.tmid.equals(other.tmid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "MD.TipoMaterial[ tmid=" + tmid + " ]";
    }

    @Override
    public Object getIdentidad() {
        return this.getTmid();
    }

    @Override
    public String getCadenaDesplegable() {
        return this.getTmnombre();
    }
    
}
