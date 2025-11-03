package conexion.entidades;

import conexion.entidades.util.JsfUtil;
import conexion.entidades.util.JsfUtil.PersistAction;
import sesiones.FacultadFacade;
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

@Named("facultadController")
@ViewScoped
public class FacultadController implements Serializable {

    @EJB
    private FacultadFacade facultadFacade;

    private List<Facultad> items = null;
    private Facultad selected;

    @PostConstruct
    public void init() {
        System.out.println("✅ Bean FacultadController creado");
        loadItems();
    }

    private void loadItems() {
        try {
            items = facultadFacade.findAll();
            System.out.println("🔍 Facultades cargadas: " + items.size());
        } catch (Exception e) {
            System.out.println("❌ Error cargando facultades: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public List<Facultad> getItems() {
        if (items == null) {
            loadItems();
        }
        return items;
    }

    public Facultad getSelected() {
        return selected;
    }

    public void setSelected(Facultad selected) {
        this.selected = selected;
    }

    public void prepareCreate() {
        selected = new Facultad();
        System.out.println("✅ Preparando nueva facultad");
    }

    public void prepareEdit() {
        System.out.println("✅ Preparando edición de facultad: " + selected.getIdfacultad());
    }

    public void prepareView() {
        System.out.println("✅ Preparando vista de facultad: " + selected.getIdfacultad());
    }

    public void create() {
        System.out.println("🔵 Intentando crear facultad...");
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("FacultadCreated"));
        if (!JsfUtil.isValidationFailed()) {
            loadItems();
        }
    }

    public void update() {
        System.out.println("🔵 Intentando actualizar facultad...");
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("FacultadUpdated"));
        if (!JsfUtil.isValidationFailed()) {
            loadItems();
        }
    }

    public void destroy() {
        System.out.println("🔵 Intentando eliminar facultad...");
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("FacultadDeleted"));
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
                    facultadFacade.edit(selected);
                    System.out.println("✅ Facultad actualizada con ID: " + selected.getIdfacultad());
                } else if (persistAction == PersistAction.CREATE) {
                    facultadFacade.create(selected);
                    System.out.println("✅ Facultad creada con ID: " + selected.getIdfacultad());
                } else if (persistAction == PersistAction.DELETE) {
                    facultadFacade.remove(selected);
                    System.out.println("✅ Facultad eliminada con ID: " + selected.getIdfacultad());
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

    public List<Facultad> getItemsAvailableSelectMany() {
        return facultadFacade.findAll();
    }

    public List<Facultad> getItemsAvailableSelectOne() {
        return facultadFacade.findAll();
    }

    public Facultad getFacultad(java.lang.Integer id) {
        return facultadFacade.find(id);
    }

    @FacesConverter(forClass = Facultad.class)
    public static class FacultadControllerConverter implements Converter<Facultad> {

        @Override
        public Facultad getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            FacultadController controller = (FacultadController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "facultadController");
            return controller.getFacultad(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            return Integer.valueOf(value);
        }

        String getStringKey(java.lang.Integer value) {
            return value.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Facultad object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Facultad) {
                Facultad o = (Facultad) object;
                return getStringKey(o.getIdfacultad());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, 
                    "object {0} is of type {1}; expected type: {2}", 
                    new Object[]{object, object.getClass().getName(), Facultad.class.getName()});
                return null;
            }
        }
    }
}