/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package paquete1.jpa;

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
 * @author Bryan
 */
@Entity
@Table(name = "inventarios")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Inventarios.findAll", query = "SELECT i FROM Inventarios i"),
    @NamedQuery(name = "Inventarios.findByNumeroUnidades", query = "SELECT i FROM Inventarios i WHERE i.numeroUnidades = :numeroUnidades"),
    @NamedQuery(name = "Inventarios.findByMaterialID", query = "SELECT i FROM Inventarios i WHERE i.inventariosPK.materialID = :materialID"),
    @NamedQuery(name = "Inventarios.findByProyectoID", query = "SELECT i FROM Inventarios i WHERE i.inventariosPK.proyectoID = :proyectoID")})
public class Inventarios implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected InventariosPK inventariosPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "numeroUnidades")
    private int numeroUnidades;
    @JoinColumn(name = "proyectoID", referencedColumnName = "proyectoID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Proyectos proyectos;
    @JoinColumn(name = "materialID", referencedColumnName = "materialID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Materiales materiales;

    public Inventarios() {
    }

    public Inventarios(InventariosPK inventariosPK) {
        this.inventariosPK = inventariosPK;
    }

    public Inventarios(InventariosPK inventariosPK, int numeroUnidades) {
        this.inventariosPK = inventariosPK;
        this.numeroUnidades = numeroUnidades;
    }

    public Inventarios(int materialID, int proyectoID) {
        this.inventariosPK = new InventariosPK(materialID, proyectoID);
    }

    public InventariosPK getInventariosPK() {
        return inventariosPK;
    }

    public void setInventariosPK(InventariosPK inventariosPK) {
        this.inventariosPK = inventariosPK;
    }

    public int getNumeroUnidades() {
        return numeroUnidades;
    }

    public void setNumeroUnidades(int numeroUnidades) {
        this.numeroUnidades = numeroUnidades;
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
        hash += (inventariosPK != null ? inventariosPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Inventarios)) {
            return false;
        }
        Inventarios other = (Inventarios) object;
        if ((this.inventariosPK == null && other.inventariosPK != null) || (this.inventariosPK != null && !this.inventariosPK.equals(other.inventariosPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "paquete1.jpa.Inventarios[ inventariosPK=" + inventariosPK + " ]";
    }
    
}
