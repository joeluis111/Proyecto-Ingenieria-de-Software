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
    @NamedQuery(name = "Tipostrabajador.findAll", query = "SELECT t FROM Tipostrabajador t"),
    @NamedQuery(name = "Tipostrabajador.findByTipoTrabajadorID", query = "SELECT t FROM Tipostrabajador t WHERE t.tipoTrabajadorID = :tipoTrabajadorID"),
    @NamedQuery(name = "Tipostrabajador.findByNombre", query = "SELECT t FROM Tipostrabajador t WHERE t.nombre = :nombre")})
public class TipoTrabajador implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "tipoTrabajadorID")
    private Integer tipoTrabajadorID;
    @Size(max = 50)
    @Column(name = "nombre")
    private String nombre;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipoTrabajadorID")
    private Collection<Empleado> empleadosCollection;

    public TipoTrabajador() {
    }

    public TipoTrabajador(Integer tipoTrabajadorID) {
        this.tipoTrabajadorID = tipoTrabajadorID;
    }

    public Integer getTipoTrabajadorID() {
        return tipoTrabajadorID;
    }

    public void setTipoTrabajadorID(Integer tipoTrabajadorID) {
        this.tipoTrabajadorID = tipoTrabajadorID;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @XmlTransient
    public Collection<Empleado> getEmpleadosCollection() {
        return empleadosCollection;
    }

    public void setEmpleadosCollection(Collection<Empleado> empleadosCollection) {
        this.empleadosCollection = empleadosCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tipoTrabajadorID != null ? tipoTrabajadorID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoTrabajador)) {
            return false;
        }
        TipoTrabajador other = (TipoTrabajador) object;
        if ((this.tipoTrabajadorID == null && other.tipoTrabajadorID != null) || (this.tipoTrabajadorID != null && !this.tipoTrabajadorID.equals(other.tipoTrabajadorID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "MD.Tipostrabajador[ tipoTrabajadorID=" + tipoTrabajadorID + " ]";
    }
    
}
