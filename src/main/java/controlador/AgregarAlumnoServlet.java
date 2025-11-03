package controlador;

import conexion.entidades.Carrera;
import sesiones.CarreraFacade;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "AgregarAlumnoServlet", urlPatterns = {"/AgregarAlumno"})
public class AgregarAlumnoServlet extends HttpServlet {

    @EJB
    private CarreraFacade carreraFacade;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        System.out.println("🔵 AgregarAlumnoServlet - Cargando formulario");
        
        // Obtiene el parámetro de la carrera elegida (si viene)
        String carreraIdParam = request.getParameter("carreraId");
        
        if (carreraIdParam != null && !carreraIdParam.isEmpty()) {
            Integer carreraId = Integer.parseInt(carreraIdParam);
            System.out.println("📋 Carrera preseleccionada: " + carreraId);
            request.setAttribute("carreraPreseleccionada", carreraId);
        }
        
        // Carga todas las carreras para el <select>
        List<Carrera> carreras = carreraFacade.findAll();
        request.setAttribute("carreras", carreras);
        System.out.println("✅ Carreras cargadas: " + carreras.size());
        
        // Transfiere al JSP
        request.getRequestDispatcher("/WEB-INF/vistas/AgregarAlumno.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}