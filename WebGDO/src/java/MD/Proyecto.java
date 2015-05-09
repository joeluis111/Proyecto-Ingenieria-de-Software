/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MD;

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
 * @author Kenny
 */
@Entity
@Table(name = "proyectos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Proyecto.findAll", query = "SELECT p FROM Proyecto p"),
    @NamedQuery(name = "Proyecto.findByProid", query = "SELECT p FROM Proyecto p WHERE p.proid = :proid"),
    @NamedQuery(name = "Proyecto.findByPronombre", query = "SELECT p FROM Proyecto p WHERE p.pronombre = :pronombre")})
public class Proyecto implements Serializable, Entidad {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "PROID")
    private Integer proid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "PRONOMBRE")
    private String pronombre;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "proyecto")
    private Collection<UsoPlaneado> usoPlaneadoCollection;
    @OneToMany(mappedBy = "proid")
    private Collection<Documento> documentoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "proid")
    private Collection<Calendario> calendarioCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "proyecto")
    private Collection<Inventario> inventarioCollection;

    public Proyecto() {
    }

    public Proyecto(Integer proid) {
        this.proid = proid;
    }

    public Proyecto(Integer proid, String pronombre) {
        this.proid = proid;
        this.pronombre = pronombre;
    }

    public Integer getProid() {
        return proid;
    }

    public void setProid(Integer proid) {
        this.proid = proid;
    }

    public String getPronombre() {
        return pronombre;
    }

    public void setPronombre(String pronombre) {
        this.pronombre = pronombre;
    }

    @XmlTransient
    public Collection<UsoPlaneado> getUsoPlaneadoCollection() {
        return usoPlaneadoCollection;
    }

    public void setUsoPlaneadoCollection(Collection<UsoPlaneado> usoPlaneadoCollection) {
        this.usoPlaneadoCollection = usoPlaneadoCollection;
    }

    @XmlTransient
    public Collection<Documento> getDocumentoCollection() {
        return documentoCollection;
    }

    public void setDocumentoCollection(Collection<Documento> documentoCollection) {
        this.documentoCollection = documentoCollection;
    }

    @XmlTransient
    public Collection<Calendario> getCalendarioCollection() {
        return calendarioCollection;
    }

    public void setCalendarioCollection(Collection<Calendario> calendarioCollection) {
        this.calendarioCollection = calendarioCollection;
    }

    @XmlTransient
    public Collection<Inventario> getInventarioCollection() {
        return inventarioCollection;
    }

    public void setInventarioCollection(Collection<Inventario> inventarioCollection) {
        this.inventarioCollection = inventarioCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (proid != null ? proid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Proyecto)) {
            return false;
        }
        Proyecto other = (Proyecto) object;
        if ((this.proid == null && other.proid != null) || (this.proid != null && !this.proid.equals(other.proid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "MD.Proyecto[ proid=" + proid + " ]";
    }

    @Override
    public Object getIdentidad() {
        return this.getProid();
    }

    @Override
    public String getCadenaDesplegable() {
        return this.getPronombre();
    }
    
}
