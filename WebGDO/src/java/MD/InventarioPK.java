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
public class InventarioPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "MATID")
    private int matid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PROID")
    private int proid;

    public InventarioPK() {
    }

    public InventarioPK(int matid, int proid) {
        this.matid = matid;
        this.proid = proid;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) matid;
        hash += (int) proid;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InventarioPK)) {
            return false;
        }
        InventarioPK other = (InventarioPK) object;
        if (this.matid != other.matid) {
            return false;
        }
        if (this.proid != other.proid) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "MD.InventarioPK[ matid=" + matid + ", proid=" + proid + " ]";
    }
    
}
