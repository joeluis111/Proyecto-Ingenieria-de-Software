/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MD;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Kenny
 */
@Entity
@Table(name = "documentos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Documento.findAll", query = "SELECT d FROM Documento d"),
    @NamedQuery(name = "Documento.findByDocid", query = "SELECT d FROM Documento d WHERE d.docid = :docid"),
    @NamedQuery(name = "Documento.findByDocdescripcion", query = "SELECT d FROM Documento d WHERE d.docdescripcion = :docdescripcion")})
public class Documento implements Serializable, Entidad {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "DOCID")
    private Integer docid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "DOCDESCRIPCION")
    private String docdescripcion;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Column(name = "DOCIMAGEN")
    private byte[] docimagen;
    @JoinColumn(name = "TDOCID", referencedColumnName = "TDOCID")
    @ManyToOne(optional = false)
    private TipoDocumento tdocid;
    @JoinColumn(name = "PROID", referencedColumnName = "PROID")
    @ManyToOne
    private Proyecto proid;
    @JoinColumn(name = "EMPID", referencedColumnName = "EMPID")
    @ManyToOne
    private Empleado empid;

    public Documento() {
    }

    public Documento(Integer docid) {
        this.docid = docid;
    }

    public Documento(Integer docid, String docdescripcion, byte[] docimagen) {
        this.docid = docid;
        this.docdescripcion = docdescripcion;
        this.docimagen = docimagen;
    }

    public Integer getDocid() {
        return docid;
    }

    public void setDocid(Integer docid) {
        this.docid = docid;
    }

    public String getDocdescripcion() {
        return docdescripcion;
    }

    public void setDocdescripcion(String docdescripcion) {
        this.docdescripcion = docdescripcion;
    }

    public byte[] getDocimagen() {
        return docimagen;
    }

    public void setDocimagen(byte[] docimagen) {
        this.docimagen = docimagen;
    }

    public TipoDocumento getTdocid() {
        return tdocid;
    }

    public void setTdocid(TipoDocumento tdocid) {
        this.tdocid = tdocid;
    }

    public Proyecto getProid() {
        return proid;
    }

    public void setProid(Proyecto proid) {
        this.proid = proid;
    }

    public Empleado getEmpid() {
        return empid;
    }

    public void setEmpid(Empleado empid) {
        this.empid = empid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (docid != null ? docid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Documento)) {
            return false;
        }
        Documento other = (Documento) object;
        if ((this.docid == null && other.docid != null) || (this.docid != null && !this.docid.equals(other.docid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "MD.Documento[ docid=" + docid + " ]";
    }

    @Override
    public Object getID() {
        return getDocid();
    }

    @Override
    public String getCadenaDesplegable() {
        return getDocdescripcion();
    }
    
}
