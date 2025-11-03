package controlador;

import conexion.entidades.Alumno;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "AlumnosSinExamenServlet", urlPatterns = {"/AlumnosSinExamen"})
@Transactional
public class AlumnosSinExamenServlet extends HttpServlet {

    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        System.out.println("🔵 AlumnosSinExamenServlet - Consultando alumnos sin exámenes en 2025");
        
        try {
            // Consulta JPQL: Alumnos que NO rindieron en 2025
            String jpql = "SELECT a FROM Alumno a " +
                         "WHERE a NOT IN (" +
                         "  SELECT DISTINCT e.alumno FROM Examen e " +
                         "  WHERE FUNCTION('YEAR', e.examenPK.fecha) = 2025" +
                         ") " +
                         "ORDER BY a.apellido, a.nombre";
            
            TypedQuery<Alumno> query = em.createQuery(jpql, Alumno.class);
            List<Alumno> alumnosSinExamen = query.getResultList();
            
            System.out.println("📋 Alumnos sin exámenes encontrados: " + alumnosSinExamen.size());
            
            // Envía los datos al JSP
            request.setAttribute("alumnos", alumnosSinExamen);
            request.getRequestDispatcher("/WEB-INF/vistas/AlumnosSinExamen.jsp").forward(request, response);
            
        } catch (Exception e) {
            System.out.println("❌ Error ejecutando consulta: " + e.getMessage());
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, 
                             "Error al consultar alumnos: " + e.getMessage());
        }
    }
}
