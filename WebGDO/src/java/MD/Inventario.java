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
@Table(name = "inventarios")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Inventario.findAll", query = "SELECT i FROM Inventario i"),
    @NamedQuery(name = "Inventario.findByMatid", query = "SELECT i FROM Inventario i WHERE i.inventarioPK.matid = :matid"),
    @NamedQuery(name = "Inventario.findByProid", query = "SELECT i FROM Inventario i WHERE i.inventarioPK.proid = :proid"),
    @NamedQuery(name = "Inventario.findByInvnumerounidades", query = "SELECT i FROM Inventario i WHERE i.invnumerounidades = :invnumerounidades")})
public class Inventario implements Serializable, Entidad {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected InventarioPK inventarioPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "INVNUMEROUNIDADES")
    private int invnumerounidades;
    @JoinColumn(name = "PROID", referencedColumnName = "PROID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Proyecto proyecto;
    @JoinColumn(name = "MATID", referencedColumnName = "MATID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Material material;

    public Inventario() {
    }

    public Inventario(InventarioPK inventarioPK) {
        this.inventarioPK = inventarioPK;
    }

    public Inventario(InventarioPK inventarioPK, int invnumerounidades) {
        this.inventarioPK = inventarioPK;
        this.invnumerounidades = invnumerounidades;
    }

    public Inventario(int matid, int proid) {
        this.inventarioPK = new InventarioPK(matid, proid);
    }

    public InventarioPK getInventarioPK() {
        return inventarioPK;
    }

    public void setInventarioPK(InventarioPK inventarioPK) {
        this.inventarioPK = inventarioPK;
    }

    public int getInvnumerounidades() {
        return invnumerounidades;
    }

    public void setInvnumerounidades(int invnumerounidades) {
        this.invnumerounidades = invnumerounidades;
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
        hash += (inventarioPK != null ? inventarioPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Inventario)) {
            return false;
        }
        Inventario other = (Inventario) object;
        if ((this.inventarioPK == null && other.inventarioPK != null) || (this.inventarioPK != null && !this.inventarioPK.equals(other.inventarioPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "MD.Inventario[ inventarioPK=" + inventarioPK + " ]";
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
