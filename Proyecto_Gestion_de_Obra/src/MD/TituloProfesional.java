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
@Table(name = "titulosprofesionales")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Titulosprofesionales.findAll", query = "SELECT t FROM Titulosprofesionales t"),
    @NamedQuery(name = "Titulosprofesionales.findByTituloProfesionalID", query = "SELECT t FROM Titulosprofesionales t WHERE t.tituloProfesionalID = :tituloProfesionalID"),
    @NamedQuery(name = "Titulosprofesionales.findByNombre", query = "SELECT t FROM Titulosprofesionales t WHERE t.nombre = :nombre")})
public class TituloProfesional implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "tituloProfesionalID")
    private Integer tituloProfesionalID;
    @Size(max = 50)
    @Column(name = "nombre")
    private String nombre;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tituloProfesionalID")
    private Collection<Empleado> empleadosCollection;

    public TituloProfesional() {
    }

    public TituloProfesional(Integer tituloProfesionalID) {
        this.tituloProfesionalID = tituloProfesionalID;
    }

    public Integer getTituloProfesionalID() {
        return tituloProfesionalID;
    }

    public void setTituloProfesionalID(Integer tituloProfesionalID) {
        this.tituloProfesionalID = tituloProfesionalID;
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
        hash += (tituloProfesionalID != null ? tituloProfesionalID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TituloProfesional)) {
            return false;
        }
        TituloProfesional other = (TituloProfesional) object;
        if ((this.tituloProfesionalID == null && other.tituloProfesionalID != null) || (this.tituloProfesionalID != null && !this.tituloProfesionalID.equals(other.tituloProfesionalID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "MD.Titulosprofesionales[ tituloProfesionalID=" + tituloProfesionalID + " ]";
    }
    
}
