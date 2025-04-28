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
public class SynObjectPeer extends AbstractComponentSynchronizePeer {

    public SynObjectPeer() {
        addOutputProperty(SynObject.ANGLE_PROPERTY);
        addOutputProperty(SynObject.CLICKABLE_PROPERTY);
        addOutputProperty(SynObject.HEIGHT_PROPERTY);
        addOutputProperty(SynObject.LEFT_PROPERTY);
        addOutputProperty(SynObject.MOVABLE_PROPERTY);
        addOutputProperty(SynObject.RESIZEABLE_PROPERTY);
        addOutputProperty(SynObject.TOP_PROPERTY);
        addOutputProperty(SynObject.WIDTH_PROPERTY);
        addOutputProperty(SynObject.ZINDEX_PROPERTY);
    }

    @Override
    public String getClientComponentType(boolean shortType) {
        // Return client-side component type name.
        return "SynObject";
    }

    @Override
    public Class getComponentClass() {
        // Return server-side Java class.
        return SynObject.class;
    }

    @Override
    public void init(Context context, Component component) {
        super.init(context, component);
    }
}
