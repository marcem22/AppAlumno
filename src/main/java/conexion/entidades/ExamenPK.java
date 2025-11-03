/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package conexion.entidades;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author marcela
 */
@Embeddable
public class ExamenPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "alumno_idalumno")
    private int alumnoIdalumno;
    @Basic(optional = false)
    @NotNull
    @Column(name = "materia_idmateria")
    private int materiaIdmateria;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;

    public ExamenPK() {
    }

    public ExamenPK(int alumnoIdalumno, int materiaIdmateria, Date fecha) {
        this.alumnoIdalumno = alumnoIdalumno;
        this.materiaIdmateria = materiaIdmateria;
        this.fecha = fecha;
    }

    public int getAlumnoIdalumno() {
        return alumnoIdalumno;
    }

    public void setAlumnoIdalumno(int alumnoIdalumno) {
        this.alumnoIdalumno = alumnoIdalumno;
    }

    public int getMateriaIdmateria() {
        return materiaIdmateria;
    }

    public void setMateriaIdmateria(int materiaIdmateria) {
        this.materiaIdmateria = materiaIdmateria;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) alumnoIdalumno;
        hash += (int) materiaIdmateria;
        hash += (fecha != null ? fecha.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ExamenPK)) {
            return false;
        }
        ExamenPK other = (ExamenPK) object;
        if (this.alumnoIdalumno != other.alumnoIdalumno) {
            return false;
        }
        if (this.materiaIdmateria != other.materiaIdmateria) {
            return false;
        }
        if ((this.fecha == null && other.fecha != null) || (this.fecha != null && !this.fecha.equals(other.fecha))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "conexion.entidades.ExamenPK[ alumnoIdalumno=" + alumnoIdalumno + ", materiaIdmateria=" + materiaIdmateria + ", fecha=" + fecha + " ]";
    }
    
}
