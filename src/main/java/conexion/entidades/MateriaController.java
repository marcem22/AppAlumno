package conexion.entidades;

import conexion.entidades.util.JsfUtil;
import conexion.entidades.util.JsfUtil.PersistAction;
import sesiones.MateriaFacade;
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

@Named("materiaController")
@ViewScoped
public class MateriaController implements Serializable {

    @EJB
    private MateriaFacade materiaFacade;

    private List<Materia> items = null;
    private Materia selected;

    @PostConstruct
    public void init() {
        System.out.println("✅ Bean MateriaController creado");
        loadItems();
    }

    private void loadItems() {
        try {
            items = materiaFacade.findAll();
            System.out.println("🔍 Materias cargadas: " + items.size());
        } catch (Exception e) {
            System.out.println("❌ Error cargando materias: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public List<Materia> getItems() {
        if (items == null) {
            loadItems();
        }
        return items;
    }

    public Materia getSelected() {
        return selected;
    }

    public void setSelected(Materia selected) {
        this.selected = selected;
    }

    public void prepareCreate() {
        selected = new Materia();
        System.out.println("✅ Preparando nueva materia");
    }

    public void prepareEdit() {
        System.out.println("✅ Preparando edición de materia: " + selected.getIdmateria());
    }

    public void prepareView() {
        System.out.println("✅ Preparando vista de materia: " + selected.getIdmateria());
    }

    public void create() {
        System.out.println("🔵 Intentando crear materia...");
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("MateriaCreated"));
        if (!JsfUtil.isValidationFailed()) {
            loadItems();
        }
    }

    public void update() {
        System.out.println("🔵 Intentando actualizar materia...");
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("MateriaUpdated"));
        if (!JsfUtil.isValidationFailed()) {
            loadItems();
        }
    }

    public void destroy() {
        System.out.println("🔵 Intentando eliminar materia...");
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("MateriaDeleted"));
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
                    materiaFacade.edit(selected);
                    System.out.println("✅ Materia actualizada con ID: " + selected.getIdmateria());
                } else if (persistAction == PersistAction.CREATE) {
                    materiaFacade.create(selected);
                    System.out.println("✅ Materia creada con ID: " + selected.getIdmateria());
                } else if (persistAction == PersistAction.DELETE) {
                    materiaFacade.remove(selected);
                    System.out.println("✅ Materia eliminada con ID: " + selected.getIdmateria());
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

    public List<Materia> getItemsAvailableSelectMany() {
        return materiaFacade.findAll();
    }

    public List<Materia> getItemsAvailableSelectOne() {
        return materiaFacade.findAll();
    }

    public Materia getMateria(java.lang.Integer id) {
        return materiaFacade.find(id);
    }

    @FacesConverter(forClass = Materia.class)
    public static class MateriaControllerConverter implements Converter<Materia> {

        @Override
        public Materia getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            MateriaController controller = (MateriaController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "materiaController");
            return controller.getMateria(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            return Integer.valueOf(value);
        }

        String getStringKey(java.lang.Integer value) {
            return value.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Materia object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Materia) {
                Materia o = (Materia) object;
                return getStringKey(o.getIdmateria());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, 
                    "object {0} is of type {1}; expected type: {2}", 
                    new Object[]{object, object.getClass().getName(), Materia.class.getName()});
                return null;
            }
        }
    }
}