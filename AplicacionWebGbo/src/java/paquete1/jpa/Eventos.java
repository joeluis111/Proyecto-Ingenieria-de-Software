/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package paquete1.jpa;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Bryan
 */
@Entity
@Table(name = "eventos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Eventos.findAll", query = "SELECT e FROM Eventos e"),
    @NamedQuery(name = "Eventos.findByEventoID", query = "SELECT e FROM Eventos e WHERE e.eventoID = :eventoID"),
    @NamedQuery(name = "Eventos.findByNombre", query = "SELECT e FROM Eventos e WHERE e.nombre = :nombre"),
    @NamedQuery(name = "Eventos.findByFechaInicioReal", query = "SELECT e FROM Eventos e WHERE e.fechaInicioReal = :fechaInicioReal"),
    @NamedQuery(name = "Eventos.findByFechaTerminacionReal", query = "SELECT e FROM Eventos e WHERE e.fechaTerminacionReal = :fechaTerminacionReal"),
    @NamedQuery(name = "Eventos.findByFechaInicioPlaneada", query = "SELECT e FROM Eventos e WHERE e.fechaInicioPlaneada = :fechaInicioPlaneada"),
    @NamedQuery(name = "Eventos.findByFechaTerminacionPlaneada", query = "SELECT e FROM Eventos e WHERE e.fechaTerminacionPlaneada = :fechaTerminacionPlaneada")})
public class Eventos implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "eventoID")
    private Integer eventoID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 16777215)
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "fechaInicioReal")
    @Temporal(TemporalType.DATE)
    private Date fechaInicioReal;
    @Column(name = "fechaTerminacionReal")
    @Temporal(TemporalType.DATE)
    private Date fechaTerminacionReal;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechaInicioPlaneada")
    @Temporal(TemporalType.DATE)
    private Date fechaInicioPlaneada;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechaTerminacionPlaneada")
    @Temporal(TemporalType.DATE)
    private Date fechaTerminacionPlaneada;
    @JoinColumn(name = "calendarioID", referencedColumnName = "calendarioID")
    @ManyToOne(optional = false)
    private Calendarios calendarioID;

    public Eventos() {
    }

    public Eventos(Integer eventoID) {
        this.eventoID = eventoID;
    }

    public Eventos(Integer eventoID, String nombre, String descripcion, Date fechaInicioPlaneada, Date fechaTerminacionPlaneada) {
        this.eventoID = eventoID;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaInicioPlaneada = fechaInicioPlaneada;
        this.fechaTerminacionPlaneada = fechaTerminacionPlaneada;
    }

    public Integer getEventoID() {
        return eventoID;
    }

    public void setEventoID(Integer eventoID) {
        this.eventoID = eventoID;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaInicioReal() {
        return fechaInicioReal;
    }

    public void setFechaInicioReal(Date fechaInicioReal) {
        this.fechaInicioReal = fechaInicioReal;
    }

    public Date getFechaTerminacionReal() {
        return fechaTerminacionReal;
    }

    public void setFechaTerminacionReal(Date fechaTerminacionReal) {
        this.fechaTerminacionReal = fechaTerminacionReal;
    }

    public Date getFechaInicioPlaneada() {
        return fechaInicioPlaneada;
    }

    public void setFechaInicioPlaneada(Date fechaInicioPlaneada) {
        this.fechaInicioPlaneada = fechaInicioPlaneada;
    }

    public Date getFechaTerminacionPlaneada() {
        return fechaTerminacionPlaneada;
    }

    public void setFechaTerminacionPlaneada(Date fechaTerminacionPlaneada) {
        this.fechaTerminacionPlaneada = fechaTerminacionPlaneada;
    }

    public Calendarios getCalendarioID() {
        return calendarioID;
    }

    public void setCalendarioID(Calendarios calendarioID) {
        this.calendarioID = calendarioID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (eventoID != null ? eventoID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Eventos)) {
            return false;
        }
        Eventos other = (Eventos) object;
        if ((this.eventoID == null && other.eventoID != null) || (this.eventoID != null && !this.eventoID.equals(other.eventoID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "paquete1.jpa.Eventos[ eventoID=" + eventoID + " ]";
    }
    
}
