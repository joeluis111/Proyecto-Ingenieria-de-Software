/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package paquete1.jpa;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Bryan
 */
@Entity
@Table(name = "proyectos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Proyectos.findAll", query = "SELECT p FROM Proyectos p"),
    @NamedQuery(name = "Proyectos.findByProyectoID", query = "SELECT p FROM Proyectos p WHERE p.proyectoID = :proyectoID"),
    @NamedQuery(name = "Proyectos.findByFechaInicio", query = "SELECT p FROM Proyectos p WHERE p.fechaInicio = :fechaInicio"),
    @NamedQuery(name = "Proyectos.findByFechaTerminacion", query = "SELECT p FROM Proyectos p WHERE p.fechaTerminacion = :fechaTerminacion")})
public class Proyectos implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "proyectoID")
    private Integer proyectoID;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechaInicio")
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;
    @Column(name = "fechaTerminacion")
    @Temporal(TemporalType.DATE)
    private Date fechaTerminacion;
    @ManyToMany(mappedBy = "proyectosCollection")
    private Collection<Empleados> empleadosCollection;
    @JoinColumn(name = "calendarioID", referencedColumnName = "calendarioID")
    @ManyToOne(optional = false)
    private Calendarios calendarioID;
    @JoinColumn(name = "contratoID", referencedColumnName = "contratoID")
    @ManyToOne(optional = false)
    private Contratos contratoID;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "proyectos")
    private Collection<Historiainventaria> historiainventariaCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "proyectos")
    private Collection<Inventarios> inventariosCollection;

    public Proyectos() {
    }

    public Proyectos(Integer proyectoID) {
        this.proyectoID = proyectoID;
    }

    public Proyectos(Integer proyectoID, Date fechaInicio) {
        this.proyectoID = proyectoID;
        this.fechaInicio = fechaInicio;
    }

    public Integer getProyectoID() {
        return proyectoID;
    }

    public void setProyectoID(Integer proyectoID) {
        this.proyectoID = proyectoID;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaTerminacion() {
        return fechaTerminacion;
    }

    public void setFechaTerminacion(Date fechaTerminacion) {
        this.fechaTerminacion = fechaTerminacion;
    }

    @XmlTransient
    public Collection<Empleados> getEmpleadosCollection() {
        return empleadosCollection;
    }

    public void setEmpleadosCollection(Collection<Empleados> empleadosCollection) {
        this.empleadosCollection = empleadosCollection;
    }

    public Calendarios getCalendarioID() {
        return calendarioID;
    }

    public void setCalendarioID(Calendarios calendarioID) {
        this.calendarioID = calendarioID;
    }

    public Contratos getContratoID() {
        return contratoID;
    }

    public void setContratoID(Contratos contratoID) {
        this.contratoID = contratoID;
    }

    @XmlTransient
    public Collection<Historiainventaria> getHistoriainventariaCollection() {
        return historiainventariaCollection;
    }

    public void setHistoriainventariaCollection(Collection<Historiainventaria> historiainventariaCollection) {
        this.historiainventariaCollection = historiainventariaCollection;
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
        hash += (proyectoID != null ? proyectoID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Proyectos)) {
            return false;
        }
        Proyectos other = (Proyectos) object;
        if ((this.proyectoID == null && other.proyectoID != null) || (this.proyectoID != null && !this.proyectoID.equals(other.proyectoID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "paquete1.jpa.Proyectos[ proyectoID=" + proyectoID + " ]";
    }
    
}
