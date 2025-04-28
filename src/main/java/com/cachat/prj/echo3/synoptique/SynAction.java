package com.cachat.prj.echo3.synoptique;

import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.annotation.XmlRootElement;

/**
 * une action a faire sur le synoptique
 *
 * @author scachat
 */
@XmlRootElement
public class SynAction {

    /**
     * objet a ajouter
     */
    private List<SynObject> add;
    /**
     * objet a modifier
     */
    private List<SynObject> update;
    /**
     * objet a supprimer
     */
    private List<String> del;
    /**
     * si true, cet objet contient l'ensemble des données
     */
    private Boolean full;

    public List<SynObject> getAdd() {
        if (add == null) {
            add = new ArrayList<>();
        }
        return add;
    }

    public List<SynObject> getUpdate() {
        if (update == null) {
            update = new ArrayList<>();
        }
        return update;
    }

    public List<String> getDel() {
        if (del == null) {
            del = new ArrayList<>();
        }
        return del;
    }

    public void setAdd(List<SynObject> add) {
        this.add = add;
    }

    public void setUpdate(List<SynObject> update) {
        this.update = update;
    }

    public void setDel(List<String> del) {
        this.del = del;
    }

    public Boolean getFull() {
        return full;
    }

    public void setFull(Boolean full) {
        this.full = full;
    }

}
