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
@Table(name = "historiatrabajo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HistoriaTrabajo.findAll", query = "SELECT h FROM HistoriaTrabajo h"),
    @NamedQuery(name = "HistoriaTrabajo.findByProid", query = "SELECT h FROM HistoriaTrabajo h WHERE h.proid = :proid"),
    @NamedQuery(name = "HistoriaTrabajo.findByEmpid", query = "SELECT h FROM HistoriaTrabajo h WHERE h.empid = :empid"),
    @NamedQuery(name = "HistoriaTrabajo.findByHtfechainicio", query = "SELECT h FROM HistoriaTrabajo h WHERE h.htfechainicio = :htfechainicio"),
    @NamedQuery(name = "HistoriaTrabajo.findByHtfechafin", query = "SELECT h FROM HistoriaTrabajo h WHERE h.htfechafin = :htfechafin"),
    @NamedQuery(name = "HistoriaTrabajo.findByHtid", query = "SELECT h FROM HistoriaTrabajo h WHERE h.htid = :htid")})
public class HistoriaTrabajo implements Serializable, Entidad {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PROID")
    private int proid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "EMPID")
    private int empid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "HTFECHAINICIO")
    @Temporal(TemporalType.DATE)
    private Date htfechainicio;
    @Column(name = "HTFECHAFIN")
    @Temporal(TemporalType.DATE)
    private Date htfechafin;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "htid")
    private Integer htid;

    public HistoriaTrabajo() {
    }

    public HistoriaTrabajo(Integer htid) {
        this.htid = htid;
    }

    public HistoriaTrabajo(Integer htid, int proid, int empid, Date htfechainicio) {
        this.htid = htid;
        this.proid = proid;
        this.empid = empid;
        this.htfechainicio = htfechainicio;
    }

    public int getProid() {
        return proid;
    }

    public void setProid(int proid) {
        this.proid = proid;
    }

    public int getEmpid() {
        return empid;
    }

    public void setEmpid(int empid) {
        this.empid = empid;
    }

    public Date getHtfechainicio() {
        return htfechainicio;
    }

    public void setHtfechainicio(Date htfechainicio) {
        this.htfechainicio = htfechainicio;
    }

    public Date getHtfechafin() {
        return htfechafin;
    }

    public void setHtfechafin(Date htfechafin) {
        this.htfechafin = htfechafin;
    }

    public Integer getHtid() {
        return htid;
    }

    public void setHtid(Integer htid) {
        this.htid = htid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (htid != null ? htid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HistoriaTrabajo)) {
            return false;
        }
        HistoriaTrabajo other = (HistoriaTrabajo) object;
        if ((this.htid == null && other.htid != null) || (this.htid != null && !this.htid.equals(other.htid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "MD.HistoriaTrabajo[ htid=" + htid + " ]";
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
