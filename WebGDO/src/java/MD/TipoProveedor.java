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
@Table(name = "tiposproveedor")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoProveedor.findAll", query = "SELECT t FROM TipoProveedor t"),
    @NamedQuery(name = "TipoProveedor.findByTproid", query = "SELECT t FROM TipoProveedor t WHERE t.tproid = :tproid"),
    @NamedQuery(name = "TipoProveedor.findByTpronombre", query = "SELECT t FROM TipoProveedor t WHERE t.tpronombre = :tpronombre")})
public class TipoProveedor implements Serializable, Entidad {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "TPROID")
    private Integer tproid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "TPRONOMBRE")
    private String tpronombre;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tproid")
    private Collection<Proveedor> proveedorCollection;

    public TipoProveedor() {
    }

    public TipoProveedor(Integer tproid) {
        this.tproid = tproid;
    }

    public TipoProveedor(Integer tproid, String tpronombre) {
        this.tproid = tproid;
        this.tpronombre = tpronombre;
    }

    public Integer getTproid() {
        return tproid;
    }

    public void setTproid(Integer tproid) {
        this.tproid = tproid;
    }

    public String getTpronombre() {
        return tpronombre;
    }

    public void setTpronombre(String tpronombre) {
        this.tpronombre = tpronombre;
    }

    @XmlTransient
    public Collection<Proveedor> getProveedorCollection() {
        return proveedorCollection;
    }

    public void setProveedorCollection(Collection<Proveedor> proveedorCollection) {
        this.proveedorCollection = proveedorCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tproid != null ? tproid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoProveedor)) {
            return false;
        }
        TipoProveedor other = (TipoProveedor) object;
        if ((this.tproid == null && other.tproid != null) || (this.tproid != null && !this.tproid.equals(other.tproid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "MD.TipoProveedor[ tproid=" + tproid + " ]";
    }

    @Override
    public Object getIdentidad() {
        return this.getTproid();
    }

    @Override
    public String getCadenaDesplegable() {
        return this.getTpronombre();
    }
    
}
