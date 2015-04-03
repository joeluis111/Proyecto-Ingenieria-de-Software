/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package paquete1.jpa;

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
 * @author Bryan
 */
@Entity
@Table(name = "calendarios")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Calendarios.findAll", query = "SELECT c FROM Calendarios c"),
    @NamedQuery(name = "Calendarios.findByCalendarioID", query = "SELECT c FROM Calendarios c WHERE c.calendarioID = :calendarioID"),
    @NamedQuery(name = "Calendarios.findByNombre", query = "SELECT c FROM Calendarios c WHERE c.nombre = :nombre")})
public class Calendarios implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "calendarioID")
    private Integer calendarioID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "nombre")
    private String nombre;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "calendarioID")
    private Collection<Proyectos> proyectosCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "calendarioID")
    private Collection<Eventos> eventosCollection;

    public Calendarios() {
    }

    public Calendarios(Integer calendarioID) {
        this.calendarioID = calendarioID;
    }

    public Calendarios(Integer calendarioID, String nombre) {
        this.calendarioID = calendarioID;
        this.nombre = nombre;
    }

    public Integer getCalendarioID() {
        return calendarioID;
    }

    public void setCalendarioID(Integer calendarioID) {
        this.calendarioID = calendarioID;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @XmlTransient
    public Collection<Proyectos> getProyectosCollection() {
        return proyectosCollection;
    }

    public void setProyectosCollection(Collection<Proyectos> proyectosCollection) {
        this.proyectosCollection = proyectosCollection;
    }

    @XmlTransient
    public Collection<Eventos> getEventosCollection() {
        return eventosCollection;
    }

    public void setEventosCollection(Collection<Eventos> eventosCollection) {
        this.eventosCollection = eventosCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (calendarioID != null ? calendarioID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Calendarios)) {
            return false;
        }
        Calendarios other = (Calendarios) object;
        if ((this.calendarioID == null && other.calendarioID != null) || (this.calendarioID != null && !this.calendarioID.equals(other.calendarioID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "paquete1.jpa.Calendarios[ calendarioID=" + calendarioID + " ]";
    }
    
}
