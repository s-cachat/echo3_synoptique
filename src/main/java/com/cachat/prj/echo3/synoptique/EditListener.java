package com.cachat.prj.echo3.synoptique;

/**
 * un évènement quand un objet est déplacé / redimensionné
 *
 * @author scachat
 */
public interface EditListener {

    /**
     * indique qu'un objet a été modifié
     *
     * @param source l'objet (non modifié)
     * @param event les modifications
     * @return true si les modifications ne doivent pas être appliquée. Dans ce
     * cas, elles sont soit ignorées, soit modifiées dans cette méthode.
     */
    public boolean edit(SynObject source, SynModifiedEvent event);
}
