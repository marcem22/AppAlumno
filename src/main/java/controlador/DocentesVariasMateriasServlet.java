package controlador;

import conexion.entidades.Docente;
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

@WebServlet(name = "DocentesVariasMateriasServlet", urlPatterns = {"/DocentesVariasMaterias"})
@Transactional
public class DocentesVariasMateriasServlet extends HttpServlet {

    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        System.out.println("🔵 DocentesVariasMateriasServlet - Consultando docentes con más de 2 materias");
        
        try {
            // Consulta JPQL: Docentes que dictan más de 2 materias
            String jpql = "SELECT d FROM Docente d " +
                         "WHERE SIZE(d.materiaCollection) > 2 " +
                         "ORDER BY d.nombre";
            
            TypedQuery<Docente> query = em.createQuery(jpql, Docente.class);
            List<Docente> docentes = query.getResultList();
            
            System.out.println("📋 Docentes con más de 2 materias: " + docentes.size());
            
            // Envía los datos al JSP
            request.setAttribute("docentes", docentes);
            request.getRequestDispatcher("/WEB-INF/vistas/DocentesVariasMaterias.jsp").forward(request, response);
            
        } catch (Exception e) {
            System.out.println("❌ Error ejecutando consulta: " + e.getMessage());
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, 
                             "Error al consultar docentes: " + e.getMessage());
        }
    }
}