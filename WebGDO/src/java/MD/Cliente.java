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
@Table(name = "clientes")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cliente.findAll", query = "SELECT c FROM Cliente c"),
    @NamedQuery(name = "Cliente.findByCliid", query = "SELECT c FROM Cliente c WHERE c.cliid = :cliid"),
    @NamedQuery(name = "Cliente.findByClinombres", query = "SELECT c FROM Cliente c WHERE c.clinombres = :clinombres"),
    @NamedQuery(name = "Cliente.findByCliapellidos", query = "SELECT c FROM Cliente c WHERE c.cliapellidos = :cliapellidos"),
    @NamedQuery(name = "Cliente.findByClidireccion", query = "SELECT c FROM Cliente c WHERE c.clidireccion = :clidireccion"),
    @NamedQuery(name = "Cliente.findByClicodigopostal", query = "SELECT c FROM Cliente c WHERE c.clicodigopostal = :clicodigopostal")})
public class Cliente implements Serializable, Entidad {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "CLIID")
    private Integer cliid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "CLINOMBRES")
    private String clinombres;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "CLIAPELLIDOS")
    private String cliapellidos;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "CLIDIRECCION")
    private String clidireccion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "CLICODIGOPOSTAL")
    private String clicodigopostal;

    public Cliente() {
    }

    public Cliente(Integer cliid) {
        this.cliid = cliid;
    }

    public Cliente(Integer cliid, String clinombres, String cliapellidos, String clidireccion, String clicodigopostal) {
        this.cliid = cliid;
        this.clinombres = clinombres;
        this.cliapellidos = cliapellidos;
        this.clidireccion = clidireccion;
        this.clicodigopostal = clicodigopostal;
    }

    public Integer getCliid() {
        return cliid;
    }

    public void setCliid(Integer cliid) {
        this.cliid = cliid;
    }

    public String getClinombres() {
        return clinombres;
    }

    public void setClinombres(String clinombres) {
        this.clinombres = clinombres;
    }

    public String getCliapellidos() {
        return cliapellidos;
    }

    public void setCliapellidos(String cliapellidos) {
        this.cliapellidos = cliapellidos;
    }

    public String getClidireccion() {
        return clidireccion;
    }

    public void setClidireccion(String clidireccion) {
        this.clidireccion = clidireccion;
    }

    public String getClicodigopostal() {
        return clicodigopostal;
    }

    public void setClicodigopostal(String clicodigopostal) {
        this.clicodigopostal = clicodigopostal;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cliid != null ? cliid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cliente)) {
            return false;
        }
        Cliente other = (Cliente) object;
        if ((this.cliid == null && other.cliid != null) || (this.cliid != null && !this.cliid.equals(other.cliid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "MD.Cliente[ cliid=" + cliid + " ]";
    }

    @Override
    public Object getIdentidad() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getCadenaDesplegable() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
