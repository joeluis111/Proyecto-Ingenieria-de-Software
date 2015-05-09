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
@Table(name = "historiacliente")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HistoriaCliente.findAll", query = "SELECT h FROM HistoriaCliente h"),
    @NamedQuery(name = "HistoriaCliente.findByCliid", query = "SELECT h FROM HistoriaCliente h WHERE h.cliid = :cliid"),
    @NamedQuery(name = "HistoriaCliente.findByProid", query = "SELECT h FROM HistoriaCliente h WHERE h.proid = :proid"),
    @NamedQuery(name = "HistoriaCliente.findByHcfechainicio", query = "SELECT h FROM HistoriaCliente h WHERE h.hcfechainicio = :hcfechainicio"),
    @NamedQuery(name = "HistoriaCliente.findByHcfechafin", query = "SELECT h FROM HistoriaCliente h WHERE h.hcfechafin = :hcfechafin"),
    @NamedQuery(name = "HistoriaCliente.findByHcid", query = "SELECT h FROM HistoriaCliente h WHERE h.hcid = :hcid")})
public class HistoriaCliente implements Serializable, Entidad {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CLIID")
    private int cliid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PROID")
    private int proid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "HCFECHAINICIO")
    @Temporal(TemporalType.DATE)
    private Date hcfechainicio;
    @Column(name = "HCFECHAFIN")
    @Temporal(TemporalType.DATE)
    private Date hcfechafin;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "hcid")
    private Integer hcid;

    public HistoriaCliente() {
    }

    public HistoriaCliente(Integer hcid) {
        this.hcid = hcid;
    }

    public HistoriaCliente(Integer hcid, int cliid, int proid, Date hcfechainicio) {
        this.hcid = hcid;
        this.cliid = cliid;
        this.proid = proid;
        this.hcfechainicio = hcfechainicio;
    }

    public int getCliid() {
        return cliid;
    }

    public void setCliid(int cliid) {
        this.cliid = cliid;
    }

    public int getProid() {
        return proid;
    }

    public void setProid(int proid) {
        this.proid = proid;
    }

    public Date getHcfechainicio() {
        return hcfechainicio;
    }

    public void setHcfechainicio(Date hcfechainicio) {
        this.hcfechainicio = hcfechainicio;
    }

    public Date getHcfechafin() {
        return hcfechafin;
    }

    public void setHcfechafin(Date hcfechafin) {
        this.hcfechafin = hcfechafin;
    }

    public Integer getHcid() {
        return hcid;
    }

    public void setHcid(Integer hcid) {
        this.hcid = hcid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (hcid != null ? hcid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HistoriaCliente)) {
            return false;
        }
        HistoriaCliente other = (HistoriaCliente) object;
        if ((this.hcid == null && other.hcid != null) || (this.hcid != null && !this.hcid.equals(other.hcid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "MD.HistoriaCliente[ hcid=" + hcid + " ]";
    }

    @Override
    public Object getIdentidad() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getCadenaDesplegable() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
