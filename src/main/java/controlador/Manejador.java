package controlador;

import conexion.entidades.Facultad;
import sesiones.FacultadFacade;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "Manejador", 
            loadOnStartup = 1, 
            urlPatterns = {"/Manejador", "/Listar", "/Facultad"})
public class Manejador extends HttpServlet {

    @EJB
    private FacultadFacade facu;

    @Override
    public void init() throws ServletException {
        super.init();
        System.out.println("🚀 Iniciando Manejador...");
        
        try {
            // Almacena la lista de facultades en el contexto del Servlet
            getServletContext().setAttribute("facultades", facu.findAll());
            System.out.println("✅ Facultades cargadas en el contexto: " + facu.findAll().size());
        } catch (Exception e) {
            System.out.println("❌ Error cargando facultades: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getServletPath();
        System.out.println("🔵 Manejador recibió petición: " + action);
        
        String url = "";
        
        switch (action) {
            case "/Facultad":
                // Captura el parámetro facultadId
                Integer nroFacultad = Integer.parseInt(request.getParameter("codigoFacu"));
                System.out.println("📋 Parámetro codigoFacu recibido: " + nroFacultad);
                
                // Busca la facultad en la BD
                Facultad miFacu = facu.find(nroFacultad);
                System.out.println("🔍 Facultad encontrada: " + (miFacu != null ? miFacu.getNombre() : "NULL"));
                
                // Guarda la facultad en el contexto
                getServletContext().setAttribute("facultad", miFacu);
                
                // Define la URL del JSP
                url = "/WEB-INF/vistas/ListarCarreras.jsp";
                System.out.println("➡️ Redirigiendo a: " + url);
                break;
                
            default:
                url = "/faces/index.xhtml";
                break;
        }
        
        // Transfiere el control al JSP
        request.getRequestDispatcher(url).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}