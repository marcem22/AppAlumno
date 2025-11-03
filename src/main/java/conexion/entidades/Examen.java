/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package conexion.entidades;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author marcela
 */
@Entity
@Table(name = "examen")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Examen.findAll", query = "SELECT e FROM Examen e"),
    @NamedQuery(name = "Examen.findByAlumnoIdalumno", query = "SELECT e FROM Examen e WHERE e.examenPK.alumnoIdalumno = :alumnoIdalumno"),
    @NamedQuery(name = "Examen.findByMateriaIdmateria", query = "SELECT e FROM Examen e WHERE e.examenPK.materiaIdmateria = :materiaIdmateria"),
    @NamedQuery(name = "Examen.findByFecha", query = "SELECT e FROM Examen e WHERE e.examenPK.fecha = :fecha"),
    @NamedQuery(name = "Examen.findByNota", query = "SELECT e FROM Examen e WHERE e.nota = :nota")})
public class Examen implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ExamenPK examenPK;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "nota")
    private BigDecimal nota;
    @JoinColumn(name = "alumno_idalumno", referencedColumnName = "idalumno", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Alumno alumno;
    @JoinColumn(name = "materia_idmateria", referencedColumnName = "idmateria", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Materia materia;

    public Examen() {
    }

    public Examen(ExamenPK examenPK) {
        this.examenPK = examenPK;
    }

    public Examen(int alumnoIdalumno, int materiaIdmateria, Date fecha) {
        this.examenPK = new ExamenPK(alumnoIdalumno, materiaIdmateria, fecha);
    }

    public ExamenPK getExamenPK() {
        return examenPK;
    }

    public void setExamenPK(ExamenPK examenPK) {
        this.examenPK = examenPK;
    }

    public BigDecimal getNota() {
        return nota;
    }

    public void setNota(BigDecimal nota) {
        this.nota = nota;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    public Materia getMateria() {
        return materia;
    }

    public void setMateria(Materia materia) {
        this.materia = materia;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (examenPK != null ? examenPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Examen)) {
            return false;
        }
        Examen other = (Examen) object;
        if ((this.examenPK == null && other.examenPK != null) || (this.examenPK != null && !this.examenPK.equals(other.examenPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "conexion.entidades.Examen[ examenPK=" + examenPK + " ]";
    }
    public conexion.entidades.ExamenPK getId() {
    return getExamenPK();
}
}
