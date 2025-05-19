package com.cachat.prj.echo3.synoptique;

import com.google.gson.Gson;

/**
 * une forme
 *
 * @author scachat
 */
public class SynShape extends SynObject {

    public static final String TYPE_PROPERTY = "type";
    public static final String COORD_PROPERTY = "coord";
    public static final String FILL_COLOR_PROPERTY = "fill";
    public static final String STROKE_COLOR_PROPERTY = "stroke";
    public static final String STROKE_WIDTH_PROPERTY = "strokeWidth";

    public SynShape() {
    }

    public SynShape(SynShapeType type, String fillColor, String strokeColor, double left, double top, double... coords) {
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

    public void setStrokeColor(String strokeColor) {
        set(STROKE_COLOR_PROPERTY, strokeColor);
    }

    public String getStrokeColor() {
        return (String) get(STROKE_COLOR_PROPERTY);
    }

    public void setStrokeWidth(int strokeWidth) {
        set(STROKE_WIDTH_PROPERTY, strokeWidth);
    }

    public int getStrokeWidth() {
        return (Integer) get(STROKE_WIDTH_PROPERTY);
    }

    public void setFillColor(String fillColor) {
        set(FILL_COLOR_PROPERTY, fillColor);
    }

    public String getFillColor() {
        return (String) get(FILL_COLOR_PROPERTY);
    }

    public void setCoord(double coords[]) {
        Gson g = new Gson();
        set(COORD_PROPERTY, g.toJson(new Coord(coords)));
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
        Gson g = new Gson();
        return g.fromJson((String) get(COORD_PROPERTY), Coord.class).getValues();
    }

    public static class Coord {

        private double values[];

        public Coord(double[] values) {
            this.values = values;
        }

        public double[] getValues() {
            return values;
        }

        public void setValues(double[] values) {
            this.values = values;
        }

    }

}
