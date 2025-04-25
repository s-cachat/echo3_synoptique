package com.cachat.prj.echo3.synoptique;

import com.cachat.prj.echo3.ng.ContainerEx;
import nextapp.echo.app.Component;

/**
 * la base d'une popup
 *
 * @author scachat
 */
public class Popup extends ContainerEx {

    /**
     * le synoptique
     */
    private SynoptiqueWithPopup synoptique;

    public Popup() {
    }

    public Popup(Component c) {
        super(c);
    }

    public Popup(Integer left, Integer top, Integer right, Integer bottom, Component c) {
        super(left, top, right, bottom, c);
    }

    public Popup(Integer left, Integer top, Integer right, Integer bottom, Integer width, Integer height) {
        super(left, top, right, bottom, width, height);
    }

    public Popup(Integer left, Integer top, Integer right, Integer bottom, Integer width, Integer height, Component comp) {
        super(left, top, right, bottom, width, height, comp);
    }

    public SynoptiqueWithPopup getSynoptique() {
        return synoptique;
    }

    public void setSynoptique(SynoptiqueWithPopup synoptique) {
        this.synoptique = synoptique;
    }

    /**
     * ferme la popup
     */
    public void userClose() {
        if (synoptique != null) {
            synoptique.closePopup(this);
        }
    }
}
