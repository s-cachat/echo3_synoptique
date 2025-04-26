package com.cachat.prj.echo3.synoptique;

import java.util.ArrayList;
import java.util.List;

/**
 * un objet sur le syoptique
 *
 * @author scachat
 */
public class SynObject {

    public SynObject() {
    }

    public SynObject(double left, double top, double width, double height, SynView view) {
        this.left = left;
        this.top = top;
        this.width = width;
        this.height = height;
        this.view = view;
    }
    /**
     * notre identifiant unique (pour le synoptique)
     */
    private String uid = SynManager.newUid();
    /**
     * position x
     */
    protected double left;
    /**
     * position y
     */
    protected double top;
    /**
     * largeur
     */
    protected double width;
    /**
     * hauteur
     */
    protected double height;
    /**
     * angle
     */
    protected double angle;
    /**
     * z index (layer)
     */
    protected int ZIndex = 100;
    /**
     * visibilité
     */
    protected boolean visible;
    /**
     * déplaçable (dnd)
     */
    protected boolean movable = false;
    /**
     * redimensionnable
     */
    protected boolean resizeable = false;
    /**
     * clickable (génération de clicEvent
     */
    protected boolean clickable = true;
    /**
     * les listeners pour l'édition
     */
    private List<EditListener> editListeners;
    /**
     * les listeners pour les clics
     */
    private List<ClicListener> clicListeners;
    /**
     * le visuel par défaut
     */
    private SynView view;
    /**
     * le visuel quand la souris passe sur l'objet
     */
    private SynView hoverView;
    /**
     * notre synoptique
     */
    private Synoptique synoptique;

    public double getLeft() {
        return left;
    }

    public void setLeft(double left) {
        this.left = left;
    }

    public double getTop() {
        return top;
    }

    public void setTop(double top) {
        this.top = top;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public int getZIndex() {
        return ZIndex;
    }

    public void setZIndex(int ZIndex) {
        this.ZIndex = ZIndex;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isMovable() {
        return movable;
    }

    public void setMovable(boolean movable) {
        this.movable = movable;
    }

    public boolean isClickable() {
        return clickable;
    }

    public void setClickable(boolean clickable) {
        this.clickable = clickable;
    }

    public boolean isResizeable() {
        return resizeable;
    }

    public void setResizeable(boolean resizeable) {
        this.resizeable = resizeable;
    }

    public SynView getView() {
        return view;
    }

    public void setView(SynView view) {
        this.view = view;
        if (synoptique != null) {
            synoptique.registerNewView(this, view);
        }
    }

    public SynView getHoverView() {
        return hoverView;
    }

    public void setHoverView(SynView hoverView) {
        this.hoverView = hoverView;
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

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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
                this.left = evt.left;
            }
            if (evt.top != null) {
                this.top = evt.top;
            }
            if (evt.angle != null) {
                this.angle = evt.angle;
            }
        }
        if (isResizeable()) {
            this.width = evt.width;
            this.height = evt.height;
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
