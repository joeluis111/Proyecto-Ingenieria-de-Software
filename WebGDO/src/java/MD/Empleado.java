/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MD;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Kenny
 */
@Entity
@Table(name = "empleados")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Empleado.findAll", query = "SELECT e FROM Empleado e"),
    @NamedQuery(name = "Empleado.findByEmpid", query = "SELECT e FROM Empleado e WHERE e.empid = :empid"),
    @NamedQuery(name = "Empleado.findByEmpcedula", query = "SELECT e FROM Empleado e WHERE e.empcedula = :empcedula"),
    @NamedQuery(name = "Empleado.findByEmpnombres", query = "SELECT e FROM Empleado e WHERE e.empnombres = :empnombres"),
    @NamedQuery(name = "Empleado.findByEmpcodigopostal", query = "SELECT e FROM Empleado e WHERE e.empcodigopostal = :empcodigopostal"),
    @NamedQuery(name = "Empleado.findByEmpdireccion", query = "SELECT e FROM Empleado e WHERE e.empdireccion = :empdireccion"),
    @NamedQuery(name = "Empleado.findByEmpfechanacimiento", query = "SELECT e FROM Empleado e WHERE e.empfechanacimiento = :empfechanacimiento"),
    @NamedQuery(name = "Empleado.findByEmpgenero", query = "SELECT e FROM Empleado e WHERE e.empgenero = :empgenero"),
    @NamedQuery(name = "Empleado.findByEmpnumerotelefono", query = "SELECT e FROM Empleado e WHERE e.empnumerotelefono = :empnumerotelefono"),
    @NamedQuery(name = "Empleado.findByEmpapellidos", query = "SELECT e FROM Empleado e WHERE e.empapellidos = :empapellidos")})
public class Empleado implements Serializable, Entidad {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "EMPID")
    private Integer empid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "EMPCEDULA")
    private String empcedula;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "EMPNOMBRES")
    private String empnombres;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "EMPCODIGOPOSTAL")
    private String empcodigopostal;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "EMPDIRECCION")
    private String empdireccion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "EMPFECHANACIMIENTO")
    @Temporal(TemporalType.DATE)
    private Date empfechanacimiento;
    @Basic(optional = false)
    @NotNull
    @Column(name = "EMPGENERO")
    private Character empgenero;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "EMPNUMEROTELEFONO")
    private String empnumerotelefono;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "EMPAPELLIDOS")
    private String empapellidos;
    @OneToMany(mappedBy = "empid")
    private Collection<Documento> documentoCollection;
    @JoinColumn(name = "TTID", referencedColumnName = "TTID")
    @ManyToOne
    private TipoTrabajador ttid;
    @JoinColumn(name = "TPID", referencedColumnName = "TPID")
    @ManyToOne(optional = false)
    private TituloProfesional tpid;

    public Empleado() {
    }

    public Empleado(Integer empid) {
        this.empid = empid;
    }

    public Empleado(Integer empid, String empcedula, String empnombres, String empcodigopostal, String empdireccion, Date empfechanacimiento, Character empgenero, String empnumerotelefono, String empapellidos) {
        this.empid = empid;
        this.empcedula = empcedula;
        this.empnombres = empnombres;
        this.empcodigopostal = empcodigopostal;
        this.empdireccion = empdireccion;
        this.empfechanacimiento = empfechanacimiento;
        this.empgenero = empgenero;
        this.empnumerotelefono = empnumerotelefono;
        this.empapellidos = empapellidos;
    }

    public Integer getEmpid() {
        return empid;
    }

    public void setEmpid(Integer empid) {
        this.empid = empid;
    }

    public String getEmpcedula() {
        return empcedula;
    }

    public void setEmpcedula(String empcedula) {
        this.empcedula = empcedula;
    }

    public String getEmpnombres() {
        return empnombres;
    }

    public void setEmpnombres(String empnombres) {
        this.empnombres = empnombres;
    }

    public String getEmpcodigopostal() {
        return empcodigopostal;
    }

    public void setEmpcodigopostal(String empcodigopostal) {
        this.empcodigopostal = empcodigopostal;
    }

    public String getEmpdireccion() {
        return empdireccion;
    }

    public void setEmpdireccion(String empdireccion) {
        this.empdireccion = empdireccion;
    }

    public Date getEmpfechanacimiento() {
        return empfechanacimiento;
    }

    public void setEmpfechanacimiento(Date empfechanacimiento) {
        this.empfechanacimiento = empfechanacimiento;
    }

    public Character getEmpgenero() {
        return empgenero;
    }

    public void setEmpgenero(Character empgenero) {
        this.empgenero = empgenero;
    }

    public String getEmpnumerotelefono() {
        return empnumerotelefono;
    }

    public void setEmpnumerotelefono(String empnumerotelefono) {
        this.empnumerotelefono = empnumerotelefono;
    }

    public String getEmpapellidos() {
        return empapellidos;
    }

    public void setEmpapellidos(String empapellidos) {
        this.empapellidos = empapellidos;
    }

    @XmlTransient
    public Collection<Documento> getDocumentoCollection() {
        return documentoCollection;
    }

    public void setDocumentoCollection(Collection<Documento> documentoCollection) {
        this.documentoCollection = documentoCollection;
    }

    public TipoTrabajador getTtid() {
        return ttid;
    }

    public void setTtid(TipoTrabajador ttid) {
        this.ttid = ttid;
    }

    public TituloProfesional getTpid() {
        return tpid;
    }

    public void setTpid(TituloProfesional tpid) {
        this.tpid = tpid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (empid != null ? empid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Empleado)) {
            return false;
        }
        Empleado other = (Empleado) object;
        if ((this.empid == null && other.empid != null) || (this.empid != null && !this.empid.equals(other.empid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "MD.Empleado[ empid=" + empid + " ]";
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
