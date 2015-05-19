/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MD;

import static MD.EntityType.TITULO_PROFESIONAL;
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
@Table(name = "titulosprofesionales")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TituloProfesional.findAll", query = "SELECT t FROM TituloProfesional t"),
    @NamedQuery(name = "TituloProfesional.findByTpid", query = "SELECT t FROM TituloProfesional t WHERE t.tpid = :tpid"),
    @NamedQuery(name = "TituloProfesional.findByTpnombre", query = "SELECT t FROM TituloProfesional t WHERE t.tpnombre = :tpnombre")})
public class TituloProfesional implements Serializable, Entidad {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "TPID")
    private Integer tpid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "TPNOMBRE")
    private String tpnombre;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tpid")
    private Collection<Empleado> empleadoCollection;

    public TituloProfesional() {
    }

    public TituloProfesional(Integer tpid) {
        this.tpid = tpid;
    }

    public TituloProfesional(Integer tpid, String tpnombre) {
        this.tpid = tpid;
        this.tpnombre = tpnombre;
    }

    public Integer getTpid() {
        return tpid;
    }

    public void setTpid(Integer tpid) {
        this.tpid = tpid;
    }

    public String getTpnombre() {
        return tpnombre;
    }

    public void setTpnombre(String tpnombre) {
        this.tpnombre = tpnombre;
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
        hash += (tpid != null ? tpid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TituloProfesional)) {
            return false;
        }
        TituloProfesional other = (TituloProfesional) object;
        if ((this.tpid == null && other.tpid != null) || (this.tpid != null && !this.tpid.equals(other.tpid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "MD.TituloProfesional[ tpid=" + tpid + " ]";
    }

    @Override
    public Object getIdentidad() {
        return this.getTpid();
    }

    @Override
    public String getCadenaDesplegable() {
        return this.getTpnombre();
    }

    @Override
    public EntityType getType() {
        return TITULO_PROFESIONAL;
    }
    
}
