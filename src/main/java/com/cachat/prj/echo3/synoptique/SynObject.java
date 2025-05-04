package com.cachat.prj.echo3.synoptique;

import java.util.ArrayList;
import java.util.List;
import nextapp.echo.app.Component;

/**
 * un objet sur le synoptique
 *
 * @author scachat
 */
public class SynObject extends Component {

    public SynObject() {
    }

    public SynObject(double left, double top, double width, double height) {
        setLeft(left);
        setTop(top);
        setWidth(width);
        setHeight(height);
    }
    /**
     * position x
     */
    public static String LEFT_PROPERTY = "left";
    /**
     * position y
     */
    public static String TOP_PROPERTY = "top";
    /**
     * largeur
     */
    public static String WIDTH_PROPERTY = "width";
    /**
     * hauteur
     */
    public static String HEIGHT_PROPERTY = "height";
    /**
     * angle
     */
    public static String ANGLE_PROPERTY = "angle";
    /**
     * z index (layer)
     */
    public static String ZINDEX_PROPERTY = "zindex";
    /**
     * déplaçable (dnd)
     */
    public static String MOVABLE_PROPERTY = "movable";
    /**
     * redimensionnable
     */
    public static String RESIZEABLE_PROPERTY = "resizeable";
    /**
     * clickable (génération de clicEvent)
     */
    public static String CLICKABLE_PROPERTY = "clickable";
    /**
     * les listeners pour l'édition
     */
    private List<EditListener> editListeners;
    /**
     * les listeners pour les clics
     */
    private List<ClicListener> clicListeners;
 
    /**
     * notre synoptique
     */
    protected Synoptique synoptique;

    public Double getLeft() {
        return (Double) get(LEFT_PROPERTY);
    }

    public void setLeft(Double left) {
        set(LEFT_PROPERTY, left);
    }

    public Double getTop() {
        return (Double) get(TOP_PROPERTY);
    }

    public void setTop(Double top) {
        set(TOP_PROPERTY, top);
    }

    public Double getWidth() {
        return (Double) get(WIDTH_PROPERTY);
    }

    public void setWidth(Double width) {
        set(WIDTH_PROPERTY, width);
    }

    public Double getHeight() {
        return (Double) get(HEIGHT_PROPERTY);
    }

    public void setHeight(Double height) {
        set(HEIGHT_PROPERTY, height);
    }

    public Double getAngle() {
        return (Double) get(ANGLE_PROPERTY);
    }

    public void setAngle(Double angle) {
        set(ANGLE_PROPERTY, angle);
    }

    public int getZIndex() {
        return (Integer) get(ZINDEX_PROPERTY);
    }

    public void setZIndex(int ZIndex) {
        set(ZINDEX_PROPERTY, ZIndex);
    }

    public Boolean isMovable() {
        return (Boolean) get(MOVABLE_PROPERTY);
    }

    public void setMovable(Boolean movable) {
        set(MOVABLE_PROPERTY, movable);
    }

    public Boolean isClickable() {
        Boolean b = (Boolean) get(CLICKABLE_PROPERTY);
        return b != null && b;
    }

    public void setClickable(Boolean clickable) {
        set(CLICKABLE_PROPERTY, clickable);
    }

    public Boolean isResizeable() {
        Boolean b = (Boolean) get(RESIZEABLE_PROPERTY);
        return b != null && b;
    }

    public void setResizeable(Boolean resizeable) {
        set(RESIZEABLE_PROPERTY, resizeable);
    }

    /**
     * ajoute un listener pour l'édition
     *
     * @param listener le listener
     */
    public void addListener(EditListener listener) {
        if (editListeners == null) {
            editListeners = new ArrayList<>();
        }
        editListeners.add(listener);
    }

    /**
     * supprime un listener pour l'édition
     *
     * @param listener le listener
     */
    public void removeListener(EditListener listener) {
        if (editListeners != null) {
            editListeners.remove(listener);
        }
    }

    /**
     * ajoute un listener pour les clics
     *
     * @param listener le listener
     */
    public void addListener(ClicListener listener) {
        if (clicListeners == null) {
            clicListeners = new ArrayList<>();
        }
        clicListeners.add(listener);
    }

    /**
     * supprime un listener pour les clics
     *
     * @param listener le listener
     */
    public void removeListener(ClicListener listener) {
        if (clicListeners != null) {
            clicListeners.remove(listener);
        }
    }

    /*package protected */ void setSynoptique(Synoptique synoptique) {
        this.synoptique = synoptique;
    }

    public Synoptique getSynoptique() {
        return synoptique;
    }

    /**
     * a-t-on des listener pour les clic
     *
     * @return true si oui
     */
    public boolean hasClicListener() {
        return clicListeners != null && !clicListeners.isEmpty();
    }

    /**
     * un clic a été reçu
     */
    /*package protected*/ void clic(SynClicEvent sce) {
        if (clicListeners != null) {
            clicListeners.forEach(l -> l.clic(this, sce));
        }
    }

    /**
     * un edit a été reçu
     *
     * @param source l'objet (non modifié)
     * @param evt les modifications
     */
    /*package protected*/ void edit(SynModifiedEvent evt) {
        if (editListeners != null) {
            editListeners.forEach(l -> l.edit(this, evt));
        }
        if (isMovable()) {
            if (evt.left != null) {
                setLeft(evt.left);
            }
            if (evt.top != null) {
                setTop(evt.top);
            }
            if (evt.angle != null) {
                setAngle(evt.angle);
            }
        }
        if (isResizeable()) {
            setWidth(evt.width);
            setHeight(evt.height);
        }
    }

    /**
     * a-t-on des listener pour les edit
     *
     * @return true si oui
     */
    public boolean hasEditListener() {
        return editListeners != null && !editListeners.isEmpty();
    }
}
