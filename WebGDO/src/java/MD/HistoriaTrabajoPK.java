/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MD;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Kenny
 */
@Embeddable
public class HistoriaTrabajoPK implements Serializable, Entidad {
    @Basic(optional = false)
    @NotNull
    @Column(name = "PROID")
    private int proid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "EMPID")
    private int empid;

    public HistoriaTrabajoPK() {
    }

    public HistoriaTrabajoPK(int proid, int empid) {
        this.proid = proid;
        this.empid = empid;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) proid;
        hash += (int) empid;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HistoriaTrabajoPK)) {
            return false;
        }
        HistoriaTrabajoPK other = (HistoriaTrabajoPK) object;
        if (this.proid != other.proid) {
            return false;
        }
        if (this.empid != other.empid) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "MD.HistoriaTrabajoPK[ proid=" + proid + ", empid=" + empid + " ]";
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
