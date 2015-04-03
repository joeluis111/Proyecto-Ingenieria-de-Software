package paquete2.jsf;

import paquete1.jpa.Historiainventaria;
import paquete2.jsf.util.JsfUtil;
import paquete2.jsf.util.PaginationHelper;
import paquete2.jcontroller.HistoriainventariaFacade;

import java.io.Serializable;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;

@Named("historiainventariaController")
@SessionScoped
public class HistoriainventariaController implements Serializable {

    private Historiainventaria current;
    private DataModel items = null;
    @EJB
    private paquete2.jcontroller.HistoriainventariaFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public HistoriainventariaController() {
    }

    public Historiainventaria getSelected() {
        if (current == null) {
            current = new Historiainventaria();
            current.setHistoriainventariaPK(new paquete1.jpa.HistoriainventariaPK());
            selectedItemIndex = -1;
        }
        return current;
    }

    private HistoriainventariaFacade getFacade() {
        return ejbFacade;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {

                @Override
                public int getItemsCount() {
                    return getFacade().count();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
                }
            };
        }
        return pagination;
    }

    public String prepareList() {
        recreateModel();
        return "List";
    }

    public String prepareView() {
        current = (Historiainventaria) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Historiainventaria();
        current.setHistoriainventariaPK(new paquete1.jpa.HistoriainventariaPK());
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            current.getHistoriainventariaPK().setProyectoID(current.getProyectos().getProyectoID());
            current.getHistoriainventariaPK().setMaterialID(current.getMateriales().getMaterialID());
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("HistoriainventariaCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Historiainventaria) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            current.getHistoriainventariaPK().setProyectoID(current.getProyectos().getProyectoID());
            current.getHistoriainventariaPK().setMaterialID(current.getMateriales().getMaterialID());
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("HistoriainventariaUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Historiainventaria) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "List";
    }

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "View";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "List";
        }
    }

    private void performDestroy() {
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("HistoriainventariaDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        int count = getFacade().count();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            current = getFacade().findRange(new int[]{selectedItemIndex, selectedItemIndex + 1}).get(0);
        }
    }

    public DataModel getItems() {
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
        return items;
    }

    private void recreateModel() {
        items = null;
    }

    private void recreatePagination() {
        pagination = null;
    }

    public String next() {
        getPagination().nextPage();
        recreateModel();
        return "List";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "List";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public Historiainventaria getHistoriainventaria(paquete1.jpa.HistoriainventariaPK id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = Historiainventaria.class)
    public static class HistoriainventariaControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            HistoriainventariaController controller = (HistoriainventariaController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "historiainventariaController");
            return controller.getHistoriainventaria(getKey(value));
        }

        paquete1.jpa.HistoriainventariaPK getKey(String value) {
            paquete1.jpa.HistoriainventariaPK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new paquete1.jpa.HistoriainventariaPK();
            key.setMaterialID(Integer.parseInt(values[0]));
            key.setProyectoID(Integer.parseInt(values[1]));
            key.setFechaUso(java.sql.Date.valueOf(values[2]));
            return key;
        }

        String getStringKey(paquete1.jpa.HistoriainventariaPK value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value.getMaterialID());
            sb.append(SEPARATOR);
            sb.append(value.getProyectoID());
            sb.append(SEPARATOR);
            sb.append(value.getFechaUso());
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Historiainventaria) {
                Historiainventaria o = (Historiainventaria) object;
                return getStringKey(o.getHistoriainventariaPK());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Historiainventaria.class.getName());
            }
        }

    }

}
