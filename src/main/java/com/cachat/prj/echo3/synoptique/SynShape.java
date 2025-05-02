package com.cachat.prj.echo3.synoptique;

/**
 * une forme
 *
 * @author scachat
 */
public class SynShape extends SynObject {

    public static final String TYPE_PROPERTY = "type";
    public static final String COORD_PROPERTY = "coord";
    public static final String FILL_COLOR_PROPERTY = "fillColor";
    public static final String STROKE_COLOR_PROPERTY = "strokeColor";

    public SynShape() {
    }

    public SynShape(SynShapeType type, int fillColor, int strokeColor, double left, double top, double... coords) {
        super();
        setLeft(left);
        setTop(top);
        setType(type);
        setCoord(coords);
        setFillColor(fillColor);
        setStrokeColor(strokeColor);
    }

    public void setType(SynShapeType type) {
        set(TYPE_PROPERTY, type.name());
    }

    public SynShapeType getType() {
        return SynShapeType.valueOf((String) get(TYPE_PROPERTY));
    }

    public void setStrokeColor(int fillColor) {
        set(FILL_COLOR_PROPERTY, fillColor);
    }

    public int getStrokeColor() {
        return (Integer) get(FILL_COLOR_PROPERTY);
    }

    public void setFillColor(int fillColor) {
        set(FILL_COLOR_PROPERTY, fillColor);
    }

    public int getFillColor() {
        return (Integer) get(FILL_COLOR_PROPERTY);
    }

    public void setCoord(double coords[]) {
        set(COORD_PROPERTY, coords);
        SynShapeType type = getType();
        if (type != null) {
            switch (type) {
                case CIRCLE -> {// rayon
                    setWidth(coords[0] * 2);
                    setHeight(coords[0] * 2);
                }
                case RECT -> {// width,height
                    setWidth(coords[0]);
                    setHeight(coords[1]);
                }
            }
        }
    }

    public double[] getCoord() {
        return (double[]) get(COORD_PROPERTY);
    }
}
