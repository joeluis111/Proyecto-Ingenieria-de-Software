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
 * @author Bryan
 */
@Entity
@Table(name = "historiainventaria")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Historiainventaria.findAll", query = "SELECT h FROM Historiainventaria h"),
    @NamedQuery(name = "Historiainventaria.findByMaterialID", query = "SELECT h FROM Historiainventaria h WHERE h.historiainventariaPK.materialID = :materialID"),
    @NamedQuery(name = "Historiainventaria.findByProyectoID", query = "SELECT h FROM Historiainventaria h WHERE h.historiainventariaPK.proyectoID = :proyectoID"),
    @NamedQuery(name = "Historiainventaria.findByNumeroUsado", query = "SELECT h FROM Historiainventaria h WHERE h.numeroUsado = :numeroUsado"),
    @NamedQuery(name = "Historiainventaria.findByFechaUso", query = "SELECT h FROM Historiainventaria h WHERE h.historiainventariaPK.fechaUso = :fechaUso")})
public class Historiainventaria implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected HistoriainventariaPK historiainventariaPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "numeroUsado")
    private int numeroUsado;
    @JoinColumn(name = "proyectoID", referencedColumnName = "proyectoID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Proyectos proyectos;
    @JoinColumn(name = "materialID", referencedColumnName = "materialID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Materiales materiales;

    public Historiainventaria() {
    }

    public Historiainventaria(HistoriainventariaPK historiainventariaPK) {
        this.historiainventariaPK = historiainventariaPK;
    }

    public Historiainventaria(HistoriainventariaPK historiainventariaPK, int numeroUsado) {
        this.historiainventariaPK = historiainventariaPK;
        this.numeroUsado = numeroUsado;
    }

    public Historiainventaria(int materialID, int proyectoID, Date fechaUso) {
        this.historiainventariaPK = new HistoriainventariaPK(materialID, proyectoID, fechaUso);
    }

    public HistoriainventariaPK getHistoriainventariaPK() {
        return historiainventariaPK;
    }

    public void setHistoriainventariaPK(HistoriainventariaPK historiainventariaPK) {
        this.historiainventariaPK = historiainventariaPK;
    }

    public int getNumeroUsado() {
        return numeroUsado;
    }

    public void setNumeroUsado(int numeroUsado) {
        this.numeroUsado = numeroUsado;
    }

    public Proyectos getProyectos() {
        return proyectos;
    }

    public void setProyectos(Proyectos proyectos) {
        this.proyectos = proyectos;
    }

    public Materiales getMateriales() {
        return materiales;
    }

    public void setMateriales(Materiales materiales) {
        this.materiales = materiales;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (historiainventariaPK != null ? historiainventariaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Historiainventaria)) {
            return false;
        }
        Historiainventaria other = (Historiainventaria) object;
        if ((this.historiainventariaPK == null && other.historiainventariaPK != null) || (this.historiainventariaPK != null && !this.historiainventariaPK.equals(other.historiainventariaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "paquete1.jpa.Historiainventaria[ historiainventariaPK=" + historiainventariaPK + " ]";
    }
    
}
