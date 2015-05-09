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
// TODO: Arreglar esta tabla en la base de datos, la clave
// primaria debería ser propia de la tabla, no una combinación
// de las otras claves primarias.
@Entity
@Table(name = "historiainventaria")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HistoriaInventaria.findAll", query = "SELECT h FROM HistoriaInventaria h"),
    @NamedQuery(name = "HistoriaInventaria.findByMatid", query = "SELECT h FROM HistoriaInventaria h WHERE h.historiaInventariaPK.matid = :matid"),
    @NamedQuery(name = "HistoriaInventaria.findByProid", query = "SELECT h FROM HistoriaInventaria h WHERE h.historiaInventariaPK.proid = :proid"),
    @NamedQuery(name = "HistoriaInventaria.findByHistnumerousado", query = "SELECT h FROM HistoriaInventaria h WHERE h.histnumerousado = :histnumerousado"),
    @NamedQuery(name = "HistoriaInventaria.findByHistfechauso", query = "SELECT h FROM HistoriaInventaria h WHERE h.histfechauso = :histfechauso")})
public class HistoriaInventaria implements Serializable, Entidad {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected HistoriaInventariaPK historiaInventariaPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "HISTNUMEROUSADO")
    private int histnumerousado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "HISTFECHAUSO")
    @Temporal(TemporalType.DATE)
    private Date histfechauso;
    @JoinColumn(name = "PROID", referencedColumnName = "PROID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Proyecto proyecto;
    @JoinColumn(name = "MATID", referencedColumnName = "MATID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Material material;

    public HistoriaInventaria() {
    }

    public HistoriaInventaria(HistoriaInventariaPK historiaInventariaPK) {
        this.historiaInventariaPK = historiaInventariaPK;
    }

    public HistoriaInventaria(HistoriaInventariaPK historiaInventariaPK, int histnumerousado, Date histfechauso) {
        this.historiaInventariaPK = historiaInventariaPK;
        this.histnumerousado = histnumerousado;
        this.histfechauso = histfechauso;
    }

    public HistoriaInventaria(int matid, int proid) {
        this.historiaInventariaPK = new HistoriaInventariaPK(matid, proid);
    }

    public HistoriaInventariaPK getHistoriaInventariaPK() {
        return historiaInventariaPK;
    }

    public void setHistoriaInventariaPK(HistoriaInventariaPK historiaInventariaPK) {
        this.historiaInventariaPK = historiaInventariaPK;
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

    public Proyecto getProyecto() {
        return proyecto;
    }

    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (historiaInventariaPK != null ? historiaInventariaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HistoriaInventaria)) {
            return false;
        }
        HistoriaInventaria other = (HistoriaInventaria) object;
        if ((this.historiaInventariaPK == null && other.historiaInventariaPK != null) || (this.historiaInventariaPK != null && !this.historiaInventariaPK.equals(other.historiaInventariaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "MD.HistoriaInventaria[ historiaInventariaPK=" + historiaInventariaPK + " ]";
    }

    @Override
    public Object getID() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getCadenaDesplegable() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
