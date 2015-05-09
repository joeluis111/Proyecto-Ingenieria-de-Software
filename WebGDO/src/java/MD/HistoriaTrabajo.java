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
@Table(name = "historiatrabajo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HistoriaTrabajo.findAll", query = "SELECT h FROM HistoriaTrabajo h"),
    @NamedQuery(name = "HistoriaTrabajo.findByProid", query = "SELECT h FROM HistoriaTrabajo h WHERE h.historiaTrabajoPK.proid = :proid"),
    @NamedQuery(name = "HistoriaTrabajo.findByEmpid", query = "SELECT h FROM HistoriaTrabajo h WHERE h.historiaTrabajoPK.empid = :empid"),
    @NamedQuery(name = "HistoriaTrabajo.findByHtfechainicio", query = "SELECT h FROM HistoriaTrabajo h WHERE h.htfechainicio = :htfechainicio"),
    @NamedQuery(name = "HistoriaTrabajo.findByHtfechafin", query = "SELECT h FROM HistoriaTrabajo h WHERE h.htfechafin = :htfechafin")})
public class HistoriaTrabajo implements Serializable, Entidad {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected HistoriaTrabajoPK historiaTrabajoPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "HTFECHAINICIO")
    @Temporal(TemporalType.DATE)
    private Date htfechainicio;
    @Column(name = "HTFECHAFIN")
    @Temporal(TemporalType.DATE)
    private Date htfechafin;
    @JoinColumn(name = "EMPID", referencedColumnName = "EMPID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Empleado empleado;
    @JoinColumn(name = "PROID", referencedColumnName = "PROID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Proyecto proyecto;

    public HistoriaTrabajo() {
    }

    public HistoriaTrabajo(HistoriaTrabajoPK historiaTrabajoPK) {
        this.historiaTrabajoPK = historiaTrabajoPK;
    }

    public HistoriaTrabajo(HistoriaTrabajoPK historiaTrabajoPK, Date htfechainicio) {
        this.historiaTrabajoPK = historiaTrabajoPK;
        this.htfechainicio = htfechainicio;
    }

    public HistoriaTrabajo(int proid, int empid) {
        this.historiaTrabajoPK = new HistoriaTrabajoPK(proid, empid);
    }

    public HistoriaTrabajoPK getHistoriaTrabajoPK() {
        return historiaTrabajoPK;
    }

    public void setHistoriaTrabajoPK(HistoriaTrabajoPK historiaTrabajoPK) {
        this.historiaTrabajoPK = historiaTrabajoPK;
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

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
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
        hash += (historiaTrabajoPK != null ? historiaTrabajoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HistoriaTrabajo)) {
            return false;
        }
        HistoriaTrabajo other = (HistoriaTrabajo) object;
        if ((this.historiaTrabajoPK == null && other.historiaTrabajoPK != null) || (this.historiaTrabajoPK != null && !this.historiaTrabajoPK.equals(other.historiaTrabajoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "MD.HistoriaTrabajo[ historiaTrabajoPK=" + historiaTrabajoPK + " ]";
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
