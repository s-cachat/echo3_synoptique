package com.cachat.prj.echo3.synoptique.popup;

import com.cachat.prj.echo3.synoptique.popup.Popup;
import com.cachat.prj.echo3.ng.ContainerEx;
import com.cachat.prj.echo3.synoptique.SynClicEvent;
import com.cachat.prj.echo3.synoptique.SynObject;
import com.cachat.prj.echo3.synoptique.SynView;
import com.cachat.prj.echo3.synoptique.Synoptique;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * un synoptique avec une fonction d'affichage de popup quand on clique sur une
 * icone. Le mode editable doit être false si on veut que les clics
 * fonctionnent.
 *
 * @author scachat
 */
public class SynoptiqueWithPopup extends ContainerEx {

    /**
     * la popup active
     */
    private ContainerEx popup = null;
    /**
     * le container a popup
     */
    private ContainerEx popupC = null;
    /**
     * la couche synoptique
     */
    private Synoptique synoptique;
    /**
     * le container a synoptique
     */
    private ContainerEx synopC = null;

    /**
     * les fournisseurs de popup (id vers supplyer)
     */
    private Map<String, Supplier<Popup>> popupSuppliers = new HashMap<>();

    /**
     * Constructeur
     */
    public SynoptiqueWithPopup() {
        initSyn();
    }

    public SynoptiqueWithPopup(Integer left, Integer top, Integer right, Integer bottom, Integer width, Integer height) {
        super(left, top, right, bottom, width, height);
        initSyn();
    }

    /**
     * fin de l'initialisation
     */
    private void initSyn() {
        synoptique = new Synoptique();
        synoptique.setBounds(0, 0, 0, 0, null, null);
        add(synopC = new ContainerEx(0, 0, 0, 0, synoptique));
        popupC = new ContainerEx(0, 0, 0, 0, null, null);
        add(popupC);
        popupC.setVisible(false);
    }

    public void add(SynObject obj) {
        synoptique.add(obj);
    }

    /**
     * ajoute un objet avec un popup. L'objet est automatiquement rendu
     * clickable.
     *
     * @param obj l'objet
     * @param supplier le fournisseur de popup.
     */
    public void add(SynObject obj, Supplier<Popup> supplier) {

        obj.setClickable(true);
        obj.addListener(this::addPopup);
        synoptique.add(obj);
        popupSuppliers.put(obj.getId(), supplier);

    }

    private void addPopup(SynObject source, SynClicEvent sce) {
        if (popup != null) {
            popupC.remove(popup);
        }
        Supplier<Popup> sup = popupSuppliers.get(source.getId());
        if (sup != null) {
            Popup c = sup.get();
            if (c != null) {
                c.setSynoptique(this);
                popup = c;
                popupC.add(c);
                popupC.setVisible(true);
                c.setLeft(source.getLeft().intValue());
                c.setTop(source.getTop().intValue());
            }
        }
    }

    public void closePopup() {
        if (popup != null) {
            popupC.remove(popup);
            popupC.setVisible(false);
            popup = null;
        }
    }

    /*package protected*/ void closePopup(Popup popupToRemove) {
        if (popupToRemove != null) {
            popupC.remove(popupToRemove);
            if (popup == popupToRemove) {
                popup = null;
                popupC.setVisible(false);
            }
        }
    }

    public void update(SynObject obj) {
        synoptique.update(obj);
    }

    public void remove(SynObject obj) {
        synoptique.remove(obj);

        popupSuppliers.remove(obj.getId());
    }

    public SynView getView(String uid) {
        return synoptique.getView(uid);
    }

    public void clear() {
        synoptique.clear();
    }

    public SynObject getObject(String uid) {
        return synoptique.getObject(uid);
    }

}
