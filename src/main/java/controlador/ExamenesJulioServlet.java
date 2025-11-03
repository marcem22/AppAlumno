package controlador;

import conexion.entidades.Examen;
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

@WebServlet(name = "ExamenesJulioServlet", urlPatterns = {"/ExamenesJulio"})
@Transactional
public class ExamenesJulioServlet extends HttpServlet {

    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        System.out.println("🔵 ExamenesJulioServlet - Consultando exámenes de julio");
        
        try {
            // Consulta JPQL: Exámenes de julio 2025
            String jpql = "SELECT e FROM Examen e " +
                         "WHERE FUNCTION('MONTH', e.examenPK.fecha) = 7 " +
                         "AND FUNCTION('YEAR', e.examenPK.fecha) = 2025 " +
                         "ORDER BY e.examenPK.fecha";
            
            TypedQuery<Examen> query = em.createQuery(jpql, Examen.class);
            List<Examen> examenesJulio = query.getResultList();
            
            System.out.println("📋 Exámenes de julio encontrados: " + examenesJulio.size());
            
            // Envía los datos al JSP
            request.setAttribute("examenes", examenesJulio);
            request.getRequestDispatcher("/WEB-INF/vistas/ExamenesJulio.jsp").forward(request, response);
            
        } catch (Exception e) {
            System.out.println("❌ Error ejecutando consulta: " + e.getMessage());
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, 
                             "Error al consultar exámenes: " + e.getMessage());
        }
    }
}