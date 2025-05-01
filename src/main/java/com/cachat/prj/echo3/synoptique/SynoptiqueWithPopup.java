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
        add(synoptique);
        synoptique.setBounds(0, 0, 0, 0, null, null);
    }

    public void add(SynObject obj) {
        synoptique.add(obj);
    }

    public void add(SynObject obj, Supplier<Popup> supplier) {
        synoptique.add(obj);
        popupSuppliers.put(obj.getId(), supplier);
        obj.addListener(this::addPopup);
    }

    private void addPopup(SynObject source, SynClicEvent sce) {
        if (popup != null) {
            remove(popup);
        }
        Supplier<Popup> sup = popupSuppliers.get(source.getId());
        if (sup != null) {
            Popup c = sup.get();
            if (c != null) {
                c.setSynoptique(this);
                popup = c;
                add(c);
                c.setLeft( source.getLeft().intValue());
                c.setTop( source.getTop().intValue());
            }
        }
    }

    public void closePopup() {
        if (popup != null) {
            remove(popup);
            popup = null;
        }
    }

    /*package protected*/ void closePopup(Popup popupToRemove) {
        if (popupToRemove != null) {
            remove(popupToRemove);
            if (popup == popupToRemove) {
                popup = null;
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
