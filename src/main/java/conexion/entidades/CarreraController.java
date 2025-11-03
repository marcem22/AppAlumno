package conexion.entidades;

import conexion.entidades.util.JsfUtil;
import conexion.entidades.util.JsfUtil.PersistAction;
import sesiones.CarreraFacade;
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

@Named("carreraController")
@ViewScoped
public class CarreraController implements Serializable {

    @EJB
    private CarreraFacade carreraFacade;

    private List<Carrera> items = null;
    private Carrera selected;

    @PostConstruct
    public void init() {
        System.out.println("✅ Bean CarreraController creado");
        loadItems();
    }

    private void loadItems() {
        try {
            items = carreraFacade.findAll();
            System.out.println("🔍 Carreras cargadas: " + items.size());
        } catch (Exception e) {
            System.out.println("❌ Error cargando carreras: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public List<Carrera> getItems() {
        if (items == null) {
            loadItems();
        }
        return items;
    }

    public Carrera getSelected() {
        return selected;
    }

    public void setSelected(Carrera selected) {
        this.selected = selected;
    }

    public void prepareCreate() {
        selected = new Carrera();
        System.out.println("✅ Preparando nueva carrera");
    }

    public void prepareEdit() {
        System.out.println("✅ Preparando edición de carrera: " + selected.getIdcarrera());
    }

    public void prepareView() {
        System.out.println("✅ Preparando vista de carrera: " + selected.getIdcarrera());
    }

    public void create() {
        System.out.println("🔵 Intentando crear carrera...");
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("CarreraCreated"));
        if (!JsfUtil.isValidationFailed()) {
            loadItems();
        }
    }

    public void update() {
        System.out.println("🔵 Intentando actualizar carrera...");
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("CarreraUpdated"));
        if (!JsfUtil.isValidationFailed()) {
            loadItems();
        }
    }

    public void destroy() {
        System.out.println("🔵 Intentando eliminar carrera...");
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("CarreraDeleted"));
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
                    carreraFacade.edit(selected);
                    System.out.println("✅ Carrera actualizada con ID: " + selected.getIdcarrera());
                } else if (persistAction == PersistAction.CREATE) {
                    carreraFacade.create(selected);
                    System.out.println("✅ Carrera creada con ID: " + selected.getIdcarrera());
                } else if (persistAction == PersistAction.DELETE) {
                    carreraFacade.remove(selected);
                    System.out.println("✅ Carrera eliminada con ID: " + selected.getIdcarrera());
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

    public List<Carrera> getItemsAvailableSelectMany() {
        return carreraFacade.findAll();
    }

    public List<Carrera> getItemsAvailableSelectOne() {
        return carreraFacade.findAll();
    }

    public Carrera getCarrera(java.lang.Integer id) {
        return carreraFacade.find(id);
    }

    @FacesConverter(forClass = Carrera.class)
    public static class CarreraControllerConverter implements Converter<Carrera> {

        @Override
        public Carrera getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            CarreraController controller = (CarreraController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "carreraController");
            return controller.getCarrera(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            return Integer.valueOf(value);
        }

        String getStringKey(java.lang.Integer value) {
            return value.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Carrera object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Carrera) {
                Carrera o = (Carrera) object;
                return getStringKey(o.getIdcarrera());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, 
                    "object {0} is of type {1}; expected type: {2}", 
                    new Object[]{object, object.getClass().getName(), Carrera.class.getName()});
                return null;
            }
        }
    }
}