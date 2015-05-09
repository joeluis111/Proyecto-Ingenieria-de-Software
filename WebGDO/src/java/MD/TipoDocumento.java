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
@Table(name = "tiposdocumento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoDocumento.findAll", query = "SELECT t FROM TipoDocumento t"),
    @NamedQuery(name = "TipoDocumento.findByTdocid", query = "SELECT t FROM TipoDocumento t WHERE t.tdocid = :tdocid"),
    @NamedQuery(name = "TipoDocumento.findByTdocnombre", query = "SELECT t FROM TipoDocumento t WHERE t.tdocnombre = :tdocnombre")})
public class TipoDocumento implements Serializable, Entidad {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "TDOCID")
    private Integer tdocid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "TDOCNOMBRE")
    private String tdocnombre;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tdocid")
    private Collection<Documento> documentoCollection;

    public TipoDocumento() {
    }

    public TipoDocumento(Integer tdocid) {
        this.tdocid = tdocid;
    }

    public TipoDocumento(Integer tdocid, String tdocnombre) {
        this.tdocid = tdocid;
        this.tdocnombre = tdocnombre;
    }

    public Integer getTdocid() {
        return tdocid;
    }

    public void setTdocid(Integer tdocid) {
        this.tdocid = tdocid;
    }

    public String getTdocnombre() {
        return tdocnombre;
    }

    public void setTdocnombre(String tdocnombre) {
        this.tdocnombre = tdocnombre;
    }

    @XmlTransient
    public Collection<Documento> getDocumentoCollection() {
        return documentoCollection;
    }

    public void setDocumentoCollection(Collection<Documento> documentoCollection) {
        this.documentoCollection = documentoCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tdocid != null ? tdocid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoDocumento)) {
            return false;
        }
        TipoDocumento other = (TipoDocumento) object;
        if ((this.tdocid == null && other.tdocid != null) || (this.tdocid != null && !this.tdocid.equals(other.tdocid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "MD.TipoDocumento[ tdocid=" + tdocid + " ]";
    }

    @Override
    public Object getIdentidad() {
        return this.getTdocid();
    }

    @Override
    public String getCadenaDesplegable() {
        return this.getTdocnombre();
    }
    
}
