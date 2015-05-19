/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MD;

import static MD.EntityType.CALENDARIO;
import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Kenny
 */
@Entity
@Table(name = "calendarios")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Calendario.findAll", query = "SELECT c FROM Calendario c"),
    @NamedQuery(name = "Calendario.findByCalid", query = "SELECT c FROM Calendario c WHERE c.calid = :calid")})
public class Calendario implements Serializable, Entidad {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "CALID")
    private Integer calid;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "calid")
    private Collection<Evento> eventoCollection;
    @JoinColumn(name = "PROID", referencedColumnName = "PROID")
    @ManyToOne(optional = false)
    private Proyecto proid;

    public Calendario() {
    }

    public Calendario(Integer calid) {
        this.calid = calid;
    }

    public Integer getCalid() {
        return calid;
    }

    public void setCalid(Integer calid) {
        this.calid = calid;
    }

    @XmlTransient
    public Collection<Evento> getEventoCollection() {
        return eventoCollection;
    }

    public void setEventoCollection(Collection<Evento> eventoCollection) {
        this.eventoCollection = eventoCollection;
    }

    public Proyecto getProid() {
        return proid;
    }

    public void setProid(Proyecto proid) {
        this.proid = proid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (calid != null ? calid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Calendario)) {
            return false;
        }
        Calendario other = (Calendario) object;
        if ((this.calid == null && other.calid != null) || (this.calid != null && !this.calid.equals(other.calid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "MD.Calendario[ calid=" + calid + " ]";
    }

    @Override
    public Object getIdentidad() {
        return this.getCalid();
    }

    @Override
    public String getCadenaDesplegable() {
        return "Calendario de " + this.getProid().getPronombre();
    }

    @Override
    public EntityType getType() {
        return CALENDARIO;
    }
    
}
