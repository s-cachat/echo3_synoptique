package com.cachat.prj.echo3.synoptique;

import com.cachat.prj.echo3.ng.ContainerEx;
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
     * la couche synoptique
     */
    private Synoptique synoptique;
    /**
     * la couche pour les popups
     */
    private ContainerEx popupCe;
    /**
     * les fournisseurs de popup (id vers supplyer)
     */
    private Map<String, Supplier<ContainerEx>> popupSuppliers = new HashMap<>();

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
        add(synoptique);
        synoptique.setBounds(0, 0, 0, 0, null, null);
        popupCe = new ContainerEx(0, 0, 0, 0, null, null);
        add(popupCe);
        popupCe.setVisible(false);
    }

    public void add(SynGroupe obj) {
        synoptique.add(obj);
    }

    public void remove(SynGroupe obj) {
        synoptique.remove(obj);
    }

    public void add(SynObject obj) {
        synoptique.add(obj);
    }

    public void add(SynObject obj, Supplier<ContainerEx> supplier) {
        synoptique.add(obj);
        popupSuppliers.put(obj.getUid(), supplier);
        obj.addListener(this::addPopup);
    }

    private void addPopup(SynObject source, SynClicEvent sce) {
        if (popup != null) {
            popupCe.remove(popup);
        }
        Supplier<ContainerEx> sup = popupSuppliers.get(source.getUid());
        if (sup != null) {
            ContainerEx c = sup.get();
            if (c != null) {
                popup = c;
                popupCe.add(c);
                c.setLeft((int) source.getLeft());
                c.setTop((int) source.getTop());
                popupCe.setVisible(true);
            }
        }
    }

    public void closePopup() {
        if (popup != null) {
            popupCe.remove(popup);
            popup = null;
            popupCe.setVisible(false);
        }
    }

    /*package protected*/ void closePopup(Popup popupToRemove) {
        if (popupToRemove != null) {
            popupCe.remove(popupToRemove);
            if (popup == popupToRemove) {
                popup = null;
                popupCe.setVisible(false);
            }
        }
    }

    public void update(SynObject obj) {
        synoptique.update(obj);
    }

    public void remove(SynObject obj) {
        synoptique.remove(obj);

        popupSuppliers.remove(obj.getUid());
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
