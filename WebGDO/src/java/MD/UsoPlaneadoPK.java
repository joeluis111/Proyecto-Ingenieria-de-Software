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
public class UsoPlaneadoPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "PROID")
    private int proid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "MATID")
    private int matid;

    public UsoPlaneadoPK() {
    }

    public UsoPlaneadoPK(int proid, int matid) {
        this.proid = proid;
        this.matid = matid;
    }

    public int getProid() {
        return proid;
    }

    public void setProid(int proid) {
        this.proid = proid;
    }

    public int getMatid() {
        return matid;
    }

    public void setMatid(int matid) {
        this.matid = matid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) proid;
        hash += (int) matid;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UsoPlaneadoPK)) {
            return false;
        }
        UsoPlaneadoPK other = (UsoPlaneadoPK) object;
        if (this.proid != other.proid) {
            return false;
        }
        if (this.matid != other.matid) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "MD.UsoPlaneadoPK[ proid=" + proid + ", matid=" + matid + " ]";
    }
    
}
