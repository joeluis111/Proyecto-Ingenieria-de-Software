/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MD;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Kenny
 */
@Entity
@Table(name = "usosplaneados")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UsoPlaneado.findAll", query = "SELECT u FROM UsoPlaneado u"),
    @NamedQuery(name = "UsoPlaneado.findByProid", query = "SELECT u FROM UsoPlaneado u WHERE u.usoPlaneadoPK.proid = :proid"),
    @NamedQuery(name = "UsoPlaneado.findByMatid", query = "SELECT u FROM UsoPlaneado u WHERE u.usoPlaneadoPK.matid = :matid"),
    @NamedQuery(name = "UsoPlaneado.findByUpnumero", query = "SELECT u FROM UsoPlaneado u WHERE u.upnumero = :upnumero")})
public class UsoPlaneado implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected UsoPlaneadoPK usoPlaneadoPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "UPNUMERO")
    private int upnumero;
    @JoinColumn(name = "MATID", referencedColumnName = "MATID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Material material;
    @JoinColumn(name = "PROID", referencedColumnName = "PROID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Proyecto proyecto;

    public UsoPlaneado() {
    }

    public UsoPlaneado(UsoPlaneadoPK usoPlaneadoPK) {
        this.usoPlaneadoPK = usoPlaneadoPK;
    }

    public UsoPlaneado(UsoPlaneadoPK usoPlaneadoPK, int upnumero) {
        this.usoPlaneadoPK = usoPlaneadoPK;
        this.upnumero = upnumero;
    }

    public UsoPlaneado(int proid, int matid) {
        this.usoPlaneadoPK = new UsoPlaneadoPK(proid, matid);
    }

    public UsoPlaneadoPK getUsoPlaneadoPK() {
        return usoPlaneadoPK;
    }

    public void setUsoPlaneadoPK(UsoPlaneadoPK usoPlaneadoPK) {
        this.usoPlaneadoPK = usoPlaneadoPK;
    }

    public int getUpnumero() {
        return upnumero;
    }

    public void setUpnumero(int upnumero) {
        this.upnumero = upnumero;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public Proyecto getProyecto() {
        return proyecto;
    }

    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (usoPlaneadoPK != null ? usoPlaneadoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UsoPlaneado)) {
            return false;
        }
        UsoPlaneado other = (UsoPlaneado) object;
        if ((this.usoPlaneadoPK == null && other.usoPlaneadoPK != null) || (this.usoPlaneadoPK != null && !this.usoPlaneadoPK.equals(other.usoPlaneadoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "MD.UsoPlaneado[ usoPlaneadoPK=" + usoPlaneadoPK + " ]";
    }
    
}
