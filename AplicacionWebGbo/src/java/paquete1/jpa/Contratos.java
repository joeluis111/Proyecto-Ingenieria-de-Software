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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
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
@Table(name = "contratos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Contratos.findAll", query = "SELECT c FROM Contratos c"),
    @NamedQuery(name = "Contratos.findByContratoID", query = "SELECT c FROM Contratos c WHERE c.contratoID = :contratoID")})
public class Contratos implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "contratoID")
    private Integer contratoID;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 2147483647)
    @Column(name = "textoContrato")
    private String textoContrato;
    @JoinTable(name = "tiene", joinColumns = {
        @JoinColumn(name = "contratoID", referencedColumnName = "contratoID")}, inverseJoinColumns = {
        @JoinColumn(name = "clienteID", referencedColumnName = "clienteID")})
    @ManyToMany
    private Collection<Clientes> clientesCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "contratoID")
    private Collection<Proyectos> proyectosCollection;

    public Contratos() {
    }

    public Contratos(Integer contratoID) {
        this.contratoID = contratoID;
    }

    public Contratos(Integer contratoID, String textoContrato) {
        this.contratoID = contratoID;
        this.textoContrato = textoContrato;
    }

    public Integer getContratoID() {
        return contratoID;
    }

    public void setContratoID(Integer contratoID) {
        this.contratoID = contratoID;
    }

    public String getTextoContrato() {
        return textoContrato;
    }

    public void setTextoContrato(String textoContrato) {
        this.textoContrato = textoContrato;
    }

    @XmlTransient
    public Collection<Clientes> getClientesCollection() {
        return clientesCollection;
    }

    public void setClientesCollection(Collection<Clientes> clientesCollection) {
        this.clientesCollection = clientesCollection;
    }

    @XmlTransient
    public Collection<Proyectos> getProyectosCollection() {
        return proyectosCollection;
    }

    public void setProyectosCollection(Collection<Proyectos> proyectosCollection) {
        this.proyectosCollection = proyectosCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (contratoID != null ? contratoID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Contratos)) {
            return false;
        }
        Contratos other = (Contratos) object;
        if ((this.contratoID == null && other.contratoID != null) || (this.contratoID != null && !this.contratoID.equals(other.contratoID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "paquete1.jpa.Contratos[ contratoID=" + contratoID + " ]";
    }
    
}
