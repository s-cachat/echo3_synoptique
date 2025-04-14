package com.cachat.prj.echo3.synoptique;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author scachat
 */
public class SynGroupe {

    private List<SynObject> objects = new ArrayList<>();

    private Synoptique synoptique;

    public Synoptique getSynoptique() {
        return synoptique;
    }

    public int getZIndex() {
        return objects.isEmpty() ? 0 : objects.get(0).getZIndex();
    }

    public void setZIndex(int zIndex) {
        for (int i = 0; i < objects.size(); i++) {
            objects.get(i).setZIndex(zIndex + i);
        }
    }

    /* package protected */
    void setSynoptique(Synoptique synoptique) {
        this.synoptique = synoptique;
    }

    /* package protected */ void addTo(Synoptique synoptique) {
        objects.forEach(a -> synoptique.add(a));
    }

    /* package protected */ void removeFrom(Synoptique synoptique) {
        objects.forEach(a -> synoptique.remove(a));
    }

    /**
     * ajoute un objet au groupe
     *
     * @param o l'objet
     */
    protected void add(SynObject o) {
        objects.add(o);
        if (objects.size() > 1) {
            setZIndex(getZIndex());
        }
        if (synoptique != null) {
            synoptique.add(o);
        }
    }

    /**
     * supprime un objet au groupe
     *
     * @param o l'objet
     */
    protected void remove(SynObject o) {
        objects.remove(o);
        if (synoptique != null) {
            synoptique.remove(o);
        }
    }

}
