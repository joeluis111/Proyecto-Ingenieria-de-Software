/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MD;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Kenny
 */
@Entity
@Table(name = "historiainventaria")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HistoriaInventaria.findAll", query = "SELECT h FROM HistoriaInventaria h"),
    @NamedQuery(name = "HistoriaInventaria.findByMatid", query = "SELECT h FROM HistoriaInventaria h WHERE h.matid = :matid"),
    @NamedQuery(name = "HistoriaInventaria.findByProid", query = "SELECT h FROM HistoriaInventaria h WHERE h.proid = :proid"),
    @NamedQuery(name = "HistoriaInventaria.findByHistnumerousado", query = "SELECT h FROM HistoriaInventaria h WHERE h.histnumerousado = :histnumerousado"),
    @NamedQuery(name = "HistoriaInventaria.findByHistfechauso", query = "SELECT h FROM HistoriaInventaria h WHERE h.histfechauso = :histfechauso"),
    @NamedQuery(name = "HistoriaInventaria.findByHistid", query = "SELECT h FROM HistoriaInventaria h WHERE h.histid = :histid")})
public class HistoriaInventaria implements Serializable, Entidad {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Column(name = "MATID")
    private int matid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PROID")
    private int proid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "HISTNUMEROUSADO")
    private int histnumerousado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "HISTFECHAUSO")
    @Temporal(TemporalType.DATE)
    private Date histfechauso;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "histid")
    private Integer histid;

    public HistoriaInventaria() {
    }

    public HistoriaInventaria(Integer histid) {
        this.histid = histid;
    }

    public HistoriaInventaria(Integer histid, int matid, int proid, int histnumerousado, Date histfechauso) {
        this.histid = histid;
        this.matid = matid;
        this.proid = proid;
        this.histnumerousado = histnumerousado;
        this.histfechauso = histfechauso;
    }

    public int getMatid() {
        return matid;
    }

    public void setMatid(int matid) {
        this.matid = matid;
    }

    public int getProid() {
        return proid;
    }

    public void setProid(int proid) {
        this.proid = proid;
    }

    public int getHistnumerousado() {
        return histnumerousado;
    }

    public void setHistnumerousado(int histnumerousado) {
        this.histnumerousado = histnumerousado;
    }

    public Date getHistfechauso() {
        return histfechauso;
    }

    public void setHistfechauso(Date histfechauso) {
        this.histfechauso = histfechauso;
    }

    public Integer getHistid() {
        return histid;
    }

    public void setHistid(Integer histid) {
        this.histid = histid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (histid != null ? histid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HistoriaInventaria)) {
            return false;
        }
        HistoriaInventaria other = (HistoriaInventaria) object;
        if ((this.histid == null && other.histid != null) || (this.histid != null && !this.histid.equals(other.histid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "MD.HistoriaInventaria[ histid=" + histid + " ]";
    }

    @Override
    public Object getIdentidad() {
        return this.getHistid();
    }

    @Override
    public String getCadenaDesplegable() {
        return "Historia Inventaria " + Integer.toString(this.getHistid());
    }
    
}
