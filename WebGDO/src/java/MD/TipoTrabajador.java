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
@Table(name = "tipostrabajador")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoTrabajador.findAll", query = "SELECT t FROM TipoTrabajador t"),
    @NamedQuery(name = "TipoTrabajador.findByTtid", query = "SELECT t FROM TipoTrabajador t WHERE t.ttid = :ttid"),
    @NamedQuery(name = "TipoTrabajador.findByTtnombre", query = "SELECT t FROM TipoTrabajador t WHERE t.ttnombre = :ttnombre")})
public class TipoTrabajador implements Serializable, Entidad {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "TTID")
    private Integer ttid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "TTNOMBRE")
    private String ttnombre;
    @OneToMany(mappedBy = "ttid")
    private Collection<Empleado> empleadoCollection;

    public TipoTrabajador() {
    }

    public TipoTrabajador(Integer ttid) {
        this.ttid = ttid;
    }

    public TipoTrabajador(Integer ttid, String ttnombre) {
        this.ttid = ttid;
        this.ttnombre = ttnombre;
    }

    public Integer getTtid() {
        return ttid;
    }

    public void setTtid(Integer ttid) {
        this.ttid = ttid;
    }

    public String getTtnombre() {
        return ttnombre;
    }

    public void setTtnombre(String ttnombre) {
        this.ttnombre = ttnombre;
    }

    @XmlTransient
    public Collection<Empleado> getEmpleadoCollection() {
        return empleadoCollection;
    }

    public void setEmpleadoCollection(Collection<Empleado> empleadoCollection) {
        this.empleadoCollection = empleadoCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ttid != null ? ttid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoTrabajador)) {
            return false;
        }
        TipoTrabajador other = (TipoTrabajador) object;
        if ((this.ttid == null && other.ttid != null) || (this.ttid != null && !this.ttid.equals(other.ttid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "MD.TipoTrabajador[ ttid=" + ttid + " ]";
    }

    @Override
    public Object getID() {
        return getTtid();
    }

    @Override
    public String getCadenaDesplegable() {
        return getTtnombre();
    }
    
}
