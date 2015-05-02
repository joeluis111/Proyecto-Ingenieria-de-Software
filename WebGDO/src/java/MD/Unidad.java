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
@Table(name = "unidades")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Unidad.findAll", query = "SELECT u FROM Unidad u"),
    @NamedQuery(name = "Unidad.findByUnid", query = "SELECT u FROM Unidad u WHERE u.unid = :unid"),
    @NamedQuery(name = "Unidad.findByUnnombre", query = "SELECT u FROM Unidad u WHERE u.unnombre = :unnombre")})
public class Unidad implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "UNID")
    private Integer unid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "UNNOMBRE")
    private String unnombre;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "unid")
    private Collection<Material> materialCollection;

    public Unidad() {
    }

    public Unidad(Integer unid) {
        this.unid = unid;
    }

    public Unidad(Integer unid, String unnombre) {
        this.unid = unid;
        this.unnombre = unnombre;
    }

    public Integer getUnid() {
        return unid;
    }

    public void setUnid(Integer unid) {
        this.unid = unid;
    }

    public String getUnnombre() {
        return unnombre;
    }

    public void setUnnombre(String unnombre) {
        this.unnombre = unnombre;
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
        hash += (unid != null ? unid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Unidad)) {
            return false;
        }
        Unidad other = (Unidad) object;
        if ((this.unid == null && other.unid != null) || (this.unid != null && !this.unid.equals(other.unid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "MD.Unidad[ unid=" + unid + " ]";
    }
    
}
