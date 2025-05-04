package com.cachat.prj.echo3.synoptique;

/**
 *
 * @author scachat
 */
public class SynClicEvent {

    /**
     * l'identifiant unique de l'objet modifié
     */
    private String uid;
    int mouseX;
    int mouseY;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getMouseX() {
        return mouseX;
    }

    public void setMouseX(int mouseX) {
        this.mouseX = mouseX;
    }

    public int getMouseY() {
        return mouseY;
    }

    public void setMouseY(int mouseY) {
        this.mouseY = mouseY;
    }

    @Override
    public String toString() {
        return "SynClicEvent{" + "uid=" + uid + ", mouseX=" + mouseX + ", mouseY=" + mouseY + '}';
    }

}
