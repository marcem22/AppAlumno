package controlador;

import conexion.entidades.Alumno;
import conexion.entidades.Carrera;
import sesiones.AlumnoFacade;
import sesiones.CarreraFacade;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "GuardarAlumnoServlet", urlPatterns = {"/GuardarAlumno"})
public class GuardarAlumnoServlet extends HttpServlet {

    @EJB
    private AlumnoFacade alumnoFacade;
    
    @EJB
    private CarreraFacade carreraFacade;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        System.out.println("🔵 GuardarAlumnoServlet - Procesando formulario");
        
        try {
            // Captura los datos del formulario
            String nombre = request.getParameter("nombre");
            String apellido = request.getParameter("apellido");
            String dni = request.getParameter("dni");  // ✅ CAMBIADO A STRING
            Integer carreraId = Integer.parseInt(request.getParameter("carreraId"));
            
            System.out.println("📋 Datos recibidos:");
            System.out.println("   Nombre: " + nombre);
            System.out.println("   Apellido: " + apellido);
            System.out.println("   DNI: " + dni);
            System.out.println("   Carrera ID: " + carreraId);
            
            // Busca la carrera
            Carrera carrera = carreraFacade.find(carreraId);
            
            if (carrera == null) {
                System.out.println("❌ Carrera no encontrada: " + carreraId);
                response.sendRedirect(request.getContextPath() + "/AgregarAlumno?error=carrera");
                return;
            }
            
            // Crea el nuevo alumno
            Alumno nuevoAlumno = new Alumno();
            nuevoAlumno.setNombre(nombre);
            nuevoAlumno.setApellido(apellido);
            nuevoAlumno.setDni(dni);  // ✅ AHORA ES STRING
            nuevoAlumno.setCarreraIdcarrera(carrera);
            
            // Guarda en la base de datos
            alumnoFacade.create(nuevoAlumno);
            
            System.out.println("✅ Alumno guardado exitosamente con ID: " + nuevoAlumno.getIdalumno());
            
            // Redirige a la lista de carreras de esa facultad
            response.sendRedirect(request.getContextPath() + 
                                "/Facultad?codigoFacu=" + carrera.getFacultadIdfacultad().getIdfacultad());
            
        } catch (NumberFormatException e) {
            System.out.println("❌ Error en formato de datos: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/AgregarAlumno?error=formato");
        } catch (Exception e) {
            System.out.println("❌ Error guardando alumno: " + e.getMessage());
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/AgregarAlumno?error=general");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Redirige al formulario si alguien accede por GET
        response.sendRedirect(request.getContextPath() + "/AgregarAlumno");
    }
}