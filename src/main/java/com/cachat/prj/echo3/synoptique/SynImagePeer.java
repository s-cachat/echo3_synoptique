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
public class SynImagePeer extends SynObjectPeer {

    public SynImagePeer() {
        addOutputProperty(SynImage.CONTENT_TYPE_PROPERTY);
        addOutputProperty(SynImage.VIEW_ID_PROPERTY);
    }

    @Override
    public String getClientComponentType(boolean shortType) {
        return "SynImage";
    }

    @Override
    public Class getComponentClass() {
        return SynImage.class;
    }

    @Override
    public void init(Context context, Component component) {
        super.init(context, component);
    }
}
