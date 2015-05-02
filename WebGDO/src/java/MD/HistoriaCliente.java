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
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
    @NamedQuery(name = "HistoriaCliente.findByCliid", query = "SELECT h FROM HistoriaCliente h WHERE h.historiaClientePK.cliid = :cliid"),
    @NamedQuery(name = "HistoriaCliente.findByProid", query = "SELECT h FROM HistoriaCliente h WHERE h.historiaClientePK.proid = :proid"),
    @NamedQuery(name = "HistoriaCliente.findByHcfechainicio", query = "SELECT h FROM HistoriaCliente h WHERE h.hcfechainicio = :hcfechainicio"),
    @NamedQuery(name = "HistoriaCliente.findByHcfechafin", query = "SELECT h FROM HistoriaCliente h WHERE h.hcfechafin = :hcfechafin")})
public class HistoriaCliente implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected HistoriaClientePK historiaClientePK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "HCFECHAINICIO")
    @Temporal(TemporalType.DATE)
    private Date hcfechainicio;
    @Column(name = "HCFECHAFIN")
    @Temporal(TemporalType.DATE)
    private Date hcfechafin;
    @JoinColumn(name = "PROID", referencedColumnName = "PROID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Proyecto proyecto;
    @JoinColumn(name = "CLIID", referencedColumnName = "CLIID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Cliente cliente;

    public HistoriaCliente() {
    }

    public HistoriaCliente(HistoriaClientePK historiaClientePK) {
        this.historiaClientePK = historiaClientePK;
    }

    public HistoriaCliente(HistoriaClientePK historiaClientePK, Date hcfechainicio) {
        this.historiaClientePK = historiaClientePK;
        this.hcfechainicio = hcfechainicio;
    }

    public HistoriaCliente(int cliid, int proid) {
        this.historiaClientePK = new HistoriaClientePK(cliid, proid);
    }

    public HistoriaClientePK getHistoriaClientePK() {
        return historiaClientePK;
    }

    public void setHistoriaClientePK(HistoriaClientePK historiaClientePK) {
        this.historiaClientePK = historiaClientePK;
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

    public Proyecto getProyecto() {
        return proyecto;
    }

    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (historiaClientePK != null ? historiaClientePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HistoriaCliente)) {
            return false;
        }
        HistoriaCliente other = (HistoriaCliente) object;
        if ((this.historiaClientePK == null && other.historiaClientePK != null) || (this.historiaClientePK != null && !this.historiaClientePK.equals(other.historiaClientePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "MD.HistoriaCliente[ historiaClientePK=" + historiaClientePK + " ]";
    }
    
}
