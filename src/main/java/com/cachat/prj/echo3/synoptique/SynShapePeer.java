package com.cachat.prj.echo3.synoptique;

import java.util.Iterator;
import nextapp.echo.app.Component;
import nextapp.echo.app.util.Context;

/**
 *
 * @author scachat
 */
public class SynShapePeer extends SynObjectPeer {

    public SynShapePeer() {
        addOutputProperty(SynShape.COORD_PROPERTY);
        addOutputProperty(SynShape.FILL_COLOR_PROPERTY);
        addOutputProperty(SynShape.STROKE_COLOR_PROPERTY);
        addOutputProperty(SynShape.STROKE_WIDTH_PROPERTY);
    }

    @Override
    public String getClientComponentType(boolean shortType) {
        return "SynShape";
    }

    @Override
    public Class getComponentClass() {
        return SynShape.class;
    }

    @Override
    public void init(Context context, Component component) {
        super.init(context, component);
    }
}
