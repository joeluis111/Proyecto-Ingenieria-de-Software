/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package paquete1.jpa;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Bryan
 */
@Embeddable
public class HistoriainventariaPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "materialID")
    private int materialID;
    @Basic(optional = false)
    @NotNull
    @Column(name = "proyectoID")
    private int proyectoID;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechaUso")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaUso;

    public HistoriainventariaPK() {
    }

    public HistoriainventariaPK(int materialID, int proyectoID, Date fechaUso) {
        this.materialID = materialID;
        this.proyectoID = proyectoID;
        this.fechaUso = fechaUso;
    }

    public int getMaterialID() {
        return materialID;
    }

    public void setMaterialID(int materialID) {
        this.materialID = materialID;
    }

    public int getProyectoID() {
        return proyectoID;
    }

    public void setProyectoID(int proyectoID) {
        this.proyectoID = proyectoID;
    }

    public Date getFechaUso() {
        return fechaUso;
    }

    public void setFechaUso(Date fechaUso) {
        this.fechaUso = fechaUso;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) materialID;
        hash += (int) proyectoID;
        hash += (fechaUso != null ? fechaUso.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HistoriainventariaPK)) {
            return false;
        }
        HistoriainventariaPK other = (HistoriainventariaPK) object;
        if (this.materialID != other.materialID) {
            return false;
        }
        if (this.proyectoID != other.proyectoID) {
            return false;
        }
        if ((this.fechaUso == null && other.fechaUso != null) || (this.fechaUso != null && !this.fechaUso.equals(other.fechaUso))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "paquete1.jpa.HistoriainventariaPK[ materialID=" + materialID + ", proyectoID=" + proyectoID + ", fechaUso=" + fechaUso + " ]";
    }
    
}
