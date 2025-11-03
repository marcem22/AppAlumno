package conexion.entidades;

import conexion.entidades.util.JsfUtil;
import conexion.entidades.util.JsfUtil.PersistAction;
import sesiones.ExamenFacade;
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

@Named("examenController")
@ViewScoped
public class ExamenController implements Serializable {

    @EJB
    private ExamenFacade examenFacade;

    private List<Examen> items = null;
    private Examen selected;

    @PostConstruct
    public void init() {
        System.out.println("✅ Bean ExamenController creado");
        loadItems();
    }

    private void loadItems() {
        try {
            items = examenFacade.findAll();
            System.out.println("🔍 Exámenes cargados: " + items.size());
        } catch (Exception e) {
            System.out.println("❌ Error cargando exámenes: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public List<Examen> getItems() {
        if (items == null) {
            loadItems();
        }
        return items;
    }

    public Examen getSelected() {
        return selected;
    }

    public void setSelected(Examen selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
        if (selected.getExamenPK() == null) {
            selected.setExamenPK(new ExamenPK());
        }
        if (selected.getMateria() != null) {
            selected.getExamenPK().setMateriaIdmateria(selected.getMateria().getIdmateria());
        }
        if (selected.getAlumno() != null) {
            selected.getExamenPK().setAlumnoIdalumno(selected.getAlumno().getIdalumno());
        }
    }

    protected void initializeEmbeddableKey() {
        selected.setExamenPK(new ExamenPK());
    }

    public void prepareCreate() {
        selected = new Examen();
        initializeEmbeddableKey();
        System.out.println("✅ Preparando nuevo examen");
    }

    public void prepareEdit() {
        System.out.println("✅ Preparando edición de examen");
    }

    public void prepareView() {
        System.out.println("✅ Preparando vista de examen");
    }

    public void create() {
        System.out.println("🔵 Intentando crear examen...");
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("ExamenCreated"));
        if (!JsfUtil.isValidationFailed()) {
            loadItems();
        }
    }

    public void update() {
        System.out.println("🔵 Intentando actualizar examen...");
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("ExamenUpdated"));
        if (!JsfUtil.isValidationFailed()) {
            loadItems();
        }
    }

    public void destroy() {
        System.out.println("🔵 Intentando eliminar examen...");
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("ExamenDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null;
            loadItems();
        }
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                System.out.println("🟢 Persistiendo acción: " + persistAction);
                System.out.println("📋 Datos del examen:");
                System.out.println("   - Alumno ID: " + (selected.getAlumno() != null ? selected.getAlumno().getIdalumno() : "NULL"));
                System.out.println("   - Materia ID: " + (selected.getMateria() != null ? selected.getMateria().getIdmateria() : "NULL"));
                System.out.println("   - Fecha: " + (selected.getExamenPK() != null ? selected.getExamenPK().getFecha() : "NULL"));
                System.out.println("   - Nota: " + selected.getNota());
                
                if (persistAction == PersistAction.UPDATE) {
                    examenFacade.edit(selected);
                    System.out.println("✅ Examen actualizado");
                } else if (persistAction == PersistAction.CREATE) {
                    examenFacade.create(selected);
                    System.out.println("✅ Examen creado");
                } else if (persistAction == PersistAction.DELETE) {
                    examenFacade.remove(selected);
                    System.out.println("✅ Examen eliminado");
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

    public List<Examen> getItemsAvailableSelectMany() {
        return examenFacade.findAll();
    }

    public List<Examen> getItemsAvailableSelectOne() {
        return examenFacade.findAll();
    }

    public Examen getExamen(ExamenPK id) {
        return examenFacade.find(id);
    }

    @FacesConverter(forClass = Examen.class)
    public static class ExamenControllerConverter implements Converter<Examen> {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        @Override
        public Examen getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ExamenController controller = (ExamenController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "examenController");
            return controller.getExamen(getKey(value));
        }

        ExamenPK getKey(String value) {
            ExamenPK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new ExamenPK();
            key.setAlumnoIdalumno(Integer.parseInt(values[0]));
            key.setMateriaIdmateria(Integer.parseInt(values[1]));
            key.setFecha(java.sql.Date.valueOf(values[2]));
            return key;
        }

        String getStringKey(ExamenPK value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value.getAlumnoIdalumno());
            sb.append(SEPARATOR);
            sb.append(value.getMateriaIdmateria());
            sb.append(SEPARATOR);
            sb.append(value.getFecha());
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Examen object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Examen) {
                Examen o = (Examen) object;
                return getStringKey(o.getExamenPK());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, 
                    "object {0} is of type {1}; expected type: {2}", 
                    new Object[]{object, object.getClass().getName(), Examen.class.getName()});
                return null;
            }
        }
    }
}