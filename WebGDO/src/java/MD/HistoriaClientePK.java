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
public class HistoriaClientePK implements Serializable, Entidad {
    @Basic(optional = false)
    @NotNull
    @Column(name = "CLIID")
    private int cliid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PROID")
    private int proid;

    public HistoriaClientePK() {
    }

    public HistoriaClientePK(int cliid, int proid) {
        this.cliid = cliid;
        this.proid = proid;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) cliid;
        hash += (int) proid;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HistoriaClientePK)) {
            return false;
        }
        HistoriaClientePK other = (HistoriaClientePK) object;
        if (this.cliid != other.cliid) {
            return false;
        }
        if (this.proid != other.proid) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "MD.HistoriaClientePK[ cliid=" + cliid + ", proid=" + proid + " ]";
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
