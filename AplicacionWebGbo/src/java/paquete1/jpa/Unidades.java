/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package paquete1.jpa;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Bryan
 */
@Entity
@Table(name = "unidades")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Unidades.findAll", query = "SELECT u FROM Unidades u"),
    @NamedQuery(name = "Unidades.findByUnidadID", query = "SELECT u FROM Unidades u WHERE u.unidadID = :unidadID"),
    @NamedQuery(name = "Unidades.findByNombre", query = "SELECT u FROM Unidades u WHERE u.nombre = :nombre")})
public class Unidades implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "unidadID")
    private Integer unidadID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "nombre")
    private String nombre;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "unidadID")
    private Collection<Materiales> materialesCollection;

    public Unidades() {
    }

    public Unidades(Integer unidadID) {
        this.unidadID = unidadID;
    }

    public Unidades(Integer unidadID, String nombre) {
        this.unidadID = unidadID;
        this.nombre = nombre;
    }

    public Integer getUnidadID() {
        return unidadID;
    }

    public void setUnidadID(Integer unidadID) {
        this.unidadID = unidadID;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @XmlTransient
    public Collection<Materiales> getMaterialesCollection() {
        return materialesCollection;
    }

    public void setMaterialesCollection(Collection<Materiales> materialesCollection) {
        this.materialesCollection = materialesCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (unidadID != null ? unidadID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Unidades)) {
            return false;
        }
        Unidades other = (Unidades) object;
        if ((this.unidadID == null && other.unidadID != null) || (this.unidadID != null && !this.unidadID.equals(other.unidadID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "paquete1.jpa.Unidades[ unidadID=" + unidadID + " ]";
    }
    
}
