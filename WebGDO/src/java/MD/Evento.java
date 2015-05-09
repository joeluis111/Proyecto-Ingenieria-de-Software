/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MD;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Kenny
 */
@Entity
@Table(name = "eventos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Evento.findAll", query = "SELECT e FROM Evento e"),
    @NamedQuery(name = "Evento.findByEvid", query = "SELECT e FROM Evento e WHERE e.evid = :evid"),
    @NamedQuery(name = "Evento.findByEvnombre", query = "SELECT e FROM Evento e WHERE e.evnombre = :evnombre"),
    @NamedQuery(name = "Evento.findByEvdescripcion", query = "SELECT e FROM Evento e WHERE e.evdescripcion = :evdescripcion"),
    @NamedQuery(name = "Evento.findByEvfechainicioplaneada", query = "SELECT e FROM Evento e WHERE e.evfechainicioplaneada = :evfechainicioplaneada"),
    @NamedQuery(name = "Evento.findByEvfechafinplaneada", query = "SELECT e FROM Evento e WHERE e.evfechafinplaneada = :evfechafinplaneada"),
    @NamedQuery(name = "Evento.findByEvfechainicioreal", query = "SELECT e FROM Evento e WHERE e.evfechainicioreal = :evfechainicioreal"),
    @NamedQuery(name = "Evento.findByEvfechafinreal", query = "SELECT e FROM Evento e WHERE e.evfechafinreal = :evfechafinreal")})
public class Evento implements Serializable, Entidad {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "EVID")
    private Integer evid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "EVNOMBRE")
    private String evnombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "EVDESCRIPCION")
    private String evdescripcion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "EVFECHAINICIOPLANEADA")
    @Temporal(TemporalType.DATE)
    private Date evfechainicioplaneada;
    @Basic(optional = false)
    @NotNull
    @Column(name = "EVFECHAFINPLANEADA")
    @Temporal(TemporalType.DATE)
    private Date evfechafinplaneada;
    @Column(name = "EVFECHAINICIOREAL")
    @Temporal(TemporalType.DATE)
    private Date evfechainicioreal;
    @Column(name = "EVFECHAFINREAL")
    @Temporal(TemporalType.DATE)
    private Date evfechafinreal;
    @JoinTable(name = "requerimientoseventos", joinColumns = {
        @JoinColumn(name = "EVREQUIERE", referencedColumnName = "EVID")}, inverseJoinColumns = {
        @JoinColumn(name = "EVREQUERIMIENTO", referencedColumnName = "EVID")})
    @ManyToMany
    private Collection<Evento> eventoCollection;
    @ManyToMany(mappedBy = "eventoCollection")
    private Collection<Evento> eventoCollection1;
    @JoinColumn(name = "CALID", referencedColumnName = "CALID")
    @ManyToOne(optional = false)
    private Calendario calid;

    public Evento() {
    }

    public Evento(Integer evid) {
        this.evid = evid;
    }

    public Evento(Integer evid, String evnombre, String evdescripcion, Date evfechainicioplaneada, Date evfechafinplaneada) {
        this.evid = evid;
        this.evnombre = evnombre;
        this.evdescripcion = evdescripcion;
        this.evfechainicioplaneada = evfechainicioplaneada;
        this.evfechafinplaneada = evfechafinplaneada;
    }

    public Integer getEvid() {
        return evid;
    }

    public void setEvid(Integer evid) {
        this.evid = evid;
    }

    public String getEvnombre() {
        return evnombre;
    }

    public void setEvnombre(String evnombre) {
        this.evnombre = evnombre;
    }

    public String getEvdescripcion() {
        return evdescripcion;
    }

    public void setEvdescripcion(String evdescripcion) {
        this.evdescripcion = evdescripcion;
    }

    public Date getEvfechainicioplaneada() {
        return evfechainicioplaneada;
    }

    public void setEvfechainicioplaneada(Date evfechainicioplaneada) {
        this.evfechainicioplaneada = evfechainicioplaneada;
    }

    public Date getEvfechafinplaneada() {
        return evfechafinplaneada;
    }

    public void setEvfechafinplaneada(Date evfechafinplaneada) {
        this.evfechafinplaneada = evfechafinplaneada;
    }

    public Date getEvfechainicioreal() {
        return evfechainicioreal;
    }

    public void setEvfechainicioreal(Date evfechainicioreal) {
        this.evfechainicioreal = evfechainicioreal;
    }

    public Date getEvfechafinreal() {
        return evfechafinreal;
    }

    public void setEvfechafinreal(Date evfechafinreal) {
        this.evfechafinreal = evfechafinreal;
    }

    @XmlTransient
    public Collection<Evento> getEventoCollection() {
        return eventoCollection;
    }

    public void setEventoCollection(Collection<Evento> eventoCollection) {
        this.eventoCollection = eventoCollection;
    }

    @XmlTransient
    public Collection<Evento> getEventoCollection1() {
        return eventoCollection1;
    }

    public void setEventoCollection1(Collection<Evento> eventoCollection1) {
        this.eventoCollection1 = eventoCollection1;
    }

    public Calendario getCalid() {
        return calid;
    }

    public void setCalid(Calendario calid) {
        this.calid = calid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (evid != null ? evid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Evento)) {
            return false;
        }
        Evento other = (Evento) object;
        if ((this.evid == null && other.evid != null) || (this.evid != null && !this.evid.equals(other.evid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "MD.Evento[ evid=" + evid + " ]";
    }

    @Override
    public Object getIdentidad() {
        return this.getEvid();
    }

    @Override
    public String getCadenaDesplegable() {
        return this.getEvnombre();
    }
    
}
