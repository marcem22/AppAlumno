package conexion.entidades;

import conexion.entidades.util.JsfUtil;
import conexion.entidades.util.JsfUtil.PersistAction;
import sesiones.AlumnoFacade;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.inject.Named;
import jakarta.faces.view.ViewScoped;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named("alumnoController")
@ViewScoped
public class AlumnoController implements Serializable {

    @EJB
    private AlumnoFacade alumnoFacade;

    private List<Alumno> items = null;
    private Alumno selected;

    @PostConstruct
    public void init() {
        System.out.println("✅ Bean AlumnoController creado");
        loadItems();
    }

    private void loadItems() {
        try {
            items = alumnoFacade.findAll();
            System.out.println("🔍 Alumnos cargados: " + items.size());
        } catch (Exception e) {
            System.out.println("❌ Error cargando alumnos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public List<Alumno> getItems() {
        if (items == null) {
            loadItems();
        }
        return items;
    }

    public Alumno getSelected() {
        return selected;
    }

    public void setSelected(Alumno selected) {
        this.selected = selected;
    }

    public void prepareCreate() {
        selected = new Alumno();
        System.out.println("✅ Preparando nuevo alumno");
    }

    public void prepareEdit() {
        System.out.println("✅ Preparando edición de alumno: " + selected.getIdalumno());
    }

    public void prepareView() {
        System.out.println("✅ Preparando vista de alumno: " + selected.getIdalumno());
    }

    public void create() {
        System.out.println("🔵 Intentando crear alumno...");
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("AlumnoCreated"));
        if (!JsfUtil.isValidationFailed()) {
            loadItems();
        }
    }

    public void update() {
        System.out.println("🔵 Intentando actualizar alumno...");
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("AlumnoUpdated"));
        if (!JsfUtil.isValidationFailed()) {
            loadItems();
        }
    }

    public void destroy() {
        System.out.println("🔵 Intentando eliminar alumno...");
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("AlumnoDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null;
            loadItems();
        }
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            try {
                System.out.println("🟢 Persistiendo acción: " + persistAction);
                
                if (persistAction == PersistAction.UPDATE) {
                    alumnoFacade.edit(selected);
                    System.out.println("✅ Alumno actualizado con ID: " + selected.getIdalumno());
                } else if (persistAction == PersistAction.CREATE) {
                    alumnoFacade.create(selected);
                    System.out.println("✅ Alumno creado con ID: " + selected.getIdalumno());
                } else if (persistAction == PersistAction.DELETE) {
                    alumnoFacade.remove(selected);
                    System.out.println("✅ Alumno eliminado con ID: " + selected.getIdalumno());
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (Exception ex) {
                System.out.println("❌ ERROR al persistir: " + ex.getMessage());
                ex.printStackTrace();
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public List<Alumno> getItemsAvailableSelectMany() {
        return alumnoFacade.findAll();
    }

    public List<Alumno> getItemsAvailableSelectOne() {
        return alumnoFacade.findAll();
    }

    public Alumno getAlumno(java.lang.Integer id) {
        return alumnoFacade.find(id);
    }

    @FacesConverter(forClass = Alumno.class)
    public static class AlumnoControllerConverter implements Converter<Alumno> {

        @Override
        public Alumno getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            AlumnoController controller = (AlumnoController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "alumnoController");
            return controller.getAlumno(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            return Integer.valueOf(value);
        }

        String getStringKey(java.lang.Integer value) {
            return value.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Alumno object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Alumno) {
                Alumno o = (Alumno) object;
                return getStringKey(o.getIdalumno());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, 
                    "object {0} is of type {1}; expected type: {2}", 
                    new Object[]{object, object.getClass().getName(), Alumno.class.getName()});
                return null;
            }
        }
    }
}