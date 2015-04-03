/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package paquete1.jpa;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Bryan
 */
@Embeddable
public class InventariosPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "materialID")
    private int materialID;
    @Basic(optional = false)
    @NotNull
    @Column(name = "proyectoID")
    private int proyectoID;

    public InventariosPK() {
    }

    public InventariosPK(int materialID, int proyectoID) {
        this.materialID = materialID;
        this.proyectoID = proyectoID;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) materialID;
        hash += (int) proyectoID;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InventariosPK)) {
            return false;
        }
        InventariosPK other = (InventariosPK) object;
        if (this.materialID != other.materialID) {
            return false;
        }
        if (this.proyectoID != other.proyectoID) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "paquete1.jpa.InventariosPK[ materialID=" + materialID + ", proyectoID=" + proyectoID + " ]";
    }
    
}
