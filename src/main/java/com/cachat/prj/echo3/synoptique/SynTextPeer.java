package com.cachat.prj.echo3.synoptique;

import com.cachat.prj.echo3.ng.peers.Extended;
import nextapp.echo.app.Component;
import nextapp.echo.app.util.Context;
import nextapp.echo.webcontainer.AbstractComponentSynchronizePeer;
import nextapp.echo.webcontainer.ServerMessage;
import nextapp.echo.webcontainer.Service;
import nextapp.echo.webcontainer.WebContainerServlet;
import nextapp.echo.webcontainer.service.JavaScriptService;

/**
 *
 * @author scachat
 */
public class SynTextPeer extends SynObjectPeer {

    public SynTextPeer() {
        addOutputProperty(SynText.ALIGNMENT_PROPERTY);
        addOutputProperty(SynText.COLOR_PROPERTY);
        addOutputProperty(SynText.FONT_FAMILY_PROPERTY);
        addOutputProperty(SynText.FONT_SIZE_PROPERTY);
        addOutputProperty(SynText.FONT_STYLE_PROPERTY);
        addOutputProperty(SynText.TEXT_PROPERTY);
    }

    @Override
    public String getClientComponentType(boolean shortType) {
        return "SynText";
    }

    @Override
    public Class getComponentClass() {
        return SynText.class;
    }

    @Override
    public void init(Context context, Component component) {
        super.init(context, component);
    }
}
